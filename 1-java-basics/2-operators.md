# Operators in Java

Operators are **symbols** that perform operations on variables and values.  
Java supports a wide range of operators, grouped by functionality.

## 1. Arithmetic Operators

Used for mathematical calculations.

| Operator | Description  | Example   |
|----------|-------------|-----------|
| `+`      | Addition     | `a + b`   |
| `-`      | Subtraction  | `a - b`   |
| `*`      | Multiplication | `a * b` |
| `/`      | Division     | `a / b`   |
| `%`      | Modulus (remainder) | `a % b` |

### Example

```java
int a = 10, b = 3;
System.out.println(a + b);  // 13
System.out.println(a - b);  // 7
System.out.println(a * b);  // 30
System.out.println(a / b);  // 3 (integer division)
System.out.println(a % b);  // 1
```

> Note: Division between integers discards decimals.

## 2. Unary Operators

Operate on a single operand.

| Operator | Description | Example           |
| -------- | ----------- | ----------------- |
| `+`      | Positive    | `+a`              |
| `-`      | Negation    | `-a`              |
| `++`     | Increment   | `++a`, `a++`      |
| `--`     | Decrement   | `--a`, `a--`      |
| `!`      | Logical NOT | `!true` → `false` |


### Example

```java
int x = 5;
System.out.println(++x); // 6 (pre-increment)
System.out.println(x--); // 6 (post-decrement → prints, then subtracts)
System.out.println(x);   // 5
```

## 3. Relational Operators

Used for comparisons (result is boolean).

| Operator | Meaning          | Example  |
| -------- | ---------------- | -------- |
| `==`     | Equal to         | `a == b` |
| `!=`     | Not equal to     | `a != b` |
| `>`      | Greater than     | `a > b`  |
| `<`      | Less than        | `a < b`  |
| `>=`     | Greater or equal | `a >= b` |
| `<=`     | Less or equal    | `a <= b` |

## 4. Logical Operators

Combine boolean expressions.

| Operator | Meaning                     | Example            |
| -------- | --------------------------- | ------------------ |
| `&&`     | Logical AND                 | `(a > 0 && b > 0)` |
| `\|\|`   | Logical OR                  | `(a > 0 \|\| b > 0)` | 
| `!`      | Logical NOT                 | `!(a > b)`         |

## 5. Bitwise Operators

Operate at the **bit level**.

| Operator | Meaning                        | Example   |
| -------- | ------------------------------ | --------- |
| `&`      | AND                            | `a & b`   |
| `\| `    | OR                             | `a \| b`  |
| `^`      | XOR                            | `a ^ b`   |
| `~`      | NOT                            | `~a`      |
| `<<`     | Left shift                     | `a << 2`  |
| `>>`     | Right shift (sign-propagating) | `a >> 2`  |
| `>>>`    | Unsigned right shift           | `a >>> 2` |

### Example

```java
int a = 5;  // 0101
int b = 3;  // 0011
System.out.println(a & b);  // 1 (0001)
System.out.println(a | b);  // 7 (0111)
System.out.println(a ^ b);  // 6 (0110)
System.out.println(~a);     // -6 (2’s complement)
System.out.println(a << 1); // 10 (1010)
```

## 6. Assignment Operators

Used for assigning values.

| Operator | Meaning             | Example  |
| -------- | ------------------- | -------- |
| `=`      | Simple assignment   | `a = 5`  |
| `+=`     | Add and assign      | `a += 2` |
| `-=`     | Subtract and assign | `a -= 3` |
| `*=`     | Multiply and assign | `a *= 4` |
| `/=`     | Divide and assign   | `a /= 2` |
| `%=`     | Modulo and assign   | `a %= 5` |

## 7. Conditional (Ternary) Operator

Shorthand for `if-else`.

```java
int age = 20;
String result = (age >= 18) ? "Adult" : "Minor";
System.out.println(result); // Adult
```

## 8. `instanceof` Operator

Tests whether an object is an instance of a class or subclass.

```java
String s = "hello";
System.out.println(s instanceof String);   // true
System.out.println(s instanceof Object);   // true
```

## 9. Operator Precedence & Associativity

When multiple operators appear in an expression, precedence determines evaluation order.

### Example

```java
int result = 10 + 2 * 3; // result = 16, because * has higher precedence
```

Simplified precedence order (high → low):

1. Postfix (`expr++`, `expr--`)
2. Unary (`++`, `--`, `!`, `~`)
3. Multiplicative (`*`, `/`, `%`)
4. Additive (`+`, `-`)
5. Shift (`<<`, `>>`, `>>>`)
6. Relational (`<`, `>`, `<=`, `>=`, `instanceof`)
7. Equality (`==`, `!=`)
8. Bitwise AND/OR/XOR (`&`, `^`, `|`)
9. Logical AND/OR (`&&`, `||`)
10. Ternary (`?:`)
11. Assignment (`=`, `+=`, `-=`, …)

