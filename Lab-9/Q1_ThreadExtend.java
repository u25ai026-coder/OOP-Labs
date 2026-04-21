class MyThread extends Thread
{
    public void run()
    {
        for (int i = 1; i <= 5; i++)
        {
            System.out.println("Thread running - count: " + i);
            try { Thread.sleep(200); } catch (InterruptedException e) {}
        }
    }
}

public class Q1_ThreadExtend
{
    public static void main(String[] args)
    {
        MyThread t = new MyThread();
        t.start();
        System.out.println("Main method finished.");
    }
}
