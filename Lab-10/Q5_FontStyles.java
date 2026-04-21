import java.awt.*;
import javax.swing.*;

public class Q5_FontStyles extends JPanel
{
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Font plain  = new Font("Serif", Font.PLAIN, 20);
        Font bold   = new Font("SansSerif", Font.BOLD, 30);
        Font italic = new Font("Monospaced", Font.ITALIC, 25);

        g.setFont(plain);
        g.setColor(Color.RED);
        g.drawString("Your Name", 50, 60);

        g.setFont(bold);
        g.setColor(Color.BLUE);
        g.drawString("Your Name", 50, 110);

        g.setFont(italic);
        g.setColor(Color.GREEN);
        g.drawString("Your Name", 50, 155);
    }

    public static void main(String[] args)
    {
        JFrame frame = new JFrame("Font Styles");
        Q5_FontStyles panel = new Q5_FontStyles();
        frame.add(panel);
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
