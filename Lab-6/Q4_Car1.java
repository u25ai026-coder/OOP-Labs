import java.util.Scanner;

abstract class Car
{
    String modelName;

    void setModel(String modelName)
    {
        this.modelName = modelName;
    }

    abstract void avg();
    abstract void mode();
}

class Maruti extends Car
{
    void avg()
    {
        System.out.println("Maruti average is 20 km/l");
    }

    void mode()
    {
        System.out.println("Maruti mode: Manual");
    }
}

class Santro extends Car
{
    void avg()
    {
        System.out.println("Santro average is 18 km/l");
    }

    void mode()
    {
        System.out.println("Santro mode: Automatic");
    }
}

public class Q4_Car1
{
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);

        Car c;

        System.out.print("Enter choice (1-Maruti, 2-Santro): ");
        int choice = sc.nextInt();

        if(choice == 1)
        {
            c = new Maruti();
            c.setModel("Maruti");
        }
        else
        {
            c = new Santro();
            c.setModel("Santro");
        }

        System.out.println("Car Model: " + c.modelName);
        c.avg();
        c.mode();
    }
}