package university;

import java.util.ArrayList;

// single instance shared across all users
public class CourseRegistry {

    private ArrayList<Course> courses = new ArrayList<Course>();
    private ArrayList<CourseGrade> grades = new ArrayList<CourseGrade>();
    private ArrayList<Complaint> complaints = new ArrayList<Complaint>();
    private ArrayList<FeedbackRecord> feedbacks = new ArrayList<FeedbackRecord>();

    public CourseRegistry() {
        loadCourses();
    }

    public void addCourse(Course c) {
        courses.add(c);
    }

    public boolean removeCourse(String code) {
        int idx = -1;
        for (int i = 0; i < courses.size(); i++) {
            if (courses.get(i).getCourseCode().equalsIgnoreCase(code)) {
                idx = i;
                break;
            }
        }
        if (idx != -1) {
            courses.remove(idx);
            return true;
        }
        return false;
    }

    public Course findCourse(String code) {
        for (int i = 0; i < courses.size(); i++) {
            if (courses.get(i).getCourseCode().equalsIgnoreCase(code)) {
                return courses.get(i);
            }
        }
        return null;
    }

    public ArrayList<Course> getAllCourses() { return courses; }

    public ArrayList<Course> getCoursesBySemester(int sem) {
        ArrayList<Course> result = new ArrayList<Course>();
        for (Course c : courses) {
            if (c.getSemester() == sem) {
                result.add(c);
            }
        }
        return result;
    }

    // grade book methods
    public void addGradeRecord(CourseGrade cg) {
        grades.add(cg);
    }

    public boolean removeGradeRecord(int sid, String code) {
        int idx = -1;
        for (int i = 0; i < grades.size(); i++) {
            CourseGrade cg = grades.get(i);
            if (cg.getStudentId() == sid && cg.getCourseCode().equalsIgnoreCase(code) && !cg.isCompleted()) {
                idx = i;
                break;
            }
        }
        if (idx != -1) {
            grades.remove(idx);
            return true;
        }
        return false;
    }

    public ArrayList<CourseGrade> getGradesForStudent(int sid) {
        ArrayList<CourseGrade> result = new ArrayList<CourseGrade>();
        for (CourseGrade cg : grades) {
            if (cg.getStudentId() == sid) result.add(cg);
        }
        return result;
    }

    public ArrayList<CourseGrade> getCompletedGrades(int sid) {
        ArrayList<CourseGrade> result = new ArrayList<CourseGrade>();
        for (CourseGrade cg : grades) {
            if (cg.getStudentId() == sid && cg.isCompleted()) result.add(cg);
        }
        return result;
    }

    public ArrayList<CourseGrade> getOngoingCourses(int sid) {
        ArrayList<CourseGrade> result = new ArrayList<CourseGrade>();
        for (CourseGrade cg : grades) {
            if (cg.getStudentId() == sid && !cg.isCompleted()) result.add(cg);
        }
        return result;
    }

    public boolean isRegistered(int sid, String code) {
        for (CourseGrade cg : grades) {
            if (cg.getStudentId() == sid && cg.getCourseCode().equalsIgnoreCase(code)) {
                return true;
            }
        }
        return false;
    }

    // current credit load for a student in a sem
    public int getOngoingCredits(int sid, int sem) {
        int total = 0;
        for (CourseGrade cg : grades) {
            if (cg.getStudentId() == sid && cg.getSemester() == sem && !cg.isCompleted()) {
                total += cg.getCredits();
            }
        }
        return total;
    }

    // only passed courses (grade point > 0)
    public ArrayList<String> getCompletedCourseCodes(int sid) {
        ArrayList<String> result = new ArrayList<String>();
        for (CourseGrade cg : grades) {
            if (cg.getStudentId() == sid && cg.isCompleted() && cg.getGradePoint() > 0) {
                result.add(cg.getCourseCode().toUpperCase());
            }
        }
        return result;
    }

    public CourseGrade findGradeRecord(int sid, String code) {
        for (CourseGrade cg : grades) {
            if (cg.getStudentId() == sid && cg.getCourseCode().equalsIgnoreCase(code)) {
                return cg;
            }
        }
        return null;
    }

    public ArrayList<CourseGrade> getAllGrades() { return grades; }

    // complaints
    public void addComplaint(Complaint c) { complaints.add(c); }
    public ArrayList<Complaint> getAllComplaints() { return complaints; }

    public ArrayList<Complaint> getComplaintsByStudent(int sid) {
        ArrayList<Complaint> result = new ArrayList<Complaint>();
        for (Complaint c : complaints) {
            if (c.getStudentId() == sid) result.add(c);
        }
        return result;
    }

    public Complaint findComplaint(int cid) {
        for (Complaint c : complaints) {
            if (c.getComplaintId() == cid) return c;
        }
        return null;
    }

    // feedback methods
    public void addFeedback(FeedbackRecord fr) { feedbacks.add(fr); }

    public ArrayList<FeedbackRecord> getFeedbackForCourse(String code) {
        ArrayList<FeedbackRecord> result = new ArrayList<FeedbackRecord>();
        for (FeedbackRecord fr : feedbacks) {
            if (fr.getCourseCode().equalsIgnoreCase(code)) result.add(fr);
        }
        return result;
    }

    public boolean hasFeedback(int sid, String code) {
        for (FeedbackRecord fr : feedbacks) {
            if (fr.getStudentId() == sid && fr.getCourseCode().equalsIgnoreCase(code)) {
                return true;
            }
        }
        return false;
    }

    // pre-load the actual 2nd sem AI courses
    private void loadCourses() {

        Course c1 = new Course("AI-102", "Data Structure", 4, 2, 60, "Mon/Wed 08:30-10:30", "Room 201");
        c1.setProfessorName("Dr. Rahul Shrivastava");

        Course c2 = new Course("AI-106", "Object-Oriented Programming", 4, 2, 60, "Tue/Thu 08:30-10:30", "Room 202");
        c2.setProfessorName("Dr. Praveen Kumar Chandaliya");

        Course c3 = new Course("MA-106", "Linear Algebra and Statistics", 4, 2, 60, "Mon/Wed 10:30-12:30", "Room 203");
        c3.setProfessorName("Raj Kamal Maurya");

        Course c4 = new Course("EC-106", "Digital Electronics and Logical Design", 4, 2, 60, "Tue/Thu 10:30-12:30", "Room 204");
        c4.setProfessorName("Sandeep Mishra");

        Course c5 = new Course("HS-120", "Indian Value System and Social Consciousness", 2, 2, 80, "Fri 08:30-10:30", "Room 101");
        c5.setProfessorName("Priyanka Gamit");

        Course c6 = new Course("EG-110", "Energy and Environmental Engineering", 2, 2, 80, "Fri 10:30-12:30", "Room 102");
        c6.setProfessorName("Rupal Waghwala");

        courses.add(c1);
        courses.add(c2);
        courses.add(c3);
        courses.add(c4);
        courses.add(c5);
        courses.add(c6);
    }
}
