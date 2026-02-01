// Base class
class X {
    int i, j;

    // Constructor
    X(int i, int j) {
        this.i = i;
        this.j = j;
    }

    // Final method
    final int sum() {
        return i + j;
    }
}

// Derived class
class Y extends X {

    // Constructor
    Y(int i, int j) {
        super(i, j);
    }

    // Method to find product
    int findProduct() {
        return i * j;
    }
}

// Further derived class
class Z extends Y {

    // Constructor
    Z(int i, int j) {
        super(i, j);
    }
}

// Main class
public class MultilevelInheritanceDemo {
    public static void main(String[] args) {

        Z obj = new Z(10, 5);

        int sum = obj.sum();
        int product = obj.findProduct();

        System.out.println("Sum = " + sum);
        System.out.println("Product = " + product);
    }
}