"University Course Registration System"
Maitri Upadhyay
U25AI026
==========================

A Java console app for managing course registration at SVNIT. Students can
register/drop courses, professors can manage their courses, TAs get both
student and TA access, and the admin manages everything.


HOW TO RUN
----------
Open a terminal in this folder.
Compile:
    javac university/*.java

Run:
    java university.Main


ADMIN LOGIN
-----------
The admin account is hardcoded (no sign up needed):
    Email   : admin@svnit.ac.in
    Password: Admin@123


ASSUMPTIONS
-----------
- All students are placed in Semester 2 (AI branch) by default on sign up.
- Credits per semester are capped at 20.
- Course credits can only be 2 or 4.
- Students can only register for courses matching their current semester.
- A student cannot drop a course once it has been graded.
- Feedback can only be given for completed (graded) courses.
- The admin can advance a student's semester only after all courses are graded.
- Data is not saved to disk, so everything resets when the program exits.
- Student IDs start at 1001, Professor IDs at 2001, TA IDs at 4001.


OOP CONCEPTS USED
-----------------

1. Abstraction
   User is an abstract class with an abstract method showMenu(). You can't create a User directly, it forces every subclass to define their of menu behavior.

2. Inheritance
   - Student extends User
   - Professor extends User
   - Administrator extends User
   - TeachingAssistant extends Student (so a TA is also a Student)
   Each subclass inherits common fields like name, email, and password from User.

3. Encapsulation
   All fields in every class are private (or protected where subclass access is needed). They are only accessed through getters and setters. For example,
   the password in User is never directly exposed -- only checkPassword() is public.

4. Polymorphism
   - showMenu() is overridden in Student, Professor, TeachingAssistant, and
     Administrator -- each shows a different menu.
   - toString() is overridden in every class to give a meaningful output.
   - instanceof is used in Main and Administrator to check the actual type at
     runtime (e.g., to tell apart a TA from a regular Student).

5. Exception Handling (Custom Exceptions)
   Three custom checked exceptions were made:
   - CourseFullException -- thrown when a student tries to register for a full course.
   - DropDeadlinePassedException -- thrown when trying to drop an already-graded course.
   - InvalidLoginException -- thrown when login fails due to wrong email or password.
   All three extend Exception and are handled with try-catch blocks in the menus.
