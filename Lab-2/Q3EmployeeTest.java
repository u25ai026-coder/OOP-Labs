public class Q3EmployeeTest {

    public static void main(String[] args) {

        // create two employee objects
        Q3Employee emp1 = new Q3Employee("John", "Doe", 30000);
        Q3Employee emp2 = new Q3Employee("Jane", "Smith", 40000);

        // display yearly salary
        System.out.println("Yearly Salary Before Hike:");
        System.out.println(emp1.getName() + " " + emp1.getLastName() + ": " + emp1.getYearlySalary());
        System.out.println(emp2.getName() + " " + emp2.getLastName() + ": " + emp2.getYearlySalary());

        // give 10% hike
        emp1.setMonthlySalary(emp1.getMonthlySalary() * 1.10);
        emp2.setMonthlySalary(emp2.getMonthlySalary() * 1.10);

        // display yearly salary after hike
        System.out.println("\nYearly Salary After 10% Hike:");
        System.out.println(emp1.getName() + " " + emp1.getLastName() + ": " + emp1.getYearlySalary());
        System.out.println(emp2.getName() + " " + emp2.getLastName() + ": " + emp2.getYearlySalary());
    }
}
