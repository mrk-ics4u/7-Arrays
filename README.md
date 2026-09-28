# ICS 4U0 — Lesson 7: Arrays

## Exercise

You're building a step tracker that reports on a stretch of days.

Everything you need is from Lesson 7:

- **creating an array** with `new` and filling it from input,
- **traversing** it with indexed and enhanced for loops,
- **passing an array to a method**, and
- the **standard algorithms**: sum, average, and maximum.

- Read the number of days, then that many step counts into an `int` array.
- Write a method `sum(int[] values)`: returns the total of every element.
- Days are numbered from 1 in the output, so index `0` is Day 1.
- The best day is the one with the most steps. On a tie, report the earliest day.
- Print the three lines of output exactly as shown below.

---

## Input

The number of days, then the steps for each day, one per line:

| Line | Value | Type |
|------|-------|------|
| 1 | Number of days (2 or more) | whole number |
| 2 onward | Steps for one day | whole number |

Example input:

```
6
8200
6100
10400
10400
5300
9800
```

---

## Output

Exactly three lines:

```
Total steps: <total>
Average: <average to 2 decimals>
Best day: Day <number> (<steps>)
```

For the example input above, your program must print **exactly**:

```
Total steps: 50200
Average: 8366.67
Best day: Day 3 (10400)
```

Every space and bracket is compared. `Best day: Day 3 (10400)` and `Best day: Day 3 ( 10400 )` are not the same answer.

---

## Testing

- Test your code yourself first.
- Open the **Testing** panel from the sidebar (flask icon) and click ▶ **Run Tests**.

A green check next to a test means it passed; a red X means it failed and will show you the expected vs. actual output.
