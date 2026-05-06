package university;

public class CourseGrade {

    private int sid;
    private String courseCode;
    private String courseTitle;
    private int credits;
    private int sem;
    private String grade;
    private double gp; // grade point

    public CourseGrade(int sid, String code, String title, int credits, int sem) {
        this.sid = sid;
        this.courseCode = code;
        this.courseTitle = title;
        this.credits = credits;
        this.sem = sem;
        this.grade = "Ongoing";
        this.gp = 0.0;
    }

    public int getStudentId() { return sid; }
    public String getCourseCode() { return courseCode; }
    public String getCourseTitle() { return courseTitle; }
    public int getCredits() { return credits; }
    public int getSemester() { return sem; }
    public String getGrade() { return grade; }
    public double getGradePoint() { return gp; }

    public boolean isCompleted() {
        return !grade.equals("Ongoing");
    }

    public void assignGrade(String g) {
        this.grade = g;
        this.gp = convertGrade(g);
    }

    // grade to points conversion
    public static double convertGrade(String g) {
        if (g.equals("O")) return 10.0;
        else if (g.equals("A+")) return 9.0;
        else if (g.equals("A")) return 8.0;
        else if (g.equals("B+")) return 7.0;
        else if (g.equals("B")) return 6.0;
        else if (g.equals("C")) return 5.0;
        else if (g.equals("F")) return 0.0;
        else return 0.0;
    }

    @Override
    public String toString() {
        return String.format("  %-8s | %-30s | %d cr | Grade: %-4s | GP: %.1f",
                courseCode, courseTitle, credits, grade, gp);
    }
}
