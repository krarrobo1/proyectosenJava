import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class JPanelTest extends JPanel {

    @Override
    public void paintComponent(Graphics g) {
        // Draw Tree Here
        g.drawOval(240,100,30,30);
        g.drawOval(220,140,30,30);
        g.drawOval(260,140,30,30);
    }

    public static void main(String[] args) {
        JFrame jFrame = new JFrame();
        jFrame.add(new JPanelTest());
        jFrame.setSize(500, 500);
        jFrame.setVisible(true);
    }

}