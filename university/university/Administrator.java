package university;

import java.util.ArrayList;
import java.util.Scanner;

public class Administrator extends User {

    // fixed credentials as per assignment
    public static final String ADMIN_EMAIL = "admin@svnit.ac.in";
    public static final String ADMIN_PASSWORD = "Admin@123";
    public static final String ADMIN_NAME = "Admin";

    private CourseRegistry reg;
    private AuthService auth;

    public Administrator() {
        super(ADMIN_NAME, ADMIN_EMAIL, ADMIN_PASSWORD, "Administrator");
    }

    public void setRegistry(CourseRegistry r) { reg = r; }
    public void setAuthService(AuthService a) { auth = a; }

    @Override
    public void showMenu() {
        Scanner sc = new Scanner(System.in);
        int ch = -1;

        while (ch != 0) {
            System.out.println("\n+==================================================+");
            System.out.println("|              ADMINISTRATOR MENU                  |");
            System.out.println("+==================================================+");
            System.out.println("|  1. Manage Course Catalog                        |");
            System.out.println("|  2. Manage Student Records                       |");
            System.out.println("|  3. Assign Professors to Courses                 |");
            System.out.println("|  4. Handle Complaints                            |");
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
                catalogMenu(sc);
            } else if (ch == 2) {
                studentsMenu(sc);
            } else if (ch == 3) {
                assignProfessor(sc);
            } else if (ch == 4) {
                complaintsMenu(sc);
            } else if (ch == 0) {
                System.out.println("  Logged out. Goodbye!");
            } else {
                System.out.println("  Invalid option.");
            }
        }
    }

    private void catalogMenu(Scanner sc) {
        int ch = -1;
        while (ch != 0) {
            System.out.println("\n  Course Catalog:");
            System.out.println("  1. View All Courses");
            System.out.println("  2. Add New Course");
            System.out.println("  3. Delete a Course");
            System.out.println("  4. View Course Details");
            System.out.println("  0. Back");
            System.out.print("  Choice: ");

            try {
                ch = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                ch = -1;
            }

            if (ch == 1) {
                showAllCourses();
            } else if (ch == 2) {
                addCourse(sc);
            } else if (ch == 3) {
                deleteCourse(sc);
            } else if (ch == 4) {
                showCourseDetails(sc);
            } else if (ch != 0) {
                System.out.println("  Invalid option.");
            }
        }
    }

    private void showAllCourses() {
        ArrayList<Course> all = reg.getAllCourses();
        System.out.println("\n  All Courses (" + all.size() + " total):");
        System.out.printf("  %-7s | %-34s | %s | %s | %-20s | %-26s | %s%n",
                "Code", "Title", "Cr", "Sm", "Timings", "Professor", "Seats");
        System.out.println("  " + "-".repeat(115));

        for (int i = 0; i < all.size(); i++) {
            Course c = all.get(i);
            System.out.printf("  %-7s | %-34s | %d  | %-2d | %-20s | %-26s | %d/%d%n",
                    c.getCourseCode(), c.getCourseTitle(), c.getCredits(),
                    c.getSemester(), c.getClassTimings(), c.getProfessorName(),
                    c.getEnrolledCount(), c.getCapacity());
        }
        System.out.println("  " + "-".repeat(115));
    }

    private void addCourse(Scanner sc) {
        System.out.println("\n  Add New Course:");

        System.out.print("  Course Code  : ");
        String code = sc.nextLine().trim().toUpperCase();

        if (reg.findCourse(code) != null) {
            System.out.println("  Course " + code + " already exists.");
            return;
        }

        System.out.print("  Title        : ");
        String title = sc.nextLine().trim();

        int credits = 0;
        while (credits != 2 && credits != 4) {
            System.out.print("  Credits (2/4): ");
            try {
                credits = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                credits = 0;
            }
            if (credits != 2 && credits != 4) System.out.println("  Must be 2 or 4.");
        }

        int sem = 0;
        while (sem < 1 || sem > 8) {
            System.out.print("  Semester     : ");
            try {
                sem = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                sem = 0;
            }
            if (sem < 1 || sem > 8) System.out.println("  Must be 1 to 8.");
        }

        int cap = 0;
        while (cap <= 0) {
            System.out.print("  Capacity     : ");
            try {
                cap = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                cap = 0;
            }
            if (cap <= 0) System.out.println("  Must be greater than 0.");
        }

        System.out.print("  Timings      : ");
        String timings = sc.nextLine().trim();

        System.out.print("  Room         : ");
        String room = sc.nextLine().trim();

        Course newC = new Course(code, title, credits, sem, cap, timings, room);

        System.out.print("  Add prerequisites? (y/n): ");
        if (sc.nextLine().trim().equalsIgnoreCase("y")) {
            System.out.println("  Enter codes one by one. Type 0 to stop.");
            while (true) {
                System.out.print("  Prereq: ");
                String p = sc.nextLine().trim().toUpperCase();
                if (p.equals("0")) break;
                newC.addPrerequisite(p);
            }
        }

        reg.addCourse(newC);
        System.out.println("  Course " + code + " added!");
    }

    private void deleteCourse(Scanner sc) {
        showAllCourses();
        System.out.print("  Enter course code to delete (0 to cancel): ");
        String code = sc.nextLine().trim().toUpperCase();
        if (code.equals("0")) return;

        Course c = reg.findCourse(code);
        if (c == null) {
            System.out.println("  Course not found.");
            return;
        }

        if (c.getEnrolledCount() > 0) {
            System.out.println("  Warning: " + c.getEnrolledCount() + " students enrolled.");
            System.out.print("  Sure? (yes/no): ");
            if (!sc.nextLine().trim().equalsIgnoreCase("yes")) {
                System.out.println("  Cancelled.");
                return;
            }
        }

        if (reg.removeCourse(code)) {
            System.out.println("  Deleted " + code);
        } else {
            System.out.println("  Could not delete.");
        }
    }

    private void showCourseDetails(Scanner sc) {
        System.out.print("  Course code: ");
        String code = sc.nextLine().trim().toUpperCase();
        Course c = reg.findCourse(code);
        if (c == null) {
            System.out.println("  Not found.");
        } else {
            System.out.println(c);
        }
    }

    private void studentsMenu(Scanner sc) {
        int ch = -1;
        while (ch != 0) {
            System.out.println("\n  Student Records:");
            System.out.println("  1. View All Students");
            System.out.println("  2. View Student Grades");
            System.out.println("  3. Assign Grade");
            System.out.println("  4. Advance Semester");
            System.out.println("  0. Back");
            System.out.print("  Choice: ");

            try {
                ch = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                ch = -1;
            }

            if (ch == 1) {
                showAllStudents();
            } else if (ch == 2) {
                showStudentGrades(sc);
            } else if (ch == 3) {
                assignGrade(sc);
            } else if (ch == 4) {
                advanceSemester(sc);
            } else if (ch != 0) {
                System.out.println("  Invalid option.");
            }
        }
    }

    private void showAllStudents() {
        ArrayList<User> all = auth.getAllUsers();
        System.out.println("\n  All Students:");
        System.out.printf("  %-6s | %-20s | %-25s | %s | %s%n",
                "ID", "Name", "Email", "Sem", "CGPA");
        System.out.println("  " + "-".repeat(75));

        boolean found = false;
        for (int i = 0; i < all.size(); i++) {
            User u = all.get(i);
            if (u instanceof Student && !(u instanceof TeachingAssistant)) {
                Student s = (Student) u;
                System.out.printf("  %-6d | %-20s | %-25s | %-3d | %.2f%n",
                        s.getStudentId(), s.getUserName(), s.getUserEmail(),
                        s.getCurrentSemester(), s.getCgpa());
                found = true;
            }
        }
        if (!found) System.out.println("  No students registered yet.");
    }

    private void showStudentGrades(Scanner sc) {
        int sid = getStudentId(sc);
        if (sid == -1) return;

        ArrayList<CourseGrade> grades = reg.getGradesForStudent(sid);
        System.out.println("\n  Grades for student #" + sid + ":");

        if (grades.size() == 0) {
            System.out.println("  No records found.");
            return;
        }

        for (CourseGrade cg : grades) {
            System.out.println(cg);
        }
    }

    private void assignGrade(Scanner sc) {
        int sid = getStudentId(sc);
        if (sid == -1) return;

        ArrayList<CourseGrade> ongoing = reg.getOngoingCourses(sid);
        if (ongoing.size() == 0) {
            System.out.println("  No ongoing courses for this student.");
            return;
        }

        System.out.println("  Ongoing courses:");
        for (CourseGrade cg : ongoing) {
            System.out.println("    " + cg.getCourseCode() + " - " + cg.getCourseTitle());
        }

        System.out.print("  Enter course code: ");
        String code = sc.nextLine().trim().toUpperCase();

        CourseGrade rec = reg.findGradeRecord(sid, code);
        if (rec == null || rec.isCompleted()) {
            System.out.println("  No ongoing record found for " + code);
            return;
        }

        System.out.print("  Grade (O/A+/A/B+/B/C/F): ");
        String g = sc.nextLine().trim().toUpperCase();

        // validate the grade
        boolean valid = g.equals("O") || g.equals("A+") || g.equals("A") ||
                g.equals("B+") || g.equals("B") || g.equals("C") || g.equals("F");

        if (!valid) {
            System.out.println("  Invalid grade.");
            return;
        }

        rec.assignGrade(g);
        System.out.println("  Grade " + g + " assigned to student #" + sid + " for " + code);
    }

    private void advanceSemester(Scanner sc) {
        int sid = getStudentId(sc);
        if (sid == -1) return;

        Student s = findStudent(sid);
        if (s == null) {
            System.out.println("  Student not found.");
            return;
        }

        // cant advance if there are still ungraded courses
        ArrayList<CourseGrade> ongoing = reg.getOngoingCourses(sid);
        if (ongoing.size() > 0) {
            System.out.println("  Cannot advance - still has ungraded courses:");
            for (CourseGrade cg : ongoing) {
                System.out.println("    " + cg.getCourseCode());
            }
            return;
        }

        int next = s.getCurrentSemester() + 1;
        if (next > 8) {
            System.out.println("  Already in final semester.");
            return;
        }

        s.setCurrentSemester(next);
        System.out.println("  " + s.getUserName() + " moved to semester " + next);
    }

    private void assignProfessor(Scanner sc) {
        showAllCourses();
        System.out.print("  Enter course code (0 to cancel): ");
        String code = sc.nextLine().trim().toUpperCase();
        if (code.equals("0")) return;

        Course c = reg.findCourse(code);
        if (c == null) {
            System.out.println("  Course not found.");
            return;
        }

        // show professors registered
        ArrayList<User> all = auth.getAllUsers();
        System.out.println("  Registered Professors:");
        for (User u : all) {
            if (u instanceof Professor) {
                Professor p = (Professor) u;
                System.out.println("    " + p.getUserName() + " | " + p.getDepartment());
            }
        }

        System.out.print("  Enter professor name to assign: ");
        String name = sc.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("  Name cannot be empty.");
            return;
        }

        c.setProfessorName(name);
        System.out.println("  Assigned " + name + " to " + code);
    }

    private void complaintsMenu(Scanner sc) {
        int ch = -1;
        while (ch != 0) {
            System.out.println("\n  Complaints:");
            System.out.println("  1. View All");
            System.out.println("  2. View Pending Only");
            System.out.println("  3. Resolve a Complaint");
            System.out.println("  0. Back");
            System.out.print("  Choice: ");

            try {
                ch = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                ch = -1;
            }

            if (ch == 1) {
                showComplaints(null);
            } else if (ch == 2) {
                showComplaints("Pending");
            } else if (ch == 3) {
                resolveComplaint(sc);
            } else if (ch != 0) {
                System.out.println("  Invalid option.");
            }
        }
    }

    private void showComplaints(String filter) {
        ArrayList<Complaint> all = reg.getAllComplaints();

        if (filter != null) {
            System.out.println("\n  " + filter + " Complaints:");
        } else {
            System.out.println("\n  All Complaints:");
        }

        if (all.size() == 0) {
            System.out.println("  No complaints.");
            return;
        }

        boolean any = false;
        for (int i = 0; i < all.size(); i++) {
            Complaint c = all.get(i);
            if (filter == null || c.getStatus().equals(filter)) {
                System.out.println(c);
                System.out.println("  ---");
                any = true;
            }
        }
        if (!any) System.out.println("  None found.");
    }

    private void resolveComplaint(Scanner sc) {
        showComplaints("Pending");
        System.out.print("  Enter complaint ID (0 to cancel): ");

        int id;
        try {
            id = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("  Invalid ID.");
            return;
        }

        if (id == 0) return;

        Complaint c = reg.findComplaint(id);
        if (c == null) {
            System.out.println("  Complaint not found.");
            return;
        }
        if (c.getStatus().equals("Resolved")) {
            System.out.println("  Already resolved.");
            return;
        }

        System.out.print("  Resolution note: ");
        String note = sc.nextLine().trim();
        c.resolve(note.isEmpty() ? "Resolved by admin." : note);
        System.out.println("  Complaint #" + id + " resolved.");
    }

    // helper to get student ID from input
    private int getStudentId(Scanner sc) {
        System.out.print("  Enter Student ID: ");
        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("  Invalid ID.");
            return -1;
        }
    }

    // find a student by ID in the user list
    private Student findStudent(int sid) {
        ArrayList<User> all = auth.getAllUsers();
        for (int i = 0; i < all.size(); i++) {
            User u = all.get(i);
            if (u instanceof Student) {
                Student s = (Student) u;
                if (s.getStudentId() == sid) return s;
            }
        }
        return null;
    }
}
