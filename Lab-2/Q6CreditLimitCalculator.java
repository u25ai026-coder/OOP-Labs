import java.util.Scanner;

public class Q6CreditLimitCalculator {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        int accountNumber;
        double beginningBalance;
        double totalCharges;
        double totalCredits;
        double creditLimit;

        System.out.print("Enter account number (-1 to quit): ");
        accountNumber = input.nextInt();

        while (accountNumber != -1) {

            System.out.print("Enter beginning balance: ");
            beginningBalance = input.nextDouble();

            System.out.print("Enter total charges: ");
            totalCharges = input.nextDouble();

            System.out.print("Enter total credits: ");
            totalCredits = input.nextDouble();

            System.out.print("Enter credit limit: ");
            creditLimit = input.nextDouble();

            // calculate new balance
            double newBalance = beginningBalance + totalCharges - totalCredits;

            System.out.println("New balance is: " + newBalance);

            // check credit limit
            if (newBalance > creditLimit) {
                System.out.println("Credit limit exceeded!");
            }

            System.out.print("\nEnter account number (-1 to quit): ");
            accountNumber = input.nextInt();
        }

        input.close();
    }
}
