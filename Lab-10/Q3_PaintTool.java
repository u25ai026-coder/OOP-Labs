import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Q3_PaintTool extends Frame implements MouseListener, MouseMotionListener
{
    int x1, y1, x2, y2;
    ArrayList<int[]> lines = new ArrayList<>();

    Q3_PaintTool()
    {
        setTitle("Paint Tool");
        setSize(600, 500);
        setVisible(true);

        addMouseListener(this);
        addMouseMotionListener(this);

        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        });
    }

    public void mousePressed(MouseEvent e)
    {
        x1 = e.getX();
        y1 = e.getY();
    }

    public void mouseDragged(MouseEvent e)
    {
        x2 = e.getX();
        y2 = e.getY();
        lines.add(new int[]{x1, y1, x2, y2});
        x1 = x2;
        y1 = y2;
        repaint();
    }

    public void paint(Graphics g)
    {
        g.setColor(Color.BLUE);
        for (int[] line : lines)
        {
            g.drawLine(line[0], line[1], line[2], line[3]);
        }
    }

    public void mouseReleased(MouseEvent e) {}
    public void mouseClicked(MouseEvent e)  {}
    public void mouseEntered(MouseEvent e)  {}
    public void mouseExited(MouseEvent e)   {}
    public void mouseMoved(MouseEvent e)    {}

    public static void main(String[] args)
    {
        new Q3_PaintTool();
    }
}
