package university;

// course is at max capacity
public class CourseFullException extends Exception {

    public CourseFullException(String code, int cap) {
        super("Course " + code + " is full! Max capacity is " + cap);
    }
}
