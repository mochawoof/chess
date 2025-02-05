import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.imageio.ImageIO;

class Main {
    public static void main(String[] args) {
        // Apply look and feel from settings
        try {
            for (UIManager.LookAndFeelInfo laf : UIManager.getInstalledLookAndFeels()) {
                if (Settings.get("Theme").equals(laf.getName())) {
                    UIManager.setLookAndFeel(laf.getClassName());
                    break;
                }
            }
        } catch (Exception e) {e.printStackTrace();}
        
        JFrame f = new JFrame("Chess");
        f.setSize(500, 400);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Image sprite;
        try {
            sprite = ImageIO.read(new File("theme.png"));
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(f, "Error", "Theme image is unreadable or doesn't exist!", JOptionPane.ERROR_MESSAGE);
            f.dispose();
        }
        
        f.add(new JComponent() {
            {
                //
            }
            
            public void paintComponent(Graphics g) {
                int x = 0;
                int y = 0;
                int w = 640;
                int h = 640;
                
                if (getWidth() > getHeight()) {
                    w = (int) (w * ((double) getHeight() / h));
                    h = getHeight();
                } else {
                    h = (int) (h * ((double) getWidth() / w));
                    w = getWidth();
                }
                
                x = (getWidth() / 2) - (w / 2);
                y = (getHeight() / 2) - (h / 2);
                
                g.setColor(Color.RED);
                g.fillRect(x, y, w, h);
            }
        });
        
        JMenuBar mb = new JMenuBar();
        f.setJMenuBar(mb);
        JMenu game = new JMenu("Game");
        mb.add(game);
            JMenuItem neww = new JMenuItem("New");
            game.add(neww);
        JMenu settings = new JMenu("Settings");
        mb.add(settings);
            JMenuItem editSettings = new JMenuItem("Edit Settings...");
            settings.add(editSettings);
            editSettings.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    int o = Settings.show(f);
                    if (o == Settings.OK || o == Settings.RESET) {
                        f.dispose();
                        main(args);
                    }
                }
            });
            
            JMenuItem about = new JMenuItem("About");
            settings.add(about);
        
        f.setVisible(true);
    }
}