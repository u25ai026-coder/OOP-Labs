// Q2HexadecimalCheck.java

import java.util.Scanner;

// Step 1: Create User Defined Exception
class NotHexException extends Exception {
    NotHexException(String message) {
        super(message);
    }
}

// Step 2: Main Class
class HexadecimalCheck {

    // Method to check hexadecimal
    static void checkHex(String str) throws NotHexException {

        // Using String function length() and charAt()
        for (int i = 0; i < str.length(); i++) {

            char ch = str.charAt(i);

            if (!( (ch >= '0' && ch <= '9') ||
                   (ch >= 'A' && ch <= 'F') ||
                   (ch >= 'a' && ch <= 'f') )) {

                throw new NotHexException("Not a Hexadecimal Number!");
            }
        }
    }

    // Main method
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter a number: ");
        String number = sc.nextLine();   // Stored in String

        try {
            checkHex(number);
            System.out.println("It is a Hexadecimal Number.");

        } catch (NotHexException e) {
            System.out.println("Exception: " + e.getMessage());
        }

        // This will print no matter what
        System.out.println("Ending the program");

        sc.close();
    }
}
