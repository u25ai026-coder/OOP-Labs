import java.util.Scanner;

class Theater
{
    double calculateProfit(int attendees)
    {
        double income = attendees * 5;
        double cost = 20 + (0.5 * attendees);
        return income - cost;
    }
}

public class Q2_TotalProfit
{
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of attendees: ");
        int attendees = sc.nextInt();

        Theater t = new Theater();
        double profit = t.calculateProfit(attendees);

        System.out.println("Total Profit: $" + profit);
    }
}