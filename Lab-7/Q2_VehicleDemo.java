import java.util.Scanner;

class Vehicle
{
    String name;
    int speed;

    Vehicle(String name, int speed)
    {
        this.name = name;
        this.speed = speed;
    }

    void display()
    {
        System.out.println("Vehicle: " + name);
    }
}

class Car extends Vehicle
{
    Car(String name, int speed)
    {
        super(name, speed);
    }

    void maxSpeed()
    {
        System.out.println("Car Max Speed: " + speed + " km/h");
    }
}

class Bicycle extends Vehicle
{
    Bicycle(String name, int speed)
    {
        super(name, speed);
    }

    void maxSpeed()
    {
        System.out.println("Bicycle Max Speed: " + speed + " km/h");
    }
}

class Scooter extends Vehicle
{
    Scooter(String name, int speed)
    {
        super(name, speed);
    }

    void maxSpeed()
    {
        System.out.println("Scooter Max Speed: " + speed + " km/h");
    }
}

public class Q2_VehicleDemo
{
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Car speed: ");
        int carSpeed = sc.nextInt();

        System.out.print("Enter Bicycle speed: ");
        int bicycleSpeed = sc.nextInt();

        System.out.print("Enter Scooter speed: ");
        int scooterSpeed = sc.nextInt();

        Car c = new Car("Car", carSpeed);
        Bicycle b = new Bicycle("Bicycle", bicycleSpeed);
        Scooter s = new Scooter("Scooter", scooterSpeed);

        c.display();
        c.maxSpeed();

        b.display();
        b.maxSpeed();

        s.display();
        s.maxSpeed();
    }
}