package university;

// rating, comment, or both
public class FeedbackRecord {

    private int sid;
    private String sName;
    private String courseCode;

    private Feedback<Integer> rating; // numeric 1-5
    private Feedback<String> comment; // text feedback

    public FeedbackRecord(int sid, String sName, String courseCode) {
        this.sid = sid;
        this.sName = sName;
        this.courseCode = courseCode;
        this.rating = null;
        this.comment = null;
    }

    public void setRating(int r) {
        rating = new Feedback<Integer>(r, "rating", sName);
    }

    public void setComment(String c) {
        comment = new Feedback<String>(c, "comment", sName);
    }

    public int getStudentId() { return sid; }
    public String getStudentName() { return sName; }
    public String getCourseCode() { return courseCode; }
    public Feedback<Integer> getRatingFeedback() { return rating; }
    public Feedback<String> getCommentFeedback() { return comment; }

    public boolean hasRating() { return rating != null; }
    public boolean hasComment() { return comment != null; }

    @Override
    public String toString() {
        String out = "  Student: " + sName + " | Course: " + courseCode + "\n";
        if (rating != null) {
            out += "  " + rating.toString() + "\n";
        }
        if (comment != null) {
            out += "  " + comment.toString() + "\n";
        }
        return out;
    }
}
