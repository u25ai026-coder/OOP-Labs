// Step 1: Interface
interface Function {
    int evaluate(int x);
}

// Step 2: Class implementing Function
class Half implements Function {

    @Override
    public int evaluate(int x) {
        return x / 2;
    }
}

// Step 3: Client class
public class FunctionDemo {

    // method that processes array
    public static int[] processArray(int[] arr) {

        Function f = new Half(); // create object

        int[] result = new int[arr.length];

        for (int i = 0; i < arr.length; i++) {
            result[i] = f.evaluate(arr[i]);
        }

        return result;
    }

    // main method
    public static void main(String[] args) {

        int[] input = {10, 20, 30, 40};

        int[] output = processArray(input);

        System.out.println("Output array:");

        for (int num : output) {
            System.out.print(num + " ");
        }
    }
}
