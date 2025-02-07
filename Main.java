import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

class Main {
    private static boolean gameStarted;
    // Pieces are indexed in their order on the spritesheet
    private static int NONE = 0; private static int ROOK = 1; private static int KNIGHT = 2; private static int BISHOP = 3; private static int QUEEN = 4; private static int KING = 5; private static int PAWN = 6;
    
    private static int BLACK = 0; private static int WHITE = 1;
    private static int whosMoving;

    private static int[][] gamee;
    private static long timerStartedAt;
    private static Timer timerUpdater;

    public static void main(String[] args) {
        // Reset variables
        gameStarted = false;
        whosMoving = WHITE;
        gamee = new int[8][8];
        timerStartedAt = 0;
        timerUpdater = null;

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
        f.setIconImage(Res.getAsImage("icon.png"));
        
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
        
        BufferedImage board = sprite.getSubimage(0, 0, 1152, 1152);
        
        f.add(new JComponent() {
            {
                //
            }
            
            public void paintComponent(Graphics g) {
                // Calculate aspect ratio square
                int x = 0;
                int y = 0;
                int w = board.getWidth();
                int h = board.getHeight();
                
                if (getWidth() > getHeight()) {
                    w = (int) (w * ((double) getHeight() / h));
                    h = getHeight();
                } else {
                    h = (int) (h * ((double) getWidth() / w));
                    w = getWidth();
                }
                
                x = (getWidth() / 2) - (w / 2);
                y = (getHeight() / 2) - (h / 2);
                
                // Draw board
                g.drawImage(board.getScaledInstance(w, h, Image.SCALE_SMOOTH), x, y, null);

                // Draw pieces
                if (gameStarted) {

                }
            }
        });
        
        JMenuBar mb = new JMenuBar();
        f.setJMenuBar(mb);
        JMenu game = new JMenu("Game");

        JMenu timer = new JMenu("0:00");

        mb.add(game);
            JMenuItem neww = new JMenuItem("New");
            game.add(neww);

            JMenuItem end = new JMenuItem("End");
            end.setEnabled(false);
            game.add(end);

            end.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (timerUpdater != null) {timerUpdater.stop();}
                    gameStarted = false;
                    neww.setEnabled(true);
                    end.setEnabled(false);
                }
            });

            neww.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    whosMoving = WHITE;
                    gamee = new int[8][8];

                    // Start timer
                    if (timerUpdater != null) {timerUpdater.stop();}
                    timerUpdater = new Timer(500, new ActionListener() {
                        public void actionPerformed(ActionEvent ev) {
                            long diff = System.currentTimeMillis() - timerStartedAt;
                            long mins = (diff / 1000) / 60;
                            long secs = (diff / 1000) - (mins * 60);
                            timer.setText(mins + ":" + ((secs < 10) ? "0" + secs : secs));
                        }
                    });
                    timerUpdater.start();
                    timerStartedAt = System.currentTimeMillis();

                    gameStarted = true;
                    neww.setEnabled(false);
                    end.setEnabled(true);
                }
            });
            
            JMenuItem settings = new JMenuItem("Settings...");
            game.add(settings);
            settings.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    int o = Settings.show(f);
                    if (o == Settings.OK || o == Settings.RESET) {
                        if (gameStarted) {
                            if (JOptionPane.showConfirmDialog(f, "Applying settings will end the current game. Continue?", "Confirm", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                                f.dispose();
                                main(args);
                            }
                        } else {
                            f.dispose();
                            main(args);
                        }
                    }
                }
            });
        JMenu help = new JMenu("Help");
        mb.add(help);
            JMenuItem about = new JMenuItem("About");
            help.add(about);
            about.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(f, "Chess\nVersion 1.0\nhttps://github.com/mochawoof/chess", "About Chess", JOptionPane.PLAIN_MESSAGE, new ImageIcon(f.getIconImage()));
                }
            });
        mb.add(Box.createGlue());
        mb.add(timer);
        
        f.setVisible(true);
    }
}