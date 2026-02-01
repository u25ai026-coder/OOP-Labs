package FigPackage;

public class Cone extends Figure {
    private double s, h;

    public Cone(double r, double s, double h) {
        this.r = r;
        this.s = s;
        this.h = h;
    }

    public void calcArea() {
        a = (pi * r * s) + (pi * r * r);
    }

    public void calcVolume() {
        v = (pi * r * r * h) / 3;
    }

    public void dispArea() {
        System.out.println("Cone Area = " + a);
    }

    public void dispVolume() {
        System.out.println("Cone Volume = " + v);
    }
}
