import java.util.Scanner;

class Q4Circle{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter The radius of Circle: ");
        int radius = sc.nextInt();

        System.out.println("The diameter of the cicrcle is " + (2 * radius));
        System.out.println("The parameter of the cicrcle is " + (2 * radius * Math.PI));
        System.out.println("The area of the cicrcle is " + (2 * radius * radius * Math.PI));

        sc.close();
    }
}