package foms.tools;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 * ScannerCheck is a utility class that provides methods to verify user input
 * from the console.
 * It provides methods to verify integer, double, string, date, and boolean
 * inputs.
 * The ScannerCheck class is used by other classes to verify user input.
 * 
 * @author Kua Li Min
 * @version 1.0
 * @since 2024-04-15
 */

public class ScannerCheck {

    /**
     * The Scanner object used to read user input.
     */
    private static final Scanner sc = new Scanner(System.in);

    /**
     * Gets and verifies the user's selection is within the specified range.
     * 
     * @param lowerBound The lower bound of the range.
     * @param Upperbound The upper bound of the range.
     * @return The user's selection.
     */
    public static int verifySelection(int lowerBound, int Upperbound) {
        int userInput;
        while (true) {
            try {
                userInput = verifyInt();
                if (userInput >= lowerBound && userInput <= Upperbound) {
                    break;
                } else {
                    System.out.println("Please enter a number between " + lowerBound + " and " + Upperbound + ".");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid selection.");
                sc.next(); // read invalid input and try again
            }
        }
        // sc.nextLine(); // read newline character left in the input buffer
        return userInput;
    }

    /**
     * Gets and verifies the user's date input is in the correct format.
     * 
     * @return The user's date input.
     */
    public static LocalDate verifyDate() {
        LocalDate date;
        while (true) {
            try {
                String userInput = sc.nextLine();
                date = LocalDate.parse(userInput, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                break;
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid date in the format dd/MM/yyyy.");
            }
        }
        return date;
    }

    /**
     * Gets and verifies the user's integer input.
     * 
     * @return The user's integer input.
     */
    public static int verifyInt() {
        int userInput;
        while (true) {
            try {
                userInput = sc.nextInt();
                break;
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                sc.next(); // read invalid input and try again
            }

        }
        sc.nextLine(); // read newline character left in the input buffer
        return userInput;
    }

    /**
     * Gets and verifies the user's double input.
     * 
     * @return The user's double input.
     */
    public static double verifyDouble() {
        double userInput;
        while (true) {
            try {
                userInput = sc.nextDouble();
                break;
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid decimal.");
                sc.next(); // read invalid input and try again
            }

        }
        sc.nextLine(); // read newline character left in the input buffer
        return userInput;
    }

    /**
     * Gets and verifies the user's string input.
     * @return The user's string input.
     */
    public static String verifyString() {
        String userInput;
        while (true) {
            try {
                userInput = sc.nextLine();
                break;
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid string.");
                sc.next(); // read invalid input and try again
            }
        }
        return userInput;
    }

    /**
     * Gets and verifies the user's boolean input.
     * 
     * @return The user's boolean input.
     */
    public static boolean verifyBool() {
        boolean userInput;
        while (true) {
            try {
                userInput = sc.nextBoolean();
                break;
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid boolean value.");
                sc.next(); // read invalid input and try again
            }
        }
        sc.nextLine(); // read newline character left in the input buffer
        return userInput;
    }

    /**
     * Closes the Scanner object.
     */
    public static void close() {
        sc.close();
    }

    /**
     * The default constructor for the ScannerCheck class.
     * This constructor initializes the class with default values.
     * 
     * Note: This constructor is provided implicitly by Java when no other constructors are defined explicitly.
     */
    public ScannerCheck() {}
}