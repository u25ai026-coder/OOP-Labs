import java.util.Scanner;

public class EnergyCalculator {

    // method to compute energy
    public static double calculateEnergy(double mass, double initialTemp, double finalTemp) {
        return mass * (finalTemp - initialTemp) * 4184;
    }

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        System.out.print("Enter the amount of water in kilograms: ");
        double mass = input.nextDouble();

        System.out.print("Enter the initial temperature: ");
        double initialTemp = input.nextDouble();

        System.out.print("Enter the final temperature: ");
        double finalTemp = input.nextDouble();

        double energy = calculateEnergy(mass, initialTemp, finalTemp);

        System.out.println("The energy needed is " + energy);

        input.close();
    }
}
