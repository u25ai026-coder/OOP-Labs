import java.awt.*;
import javax.swing.*;

public class Q2_ImageAudio extends JPanel
{
    Image img;

    public void init()
    {
        img = Toolkit.getDefaultToolkit().getImage("image.jpg");
    }

    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        if (img != null)
            g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
        else
        {
            g.setColor(Color.RED);
            g.drawString("Image not found. Place image.jpg in the same folder.", 20, 50);
        }
    }

    public static void main(String[] args)
    {
        JFrame frame = new JFrame("Image Viewer");
        Q2_ImageAudio panel = new Q2_ImageAudio();
        panel.init();
        frame.add(panel);
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
