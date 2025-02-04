import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class Main {
    public static void main(String[] args) {
        JFrame f = new JFrame("Chess");
        f.setSize(500, 400);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        f.add(new JComponent() {
            {
                //
            }
            
            public void paintComponent(Graphics g) {                
                int w = 300;
                int h = 300;
                
                if (w > getWidth()) {
                    h = h * (getWidth() / w);
                    w = getWidth();
                } else if (h > getHeight()) {
                    w = w * (getHeight() / h);
                    h = getHeight();
                }
                
                
            }
        });
        
        f.setVisible(true);
    }
}