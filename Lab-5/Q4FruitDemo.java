// Q4FruitDemo.java

import java.util.Scanner;

// Fruit class
class Fruit {

    String name;
    String type;
    double price;

    // Constructor
    Fruit(String name, String type, double price) {
        this.name = name;
        this.type = type;
        this.price = price;
    }

    // Method to display details
    void displayFruit() {
        System.out.println("Fruit Name: " + name);
        System.out.println("Type: " + type);
        System.out.println("Price: " + price);
        System.out.println("         ");
    }
}

// Main class
public class Q4FruitDemo {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // Taking input for first fruit
        System.out.println("Enter details for Fruit 1");
        System.out.print("Name: ");
        String name1 = sc.nextLine();

        System.out.print("Type (Single/Bunch): ");
        String type1 = sc.nextLine();

        System.out.print("Price: ");
        double price1 = sc.nextDouble();
        sc.nextLine();  // clear buffer

        // Taking input for second fruit
        System.out.println("\nEnter details for Fruit 2");
        System.out.print("Name: ");
        String name2 = sc.nextLine();

        System.out.print("Type (Single/Bunch): ");
        String type2 = sc.nextLine();

        System.out.print("Price: ");
        double price2 = sc.nextDouble();

        // Creating objects
        Fruit f1 = new Fruit(name1, type1, price1);
        Fruit f2 = new Fruit(name2, type2, price2);

        // Displaying details
        System.out.println("\nFruit Details:");
        f1.displayFruit();
        f2.displayFruit();

        sc.close();
    }
}
