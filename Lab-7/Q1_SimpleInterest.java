import java.util.Scanner;

class Interest
{
    double calculateBalance(double principal, double rate, int months)
    {
        double years = months / 12.0;
        double interest = principal * rate * years;
        return principal + interest;
    }
}

public class Q1_SimpleInterest
{
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);

        Interest i = new Interest();

        System.out.print("Enter principal amount: ");
        double principal = sc.nextDouble();

        System.out.print("Enter annual interest rate (in decimal): ");
        double rate = sc.nextDouble();

        System.out.print("Enter number of months: ");
        int months = sc.nextInt();

        double balance = i.calculateBalance(principal, rate, months);

        System.out.println("Final Balance: " + balance);
    }
}