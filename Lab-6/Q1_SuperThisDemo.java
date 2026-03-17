import java.util.Scanner;

class Person 
{
    String name;
    int age;

    Person() 
    {
        name = "Unknown";
        age = 0;
    }

    Person(String name, int age) 
    {
        this.name = name;
        this.age = age;
    }

    void display() 
    {
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
    }
}

class Student extends Person 
{
    int marks;

    Student() 
    {
        super();
        marks = 0;
    }

    Student(String name, int age, int marks) 
    {
        super(name, age);
        this.marks = marks;
    }

    void show() 
    {
        System.out.println("Name (from super class): " + super.name);
        System.out.println("Age (from super class): " + super.age);
        System.out.println("Marks: " + marks);
    }
}

public class Q1_SuperThisDemo 
{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter name: ");
        String name = sc.nextLine();

        System.out.print("Enter age: ");
        int age = sc.nextInt();

        System.out.print("Enter marks: ");
        int marks = sc.nextInt();

        Student s1 = new Student(name, age, marks);

        System.out.println("\nStudent Details:");
        s1.show();
    }
}