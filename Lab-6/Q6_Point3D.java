import java.util.Scanner;

class Point3D
{
    int x, y, z;

    Point3D()
    {
        x = 0;
        y = 0;
        z = 0;
    }

    Point3D(int x, int y, int z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    void input()
    {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter x, y, z: ");
        x = sc.nextInt();
        y = sc.nextInt();
        z = sc.nextInt();
    }

    void display()
    {
        System.out.println("(" + x + ", " + y + ", " + z + ")");
    }

    double distance(Point3D p)
    {
        int dx = this.x - p.x;
        int dy = this.y - p.y;
        int dz = this.z - p.z;

        return Math.sqrt(dx*dx + dy*dy + dz*dz);
    }
}

public class Q6_Point3D
{
    public static void main(String[] args)
    {
        Point3D p1 = new Point3D();
        Point3D p2 = new Point3D();

        System.out.println("Enter first point:");
        p1.input();

        System.out.println("Enter second point:");
        p2.input();

        System.out.print("Point 1: ");
        p1.display();

        System.out.print("Point 2: ");
        p2.display();

        double dist = p1.distance(p2);
        System.out.println("Distance between points: " + dist);
    }
}