// Q6StudentDemo.java

import java.util.Scanner;

// Student class
class Student {

    // Method to input name
    String inputName(String name) {
        return name;
    }

    // Method to calculate average and check pass/fail
    String Average(int m1, int m2, int m3) {

        double avg = (m1 + m2 + m3) / 3.0;

        if (avg > 50) {
            return "Passed (Average = " + avg + ")";
        } else {
            return "Failed (Average = " + avg + ")";
        }
    }
}

// Main class
public class Q6StudentDemo {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Student s = new Student();

        // Input name
        System.out.print("Enter student name: ");
        String name = sc.nextLine();

        // Input marks
        System.out.print("Enter marks of Exam 1: ");
        int m1 = sc.nextInt();

        System.out.print("Enter marks of Exam 2: ");
        int m2 = sc.nextInt();

        System.out.print("Enter marks of Exam 3: ");
        int m3 = sc.nextInt();

        // Calling methods
        String studentName = s.inputName(name);
        String result = s.Average(m1, m2, m3);

        // Display result
        System.out.println("\nStudent Name: " + studentName);
        System.out.println("Result: " + result);

        sc.close();
    }
}
