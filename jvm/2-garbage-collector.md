# Garbage Collection (GC)

Garbage Collection (GC) in Java is the process of **automatically managing memory** by reclaiming objects that are no longer referenced by a program. This frees developers from manual memory allocation/deallocation (like in C/C++), reduces memory leaks, and prevents dangling pointers.

## How Garbage Collection Works

### Mark and Sweep (Core Algorithm)

1. **Mark Phase**: The GC traverses the object graph starting from *GC roots* (local variables, static variables, active threads) and **marks all reachable objects**.
2. **Sweep Phase**: Unmarked objects are considered unreachable → **collected** (memory reclaimed).
3. **Compaction (optional)**: Some GCs compact memory to avoid fragmentation.

## JVM Memory Model & Generations

Java uses a **generational heap**:

* **Young Generation**
  * New objects allocated here.
  * Minor GC occurs frequently (cheap & fast).

* **Old Generation (Tenured)**
  * Long-lived objects promoted here.
  * Major GC (more expensive).

* **Metaspace** (since Java 8)
  * Stores class metadata.

## Garbage Collectors in Java

### 1. **Serial Garbage Collector**

* **How it works**:
  * Uses a **single thread** for both minor and major collections.
  * Best for **single-threaded applications**, desktop apps or small heaps.

* **Activate**:
  ```sh
  java -XX:+UseSerialGC MyApp
  ```

* **Pros**:
  * Simple, minimal overhead.
  * Good for small applications.

* **Cons**:
  * **Stop-the-world** pauses.
  * Doesn’t scale on multi-core CPUs.

### 2. **Parallel Garbage Collector (Throughput Collector)**

* **How it works**:
  * Multiple threads perform GC in parallel.
  * Focus on **throughput** (maximize application work vs. GC time).

* **Activate**:
  ```sh
  java -XX:+UseParallelGC MyApp
  ```

* **Pros**:
  * Faster collection on multi-core CPUs.
  * Good for batch jobs or backend services.

* **Cons**:
  * Longer pauses than low-latency collectors.
  * Not ideal for interactive/latency-sensitive apps.

### 3. **G1 Garbage Collector (Default since Java 9)**

* **How it works**:
  * Divides heap into **regions** instead of strict generations.
  * Performs **concurrent marking** and **region-based evacuation**.
  * Aims for **predictable pause times**.

* **Activate**:
  ```sh
  java -XX:+UseG1GC MyApp
  ```

* **Pros**:
  * Predictable pause times.
  * Good for large heaps (multi-GB).
  * Less fragmentation.

* **Cons**:
  * More complex tuning.
  * Slightly more CPU overhead than ParallelGC.

### 4. **Z Garbage Collector (ZGC)**

* **How it works**:
  * Low-latency GC designed for **sub-millisecond pause times**.
  * Works concurrently with the application.
  * Supports **huge heaps** (TB scale).

* **Activate**:
  ```sh
  java -XX:+UseZGC MyApp
  ```

* **Pros**:
  * Extremely low pause times.
  * Scales to massive heaps.

* **Cons**:
  * Slightly higher CPU and memory overhead.
  * Available only in newer Java versions (11+, improved in 15+).


## Choosing the Right GC

| GC             | Best For                      | Heap Size  | Latency          | Throughput |
| -- | -- | - | - | - |
| SerialGC       | Small apps, single-threaded   | Smal   | High pauses      | Low        |
| ParallelGC     | Batch jobs, throughput focus  | Meduim | Medium pauses    | High       |
| G1GC (default) | General-purpose, large heaps  | Large  | Low, predictable | High       |
| ZGC            | Ultra-low latency, huge heaps | Large  | Very low         | Medium     |


## GC Tuning Examples

You can tune the Garbage Collector with **JVM flags** to balance throughput, latency, and memory usage.
Always test with **your application’s workload** before applying in production.

### Common JVM Options

```sh
# Set maximum heap size (important for GC behavior)
- Xmx4g

# Set initial heap size
- Xms4g

# Set size of young generation
- Xmn512m
```

### G1GC Tuning

```sh
# Enable G1GC
-XX:+UseG1GC

# Target maximum GC pause time (in ms)
-XX:MaxGCPauseMillis=200

# Percentage of heap for young generation (default is adaptive)
-XX:NewRatio=2
-XX:SurvivorRatio=8

# Initiating Heap Occupancy Percent (when to start concurrent GC cycle)
-XX:InitiatingHeapOccupancyPercent=45
```

### Parallel GC Tuning

```sh
# Enable ParallelGC
-XX:+UseParallelGC

# Number of parallel GC threads (default = # of CPUs)
-XX:ParallelGCThreads=8

# Focus on throughput (higher ratio means less GC time)
-XX:GCTimeRatio=9  # means 1/10th time in GC max

# Maximum pause time (not guaranteed, GC tries to honor)
-XX:MaxGCPauseMillis=500
```

### ZGC Tuning

```sh
# Enable ZGC
-XX:+UseZGC

# Soft max heap (ZGC adjusts automatically)
-XX:SoftMaxHeapSize=8g

# GC logging for analysis
-Xlog:gc*
```

> we can request the execution of the gc with `System.gc()`. This will not garantee that the GC will be executed, it's up to the JVM to decide.

## Monitoring & Analyzing GC

### General Monitoring Flags

```sh
# GC logs with details
-Xlog:gc*:gc.log:time,uptime,level,tags

# Print GC details (Java 8 and earlier)
-verbose:gc
-XX:+PrintGCDetails
-XX:+PrintGCDateStamps
-XX:+PrintHeapAtGC
```

### Tools

* **jconsole**: GUI for monitoring memory & GC.
* **jvisualvm**: Profiling and GC monitoring.
* **jstat**: Command-line monitoring `jstat -gc <pid> 1s`.
* **Flight Recorder / Mission Control**: Advanced performance analysis.


## Pros & Cons of GC in General

**Pros**:

* No manual memory management.
* Avoids dangling pointers & leaks.
* Easier development.

**Cons**:

* CPU overhead.
* No precise control over execution time.
* "Stop-the-world" pauses can affect performance.

## Tips & Best Practices

* Use GC logs to understand behavior before tuning.
* Benchmark with **your workload** before choosing a GC.
* For latency-sensitive systems → prefer G1 or ZGC.
* For batch jobs → ParallelGC is usually best.



## References

* [Baeldung  JVM Garbage Collectors](https://www.baeldung.com/jvm-garbage-collectors)
* [Meritis  Fonctionnement du Garbage Collector en Java](https://meritis.fr/blog/fonctionnement-du-garbage-collector-en-java/)
* [YouTube  Garbage Collection in Java](https://www.youtube.com/watch?v=L68zxvl2LPY)
