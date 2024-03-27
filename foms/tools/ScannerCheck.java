package foms.tools;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

// Li Min

public class ScannerCheck {

    private static final Scanner sc = new Scanner(System.in);

    public static int verifySelection(int lowerBound, int Upperbound) {
        int userInput;
        while (true) {
            try {
                userInput = sc.nextInt();
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
        sc.nextLine(); // read newline character left in the input buffer
        return userInput;
    }

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

    public static void close() {
        sc.close();
    }
}