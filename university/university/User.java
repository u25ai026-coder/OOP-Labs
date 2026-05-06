package university;

public abstract class User {

    private String name;
    private String email;
    private String password;
    private String role;

    public User(String name, String email, String password, String role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public String getUserName() {return name;}
    public String getUserEmail() {return email;}
    public String getUserRole() {return role;}

    // password is not exposed directly, just checking
    public boolean checkPassword(String input) {
        return this.password.equals(input);
    }

    public void setUserName(String n) {this.name = n;}
    public void setUserEmail(String e) {this.email = e;}
    public void setPassWord(String p) {this.password = p;}
    public void setUserRole(String r) {this.role = r;}

    public abstract void showMenu();

    @Override
    public String toString() {
        return "[" + role + "] " + name + " (" + email + ")";
    }
}