package university;

// bad email or password
public class InvalidLoginException extends Exception {

    public InvalidLoginException(String email, String reason) {
        super("Login failed for " + email + ": " + reason);
    }
}
