// Parent class
class Figure {
    double r, a, v;

    public void dispArea() {
        System.out.println("Area = " + a);
    }

    public void dispVolume() {
        System.out.println("Volume = " + v);
    }
}

// Child class
class Cone extends Figure {
    double h, s;
    final double pi = 3.142;

    public void calcArea() {
        a = pi * r * s;
    }

    public void calcVolume() {       
        v = (pi * r * r * h) / 3;
    }
}

// Main class
public class InheritanceDemo {
    public static void main(String[] args) {
        Cone c = new Cone();

        c.r = 7;
        c.h = 10;
        c.s = 12;

        c.calcArea();
        c.calcVolume();

        c.dispArea();
        c.dispVolume();
    }
}
