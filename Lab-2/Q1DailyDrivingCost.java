import java.util.Scanner;

public class Q1DailyDrivingCost {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        // Input values
        System.out.print("Enter total miles driven per day: ");
        double milesPerDay = input.nextDouble();

        System.out.print("Enter cost per gallon of gasoline: ");
        double costPerGallon = input.nextDouble();

        System.out.print("Enter average fees per day: ");
        double feesPerDay = input.nextDouble();

        System.out.print("Enter tolls per day: ");
        double tollsPerDay = input.nextDouble();

        // Assume average car mileage (miles per gallon)
        double milesPerGallon = 25.0;

        // Calculate daily fuel cost
        double fuelCost = (milesPerDay / milesPerGallon) * costPerGallon;

        // Calculate total daily driving cost
        double totalDailyCost = fuelCost + feesPerDay + tollsPerDay;

        // Display result
        System.out.println("\nYour daily driving cost is: rupees " + totalDailyCost);

        input.close();
    }
}
