# Control Flow in Java

Control flow statements in Java determine the **order in which instructions are executed**.  
They allow branching, looping, and early termination of code execution.

## 1. Conditional Statements

### `if` Statement

```java
if (condition) {
    // executed if condition is true
}
````

Example:

```java
int x = 10;
if (x > 5) {
    System.out.println("x is greater than 5");
}
```

### `if-else` Statement

```java
if (condition) {
    // true branch
} else {
    // false branch
}
```

Example:

```java
if (x % 2 == 0) {
    System.out.println("Even");
} else {
    System.out.println("Odd");
}
```

### `if-else-if` Ladder

```java
if (condition1) {
    ...
} else if (condition2) {
    ...
} else {
    ...
}
```

Example:

```java
if (x < 0) {
    System.out.println("Negative");
} else if (x == 0) {
    System.out.println("Zero");
} else {
    System.out.println("Positive");
}
```

### `switch` Statement

The `switch` statement is used when a variable is compared against multiple values.

```java
switch (expression) {
    case value1:
        // code
        break;
    case value2:
        // code
        break;
    default:
        // code
}
```

Example:

```java
int day = 3;
switch (day) {
    case 1: System.out.println("Monday"); break;
    case 2: System.out.println("Tuesday"); break;
    case 3: System.out.println("Wednesday"); break;
    default: System.out.println("Other day");
}
```

> Notes:

> * Since **Java 7**, `switch` supports `String`.
> * Since **Java 14**, **switch expressions** return values:

```java
String result = switch (day) {
    case 1 -> "Monday";
    case 2 -> "Tuesday";
    case 3 -> "Wednesday";
    default -> "Other";
};
System.out.println(result);
```

## 2. Looping Statements

### `for` Loop

```java
for (initialization; condition; update) {
    // loop body
}
```

Example:

```java
for (int i = 0; i < 5; i++) {
    System.out.println(i);
}
```

### Enhanced `for` Loop (for-each)

```java
for (type variable : collection) {
    // loop body
}
```

Example:

```java
int[] nums = {1, 2, 3};
for (int n : nums) {
    System.out.println(n);
}
```

### `while` Loop

```java
while (condition) {
    // loop body
}
```

Example:

```java
int i = 0;
while (i < 3) {
    System.out.println(i);
    i++;
}
```

### `do-while` Loop

Executes the body at least once, regardless of condition.

```java
do {
    // loop body
} while (condition);
```

Example:

```java
int j = 0;
do {
    System.out.println(j);
    j++;
} while (j < 3);
```

## 3. Jump Statements

### `break`

* Exits the **innermost loop or switch**.

```java
for (int i = 0; i < 5; i++) {
    if (i == 3) break;
    System.out.println(i);
}
```

### `continue`

* Skips the current iteration and continues with the next.

```java
for (int i = 0; i < 5; i++) {
    if (i == 2) continue;
    System.out.println(i);
}
```

### `return`

* Exits from the current method.

```java
int square(int x) {
    return x * x;
}
```

## 4. Labeled Statements

In Java, loops can have labels, and `break`/`continue` can refer to them.

Example:

```java
outer: for (int i = 0; i < 3; i++) {
    for (int j = 0; j < 3; j++) {
        if (i == j) continue outer; // jumps to next iteration of outer loop
        System.out.println(i + " " + j);
    }
}
```

## 5. Exception-Based Control Flow

Although not recommended for normal logic, exceptions alter control flow when errors occur.

```java
try {
    int res = 10 / 0;
} catch (ArithmeticException e) {
    System.out.println("Division by zero!");
}
```
