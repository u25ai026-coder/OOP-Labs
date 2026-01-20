import java.util.Scanner;

public class Q2Invoice {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter part number: ");
        String partNumber = sc.nextLine();

        System.out.print("Enter part description: ");
        String description = sc.nextLine();

        System.out.print("Enter quantity: ");
        int quantity = sc.nextInt();

        System.out.print("Enter price per item: ");
        double pricePerItem = sc.nextDouble();

        if (quantity < 0) {
            quantity = 0;
        }

        if (pricePerItem < 0) {
            pricePerItem = 0.0;
        }

        double invoiceAmount = quantity * pricePerItem;

        System.out.println("\n----- INVOICE DETAILS -----");
        System.out.println("Part Number: " + partNumber);
        System.out.println("Description: " + description);
        System.out.println("Quantity: " + quantity);
        System.out.println("Price per Item: rupees " + pricePerItem);
        System.out.println("Total Invoice Amount: rupees " + invoiceAmount);

        sc.close();
    }
}
