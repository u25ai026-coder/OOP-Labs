import java.util.Scanner;

public class BMICalculator {

    // constants
    public static final double POUND_TO_KG = 0.45359237;
    public static final double INCH_TO_METER = 0.0254;

    // method to calculate BMI
    public static double calculateBMI(double pounds, double inches) {

        double weightKg = pounds * POUND_TO_KG;
        double heightMeters = inches * INCH_TO_METER;

        return weightKg / (heightMeters * heightMeters);
    }

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        System.out.print("Enter weight in pounds: ");
        double weight = input.nextDouble();

        System.out.print("Enter height in inches: ");
        double height = input.nextDouble();

        double bmi = calculateBMI(weight, height);

        System.out.println("BMI is " + bmi);

        input.close();
    }
}
