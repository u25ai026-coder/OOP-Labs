package university;

import java.util.ArrayList;

public class Course {

    private String code;
    private String title;
    private int credits;
    private int sem;
    private String profName;
    private String timings;
    private String room;
    private int capacity;
    private String syllabus;
    private ArrayList<String> prereqs;
    private ArrayList<Integer> enrolled; // student IDs enrolled

    public Course(String code, String title, int credits, int sem, int capacity, String timings, String room) {
        this.code = code;
        this.title = title;
        this.credits = credits;
        this.sem = sem;
        this.capacity = capacity;
        this.timings = timings;
        this.room = room;
        this.profName = "TBA";
        this.syllabus = "Not set";
        prereqs = new ArrayList<String>();
        enrolled = new ArrayList<Integer>();
    }

    public String getCourseCode() { return code; }
    public String getCourseTitle() { return title; }
    public int getCredits() { return credits; }
    public int getSemester() { return sem; }
    public String getProfessorName() { return profName; }
    public String getClassTimings() { return timings; }
    public String getLocation() { return room; }
    public int getCapacity() { return capacity; }
    public String getSyllabus() { return syllabus; }
    public ArrayList<String> getPrerequisites() { return prereqs; }
    public ArrayList<Integer> getEnrolledStudentIds() { return enrolled; }

    public int getEnrolledCount() { return enrolled.size(); }

    public boolean isFull() {
        return enrolled.size() >= capacity;
    }

    public void setProfessorName(String p) { profName = p; }
    public void setClassTimings(String t) { timings = t; }
    public void setLocation(String r) { room = r; }
    public void setCapacity(int c) { capacity = c; }
    public void setSyllabus(String s) { syllabus = s; }
    public void setCourseTitle(String t) { title = t; }
    public void setSemester(int s) { sem = s; }
    public void setCredits(int c) { credits = c; }

    public void addPrerequisite(String c) { prereqs.add(c); }
    public void removePrerequisite(String c) { prereqs.remove(c); }

    public boolean enrollStudent(int sid) {
        if (isFull()) return false;
        if (enrolled.contains(sid)) return false;
        enrolled.add(sid);
        return true;
    }

    public boolean dropStudent(int sid) {
        return enrolled.remove(Integer.valueOf(sid));
    }

    public boolean isStudentEnrolled(int sid) {
        return enrolled.contains(sid);
    }

    // short one line display for tables
    public String toShortString() {
        return String.format("  %-7s | %-40s | %dcr | %-20s | %-30s | %d/%d seats",
                code, title, credits, timings, profName, getEnrolledCount(), capacity);
    }

    @Override
    public String toString() {
        String prereqStr = "None";
        if (prereqs.size() > 0) {
            prereqStr = String.join(", ", prereqs);
        }
        return "\n  Code      : " + code +
               "\n  Title     : " + title +
               "\n  Credits   : " + credits +
               "\n  Semester  : " + sem +
               "\n  Professor : " + profName +
               "\n  Timings   : " + timings +
               "\n  Room      : " + room +
               "\n  Seats     : " + getEnrolledCount() + "/" + capacity +
               "\n  Prereqs   : " + prereqStr +
               "\n  Syllabus  : " + syllabus;
    }
}
