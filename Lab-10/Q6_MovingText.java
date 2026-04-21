import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Q6_MovingText extends JPanel implements KeyListener, Runnable
{
    String typed = "";
    String display = "";
    int x = 500;
    Thread t;

    public void init()
    {
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);
    }

    public void start()
    {
        t = new Thread(this);
        t.start();
    }

    public void run()
    {
        while (true)
        {
            x -= 3;
            if (x < -(display.length() * 12))
                x = getWidth();

            repaint();

            try { Thread.sleep(30); } catch (InterruptedException e) {}
        }
    }

    public void keyPressed(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
        {
            display = typed;
            typed = "";
            x = getWidth();
        }
        else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE && typed.length() > 0)
        {
            typed = typed.substring(0, typed.length() - 1);
        }
    }

    public void keyTyped(KeyEvent e)
    {
        char c = e.getKeyChar();
        if (c != KeyEvent.VK_ENTER && c != KeyEvent.VK_BACK_SPACE)
            typed += c;
    }

    public void keyReleased(KeyEvent e) {}

    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.drawString(display, x, 80);

        g.setColor(Color.GRAY);
        g.setFont(new Font("Arial", Font.PLAIN, 14));
        g.drawString("Typing: " + typed, 10, 130);
    }

    public static void main(String[] args)
    {
        JFrame frame = new JFrame("Moving Text");
        Q6_MovingText panel = new Q6_MovingText();
        panel.init();
        panel.start();
        frame.add(panel);
        frame.setSize(500, 180);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        panel.requestFocusInWindow();
    }
}
