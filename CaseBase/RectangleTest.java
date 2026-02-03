import java.util.Scanner;

public class RectangleTest {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        System.out.print("Enter r1's center x-, y-coordinates, width, and height: ");
        Rectangle r1 = new Rectangle(
                input.nextDouble(),
                input.nextDouble(),
                input.nextDouble(),
                input.nextDouble()
        );

        System.out.print("Enter r2's center x-, y-coordinates, width, and height: ");
        Rectangle r2 = new Rectangle(
                input.nextDouble(),
                input.nextDouble(),
                input.nextDouble(),
                input.nextDouble()
        );

        if (r1.contains(r2)) {
            System.out.println("r2 is inside r1");
        }
        else if (r1.overlaps(r2)) {
            System.out.println("r2 overlaps r1");
        }
        else {
            System.out.println("r2 does not overlap r1");
        }

        input.close();
    }
}
