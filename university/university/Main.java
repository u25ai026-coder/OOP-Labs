package university;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        AuthService auth = new AuthService();
        CourseRegistry reg = new CourseRegistry();

        // wire admin to registry and auth at the start
        auth.getAdmin().setRegistry(reg);
        auth.getAdmin().setAuthService(auth);

        printBanner();

        boolean running = true;

        while (running) {
            printMainMenu();
            String pick = sc.nextLine().trim();

            if (pick.equals("1")) {

                boolean inApp = true;
                while (inApp) {
                    printRoleMenu();
                    String role = sc.nextLine().trim();

                    if (role.equals("1")) {
                        // student login or signup
                        printAuthMenu("Student");
                        String opt = sc.nextLine().trim();
                        User loggedIn = null;

                        if (opt.equals("1")) {
                            System.out.println("\n-- Student Sign Up --");
                            loggedIn = auth.runStudentSignUp(sc);
                        } else if (opt.equals("2")) {
                            System.out.println("\n-- Student Login --");
                            try {
                                loggedIn = auth.runLogin(sc);
                                if (loggedIn != null && !(loggedIn instanceof Student)) {
                                    System.out.println("  This is not a student account.");
                                    loggedIn = null;
                                }
                            } catch (InvalidLoginException e) {
                                System.out.println("  Login failed: " + e.getMessage());
                            }
                        }

                        // TA also extends Student so check TA first
                        if (loggedIn instanceof TeachingAssistant) {
                            TeachingAssistant ta = (TeachingAssistant) loggedIn;
                            ta.setRegistry(reg);
                            ta.showMenu();
                        } else if (loggedIn instanceof Student) {
                            Student s = (Student) loggedIn;
                            s.setRegistry(reg);
                            s.showMenu();
                        }

                    } else if (role.equals("2")) {
                        // professor login or signup
                        printAuthMenu("Professor");
                        String opt = sc.nextLine().trim();
                        User loggedIn = null;

                        if (opt.equals("1")) {
                            System.out.println("\n-- Professor Sign Up --");
                            loggedIn = auth.runProfessorSignUp(sc);
                        } else if (opt.equals("2")) {
                            System.out.println("\n-- Professor Login --");
                            try {
                                loggedIn = auth.runLogin(sc);
                                if (loggedIn != null && !(loggedIn instanceof Professor)) {
                                    System.out.println("  This is not a professor account.");
                                    loggedIn = null;
                                }
                            } catch (InvalidLoginException e) {
                                System.out.println("  Login failed: " + e.getMessage());
                            }
                        }

                        if (loggedIn instanceof Professor) {
                            Professor p = (Professor) loggedIn;
                            p.setRegistry(reg);
                            p.showMenu();
                        }

                    } else if (role.equals("3")) {
                        // teaching assistant login or signup
                        printAuthMenu("Teaching Assistant");
                        String opt = sc.nextLine().trim();
                        User loggedIn = null;

                        if (opt.equals("1")) {
                            System.out.println("\n-- TA Sign Up --");
                            loggedIn = auth.runTASignUp(sc);
                        } else if (opt.equals("2")) {
                            System.out.println("\n-- TA Login --");
                            try {
                                loggedIn = auth.runLogin(sc);
                                if (loggedIn != null && !(loggedIn instanceof TeachingAssistant)) {
                                    System.out.println("  This is not a TA account.");
                                    loggedIn = null;
                                }
                            } catch (InvalidLoginException e) {
                                System.out.println("  Login failed: " + e.getMessage());
                            }
                        }

                        if (loggedIn instanceof TeachingAssistant) {
                            TeachingAssistant ta = (TeachingAssistant) loggedIn;
                            ta.setRegistry(reg);
                            ta.showMenu();
                        }

                    } else if (role.equals("4")) {
                        // admin - no signup, just login
                        System.out.println("\n-- Admin Login --");
                        try {
                            Administrator a = auth.runAdminLogin(sc);
                            a.showMenu();
                        } catch (InvalidLoginException e) {
                            System.out.println("  Login failed: " + e.getMessage());
                        }

                    } else if (role.equals("0")) {
                        inApp = false;
                    } else {
                        System.out.println("  Invalid choice.");
                    }
                }

            } else if (pick.equals("0")) {
                System.out.println("\n  Goodbye!");
                running = false;
            } else {
                System.out.println("  Enter 1 or 0.");
            }
        }

        sc.close();
    }

    static void printBanner() {
        System.out.println("\n+==================================================+");
        System.out.println("|    SVNIT University Course Registration System   |");
        System.out.println("+==================================================+");
    }

    static void printMainMenu() {
        System.out.println("\n+==================================================+");
        System.out.println("|                   MAIN MENU                      |");
        System.out.println("+==================================================+");
        System.out.println("|  1. Enter the Application                        |");
        System.out.println("|  0. Exit the Application                         |");
        System.out.println("+==================================================+");
        System.out.print("Your choice: ");
    }

    static void printRoleMenu() {
        System.out.println("\n+==================================================+");
        System.out.println("|                   LOGIN AS                       |");
        System.out.println("+==================================================+");
        System.out.println("|  1. Student                                      |");
        System.out.println("|  2. Professor                                    |");
        System.out.println("|  3. Teaching Assistant                           |");
        System.out.println("|  4. Administrator                                |");
        System.out.println("|  0. Back                                         |");
        System.out.println("+==================================================+");
        System.out.print("Your choice: ");
    }

    static void printAuthMenu(String role) {
        System.out.println("\n+==================================================+");
        System.out.printf("|  %-48s|%n", role + " -- Auth");
        System.out.println("+==================================================+");
        System.out.println("|  1. Sign Up                                      |");
        System.out.println("|  2. Log In                                       |");
        System.out.println("+==================================================+");
        System.out.print("Your choice: ");
    }
}
