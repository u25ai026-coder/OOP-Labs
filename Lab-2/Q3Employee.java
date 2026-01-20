public class Q3Employee {

    // instance variables
    private String name;
    private String lastName;
    private double monthlySalary;

    // constructor
    public Q3Employee(String name, String lastName, double monthlySalary) {
        this.name = name;
        this.lastName = lastName;

        if (monthlySalary > 0) {
            this.monthlySalary = monthlySalary;
        } else {
            this.monthlySalary = 0.0;
        }
    }

    // set methods
    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setMonthlySalary(double monthlySalary) {
        if (monthlySalary > 0) {
            this.monthlySalary = monthlySalary;
        }
    }

    // get methods
    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public double getMonthlySalary() {
        return monthlySalary;
    }

    // yearly salary method
    public double getYearlySalary() {
        return monthlySalary * 12;
    }
}
