/*
 * Name:        Lesson 7: Arrays (ArraysDemo.java)
 * Description: A runnable tour of creating, accessing, traversing, and printing
 *              arrays, array references, and the standard array algorithms.
 * Created by:  Mr Kowalczewski
 * Last edited: 2026-09-23
 */
import java.util.Arrays;

public class ArraysDemo {

    public static void main(String[] args) {
        creatingAnArray();
        accessingAndModifying();
        traversingAnArray();
        printingAnArray();
        arrayReferences();
        standardAlgorithms();
        commonArrayBugs();
    }

    // method to demonstrate initializer lists, new, and default values
    public static void creatingAnArray() {
        System.out.println();
        System.out.println("=== 1. creating an array ===");

        int[] scores = {88, 92, 75};   // initializer list: contents AND length
        System.out.println("scores.length = " + scores.length);

        // new gives every element its default value
        int[] steps = new int[7];
        double[] prices = new double[3];
        boolean[] done = new boolean[4];
        String[] names = new String[5];

        System.out.println("new int[7]:     " + Arrays.toString(steps));
        System.out.println("new double[3]:  " + Arrays.toString(prices));
        System.out.println("new boolean[4]: " + Arrays.toString(done));
        System.out.println("new String[5]:  " + Arrays.toString(names));

        // length is a field (no parentheses), unlike String's length() method
        String word = "hello";
        System.out.println("array length: " + steps.length + ", String length(): " + word.length());
    }

    // method to demonstrate reading and writing elements by index
    public static void accessingAndModifying() {
        System.out.println();
        System.out.println("=== 2. accessing and modifying elements ===");

        int[] scores = {88, 92, 75};
        System.out.println("scores[0] = " + scores[0]);

        scores[2] = 80;
        scores[1] += 5;
        System.out.println("after changes: " + Arrays.toString(scores));

        // the last element is always at length - 1 (no negative indexes in Java)
        System.out.println("last element: " + scores[scores.length - 1]);

        // an out-of-range index crashes; try/catch here just keeps the demo running
        try {
            System.out.println(scores[3]);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("scores[3] -> " + e);
        }
    }

    // method to demonstrate indexed and enhanced for loops
    public static void traversingAnArray() {
        System.out.println();
        System.out.println("=== 3. traversing an array ===");

        int[] scores = {88, 97, 80};

        // indexed for: Python's for i in range(len(scores))
        for (int i = 0; i < scores.length; i++) {
            System.out.println("Score " + (i + 1) + ": " + scores[i]);
        }

        // enhanced for: Python's for score in scores
        for (int score : scores) {
            System.out.print(score + " ");
        }
        System.out.println();

        // the enhanced for variable is a COPY, so this changes nothing
        for (int score : scores) {
            score = 0;
        }
        System.out.println("after setting the loop variable to 0: " + Arrays.toString(scores));

        // the indexed loop CAN change elements
        for (int i = 0; i < scores.length; i++) {
            scores[i] = 0;
        }
        System.out.println("after setting scores[i] to 0:         " + Arrays.toString(scores));

        // the same traversal written as a while loop
        int[] more = {4, 5, 6};
        int i = 0;
        while (i < more.length) {
            System.out.print(more[i] + " ");
            i++;
        }
        System.out.println();
    }

    // method to demonstrate why println(array) doesn't print the contents
    public static void printingAnArray() {
        System.out.println();
        System.out.println("=== 4. printing an array ===");

        int[] scores = {88, 97, 80};
        System.out.println("println(scores):                 " + scores);
        System.out.println("println(Arrays.toString(scores)): " + Arrays.toString(scores));

        // building exact output yourself, e.g. "88, 97, 80" with no trailing comma
        String line = "";
        for (int i = 0; i < scores.length; i++) {
            if (i > 0) {
                line += ", ";
            }
            line += scores[i];
        }
        System.out.println("built with a loop:               " + line);
    }

    // method to demonstrate that array variables hold references
    public static void arrayReferences() {
        System.out.println();
        System.out.println("=== 5. array variables hold references ===");

        int[] a = {1, 2, 3};
        int[] b = a;   // same array, two names
        b[0] = 99;
        System.out.println("a = " + Arrays.toString(a) + ", b = " + Arrays.toString(b));

        // a method receives a copy of the REFERENCE, so it can change the caller's elements
        int[] nums = {1, 2, 3};
        doubleAll(nums);
        System.out.println("after doubleAll(nums): " + Arrays.toString(nums));

        // ...but reassigning the parameter itself does nothing to the caller
        replaceWithZeros(nums);
        System.out.println("after replaceWithZeros(nums): " + Arrays.toString(nums));

        // == compares references, not contents
        int[] c = {1, 2, 3};
        int[] d = {1, 2, 3};
        System.out.println("c == d: " + (c == d));
    }

