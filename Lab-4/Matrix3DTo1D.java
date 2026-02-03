public class Matrix3DTo1D {

    static int X = 3;
    static int Y = 3;
    static int Z = 3;

    // Convert 3D index to 1D
    static int getIndex(int x, int y, int z) {
        return x * (Y * Z) + y * Z + z;
    }

    // set value
    static void set(int value, int x, int y, int z, int[] arr) {
        int index = getIndex(x, y, z);
        arr[index] = value;
    }

    // get value
    static int get(int x, int y, int z, int[] arr) {
        int index = getIndex(x, y, z);
        return arr[index];
    }

    public static void main(String[] args) {

        int[] arr = new int[X * Y * Z];

        set(10, 0, 0, 0, arr);
        set(20, 1, 1, 1, arr);
        set(30, 2, 2, 2, arr);

        System.out.println("Value at (0,0,0): " + get(0,0,0,arr));
        System.out.println("Value at (1,1,1): " + get(1,1,1,arr));
        System.out.println("Value at (2,2,2): " + get(2,2,2,arr));
    }
}