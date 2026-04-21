class FibonacciThread extends Thread
{
    static long[] fib = new long[50];

    FibonacciThread()
    {
        setPriority(8);
    }

    public void run()
    {
        fib[0] = 1;
        fib[1] = 1;

        System.out.println("First 50 Fibonacci numbers:");

        for (int i = 2; i < 50; i++)
        {
            fib[i] = fib[i - 1] + fib[i - 2];
        }

        for (int i = 0; i < 50; i++)
        {
            System.out.print(fib[i] + " ");
        }

        System.out.println();

        try
        {
            System.out.println("\nFibonacci thread sleeping...");
            Thread.sleep(500);
        }
        catch (InterruptedException e) {}

        System.out.println("Fibonacci thread resumed after sleep.");
    }
}

class PrimeThread extends Thread
{
    PrimeThread()
    {
        setPriority(5);
    }

    public void run()
    {
        System.out.println("\nFirst 25 prime numbers:");
        int count = 0;
        int num = 2;

        while (count < 25)
        {
            if (isPrime(num))
            {
                System.out.print(num + " ");
                count++;
            }
            num++;
        }

        System.out.println();
    }

    boolean isPrime(int n)
    {
        if (n < 2) return false;

        for (int i = 2; i <= Math.sqrt(n); i++)
        {
            if (n % i == 0) return false;
        }

        return true;
    }
}

public class Q6_PrimeFibonacci
{
    public static void main(String[] args) throws InterruptedException
    {
        FibonacciThread ft = new FibonacciThread();
        PrimeThread pt = new PrimeThread();

        ft.start();
        ft.join();

        pt.start();
        pt.join();
    }
}
