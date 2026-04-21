import java.awt.*;
import javax.swing.*;

public class Q4_ExpandingText extends JPanel implements Runnable
{
    int fontSize = 6;
    boolean growing = true;
    Thread t;

    public void start()
    {
        t = new Thread(this);
        t.start();
    }

    public void run()
    {
        while (true)
        {
            if (growing)
            {
                fontSize += 2;
                if (fontSize >= 96) growing = false;
            }
            else
            {
                fontSize -= 2;
                if (fontSize <= 6) growing = true;
            }

            repaint();

            try { Thread.sleep(50); } catch (InterruptedException e) {}
        }
    }

    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.setColor(Color.BLUE);
        g.setFont(new Font("Arial", Font.BOLD, fontSize));
        g.drawString("Hello", 50, 100);
    }

    public static void main(String[] args)
    {
        JFrame frame = new JFrame("Expanding Text");
        Q4_ExpandingText panel = new Q4_ExpandingText();
        panel.start();
        frame.add(panel);
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
