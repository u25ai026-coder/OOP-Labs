import java.util.Random;

class BankAccount
{
    int balance = 600;

    synchronized void deposit(int amount)
    {
        balance += amount;
        System.out.println("Father deposited: " + amount + " | Balance: " + balance);
        notifyAll();
    }

    synchronized void withdraw(int amount)
    {
        balance -= amount;
        System.out.println("Son withdrew: " + amount + " | Balance: " + balance);
        notifyAll();
    }

    synchronized int getBalance()
    {
        return balance;
    }
}

class Father extends Thread
{
    BankAccount account;
    Random rand = new Random();

    Father(BankAccount account)
    {
        this.account = account;
    }

    public void run()
    {
        for (int i = 0; i < 20; i++)
        {
            synchronized (account)
            {
                while (account.balance >= 2000)
                {
                    try { account.wait(); } catch (InterruptedException e) {}
                }

                int amount = rand.nextInt(200) + 1;
                account.deposit(amount);

                try { Thread.sleep(200); } catch (InterruptedException e) {}
            }
        }
    }
}

class Son extends Thread
{
    BankAccount account;
    Random rand = new Random();

    Son(BankAccount account)
    {
        this.account = account;
    }

    public void run()
    {
        for (int i = 0; i < 20; i++)
        {
            synchronized (account)
            {
                while (account.balance <= 2000)
                {
                    try { account.wait(); } catch (InterruptedException e) {}
                }

                int amount = rand.nextInt(150) + 1;
                account.withdraw(amount);

                try { Thread.sleep(200); } catch (InterruptedException e) {}
            }
        }
    }
}

public class Q7_BankAccount
{
    public static void main(String[] args) throws InterruptedException
    {
        BankAccount account = new BankAccount();
        System.out.println("Initial balance: " + account.balance);

        Father father = new Father(account);
        Son son = new Son(account);

        father.start();
        son.start();

        father.join();
        son.join();

        System.out.println("\nFinal balance: " + account.balance);
    }
}
