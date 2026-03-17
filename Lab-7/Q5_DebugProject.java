
class BaseClass
{
    void debug()
    {
        System.out.println("Debugging started...");
    }

    void showClass()
    {
        System.out.println("This is a base class method");
    }
}

class Module1 extends BaseClass
{
    void display()
    {
        System.out.println("Module1 working");
    }
}

class Module2 extends BaseClass
{
    void display()
    {
        System.out.println("Module2 working");
    }
}

public class Q5_DebugProject
{
    public static void main(String[] args)
    {
        Module1 m1 = new Module1();
        Module2 m2 = new Module2();

        System.out.println("For Module1:");
        m1.debug();
        m1.display();

        System.out.println("\nFor Module2:");
        m2.debug();
        m2.display();
    }
}