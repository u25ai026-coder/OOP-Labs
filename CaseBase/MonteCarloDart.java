import java.util.Random;

public class MonteCarloDart {

    public static void main(String[] args) {

        final int TRIALS = 1_000_000;
        int oddCount = 0;

        Random rand = new Random();

        for (int i = 0; i < TRIALS; i++) {

            // random point in [-1, 1]
            double x = 2 * rand.nextDouble() - 1;
            double y = 2 * rand.nextDouble() - 1;

            boolean isOdd = false;

            // Region 1 (left half)
            if (x < 0) {
                isOdd = true;
            }
            // Right half
            else {
                if (y >= 0) { // top-right triangle area
                    if (y <= 1 - x) { // region 3
                        isOdd = true;
                    }
                }
                // region 4 is even -> ignore
            }

            if (isOdd)
                oddCount++;
        }

        double probability = (double) oddCount / TRIALS;

        System.out.println("Estimated probability = " + probability);
    }
}
