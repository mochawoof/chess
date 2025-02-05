import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

class Main {
    public static void main(String[] args) {
        // Apply look and feel from settings
        try {
            for (UIManager.LookAndFeelInfo laf : UIManager.getInstalledLookAndFeels()) {
                if (Settings.get("App_Theme").equals(laf.getName())) {
                    UIManager.setLookAndFeel(laf.getClassName());
                    break;
                }
            }
        } catch (Exception e) {e.printStackTrace();}
        
        JFrame f = new JFrame("Chess");
        f.setSize(500, 400);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        BufferedImage sprite = null;
        try {
            Image si = ImageIO.read(new File("theme.png"));
            sprite = new BufferedImage(si.getWidth(null), si.getHeight(null), BufferedImage.TYPE_INT_ARGB);
            sprite.getGraphics().drawImage(si, 0, 0, null);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(f, "Error", "Theme image is unreadable or doesn't exist!", JOptionPane.ERROR_MESSAGE);
            f.dispose();
        }
        
        BufferedImage board = sprite.getSubimage(0, 0, 640, 640);
        
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
                
                g.drawImage(board.getScaledInstance(w, h, Image.SCALE_SMOOTH), x, y, null);
            }
        });
        
        JMenuBar mb = new JMenuBar();
        f.setJMenuBar(mb);
        JMenu game = new JMenu("Game");
        mb.add(game);
            JMenuItem neww = new JMenuItem("New");
            game.add(neww);
            
            JMenuItem settings = new JMenuItem("Settings...");
            game.add(settings);
            settings.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    int o = Settings.show(f);
                    if (o == Settings.OK || o == Settings.RESET) {
                        f.dispose();
                        main(args);
                    }
                }
            });
        JMenu help = new JMenu("Help");
        mb.add(help);
            JMenuItem about = new JMenuItem("About");
            help.add(about);
        
        f.setVisible(true);
    }
}