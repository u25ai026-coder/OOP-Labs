import java.util.Scanner;

class SinThread extends Thread
{
    double x;

    SinThread(double x)
    {
        this.x = x;
    }

    public void run()
    {
        double result = Math.sin(x);
        System.out.println("sin(" + x + ") = " + result);
    }
}

class CosThread extends Thread
{
    double x;

    CosThread(double x)
    {
        this.x = x;
    }

    public void run()
    {
        double result = Math.cos(x);
        System.out.println("cos(" + x + ") = " + result);
    }
}

public class Q3_SinCosThreads
{
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter value of x (in radians): ");
        double x = sc.nextDouble();

        SinThread st = new SinThread(x);
        CosThread ct = new CosThread(x);

        st.start();
        ct.start();

        sc.close();
    }
}
