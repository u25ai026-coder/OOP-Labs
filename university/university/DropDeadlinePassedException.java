package university;

// can't drop after grading
public class DropDeadlinePassedException extends Exception {

    public DropDeadlinePassedException(String code, String reason) {
        super("Cannot drop " + code + ": " + reason);
    }
}
