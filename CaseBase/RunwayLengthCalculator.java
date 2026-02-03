import java.util.Scanner;

public class RunwayLengthCalculator {

    // method to calculate runway length
    public static double calculateLength(double speed, double acceleration) {
        return (speed * speed) / (2 * acceleration);
    }

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        System.out.print("Enter speed and acceleration: ");

        double speed = input.nextDouble();
        double acceleration = input.nextDouble();

        double length = calculateLength(speed, acceleration);

        System.out.println("The minimum runway length for this airplane is " + length);

        input.close();
    }
}
