class MyRunnable implements Runnable
{
    public MyRunnable()
    {
        Thread t = new Thread(this);
        t.start();
    }

    public void run()
    {
        for (int i = 1; i <= 5; i++)
        {
            System.out.println("Runnable thread - count: " + i);
            try { Thread.sleep(200); } catch (InterruptedException e) {}
        }
    }
}

public class Q2_ThreadRunnable
{
    public static void main(String[] args)
    {
        new MyRunnable();
        System.out.println("Main method finished.");
    }
}
