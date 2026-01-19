import java.util.Scanner;

class Q5DigitSeparator
{
    public static void main(String[] args) 
  {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter The Five Digit Number: ");
        int a = sc.nextInt();

        while (a > 0) 
	{
            int q = a % 10;
            a /= 10;
            System.out.print(q + "   ");
        }
        sc.close();
    }
}