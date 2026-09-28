# Lesson 7 - Arrays

Python has one list type that grows, shrinks, and holds anything. Java splits that job in two. An **array** has a fixed length and holds a single type. An **ArrayList** (Lesson 8) grows and shrinks like a Python list. Arrays come first because they're simpler and they're everywhere in Java, including the `String[] args` in every `main` you've written since Lesson 1.

---

## 1. Creating an Array

```python
# Python
scores = [88, 92, 75]
```

```java
// Java
int[] scores = {88, 92, 75};
```

`int[]` is the type, read as "array of int". The braces are an **initializer list**: they set both the contents and the length.

When you know how many elements you need but not their values yet, create the array with `new`:

```java
int[] steps = new int[7];          // 0, 0, 0, 0, 0, 0, 0
double[] prices = new double[3];   // 0.0, 0.0, 0.0
boolean[] done = new boolean[4];   // false, false, false, false
String[] names = new String[5];    // null, null, null, null, null
```

Every element starts at a **default value**: `0` for `int`, `0.0` for `double`, `false` for `boolean`, and `null` for any object type such as `String`. `null` means "no object here yet". Calling a method on it (`names[0].length()`) crashes with a `NullPointerException`.

The length is set when the array is created and never changes. There is no `append`. `scores.length` gives the length, with no parentheses, because it's a field and not a method (compare `String`'s `length()`).

---

## 2. Accessing and Modifying Elements

Square brackets and zero-based indexes, same as Python:

```java
int[] scores = {88, 92, 75};
System.out.println(scores[0]);   // 88
scores[2] = 80;                  // {88, 92, 80}
scores[1] += 5;                  // {88, 97, 80}
```

Valid indexes run from `0` to `scores.length - 1`. There are no negative indexes: `scores[-1]` is not the last element, it's a crash. Any index outside the valid range throws `ArrayIndexOutOfBoundsException`.

```java
System.out.println(scores[scores.length - 1]);   // 80, the last element
System.out.println(scores[3]);                   // ArrayIndexOutOfBoundsException
```

---

## 3. Traversing an Array

The indexed `for` loop is Java's `for i in range(len(scores))`:

```java
for (int i = 0; i < scores.length; i++) {
    System.out.println("Score " + (i + 1) + ": " + scores[i]);
}
```

The **enhanced for loop** is Java's `for score in scores`. Read the colon as "in":

```java
for (int score : scores) {
    System.out.println(score);
}
```

The enhanced for loop variable gets a **copy** of each element, so assigning to it doesn't change the array:

```java
for (int score : scores) {
    score = 0;   // changes the copy only
}
// scores is still {88, 97, 80}
```

Use the enhanced for loop when you only need the values. Use the indexed loop when you need the position, need to change elements, or need a neighbour like `scores[i + 1]`. Any enhanced for loop can be rewritten as an indexed `for` or a `while` loop.

---

## 4. Printing an Array

```java
System.out.println(scores);                    // [I@1b6d3586  (not the contents)
System.out.println(Arrays.toString(scores));   // [88, 97, 80]
```

Printing an array variable directly prints a type code and a memory address. `Arrays.toString` needs `import java.util.Arrays;` at the top of the file. It's for debugging; when output has to match a format exactly, build it yourself with a loop.

---

## 5. Array Variables Hold References

An array is an object. The variable doesn't hold the elements, it holds a **reference**: the location of the array in memory.

```java
int[] a = {1, 2, 3};
int[] b = a;              // copies the reference, not the array
b[0] = 99;
System.out.println(a[0]); // 99, because a and b are the same array
```

Python lists behave the same way (`b = a` shares one list in Python too).

This matters when you pass an array to a method. Lesson 6 said Java always passes a copy, and it still does, but the copy is of the reference. The parameter points at the caller's array, so changing elements inside the method changes the caller's array:

```java
public static void doubleAll(int[] values) {
    for (int i = 0; i < values.length; i++) {
        values[i] *= 2;
    }
}
```

```java
int[] nums = {1, 2, 3};
doubleAll(nums);
System.out.println(Arrays.toString(nums));   // [2, 4, 6]
```

Reassigning the parameter itself (`values = new int[5];`) still has no effect on the caller. That only changes the method's own copy of the reference.

The same logic explains why `==` on two arrays compares references, not contents. Two separate arrays holding `{1, 2, 3}` are not `==`.

---

## 6. Standard Array Algorithms

The array algorithms below are something that will appear on the AP exam, but usually these are steps you can logically come up with yourself:

**Sum and average.** Cast before dividing or integer division drops the decimals:

```java
int total = 0;
for (int s : scores) {
    total += s;
}
double average = (double) total / scores.length;
```

**Minimum and maximum.** Start from the first element, not from `0`. Starting a minimum at `0` gives `0` for any array of positive numbers. Tracking the index gives you both the position and the value:

