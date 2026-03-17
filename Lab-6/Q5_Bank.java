import java.util.Scanner;

class Bank
{
    double deposit(double amount, double balance)
    {
        return balance + amount;
    }

    double withdraw(double amount, double balance)
    {
        if(balance >= amount)
            return balance - amount;
        else
            return 0;
    }
}

public class Q5_Bank
{
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);

        Bank b = new Bank();

        System.out.print("Enter initial balance: ");
        double balance = sc.nextDouble();

        System.out.print("Enter deposit amount: ");
        double depositAmt = sc.nextDouble();

        balance = b.deposit(depositAmt, balance);
        System.out.println("Balance after deposit: " + balance);

        System.out.print("Enter withdraw amount: ");
        double withdrawAmt = sc.nextDouble();

        double newBalance = b.withdraw(withdrawAmt, balance);

        if(newBalance == 0 && withdrawAmt > balance)
            System.out.println("Insufficient balance");
        else
            System.out.println("Balance after withdrawal: " + newBalance);
    }
}