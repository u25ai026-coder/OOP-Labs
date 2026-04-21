import java.util.Arrays;
import java.util.Scanner;

class NameProcessor
{
    private String[] names;
    private int size;

    // Constructor
    public NameProcessor(int size)
    {
        this.size = size;
        names = new String[size];
    }

    // Method to input names
    public void inputNames(Scanner sc)
    {
        System.out.println("Enter " + size + " names:");

        for (int i = 0; i < size; i++)
        {
            names[i] = sc.nextLine();
        }
    }

    // Method to delete first 3 characters
    public void processNames()
    {
        for (int i = 0; i < size; i++)
        {
            if (names[i].length() > 3)
            {
                names[i] = names[i].substring(3);
            }
            else
            {
                names[i] = "";   // if name length ≤ 3
            }
        }
    }

    // Method to sort names
    public void sortNames()
    {
        Arrays.sort(names);
    }

    // Method to display names
    public void displayNames()
    {
        System.out.println("\nProcessed and Sorted Names:");

        for (String name : names)
        {
            System.out.println(name);
        }
    }
}

public class Q3_NameProcessor
{
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);

        NameProcessor obj = new NameProcessor(10);

        obj.inputNames(sc);
        obj.processNames();
        obj.sortNames();
        obj.displayNames();

        sc.close();
    }
}