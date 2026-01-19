import java.util.Scanner;

class Q3CompareNumbers{
    void biggest(int num1, int num2, int num3) {
        int k;
        if (num1 > num2)
            k = num1;
        else
            k = num2;
        if (num3 > k)
            System.out.println("The biggest number is " + num3);
        else
            System.out.println("The biggest number is " + k);
    }

    void smallest(int num1, int num2, int num3) {
        int k;
        if (num1 > num2)
            k = num2;
        else
            k = num1;
        if (num3 > k)
            System.out.println("The smallest number is " + k);
        else
            System.out.println("The smallest number is " + num3);
    }
}

class Q3NumberOperations{
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the First number: ");
        int num1 = sc.nextInt();

        System.out.print("Enter the Second number: ");
        int num2 = sc.nextInt();

        System.out.print("Enter the Third number: ");
        int num3 = sc.nextInt();

        System.out.println("The sum of all the number is " + (num1 + num2 + num3));
        System.out.println("The product of all the number is " + (num1 * num2 * num3));
        System.out.println("The average of all the number is " + ((num1 + num2 + num3) / 3));

        Q3CompareNumbers c = new Q3CompareNumbers();
        c.biggest(num1, num2, num3);
        c.smallest(num1, num2, num3);

        sc.close();
    }
}