import java.util.Scanner;

class WashingMachine
{
    void switchOn()
    {
        System.out.println("Machine is ON");
    }

    int acceptClothes(int noOfClothes)
    {
        return noOfClothes;
    }

    void acceptDetergent()
    {
        System.out.println("Detergent added");
    }

    void switchOff()
    {
        System.out.println("Machine is OFF");
    }
}

public class Q3_WashingMachine
{
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);

        WashingMachine wm = new WashingMachine();

        wm.switchOn();

        System.out.print("Enter number of clothes: ");
        int clothes = sc.nextInt();

        int acceptedClothes = wm.acceptClothes(clothes);
        System.out.println("Clothes accepted: " + acceptedClothes);

        wm.acceptDetergent();

        wm.switchOff();
    }
}