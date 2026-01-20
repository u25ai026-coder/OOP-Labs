import java.util.Scanner;

public class Q5GasMileage {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        int miles;
        int gallons;

        double totalMiles = 0;
        double totalGallons = 0;

        System.out.print("Enter miles driven (-1 to quit): ");
        miles = input.nextInt();

        while (miles != -1) {

            System.out.print("Enter gallons used: ");
            gallons = input.nextInt();

            double mpg = (double) miles / gallons;
            System.out.println("Miles per gallon for this trip: " + mpg);

            totalMiles += miles;
            totalGallons += gallons;

            double totalMPG = totalMiles / totalGallons;
            System.out.println("Combined miles per gallon: " + totalMPG);

            System.out.print("\nEnter miles driven (-1 to quit): ");
            miles = input.nextInt();
        }

        input.close();
    }
}
