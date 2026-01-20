import java.util.Scanner;

public class Q7ParkingCharges {

    // method to calculate parking charges
    public static double calculateCharges(double hours) {

        double charge = 2.0; // minimum fee for up to 3 hours

        if (hours > 3) {
            charge = 2.0 + (hours - 3) * 0.5;
        }

        if (charge > 10.0) {
            charge = 10.0; // maximum charge
        }

        return charge;
    }

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        double hours;
        double totalReceipts = 0.0;

        System.out.print("Enter hours parked (-1 to quit): ");
        hours = input.nextDouble();

        while (hours != -1) {

            double charge = calculateCharges(hours);
            System.out.println("Parking charge: $" + charge);

            totalReceipts += charge;
            System.out.println("Total receipts so far: $" + totalReceipts);

            System.out.print("\nEnter hours parked (-1 to quit): ");
            hours = input.nextDouble();
        }

        input.close();
    }
}
