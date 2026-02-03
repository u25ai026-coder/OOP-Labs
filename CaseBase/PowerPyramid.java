public class PowerPyramid {

    public static void main(String[] args) {

        int n = 8;   
        for (int i = 0; i < n; i++) {

            // print leading spaces
            for (int s = 0; s < n - i - 1; s++) {
                System.out.print("   ");   // three spaces for alignment
            }

            int value = 1;

            // ascending powers
            for (int j = 0; j <= i; j++) {
                System.out.print(value + "  ");
                value *= 2;
            }

            // descending powers
            value /= 4;
            for (int j = 0; j < i; j++) {
                System.out.print(value + "  ");
                value /= 2;
            }

            System.out.println();
        }
    }
}
