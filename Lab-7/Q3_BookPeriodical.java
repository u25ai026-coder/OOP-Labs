import java.util.Scanner;

class Book
{
    int bookId;
    String title;
    String author;
    double price;

    Book(int bookId, String title, String author, double price)
    {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.price = price;
    }

    void display()
    {
        System.out.println("Book ID: " + bookId);
        System.out.println("Title: " + title);
        System.out.println("Author: " + author);
        System.out.println("Price: " + price);
    }
}

class Periodical extends Book
{
    String period;

    Periodical(int bookId, String title, String author, double price, String period)
    {
        super(bookId, title, author, price);
        this.period = period;
    }

    void modify(double newPrice, String newPeriod)
    {
        price = newPrice;
        period = newPeriod;
    }

    void displayPeriodical()
    {
        display();
        System.out.println("Period: " + period);
    }
}

public class Q3_BookPeriodical
{
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Book ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter Title: ");
        String title = sc.nextLine();

        System.out.print("Enter Author: ");
        String author = sc.nextLine();

        System.out.print("Enter Price: ");
        double price = sc.nextDouble();
        sc.nextLine();

        System.out.print("Enter Period (weekly/monthly): ");
        String period = sc.nextLine();

        Periodical p = new Periodical(id, title, author, price, period);

        System.out.println("\nOriginal Details:");
        p.displayPeriodical();

        System.out.print("\nEnter new Price: ");
        double newPrice = sc.nextDouble();
        sc.nextLine();

        System.out.print("Enter new Period: ");
        String newPeriod = sc.nextLine();

        p.modify(newPrice, newPeriod);

        System.out.println("\nUpdated Details:");
        p.displayPeriodical();
    }
}