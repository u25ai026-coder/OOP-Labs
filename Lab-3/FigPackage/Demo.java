package FigPackage;

public class Demo {
    public static void main(String[] args) {

        Cone cone = new Cone(5, 6, 10);
        cone.calcArea();
        cone.calcVolume();
        cone.dispArea();
        cone.dispVolume();

        System.out.println();

        Sphere sphere = new Sphere(7);
        sphere.calcArea();
        sphere.calcVolume();
        sphere.dispArea();
        sphere.dispVolume();

        System.out.println();

        Cylinder cylinder = new Cylinder(4, 8);
        cylinder.calcArea();
        cylinder.calcVolume();
        cylinder.dispArea();
        cylinder.dispVolume();
    }
}
