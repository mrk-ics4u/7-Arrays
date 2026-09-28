/*
 * StepTrackerTest.java -- unit tests for the Lesson 7 exercise
 * ICS 4U0 - Lesson 7 Exercise: Step Tracker
 *
 * These tests run StepTracker.main() exactly as it will be graded: they
 * feed it the input lines it expects on System.in and check the lines it
 * prints to System.out. You don't need to change this file -- just run the
 * tests (see README.md) and fix StepTracker.java until they all pass.
 */
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StepTrackerTest {

    private InputStream originalIn;
    private PrintStream originalOut;
    private Locale originalLocale;

    @BeforeEach
    public void redirectIoAndPinLocale() {
        originalIn = System.in;
        originalOut = System.out;
        originalLocale = Locale.getDefault();
        // printf("%.2f") follows the default locale -- on a machine that uses
        // a comma for decimals, output like "8366,67" would fail the exercise
        // for reasons that have nothing to do with your code, so every test
        // pins the locale before calling main().
        Locale.setDefault(Locale.CANADA);
    }

    @AfterEach
    public void restoreIoAndLocale() {
        System.setIn(originalIn);
        System.setOut(originalOut);
        Locale.setDefault(originalLocale);
    }

    /**
     * Feeds {@code input} to StepTracker.main() on System.in and returns
     * everything it printed to System.out.
     */
    private String run(String input) {
        System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
        ByteArrayOutputStream captured = new ByteArrayOutputStream();
        System.setOut(new PrintStream(captured, true, StandardCharsets.UTF_8));

        StepTracker.main(new String[0]);

        return captured.toString(StandardCharsets.UTF_8);
    }

    /**
     * Compares output line by line, trimming trailing whitespace on each
     * line and ignoring one blank line at the very end (println's final
     * newline). Everything else -- including every space and comma -- is
     * compared exactly.
     */
    private void assertOutputEquals(String expected, String actual) {
        String[] expectedLines = expected.stripTrailing().split("\n", -1);
        String[] actualLines = actual.stripTrailing().split("\n", -1);

        assertEquals(expectedLines.length, actualLines.length,
                "Expected " + expectedLines.length + " line(s) of output, got " + actualLines.length
                        + ".\n--- expected ---\n" + expected + "--- actual ---\n" + actual);

        for (int i = 0; i < expectedLines.length; i++) {
            assertEquals(expectedLines[i].stripTrailing(), actualLines[i].stripTrailing(),
                    "Line " + (i + 1) + " didn't match.\n--- expected ---\n" + expected
                            + "--- actual ---\n" + actual);
        }
    }

    @Test
    public void exampleFromReadme() {
        String input = "6\n8200\n6100\n10400\n10400\n5300\n9800\n";
        String expected = "Total steps: 50200\n"
                + "Average: 8366.67\n"
                + "Best day: Day 3 (10400)\n";
        assertOutputEquals(expected, run(input));
    }

    @Test
    public void averageKeepsDecimalsNotIntegerDivision() {
        // total / days with both as int gives 1000, and 1000.00 would print.
        String input = "2\n1000\n1001\n";
        String expected = "Total steps: 2001\n"
                + "Average: 1000.50\n"
                + "Best day: Day 2 (1001)\n";
        assertOutputEquals(expected, run(input));
    }



    @Test
    public void tiesReportTheEarliestDay() {
        // Using >= instead of > picks the LAST tied day.
        String input = "4\n7000\n3000\n7000\n3000\n";
        String expected = "Total steps: 20000\n"
                + "Average: 5000.00\n"
                + "Best day: Day 1 (7000)\n";
        assertOutputEquals(expected, run(input));
    }
}
