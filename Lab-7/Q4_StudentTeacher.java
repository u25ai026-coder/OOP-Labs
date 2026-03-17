import java.util.Scanner;

class Teacher
{
    String name;
    String subject;

    Teacher(String name, String subject)
    {
        this.name = name;
        this.subject = subject;
    }

    void displayTeacher()
    {
        System.out.println("Name: " + name);
        System.out.println("Subject: " + subject);
    }
}

class Student extends Teacher
{
    int rollNo;
    int marks;

    Student(String name, String subject, int rollNo, int marks)
    {
        super(name, subject);
        this.rollNo = rollNo;
        this.marks = marks;
    }

    void displayStudent()
    {
        displayTeacher();
        System.out.println("Roll No: " + rollNo);
        System.out.println("Marks: " + marks);
    }
}

public class Q4_StudentTeacher
{
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Subject: ");
        String subject = sc.nextLine();

        System.out.print("Enter Roll No: ");
        int rollNo = sc.nextInt();

        System.out.print("Enter Marks: ");
        int marks = sc.nextInt();

        Student s = new Student(name, subject, rollNo, marks);

        System.out.println("\nStudent Details:");
        s.displayStudent();
    }
}