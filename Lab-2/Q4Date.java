public class Q4Date {

    // instance variables
    private int month;
    private int day;
    private int year;

    // constructor
    public Q4Date(int month, int day, int year) {
        this.month = month;
        this.day = day;
        this.year = year;
    }

    // set methods
    public void setMonth(int month) {
        this.month = month;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setYear(int year) {
        this.year = year;
    }

    // get methods
    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public int getYear() {
        return year;
    }

    // display method
    public void displayDate() {
        System.out.println(month + "/" + day + "/" + year);
    }
}
