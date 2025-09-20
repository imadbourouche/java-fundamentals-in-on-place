# Java Compilers 

# Table of Contents

1. [Java Compilers](#java-compilers)  

2. [Just-In-Time (JIT) Compiler](#just-in-time-jit-compiler)  
   1. [Multi-Tiered Execution in the JVM](#multi-tiered-execution-in-the-jvm)  
      - [Level 0: The Interpreter](#level-0-the-interpreter)  
      - [Levels 1–3: C1 Compiler (Client Compiler)](#levels-13-c1-compiler-client-compiler)  
      - [Level 4: C2 Compiler (Server Compiler)](#level-4-c2-compiler-server-compiler)  
   2. [Key JIT Optimizations in Detail](#key-jit-optimizations-in-detail)  
      - [Method Inlining](#1-method-inlining)  
      - [Escape Analysis](#2-escape-analysis)  
      - [Loop Unrolling](#3-loop-unrolling)  
      - [Dead Code Elimination](#4-dead-code-elimination)  
      - [Monomorphic Dispatch](#5-monomorphic-dispatch)  
      - [Deoptimization](#6-deoptimization)  
      - [Intrinsics](#7-intrinsics)  
   3. [The Code Cache](#the-code-cache)  
   4. [Tuning and Observing JIT Behavior](#tuning-and-observing-jit-behavior)  
      - [Basic Monitoring](#basic-monitoring)  
      - [Controlling Compilation](#controlling-compilation)  
      - [Controlling Code Cache](#controlling-code-cache)  
   5. [Why JIT Improves Performance](#why-jit-improves-performance)  

3. [Ahead-of-Time (AOT) Compilation in Java](#ahead-of-time-aot-compilation-in-java)  
   1. [AOT in the JVM](#aot-in-the-jvm)  
      - [Java 9+ Experimental AOT (`jaotc`)](#1-java-9-experimental-aot-jaotc)  
      - [GraalVM Native Image](#2-graalvm-native-image)  
   2. [Pros & Cons of AOT](#pros--cons-of-aot)  
   3. [When to Use AOT?](#when-to-use-aot)  

4. [JIT vs AOT (Ahead-of-Time Compilation)](#jit-vs-aot-ahead-of-time-compilation)  

5. [References](#references)  


# Just-In-Time (JIT) Compiler

The **Just-In-Time (JIT) compiler** is a key part of the Java Virtual Machine (JVM) that improves the performance of Java applications by **compiling bytecode into native machine code at runtime**.

Instead of interpreting every bytecode instruction, the JIT selectively compiles *hot methods* (frequently executed methods) into optimized machine code, making Java programs run close to the speed of native C/C++ programs.

## Multi-Tiered Execution in the JVM

The JVM uses a **multi-tiered compilation strategy** for balancing fast startup and long-term performance.

### **Level 0: The Interpreter**

* All code starts in the **bytecode interpreter**.
* Executes instructions one by one.
* Slow, but allows immediate startup without waiting for compilation.

### **Levels 1–3: C1 Compiler (Client Compiler)**

* Compiles methods after a threshold of invocations (≈ 200–2000).
* Provides *lightweight optimizations* while still collecting profiling data for later use by C2.

#### Optimizations include:

* **Level 1**: Minimal compilation (fast, low overhead).
* **Level 2**: Adds lightweight profiling.
* **Level 3**: Full profiling mode, gathering runtime statistics for C2.

### **Level 4: C2 Compiler (Server Compiler)**

* Aggressively optimizes *hot* methods based on profiling data.
* Focuses on **long-term performance**, at the cost of longer compilation times.

## Key JIT Optimizations in Detail

The JIT compiler applies a wide set of optimizations to compiled methods. These optimizations are **profile-guided**, meaning they rely on runtime information collected by the interpreter and lower-tier compilers (C1) before being aggressively applied in C2.


### **1. Method Inlining**

* **What it does**: Replaces a method call with the actual method body.
* **Why**: Reduces overhead of method invocation (stack frames, parameter passing), and exposes opportunities for further optimizations (constant folding, loop optimizations).
* **When applied**:

  * Small methods are preferred (e.g., getters/setters, arithmetic).
  * The JIT uses heuristics based on method size, call frequency, and hotness.

#### Example (before inlining)

```java
int add(int a, int b) {
    return a + b;
}

int compute(int x) {
    return add(x, 5);  // call overhead
}
```

#### After inlining (optimized machine code)

```java
int compute(int x) {
    return x + 5; // no method call
}
```

*Inlining often enables **constant folding** and **dead code elimination***.


### **2. Escape Analysis**

* **What it does**: Determines whether an object can be accessed outside the current scope (does it “escape” the method or thread?).
* **Why**: If an object does **not escape**, the JVM can:

  * Allocate it on the **stack** instead of heap (faster allocation, no GC overhead).
  * Perform **scalar replacement** (replace object with its fields).

#### Example

```java
class Point {
    int x, y;
}

void move() {
    Point p = new Point();  // does not escape
    p.x = 10;
    p.y = 20;
}
```

 With escape analysis, `p` can be allocated **on the stack** or even optimized away (fields stored in registers).


### **3. Loop Unrolling**

* **What it does**: Expands loop iterations into repeated code blocks to reduce branch instructions and improve instruction-level parallelism.
* **Why**: Minimizes loop overhead and improves CPU pipelining efficiency.

#### Example (before unrolling)

```java
for (int i = 0; i < 4; i++) {
    sum += arr[i];
}
```

#### After unrolling

```java
sum += arr[0];
sum += arr[1];
sum += arr[2];
sum += arr[3];
```

Useful for small, fixed-bound loops. The JIT uses heuristics to balance code size vs. performance.


### **4. Dead Code Elimination**

* **What it does**: Removes code that **does not affect program results**.
* **Why**: Saves CPU cycles and reduces memory operations.

#### Example

```java
int foo() {
    int x = 10;
    int y = 20;
    return x;   // y is unused → eliminated
}
```

This also applies after constant folding (e.g., eliminating branches like `if (false)`).


### **5. Monomorphic Dispatch**

* **What it does**: Optimizes **virtual method calls** (dynamic dispatch).
* **Why**: Virtual calls normally require a vtable lookup (indirection). If profiling shows only **one target type** is used, the JIT generates a **direct call**.

#### Example

```java
interface Shape { int area(); }

class Square implements Shape {
    public int area() { return side * side; }
}

Shape s = new Square();
s.area(); // Monomorphic call → optimized direct call
```

* If multiple implementations are observed (polymorphic), the JIT may use **inline caches** or speculative optimizations.


### **6. Deoptimization**

* **What it does**: Reverts compiled machine code back to interpreted mode when runtime assumptions are invalidated.
* **Why**: Allows **speculative optimizations** (e.g., assuming only one type in polymorphic call). If the assumption breaks, the JVM **deoptimizes** and recompiles with safer code.

#### Example

```java
Shape s = new Square();
s.area(); // Optimized as direct call

s = new Circle(); // Assumption broken → deopt
s.area(); // Back to generic virtual call, may recompile
```

This makes JIT optimizations **safe but aggressive**.


### **7. Intrinsics**

* **What it does**: Replaces critical methods with **hand-written assembly** (platform-specific).
* **Why**: Achieves performance that bytecode/JIT alone cannot reach.

#### Examples

* `System.arraycopy()` → replaced with native memory copy instructions.
* `Math.sin()`, `Math.cos()`, `Math.sqrt()` → mapped to CPU math instructions (x87, SSE, AVX).
* `String.equals()` → optimized using word-wise comparisons instead of byte-by-byte.


## The Code Cache

* Compiled native methods are stored in the **code cache**.
* Can be monitored or resized with flags: `java -XX:+PrintCodeCache -XX:ReservedCodeCacheSize=256m MyApp`

## Tuning and Observing JIT Behavior

### Basic Monitoring

```bash
# Print compilation events
java -XX:+PrintCompilation MyApp

# Detailed logs (needs HotSpot diagnostic flags)
java -XX:+UnlockDiagnosticVMOptions -XX:+LogCompilation MyApp
```

### Controlling Compilation

```bash
# Disable tiered compilation (use only C2) - not recommended
java -XX:-TieredCompilation MyApp

# Change method compilation threshold
java -XX:CompileThreshold=1000 MyApp
```

### Controlling Code Cache

```bash
# Increase reserved code cache size
java -XX:ReservedCodeCacheSize=512m MyApp
```

## Why JIT Improves Performance

1. **Profile-Guided Optimization**: JIT adapts to runtime behavior (something ahead-of-time compilers can’t do).
2. **Platform-Specific Code**: Generates machine code tailored to the host CPU.
3. **Adaptive Optimization**: Can recompile methods at higher levels as they get hotter.
4. **Speculative Optimization**: Makes assumptions (e.g., method always has one type) and deoptimizes if needed.












# Ahead-of-Time (AOT) Compilation in Java

The **Ahead-of-Time (AOT) compiler** allows Java bytecode to be compiled into **native machine code before the program runs**. Unlike the JIT compiler, which compiles methods *at runtime* based on profiling data, AOT compilation happens **ahead of execution** (during build or startup).

* **Definition**: A process where the JVM converts bytecode (`.class` / `.jar`) into native code before program execution.
* **Goal**: Improve **startup time** by avoiding JIT warm-up and interpretation.
* **Trade-off**: Less adaptive and usually less optimized than JIT, since it cannot rely on runtime profiling.

## AOT in the JVM

### 1. **Java 9+ Experimental AOT (`jaotc`)**

* Introduced in **JDK 9** as an experimental feature.
* Uses **Graal compiler** to compile bytecode into `.so` (shared library).
* Can be loaded by the JVM at runtime to skip interpretation.

#### Example

```bash
# Compile class to native code
jaotc --output libHello.so Hello.class

# Run with AOT library
java -XX:AOTLibrary=./libHello.so Hello
```


### 2. **GraalVM Native Image**

* GraalVM provides a **native-image tool** to compile an entire Java app into a single native binary.
* No JVM needed at runtime — includes application + runtime libraries.
* Ideal for **microservices, serverless functions, and CLI tools**.

#### Example

```bash
# Compile to native binary
native-image -jar myapp.jar

# Run binary directly
./myapp
```

- Startup is almost instantaneous, with lower memory footprint.
- However, reflection, dynamic class loading, and proxies require special configuration.


## Pros & Cons of AOT

**Pros:**

* 🚀 **Faster startup** (no interpreter/JIT warm-up).
* 📉 **Lower memory usage** (no JIT compiler overhead, smaller footprint).
* 🔒 **Predictable performance** (no JIT pauses or deoptimizations).
* ✅ Useful for **short-lived apps** (serverless, command-line tools).

**Cons:**

* ⚡ **Lower peak performance** (no profile-guided optimizations).
* 🛠️ **Harder to support reflection & dynamic features**.
* 📦 Larger binaries (native executables).
* 🌍 Platform-dependent binaries (compile separately for each OS/arch).


## When to Use AOT?

* **Good fit**:

  * Short-lived apps (e.g., CLI tools, serverless functions).
  * Microservices where startup latency matters.
  * Memory-constrained environments (IoT, containers).

* **Better stick with JIT**:

  * Long-running backend servers where **peak performance** matters more than startup time.
  * Workloads with dynamic behavior (benefit from adaptive optimizations).



# JIT vs AOT (Ahead-of-Time Compilation)

| Feature          | JIT Compiler               | AOT Compiler (`jaotc`)     |
|- |-- |-- |
| Compilation Time | At runtime                 | Before execution           |
| Startup Time     | Slower (interpreter + JIT) | Faster                     |
| Peak Performance | Very high (adaptive)       | Good, but not adaptive     |
| Adaptability     | Adapts to real workload    | Fixed, no runtime feedback |

# References

* [Medium – The Java JIT Compiler Optimization](https://medium.com/@sohail_saifi/the-java-jit-compiler-optimization-that-rewrites-your-code-while-it-runs-0b1584927900)
* [OpenJDK – HotSpot Compiler](https://openjdk.org/groups/hotspot/docs/HotSpotCompilationOverview.html)
* [Baeldung – JIT in Java](https://www.baeldung.com/jvm-jit-compiler)
