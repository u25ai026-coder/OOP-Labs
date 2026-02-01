package FigPackage;

public class Cylinder extends Figure {
    private double h;

    public Cylinder(double r, double h) {
        this.r = r;
        this.h = h;
    }

    public void calcArea() {
        a = (2 * pi * r * r) + (2 * pi * r * h);
    }

    public void calcVolume() {
        v = pi * r * r * h;
    }

    public void dispArea() {
        System.out.println("Cylinder Area = " + a);
    }

    public void dispVolume() {
        System.out.println("Cylinder Volume = " + v);
    }
}
