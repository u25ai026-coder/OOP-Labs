public class Rectangle {

    double x, y, width, height;

    public Rectangle(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    // edges
    double left()   { return x - width / 2; }
    double right()  { return x + width / 2; }
    double top()    { return y + height / 2; }
    double bottom() { return y - height / 2; }

    // check if this contains other
    public boolean contains(Rectangle r) {
        return r.left() >= left() &&
               r.right() <= right() &&
               r.top() <= top() &&
               r.bottom() >= bottom();
    }

    // check overlap
    public boolean overlaps(Rectangle r) {
        return !(r.left() > right() ||
                 r.right() < left() ||
                 r.top() < bottom() ||
                 r.bottom() > top());
    }
}
