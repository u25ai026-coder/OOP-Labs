package university;

// T is Integer for rating, String for comment
public class Feedback<T> {

    private T value;
    private String type; // "rating" or "comment"
    private String byStudent;

    public Feedback(T value, String type, String byStudent) {
        this.value = value;
        this.type = type;
        this.byStudent = byStudent;
    }

    public T getFeedbackValue() { return value; }
    public String getFeedbackType() { return type; }
    public String getSubmittedBy() { return byStudent; }

    // 1-5 only
    public static boolean isValidRating(Object val) {
        if (val instanceof Integer) {
            int r = (Integer) val;
            if (r >= 1 && r <= 5) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        String display;
        if (type.equals("rating")) {
            display = value + "/5 stars";
        } else {
            display = "\"" + value + "\"";
        }
        return "  [" + type + "] " + display + "  -- by " + byStudent;
    }
}
