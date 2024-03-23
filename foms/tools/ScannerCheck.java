package foms.tools;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

// Li Min

public class ScannerCheck {

    private static final Scanner sc = new Scanner(System.in);

    public static int verifySelection(int lowestChoiceIdx, int highestChoiceIdx) {
        int response;
        while (true) {
            try {
                response = sc.nextInt();
                break;
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                sc.next(); // read invalid input and try again
            }
        }
        sc.nextLine(); // read newline character left in the input buffer
        return response;
    }

    public static LocalDate verifyDate() {
        // ... method implementation ...
        return LocalDate.now();
    }

    public static int verifyInt() {
        int response;
        while (true) {
            try {
                response = sc.nextInt();
                break;
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                sc.next(); // read invalid input and try again
            }
        }
        sc.nextLine(); // read newline character left in the input buffer
        return response;
    }

    public static String verifyString() {
        // ... method implementation ...
        return "";
    }

    public static boolean verifyBool() {
        // ... method implementation ...

        return false;
    }

    public static void close() {
        sc.close();
    }
}