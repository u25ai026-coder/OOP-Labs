import java.util.Scanner;

class Course
{
    int id;
    String description;
    int duration;
    double fees;

    Course(int id, String description, int duration, double fees)
    {
        this.id = id;
        this.description = description;
        this.duration = duration;
        this.fees = fees;
    }

    void GetData()
    {
        System.out.println("ID: " + id);
        System.out.println("Description: " + description);
        System.out.println("Duration: " + duration + " months");
        System.out.println("Fees: " + fees);
        System.out.println();
    }
}

public class Q6_Course
{
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);

        Course[] c = new Course[5];

        for(int i = 0; i < 5; i++)
        {
            System.out.println("Enter details for Course " + (i+1));

            System.out.print("Enter ID: ");
            int id = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter Description: ");
            String desc = sc.nextLine();

            System.out.print("Enter Duration (months): ");
            int duration = sc.nextInt();

            System.out.print("Enter Fees: ");
            double fees = sc.nextDouble();

            c[i] = new Course(id, desc, duration, fees);
        }

        System.out.println("\nCourse Details:");

        for(int i = 0; i < 5; i++)
        {
            c[i].GetData();
        }
    }
}