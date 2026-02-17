// Q3ProductDemo.java

import java.util.Scanner;

// Parent class
class Product {

    int productID;
    String name;
    int categoryID;
    double unitPrice;

    // Constructor
    Product(int productID, String name, int categoryID, double unitPrice) {
        this.productID = productID;
        this.name = name;
        this.categoryID = categoryID;
        this.unitPrice = unitPrice;
    }

    // Method to display product details
    void displayProduct() {
        System.out.println("Product ID: " + productID);
        System.out.println("Name: " + name);
        System.out.println("Category ID: " + categoryID);
        System.out.println("Unit Price: " + unitPrice);
    }
}

// Child class
class ElectricalProduct extends Product {

    String voltageRange;
    int wattage;

    // Constructor
    ElectricalProduct(int productID, String name, int categoryID,
                      double unitPrice, String voltageRange, int wattage) {

        super(productID, name, categoryID, unitPrice);
        this.voltageRange = voltageRange;
        this.wattage = wattage;
    }

    // Method to change wattage and price
    void updateDetails(int newWattage, double newPrice) {
        this.wattage = newWattage;
        this.unitPrice = newPrice;
    }

    // Method to display electrical product details
    void displayElectricalProduct() {
        displayProduct();  // call parent method
        System.out.println("Voltage Range: " + voltageRange);
        System.out.println("Wattage: " + wattage);
    }
}

// Main class
public class Q3ProductDemo{

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // Taking input
        System.out.print("Enter Product ID: ");
        int pid = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter Product Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Category ID: ");
        int cid = sc.nextInt();

        System.out.print("Enter Unit Price: ");
        double price = sc.nextDouble();
        sc.nextLine();

        System.out.print("Enter Voltage Range: ");
        String voltage = sc.nextLine();

        System.out.print("Enter Wattage: ");
        int watt = sc.nextInt();

        // Create object
        ElectricalProduct ep = new ElectricalProduct(pid, name, cid, price, voltage, watt);

        System.out.println("\nOriginal Electrical Product Details:");
        ep.displayElectricalProduct();

        // Update details
        System.out.print("\nEnter New Wattage: ");
        int newWatt = sc.nextInt();

        System.out.print("Enter New Price: ");
        double newPrice = sc.nextDouble();

        ep.updateDetails(newWatt, newPrice);

        System.out.println("\nUpdated Electrical Product Details:");
        ep.displayElectricalProduct();

        sc.close();
    }
}
