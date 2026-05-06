package university;

import java.util.ArrayList;
import java.util.Scanner;

// extends Student, gets student features + TA-specific ones
public class TeachingAssistant extends Student {

    private int taId;
    private String assignedCourse;

    public TeachingAssistant(String name, String email, String pass, int sid, int taId, String assignedCourse) {
        super(name, email, pass, sid);
        this.taId = taId;
        this.assignedCourse = assignedCourse.toUpperCase();
        setUserRole("TA");
    }

    public int getTaId() { return taId; }
    public String getAssignedCourse() { return assignedCourse; }
    public void setAssignedCourse(String c) { assignedCourse = c.toUpperCase(); }

    @Override
    public void showMenu() {
        Scanner sc = new Scanner(System.in);
        int ch = -1;

        while (ch != 0) {
            System.out.println("\n+==================================================+");
            System.out.printf("|  TA MENU  --  %-35s|%n", getUserName());
            System.out.printf("|  TA ID: %-5d     Assigned: %-18s|%n", taId, assignedCourse);
            System.out.println("+==================================================+");
            System.out.println("|  -- Student Options --                           |");
            System.out.println("|  1. View Available Courses                       |");
            System.out.println("|  2. Register for a Course                        |");
            System.out.println("|  3. View My Schedule                             |");
            System.out.println("|  4. Track My Academic Progress                   |");
            System.out.println("|  5. Drop a Course                                |");
            System.out.println("|  6. Submit / View Complaints                     |");
            System.out.println("|  7. Give Course Feedback                         |");
            System.out.println("|  -- TA Options --                                |");
            System.out.println("|  8. View Grades of Assigned Course               |");
            System.out.println("|  9. View Enrolled Students                       |");
            System.out.println("|  0. Logout                                       |");
            System.out.println("+==================================================+");
            System.out.print("Choice: ");

            try {
                ch = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("  Enter a number.");
                continue;
            }

            if (ch == 1) {
                viewCourses();
            } else if (ch == 2) {
                try {
                    registerCourse(sc);
                } catch (CourseFullException e) {
                    System.out.println("  Error: " + e.getMessage());
                }
            } else if (ch == 3) {
                viewSchedule();
            } else if (ch == 4) {
                viewProgress();
            } else if (ch == 5) {
                try {
                    dropCourse(sc);
                } catch (DropDeadlinePassedException e) {
                    System.out.println("  Error: " + e.getMessage());
                }
            } else if (ch == 6) {
                complaintsMenu(sc);
            } else if (ch == 7) {
                giveFeedback(sc);
            } else if (ch == 8) {
                viewGrades();
            } else if (ch == 9) {
                viewEnrolled();
            } else if (ch == 0) {
                System.out.println("  Logged out. Bye " + getUserName() + "!");
            } else {
                System.out.println("  Invalid option.");
            }
        }
    }

    // view only, no editing
    private void viewGrades() {
        if (reg == null) {
            System.out.println("  Registry not connected.");
            return;
        }
        Course c = reg.findCourse(assignedCourse);
        if (c == null) {
            System.out.println("  Assigned course not found.");
            return;
        }

        System.out.println("\n  Grade Report for " + assignedCourse + " - " + c.getCourseTitle());
        System.out.printf("  %-10s | %-8s | %s%n", "Student ID", "Code", "Grade");
        System.out.println("  " + "-".repeat(40));

        ArrayList<CourseGrade> all = reg.getAllGrades();
        boolean found = false;
        for (CourseGrade cg : all) {
            if (cg.getCourseCode().equalsIgnoreCase(assignedCourse)) {
                System.out.printf("  %-10d | %-8s | %s%n", cg.getStudentId(), cg.getCourseCode(), cg.getGrade());
                found = true;
            }
        }
        if (!found) System.out.println("  No grade records yet.");
        System.out.println("  (View only - you cannot modify grades)");
    }

    private void viewEnrolled() {
        if (reg == null) {
            System.out.println("  Registry not connected.");
            return;
        }
        Course c = reg.findCourse(assignedCourse);
        if (c == null) {
            System.out.println("  Course not found.");
            return;
        }

        ArrayList<Integer> ids = c.getEnrolledStudentIds();
        System.out.println("\n  Enrolled in " + assignedCourse + ": " + ids.size() + "/" + c.getCapacity());
        if (ids.size() == 0) {
            System.out.println("  Nobody enrolled yet.");
        } else {
            for (int id : ids) {
                System.out.println("    Student ID: " + id);
            }
        }
    }

    @Override
    public String toString() {
        return super.toString() + " | TA ID: " + taId + " | Assigned: " + assignedCourse;
    }
}
