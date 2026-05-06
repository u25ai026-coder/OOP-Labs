package university;

import java.util.ArrayList;
import java.util.Scanner;

public class Student extends User {

    private int sid;
    private int sem;
    private double cgpa;
    protected CourseRegistry reg; // protected so TA can use it too

    private static final int MAX_CR = 20;

    public Student(String name, String email, String pass, int sid) {
        super(name, email, pass, "Student");
        this.sid = sid;
        this.sem = 2; // 2nd semester AI branch
        this.cgpa = 0.0;
    }

    public int getStudentId() { return sid; }
    public int getCurrentSemester() { return sem; }
    public double getCgpa() { return cgpa; }

    public void setCurrentSemester(int s) { sem = s; }
    public void setCgpa(double c) { cgpa = c; }
    public void setRegistry(CourseRegistry r) { reg = r; }

    @Override
    public void showMenu() {
        Scanner sc = new Scanner(System.in);
        int ch = -1;

        while (ch != 0) {
            System.out.println("\n+==================================================+");
            System.out.printf("|  STUDENT MENU  --  %-30s|%n", getUserName());
            System.out.printf("|  Semester: %-4d          CGPA: %-13.2f     |%n", sem, cgpa);
            System.out.println("+==================================================+");
            System.out.println("|  1. View Available Courses                       |");
            System.out.println("|  2. Register for a Course                        |");
            System.out.println("|  3. View My Schedule                             |");
            System.out.println("|  4. Track Academic Progress                      |");
            System.out.println("|  5. Drop a Course                                |");
            System.out.println("|  6. Submit / View Complaints                     |");
            System.out.println("|  7. Give Course Feedback                         |");
            System.out.println("|  0. Logout                                       |");
            System.out.println("+==================================================+");
            System.out.print("Choice: ");

            try {
                ch = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("  Please enter a number.");
                continue;
            }

            if (ch == 1) {
                viewCourses();
            } else if (ch == 2) {
                try {
                    registerCourse(sc);
                } catch (CourseFullException e) {
                    System.out.println("  Error: " + e.getMessage());
                    System.out.println("  Try a different course.");
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
            } else if (ch == 0) {
                System.out.println("  Logged out. Bye " + getUserName() + "!");
            } else {
                System.out.println("  Invalid option.");
            }
        }
    }

    protected void viewCourses() {
        ArrayList<Course> list = reg.getCoursesBySemester(sem);
        System.out.println("\n  Courses for Semester " + sem + ":");
        System.out.printf("  %-7s | %-45s | %4s | %-20s | %-30s | %-15s%n",
                "Code", "Title", "Cr", "Timings", "Professor", "Status");
        System.out.println("  " + "-".repeat(135));

        if (list.size() == 0) {
            System.out.println("  No courses found.");
            return;
        }

        for (int i = 0; i < list.size(); i++) {
            Course c = list.get(i);
            String status = "Open";
            if (reg.isRegistered(sid, c.getCourseCode())) {
                status = "[Registered]";
            } else if (c.isFull()) {
                status = "[Full]";
            }
            System.out.printf("  %-7s | %-45s | %-2dcr | %-20s | %-30s | %-15s%n",
                    c.getCourseCode(), c.getCourseTitle(), c.getCredits(),
                    c.getClassTimings(), c.getProfessorName(), status);
        }
        System.out.println("  " + "-".repeat(135));
    }

    protected void registerCourse(Scanner sc) throws CourseFullException {
        viewCourses();
        System.out.print("\n  Enter course code (0 to cancel): ");
        String code = sc.nextLine().trim().toUpperCase();
        if (code.equals("0")) return;

        Course c = reg.findCourse(code);
        if (c == null) {
            System.out.println("  Course not found.");
            return;
        }

        // must be same semester
        if (c.getSemester() != sem) {
            System.out.println("  This course is not for your semester.");
            return;
        }

        // already registered?
        if (reg.isRegistered(sid, code)) {
            System.out.println("  Already registered in " + code);
            return;
        }

        // check prerequisites
        ArrayList<String> prereqs = c.getPrerequisites();
        ArrayList<String> done = reg.getCompletedCourseCodes(sid);
        for (int i = 0; i < prereqs.size(); i++) {
            if (!done.contains(prereqs.get(i).toUpperCase())) {
                System.out.println("  Missing prerequisite: " + prereqs.get(i));
                return;
            }
        }

        // credit limit check
        int currentCr = reg.getOngoingCredits(sid, sem);
        if (currentCr + c.getCredits() > MAX_CR) {
            System.out.println("  Credit limit exceeded. You have " + currentCr + " credits already.");
            return;
        }

        // if full throw exception
        if (c.isFull()) {
            throw new CourseFullException(code, c.getCapacity());
        }

        // all good, enroll
        c.enrollStudent(sid);
        reg.addGradeRecord(new CourseGrade(sid, code, c.getCourseTitle(), c.getCredits(), sem));
        System.out.println("  Registered for " + code + " successfully!");
        System.out.println("  Total credits: " + (currentCr + c.getCredits()) + "/" + MAX_CR);
    }

    protected void viewSchedule() {
        ArrayList<CourseGrade> ongoing = reg.getOngoingCourses(sid);
        System.out.println("\n  Your Schedule - Semester " + sem + ":");

        if (ongoing.size() == 0) {
            System.out.println("  No courses registered yet.");
            return;
        }

        System.out.printf("  %-7s | %-45s | %-20s | %-10s | %-30s%n",
                "Code", "Title", "Timings", "Room", "Professor");
        System.out.println("  " + "-".repeat(125));

        for (int i = 0; i < ongoing.size(); i++) {
            CourseGrade cg = ongoing.get(i);
            Course c = reg.findCourse(cg.getCourseCode());
            if (c != null) {
                System.out.printf("  %-7s | %-45s | %-20s | %-10s | %-30s%n",
                        c.getCourseCode(), c.getCourseTitle(),
                        c.getClassTimings(), c.getLocation(), c.getProfessorName());
            }
        }
        System.out.println("  " + "-".repeat(125));
    }

    protected void viewProgress() {
        ArrayList<CourseGrade> all = reg.getGradesForStudent(sid);
        System.out.println("\n  Academic Progress:");

        if (all.size() == 0) {
            System.out.println("  No records found.");
            return;
        }

        double totalPoints = 0;
        int totalCr = 0;

        for (int s = 1; s <= sem; s++) {
            double semPts = 0;
            int semCr = 0;
            boolean printed = false;

            for (int i = 0; i < all.size(); i++) {
                CourseGrade cg = all.get(i);
                if (cg.getSemester() != s) continue;

                if (!printed) {
                    System.out.println("\n  Semester " + s + ":");
                    System.out.printf("  %-8s | %-30s | %s | %s | %s%n",
                            "Code", "Title", "Cr", "Grade", "GP");
                    System.out.println("  " + "-".repeat(70));
                    printed = true;
                }
                System.out.println(cg);
                if (cg.isCompleted()) {
                    semPts += cg.getGradePoint() * cg.getCredits();
                    semCr += cg.getCredits();
                }
            }

            if (semCr > 0) {
                double sgpa = semPts / semCr;
                System.out.printf("  SGPA for Sem %d: %.2f%n", s, sgpa);
                totalPoints += semPts;
                totalCr += semCr;
            }
        }

        if (totalCr > 0) {
            cgpa = totalPoints / totalCr;
            System.out.printf("%n  Overall CGPA: %.2f%n", cgpa);
        } else {
            System.out.println("  No grades assigned yet.");
        }
    }

    protected void dropCourse(Scanner sc) throws DropDeadlinePassedException {
        ArrayList<CourseGrade> ongoing = reg.getOngoingCourses(sid);
        System.out.println("\n  Drop a Course:");

        if (ongoing.size() == 0) {
            System.out.println("  No ongoing courses to drop.");
            return;
        }

        System.out.println("  Your current courses:");
        for (int i = 0; i < ongoing.size(); i++) {
            CourseGrade cg = ongoing.get(i);
            System.out.println("    " + cg.getCourseCode() + " - " + cg.getCourseTitle());
        }

        System.out.print("  Enter course code to drop (0 to cancel): ");
        String code = sc.nextLine().trim().toUpperCase();
        if (code.equals("0")) return;

        CourseGrade rec = reg.findGradeRecord(sid, code);
        if (rec == null) {
            System.out.println("  Not enrolled in " + code);
            return;
        }

        // if already graded, can't drop
        if (rec.isCompleted()) {
            throw new DropDeadlinePassedException(code, "Already graded with " + rec.getGrade());
        }

        boolean removed = reg.removeGradeRecord(sid, code);
        Course c = reg.findCourse(code);
        if (removed && c != null) {
            c.dropStudent(sid);
            System.out.println("  Dropped " + code + " successfully.");
        } else {
            System.out.println("  Could not drop. Contact admin.");
        }
    }

    protected void complaintsMenu(Scanner sc) {
        int ch = -1;
        while (ch != 0) {
            System.out.println("\n  Complaints:");
            System.out.println("  1. Submit Complaint");
            System.out.println("  2. View My Complaints");
            System.out.println("  0. Back");
            System.out.print("  Choice: ");

            try {
                ch = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                ch = -1;
            }

            if (ch == 1) {
                System.out.print("  Describe your issue: ");
                String desc = sc.nextLine().trim();
                if (desc.isEmpty()) {
                    System.out.println("  Cannot be empty.");
                } else {
                    Complaint comp = new Complaint(sid, getUserName(), desc);
                    reg.addComplaint(comp);
                    System.out.println("  Complaint #" + comp.getComplaintId() + " submitted.");
                }
            } else if (ch == 2) {
                ArrayList<Complaint> mine = reg.getComplaintsByStudent(sid);
                if (mine.size() == 0) {
                    System.out.println("  No complaints submitted.");
                } else {
                    for (Complaint comp : mine) {
                        System.out.println(comp);
                        System.out.println("  ---");
                    }
                }
            } else if (ch != 0) {
                System.out.println("  Invalid option.");
            }
        }
    }

    protected void giveFeedback(Scanner sc) {
        ArrayList<CourseGrade> done = reg.getCompletedGrades(sid);
        System.out.println("\n  Give Feedback (only for completed courses):");

        if (done.size() == 0) {
            System.out.println("  No completed courses yet.");
            return;
        }

        for (CourseGrade cg : done) {
            String tag = reg.hasFeedback(sid, cg.getCourseCode()) ? " [done]" : "";
            System.out.println("    " + cg.getCourseCode() + " - " + cg.getCourseTitle() + " (Grade: " + cg.getGrade() + ")" + tag);
        }

        System.out.print("  Enter course code (0 to cancel): ");
        String code = sc.nextLine().trim().toUpperCase();
        if (code.equals("0")) return;

        // check if its a completed course
        CourseGrade target = null;
        for (CourseGrade cg : done) {
            if (cg.getCourseCode().equals(code)) {
                target = cg;
                break;
            }
        }
        if (target == null) {
            System.out.println("  You haven't completed " + code);
            return;
        }

        if (reg.hasFeedback(sid, code)) {
            System.out.println("  Already submitted feedback for " + code);
            return;
        }

        FeedbackRecord fr = new FeedbackRecord(sid, getUserName(), code);
        boolean hasInput = false;

        System.out.print("  Give a rating? (y/n): ");
        String ans = sc.nextLine().trim();
        if (ans.equalsIgnoreCase("y")) {
            int r = 0;
            while (!Feedback.isValidRating(r)) {
                System.out.print("  Rating (1-5): ");
                try {
                    r = Integer.parseInt(sc.nextLine().trim());
                } catch (NumberFormatException e) {
                    r = 0;
                }
                if (!Feedback.isValidRating(r)) {
                    System.out.println("  Must be 1 to 5.");
                }
            }
            fr.setRating(r);
            hasInput = true;
            System.out.println("  Rating saved: " + r + "/5");
        }

        System.out.print("  Give a comment? (y/n): ");
        String ans2 = sc.nextLine().trim();
        if (ans2.equalsIgnoreCase("y")) {
            System.out.print("  Comment: ");
            String cmt = sc.nextLine().trim();
            if (!cmt.isEmpty()) {
                fr.setComment(cmt);
                hasInput = true;
                System.out.println("  Comment saved.");
            }
        }

        if (!hasInput) {
            System.out.println("  No feedback entered, nothing saved.");
            return;
        }

        reg.addFeedback(fr);
        System.out.println("  Feedback submitted for " + code + "!");
    }

    @Override
    public String toString() {
        return super.toString() + " | ID: " + sid + " | Sem: " + sem + " | CGPA: " + cgpa;
    }
}