## Example Program

```java
public class OperatorsDemo {
    public static void main(String[] args) {
        int a = 10, b = 5;

        // Arithmetic
        System.out.println("a + b = " + (a + b));

        // Relational
        System.out.println("a > b: " + (a > b));

        // Logical
        System.out.println("a > 0 && b > 0: " + (a > 0 && b > 0));

        // Bitwise
        System.out.println("a & b: " + (a & b));

        // Assignment
        a += 3;
        System.out.println("a after += 3: " + a);

        // Ternary
        String res = (a > b) ? "a is bigger" : "b is bigger";
        System.out.println(res);

        // instanceof
        String name = "Imad";
        System.out.println(name instanceof String);
    }
}
```


Good catch ⚡ This is **one of the most common pitfalls in Java**. Let’s break down **all the cases where `==` and `.equals()` behave differently** in Java, depending on **primitives vs reference types**.

## `==` vs `.equals()` in Java

### 1. **Primitives**

* `==` compares **values directly** (bit-level).
* `.equals()` **cannot be used** on primitives (compile error).

```java
int a = 10;
int b = 10;
System.out.println(a == b);     // true
// a.equals(b); ❌ compile error
```

> Always use `==` with primitives.

### 2. **Reference Types (Objects)**

#### (a) Using `==`

* Compares **object references** (memory addresses), not content.
* Returns true only if both variables point to the same object in memory.

#### (b) Using `.equals()`

* By default (`Object.equals()`), behaves the same as `==` (reference equality).
* But many classes **override `.equals()`** to compare **contents**.

### 3. **String**

Strings are special because of the **String pool**.

```java
String s1 = "hello";
String s2 = "hello";
String s3 = new String("hello");

System.out.println(s1 == s2);      // true (same pool object)
System.out.println(s1 == s3);      // false (different object)
System.out.println(s1.equals(s3)); // true (content comparison)
```

> Rule: Use `.equals()` for string content.

### 4. **Wrapper Classes (`Integer`, `Double`, …)**

* Wrappers are **objects**, not primitives.
* `==` checks reference equality.
* `.equals()` checks value equality (overridden).

> **Integer Caching**: values from **-128 to 127** are cached → `==` may look like it works.

```java
Integer a = 100;
Integer b = 100;
Integer c = 200;
Integer d = 200;

System.out.println(a == b);      // true (cached)
System.out.println(c == d);      // false (different objects)
System.out.println(c.equals(d)); // true (value equality)
```

> Always use `.equals()` for wrappers.

### 5. **Custom Classes**

If you don’t override `.equals()`, it behaves like `==` (reference equality).

```java
class Person {
    String name;
    Person(String name) { this.name = name; }
}

Person p1 = new Person("Imad");
Person p2 = new Person("Imad");

System.out.println(p1 == p2);      // false
System.out.println(p1.equals(p2)); // false (no override)
```

If you **override `.equals()`**, you can compare by content:

```java
@Override
public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Person)) return false;
    Person p = (Person) o;
    return name.equals(p.name);
}
```

Now:

```java
System.out.println(p1.equals(p2)); // true
```

### 6. **Arrays**

* `==` compares references.
* `.equals()` in arrays **does not compare contents** (it is not overridden).

```java
int[] arr1 = {1, 2, 3};
int[] arr2 = {1, 2, 3};

System.out.println(arr1 == arr2);       // false
System.out.println(arr1.equals(arr2));  // false
```

> Use `Arrays.equals(arr1, arr2)` for deep element comparison.


### 7. **Collections (`List`, `Set`, `Map`)**

Collections override `.equals()` to compare **contents**.

```java
List<Integer> l1 = Arrays.asList(1, 2, 3);
List<Integer> l2 = Arrays.asList(1, 2, 3);

System.out.println(l1 == l2);      // false
System.out.println(l1.equals(l2)); // true (same elements)
```

For nested structures, use `Objects.deepEquals()` or `Arrays.deepEquals()`.

### Summary Table

| Type                     | `==` behavior                  | `.equals()` behavior                  |
| ------------------------ | ------------------------------ | ------------------------------------- |
| Primitives               | Value comparison               | ❌ Not available                       |
| Objects                  | Reference comparison           | Default: reference, unless overridden |
| String                   | Reference (pool-aware)         | Content comparison                    |
| Wrappers (Integer, etc.) | Reference (with caching quirk) | Value comparison                      |
| Arrays                   | Reference                      | Reference (not overridden)            |
| Collections              | Reference                      | Content comparison                    |
| Custom Classes           | Reference                      | Depends on override                   |