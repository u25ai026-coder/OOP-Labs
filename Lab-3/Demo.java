// Abstract class Figure
abstract class Figure {
    protected final double pi = 3.1420;
    protected double a; // area
    protected double v; // volume

    public abstract void calcArea();
    public abstract void calcVol();
    public abstract void DispArea();
    public abstract void Dispvol();
}

// Cone class
class Cone extends Figure {
    private double r, s;

    public Cone(double r, double s) {
        this.r = r;
        this.s = s;
    }

    public void calcArea() {
        a = pi * r * (r + s);
    }

    public void calcVol() {
        v = (1.0 / 3.0) * pi * r * r * s;
    }

    public void DispArea() {
        System.out.println("Cone Area = " + a);
    }

    public void Dispvol() {
        System.out.println("Cone Volume = " + v);
    }
}

// Sphere class
class Sphere extends Figure {
    private double r;

    public Sphere(double r) {
        this.r = r;
    }

    public void calcArea() {
        a = 4 * pi * r * r;
    }

    public void calcVol() {
        v = (4.0 / 3.0) * pi * r * r * r;
    }

    public void DispArea() {
        System.out.println("Sphere Area = " + a);
    }

    public void Dispvol() {
        System.out.println("Sphere Volume = " + v);
    }
}

// Cylinder class
class Cylinder extends Figure {
    private double r, h;

    public Cylinder(double r, double h) {
        this.r = r;
        this.h = h;
    }

    public void calcArea() {
        a = 2 * pi * r * (r + h);
    }

    public void calcVol() {
        v = pi * r * r * h;
    }

    public void DispArea() {
        System.out.println("Cylinder Area = " + a);
    }

    public void Dispvol() {
        System.out.println("Cylinder Volume = " + v);
    }
}

// Demo class with main method
public class Demo {
    public static void main(String[] args) {

        Cone cone = new Cone(5, 10);
        cone.calcArea();
        cone.calcVol();
        cone.DispArea();
        cone.Dispvol();

        System.out.println();

        Sphere sphere = new Sphere(7);
        sphere.calcArea();
        sphere.calcVol();
        sphere.DispArea();
        sphere.Dispvol();

        System.out.println();

        Cylinder cylinder = new Cylinder(4, 8);
        cylinder.calcArea();
        cylinder.calcVol();
        cylinder.DispArea();
        cylinder.Dispvol();
    }
}
