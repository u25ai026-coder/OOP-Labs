package university;

import java.util.ArrayList;
import java.util.Scanner;

public class Professor extends User {

    private int pid;
    private String dept;
    private String officeHours;
    private CourseRegistry reg;

    public Professor(String name, String email, String pass, int pid, String dept) {
        super(name, email, pass, "Professor");
        this.pid = pid;
        this.dept = dept;
        this.officeHours = "Not set";
    }

    public int getProfessorId() { return pid; }
    public String getDepartment() { return dept; }
    public String getOfficeHours() { return officeHours; }

    public void setDepartment(String d) { dept = d; }
    public void setOfficeHours(String o) { officeHours = o; }
    public void setRegistry(CourseRegistry r) { reg = r; }

    @Override
    public void showMenu() {
        Scanner sc = new Scanner(System.in);
        int ch = -1;

        while (ch != 0) {
            System.out.println("\n+==================================================+");
            System.out.printf("|  PROFESSOR MENU  --  Dr. %-24s|%n", getUserName());
            System.out.printf("|  Dept: %-42s|%n", dept);
            System.out.println("+==================================================+");
            System.out.println("|  1. View My Assigned Courses                     |");
            System.out.println("|  2. Manage a Course                              |");
            System.out.println("|  3. View Enrolled Students                       |");
            System.out.println("|  4. View Student Feedback                        |");
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
                viewMyCourses();
            } else if (ch == 2) {
                manageCourse(sc);
            } else if (ch == 3) {
                viewEnrolled(sc);
            } else if (ch == 4) {
                viewFeedback(sc);
            } else if (ch == 0) {
                System.out.println("  Logged out. Bye Dr. " + getUserName() + "!");
            } else {
                System.out.println("  Invalid option.");
            }
        }
    }

    private void viewMyCourses() {
        if (reg == null) {
            System.out.println("  Registry not connected.");
            return;
        }

        ArrayList<Course> all = reg.getAllCourses();
        System.out.println("\n  My Courses:");
        System.out.printf("  %-7s | %-34s | %s | %-20s | %-26s | %s%n",
                "Code", "Title", "Cr", "Timings", "Professor", "Seats");
        System.out.println("  " + "-".repeat(115));

        boolean found = false;
        for (int i = 0; i < all.size(); i++) {
            Course c = all.get(i);
            // match by name - prof logs in with their name
            if (c.getProfessorName().toLowerCase().contains(getUserName().toLowerCase())) {
                System.out.println(c.toShortString());
                found = true;
            }
        }
        if (!found) {
            System.out.println("  No courses assigned to you yet.");
        }
        System.out.println("  " + "-".repeat(115));
    }

    private void manageCourse(Scanner sc) {
        if (reg == null) {
            System.out.println("  Registry not connected.");
            return;
        }

        System.out.print("  Enter course code to manage: ");
        String code = sc.nextLine().trim().toUpperCase();
        Course c = reg.findCourse(code);

        if (c == null) {
            System.out.println("  Course not found.");
            return;
        }

        int ch = -1;
        while (ch != 0) {
            System.out.println("\n  Managing: " + c.getCourseCode() + " - " + c.getCourseTitle());
            System.out.println("  1. Update Timings");
            System.out.println("  2. Update Room");
            System.out.println("  3. Update Syllabus");
            System.out.println("  4. Update Capacity");
            System.out.println("  0. Back");
            System.out.print("  Choice: ");

            try {
                ch = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                ch = -1;
            }

            if (ch == 1) {
                System.out.print("  New timings: ");
                c.setClassTimings(sc.nextLine().trim());
                System.out.println("  Updated.");
            } else if (ch == 2) {
                System.out.print("  New room: ");
                c.setLocation(sc.nextLine().trim());
                System.out.println("  Updated.");
            } else if (ch == 3) {
                System.out.print("  New syllabus: ");
                c.setSyllabus(sc.nextLine().trim());
                System.out.println("  Updated.");
            } else if (ch == 4) {
                System.out.print("  New capacity: ");
                try {
                    int cap = Integer.parseInt(sc.nextLine().trim());
                    c.setCapacity(cap);
                    System.out.println("  Updated.");
                } catch (NumberFormatException e) {
                    System.out.println("  Invalid number.");
                }
            } else if (ch != 0) {
                System.out.println("  Invalid option.");
            }
        }
    }

    private void viewEnrolled(Scanner sc) {
        if (reg == null) {
            System.out.println("  Registry not connected.");
            return;
        }

        System.out.print("  Enter course code: ");
        String code = sc.nextLine().trim().toUpperCase();
        Course c = reg.findCourse(code);

        if (c == null) {
            System.out.println("  Course not found.");
            return;
        }

        ArrayList<Integer> ids = c.getEnrolledStudentIds();
        System.out.println("\n  Students in " + code + ": " + ids.size() + "/" + c.getCapacity());

        if (ids.size() == 0) {
            System.out.println("  No students enrolled.");
            return;
        }

        for (int i = 0; i < ids.size(); i++) {
            System.out.println("    Student ID: " + ids.get(i));
        }
    }

    private void viewFeedback(Scanner sc) {
        if (reg == null) {
            System.out.println("  Registry not connected.");
            return;
        }

        viewMyCourses();
        System.out.print("  Enter course code to view feedback (0 to cancel): ");
        String code = sc.nextLine().trim().toUpperCase();
        if (code.equals("0")) return;

        Course c = reg.findCourse(code);
        if (c == null) {
            System.out.println("  Course not found.");
            return;
        }

        ArrayList<FeedbackRecord> list = reg.getFeedbackForCourse(code);
        System.out.println("\n  Feedback for " + code + " - " + c.getCourseTitle() + ":");

        if (list.size() == 0) {
            System.out.println("  No feedback submitted yet.");
            return;
        }

        int ratingSum = 0;
        int ratingCount = 0;

        for (int i = 0; i < list.size(); i++) {
            FeedbackRecord fr = list.get(i);
            System.out.println(fr);
            System.out.println("  ---");

            if (fr.hasRating()) {
                ratingSum += (Integer) fr.getRatingFeedback().getFeedbackValue();
                ratingCount++;
            }
        }

        System.out.println("  Total responses: " + list.size());
        if (ratingCount > 0) {
            double avg = (double) ratingSum / ratingCount;
            System.out.printf("  Average rating : %.1f / 5.0%n", avg);
        }
    }

    @Override
    public String toString() {
        return super.toString() + " | Dept: " + dept;
    }
}
