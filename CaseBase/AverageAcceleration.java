import java.util.Scanner;

public class AverageAcceleration {

    // method to calculate acceleration
    public static double calculate(double v0, double v1, double t) {
        return (v1 - v0) / t;
    }

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        System.out.print("Enter v0, v1, and t: ");

        double v0 = input.nextDouble();
        double v1 = input.nextDouble();
        double t  = input.nextDouble();

        double acceleration = calculate(v0, v1, t);

        System.out.println("The average acceleration is " + acceleration);

        input.close();
    }
}