    // method to demonstrate changing a caller's array through a parameter
    public static void doubleAll(int[] values) {
        for (int i = 0; i < values.length; i++) {
            values[i] *= 2;
        }
    }

    // method to demonstrate that reassigning a parameter only changes the local copy
    public static void replaceWithZeros(int[] values) {
        values = new int[values.length];   // points the LOCAL variable at a new array
    }

    // method to demonstrate the standard array algorithms
    public static void standardAlgorithms() {
        System.out.println();
        System.out.println("=== 6. standard array algorithms ===");

        int[] scores = {72, 91, 45, 91, 88};
        System.out.println("scores: " + Arrays.toString(scores));

        // sum and average (cast before dividing)
        int total = 0;
        for (int s : scores) {
            total += s;
        }
        double average = (double) total / scores.length;
        System.out.println("total = " + total + ", average = " + average);

        // maximum and minimum, tracked by index, starting from the first element
        int maxIndex = 0;
        int minIndex = 0;
        for (int i = 1; i < scores.length; i++) {
            if (scores[i] > scores[maxIndex]) {   // > keeps the FIRST 91 on a tie
                maxIndex = i;
            }
            if (scores[i] < scores[minIndex]) {
                minIndex = i;
            }
        }
        System.out.println("max " + scores[maxIndex] + " at index " + maxIndex
                + ", min " + scores[minIndex] + " at index " + minIndex);

        // count, at least one, all
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
        System.out.println("80+: " + countA + ", any failing: " + anyFailing + ", all passing: " + allPassing);

        // consecutive pairs (stop one early so i + 1 stays in bounds)
        for (int i = 0; i < scores.length - 1; i++) {
            System.out.print((scores[i + 1] - scores[i]) + " ");
        }
        System.out.println("<- changes between neighbours");

        // duplicates: every pair once, inner loop starts at i + 1
        boolean hasDuplicate = false;
        for (int i = 0; i < scores.length; i++) {
            for (int j = i + 1; j < scores.length; j++) {
                if (scores[i] == scores[j]) {
                    hasDuplicate = true;
                }
            }
        }
        System.out.println("has duplicate: " + hasDuplicate);

        // reverse in place: swap toward the middle, stop halfway
        for (int i = 0; i < scores.length / 2; i++) {
            int temp = scores[i];
            scores[i] = scores[scores.length - 1 - i];
            scores[scores.length - 1 - i] = temp;
        }
        System.out.println("reversed:       " + Arrays.toString(scores));

        // rotate left by one
        int first = scores[0];
        for (int i = 0; i < scores.length - 1; i++) {
            scores[i] = scores[i + 1];
        }
        scores[scores.length - 1] = first;
        System.out.println("rotated left:   " + Arrays.toString(scores));

        // rotate right by one: loop BACKWARD so nothing is overwritten before it's copied
        int last = scores[scores.length - 1];
        for (int i = scores.length - 1; i > 0; i--) {
            scores[i] = scores[i - 1];
        }
        scores[0] = last;
        System.out.println("rotated right:  " + Arrays.toString(scores));
    }

    // method to demonstrate common array mistakes
    public static void commonArrayBugs() {
        System.out.println();
        System.out.println("=== 7. common array bugs ===");

        int[] scores = {88, 97, 80};

        // <= runs one index too far
        try {
            for (int i = 0; i <= scores.length; i++) {
                System.out.print(scores[i] + " ");
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("\n<= in the condition -> " + e);
        }

        // scores.length() does not compile -- length is a field on arrays:
        // int n = scores.length();

        // elements of a new String[] are null until assigned
        String[] names = new String[2];
        try {
            System.out.println(names[0].toUpperCase());
        } catch (NullPointerException e) {
            System.out.println("names[0] was never assigned -> NullPointerException");
        }

        // integer division in an average
        int total = 88 + 97 + 80;
        System.out.println("total / length          = " + (total / scores.length));
        System.out.println("(double) total / length = " + ((double) total / scores.length));
    }
}