```java
int maxIndex = 0;
for (int i = 1; i < scores.length; i++) {
    if (scores[i] > scores[maxIndex]) {
        maxIndex = i;
    }
}
System.out.println("Highest: " + scores[maxIndex] + " at index " + maxIndex);
```

Using `>` keeps the first occurrence when there's a tie. `>=` keeps the last.

**Count, at least one, all.** Same loop, different bookkeeping:

```java
int countA = 0;
boolean anyFailing = false;
boolean allPassing = true;
for (int s : scores) {
    if (s >= 80) {
        countA++;
    }
    if (s < 50) {
        anyFailing = true;
        allPassing = false;
    }
}
```

"All" starts `true` and looks for one counterexample. "At least one" starts `false` and looks for one example.

**Consecutive pairs.** Stop one early so `i + 1` stays in bounds:

```java
for (int i = 0; i < scores.length - 1; i++) {
    int change = scores[i + 1] - scores[i];
    System.out.println("Change: " + change);
}
```

**Duplicates.** Compare every pair once. The inner loop starts at `i + 1`:

```java
boolean hasDuplicate = false;
for (int i = 0; i < scores.length; i++) {
    for (int j = i + 1; j < scores.length; j++) {
        if (scores[i] == scores[j]) {
            hasDuplicate = true;
        }
    }
}
```

**Reverse in place.** Swap from both ends toward the middle, stopping at the halfway point (going all the way swaps everything back):

```java
for (int i = 0; i < scores.length / 2; i++) {
    int temp = scores[i];
    scores[i] = scores[scores.length - 1 - i];
    scores[scores.length - 1 - i] = temp;
}
```

**Rotate left by one.** Save the first element, shift everything left, put the saved one at the end:

```java
int first = scores[0];
for (int i = 0; i < scores.length - 1; i++) {
    scores[i] = scores[i + 1];
}
scores[scores.length - 1] = first;
```

Shifting right works the same way but has to loop backward, or each element overwrites the one it needs next.

---

## 7. Common Array Bugs

**`<=` in the loop condition.** `i <= scores.length` runs one index too far and throws `ArrayIndexOutOfBoundsException` on the last pass.

**`length()` or `size()` instead of `length`.** Arrays have a `length` field. `scores.length()` doesn't compile.

**Using an element that was never assigned.** `new String[5]` gives five `null`s. `names[0].toUpperCase()` throws `NullPointerException` until something is stored there.

**Printing the array variable.** `println(scores)` prints an address. Loop, or use `Arrays.toString` while debugging.

**Integer division in an average.** `total / scores.length` is `int / int`. Cast one side to `double` first.

---

## Try It Yourself

Compile and run the companion file in this folder:

```bash
javac ArraysDemo.java
java ArraysDemo
```

Then work on the exercise in `StepTracker.java`.

---

## Course Expectations

### ICS 4U

Nothing is assessed directly from this lesson. Declaring, initializing, modifying, and traversing one-dimensional arrays, and the count/total/highest/lowest algorithms, were assessed in ICS3U (A1.5, A1.6, A2.3); this lesson is the Java syntax for those skills. It is the foundation for ICS4U A3.2 (linear and binary search in an array), A3.3 (subprograms that insert and delete array elements), A3.4 (sorting an array), and A1.5 (arrays of objects, once you write your own classes), Strand A: Programming Concepts and Skills.

### AP Expectations

The following are expectations of the AP exam and will show up on the final exam:

- An array stores multiple values of the same type, either primitive values or object references (Topic 4.3, Array Creation and Access, 4.3.A.1)
- An array's length is set when it's created and can't change; it's accessed through the `length` attribute (Topic 4.3, 4.3.A.2)
- Arrays created with `new` have every element set to the default value for its type: `0`, `0.0`, `false`, or `null` (Topic 4.3, 4.3.A.3)
- Initializer lists can be used to create and initialize arrays (Topic 4.3, 4.3.A.4)
- Square brackets `[]` access and modify an element by index (Topic 4.3, 4.3.A.5)
- Valid indexes run from `0` to `length - 1`; any other index throws `ArrayIndexOutOfBoundsException` (Topic 4.3, 4.3.A.6)
- Traversing an array uses repetition to access all elements or an ordered sequence of them; indexed `for` and `while` loops access elements by index (Topic 4.4, Array Traversals, 4.4.A.1, 4.4.A.2)
- The enhanced for loop variable is assigned a copy of each element, and assigning a new value to it doesn't change the array (Topic 4.4, 4.4.A.3, 4.4.A.4)
- An enhanced for loop can be rewritten as an indexed `for` loop or a `while` loop (Topic 4.4, 4.4.A.6)
- Standard array algorithms: minimum or maximum, sum or average, at least one / all / count of elements with a property, consecutive pairs, presence of duplicates, shifting or rotating, and reversing (Topic 4.5, Implementing Array Algorithms, 4.5.A.1)

Arrays of objects, including modifying objects through the enhanced for loop variable (4.4.A.5), come back once you've written your own classes.
