import java.awt.*;
import java.util.Calendar;
import javax.swing.*;

public class Q1_DigitalClock extends JPanel implements Runnable
{
    Thread t;

    public void init()
    {
        setBackground(Color.BLACK);
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
            repaint();
            try { Thread.sleep(1000); } catch (InterruptedException e) {}
        }
    }

    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Calendar cal = Calendar.getInstance();

        int hour   = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        int second = cal.get(Calendar.SECOND);

        String time = String.format("%02d : %02d : %02d", hour, minute, second);

        g.setColor(Color.GREEN);
        g.setFont(new Font("Monospaced", Font.BOLD, 36));
        g.drawString(time, 40, 60);
    }

    public static void main(String[] args)
    {
        JFrame frame = new JFrame("Digital Clock");
        Q1_DigitalClock panel = new Q1_DigitalClock();
        panel.init();
        panel.start();
        frame.add(panel);
        frame.setSize(300, 130);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
