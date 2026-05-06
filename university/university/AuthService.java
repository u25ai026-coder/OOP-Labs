package university;

import java.util.ArrayList;
import java.util.Scanner;

public class AuthService {

    private ArrayList<User> users = new ArrayList<User>();
    private int nextSid = 1001;
    private int nextPid = 2001;
    private int nextTid = 4001;

    private Administrator admin = new Administrator();

    public Student signUpStudent(String name, String email, String pass) {
        if (findByEmail(email) != null) {
            System.out.println("  Email already in use.");
            return null;
        }
        Student s = new Student(name, email, pass, nextSid++);
        users.add(s);
        System.out.println("  Account created! Student ID: " + s.getStudentId());
        return s;
    }

    public Professor signUpProfessor(String name, String email, String pass, String dept) {
        if (findByEmail(email) != null) {
            System.out.println("  Email already in use.");
            return null;
        }
        Professor p = new Professor(name, email, pass, nextPid++, dept);
        users.add(p);
        System.out.println("  Account created! Professor ID: " + p.getProfessorId());
        return p;
    }

    public TeachingAssistant signUpTA(String name, String email, String pass, String assignedCourse) {
        if (findByEmail(email) != null) {
            System.out.println("  Email already in use.");
            return null;
        }
        TeachingAssistant ta = new TeachingAssistant(name, email, pass, nextSid++, nextTid++, assignedCourse);
        users.add(ta);
        System.out.println("  TA account created! ID: " + ta.getStudentId() + " | TA ID: " + ta.getTaId());
        return ta;
    }

    // throws on wrong email/password
    public User loginStudentOrProfessor(String email, String pass) throws InvalidLoginException {
        User u = findByEmail(email);
        if (u == null) {
            throw new InvalidLoginException(email, "No account with this email.");
        }
        if (!u.checkPassword(pass)) {
            throw new InvalidLoginException(email, "Wrong password.");
        }
        System.out.println("  Welcome back, " + u.getUserName() + "!");
        return u;
    }

    public Administrator loginAdmin(String pass) throws InvalidLoginException {
        if (!admin.checkPassword(pass)) {
            throw new InvalidLoginException("admin", "Wrong password.");
        }
        System.out.println("  Admin login successful.");
        return admin;
    }

    private User findByEmail(String email) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserEmail().equalsIgnoreCase(email)) {
                return users.get(i);
            }
        }
        return null;
    }

    public ArrayList<User> getAllUsers() { return users; }
    public Administrator getAdmin() { return admin; }

    public Student runStudentSignUp(Scanner sc) {
        System.out.print("  Name     : ");
        String name = sc.nextLine();
        System.out.print("  Email    : ");
        String email = sc.nextLine();
        System.out.print("  Password : ");
        String pass = sc.nextLine();
        return signUpStudent(name.trim(), email.trim(), pass.trim());
    }

    public Professor runProfessorSignUp(Scanner sc) {
        System.out.print("  Name       : ");
        String name = sc.nextLine();
        System.out.print("  Email      : ");
        String email = sc.nextLine();
        System.out.print("  Password   : ");
        String pass = sc.nextLine();
        System.out.print("  Department : ");
        String dept = sc.nextLine();
        return signUpProfessor(name.trim(), email.trim(), pass.trim(), dept.trim());
    }

    public TeachingAssistant runTASignUp(Scanner sc) {
        System.out.print("  Name           : ");
        String name = sc.nextLine();
        System.out.print("  Email          : ");
        String email = sc.nextLine();
        System.out.print("  Password       : ");
        String pass = sc.nextLine();
        System.out.print("  Assigned Course: ");
        String course = sc.nextLine();
        return signUpTA(name.trim(), email.trim(), pass.trim(), course.trim());
    }

    public User runLogin(Scanner sc) throws InvalidLoginException {
        System.out.print("  Email    : ");
        String email = sc.nextLine().trim();
        System.out.print("  Password : ");
        String pass = sc.nextLine().trim();
        return loginStudentOrProfessor(email, pass);
    }

    public Administrator runAdminLogin(Scanner sc) throws InvalidLoginException {
        System.out.print("  Password : ");
        String pass = sc.nextLine().trim();
        return loginAdmin(pass);
    }
}
