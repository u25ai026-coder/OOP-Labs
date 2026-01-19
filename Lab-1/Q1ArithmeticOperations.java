import java.util.Scanner;

class Q1ArithmeticOperations 
{
        public static void main(String args[]) 
	{
                Scanner sc = new Scanner(System.in);

                System.out.print("Enter First number:- ");
                int num1 = sc.nextInt();
                System.out.print("Enter Second number:- ");
                int num2 = sc.nextInt();
                System.out.println("Plus =" + (num1 + num2));
                System.out.println("Minus =" + (num1 - num2));
                System.out.println("Multiply =" + (num1 * num2));
                System.out.println("Divide =" + (num1 / num2));
                sc.close();
        }
}