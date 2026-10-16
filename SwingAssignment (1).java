import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.File;

/**
 * PROG209 - Event Driven Programming
 * Assignment 1: Basic Interactive GUI (Java Swing)
 *
 * Demonstrates: JFrame, JPanel, JLabel, ImageIcon, inheritance, constructors,
 * and event handling (change label text + change panel background).
 */
public class SwingAssignment {

    // TODO: replace with your own full name
    private static final String STUDENT_NAME = "Your Name";

    // Required variables (names fixed by the assignment brief)
    private JFrame frame;
    private JPanel panel;
    private JLabel label;

    // Data used by the interactive "Addition" feature
    private final String[] messages = {
        "Welcome to Java Swing First Individual Assignment by " + STUDENT_NAME,
        "Event Driven Programming is fun!",
        "Hello from PROG209 - Limkokwing University, Sierra Leone",
        "You just changed the text of a JLabel!"
    };
    private final Color[] colours = {
        new Color(0xE3F2FD), new Color(0xFFF3E0), new Color(0xE8F5E9),
        new Color(0xF3E5F5), new Color(0x263238)
    };
    private int messageIndex = 0;
    private int colourIndex = 0;

    /* ---------- INHERITANCE: AppFrame IS-A JFrame ---------- */
    static class AppFrame extends JFrame {

        // Constructor: configures everything common to the window
        AppFrame(String title, int width, int height) {
            super(title);                                   // call parent constructor
            setSize(width, height);                         // 800 x 800
            setLocationRelativeTo(null);                    // centre on screen
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // app closes with the X button
            setIconImage(loadAppIcon().getImage());         // own application icon
        }

        // Loads icon.png (ImageIcon); falls back to a drawn icon if file is missing
        private static ImageIcon loadAppIcon() {
            File file = new File("icon.png");
            if (file.exists()) {
                ImageIcon icon = new ImageIcon(file.getPath());
                if (icon.getIconWidth() > 0) {
                    return icon;
                }
            }
            BufferedImage img = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = img.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setColor(new Color(0x1565C0));
            g.fillRoundRect(0, 0, 64, 64, 18, 18);
            g.setColor(Color.WHITE);
            g.setFont(new Font("SansSerif", Font.BOLD, 34));
            g.drawString("J", 22, 46);
            g.dispose();
            return new ImageIcon(img);
        }
    }

    /* ---------- CONSTRUCTOR: builds the whole GUI ---------- */
    public SwingAssignment() {
        frame = new AppFrame("Java Swing Assignment", 800, 800); // JFrame instantiated

        panel = new JPanel(new GridBagLayout());                 // JPanel instantiated
        panel.setBackground(colours[0]);

        label = new JLabel(messages[0]);                         // JLabel instantiated
        label.setFont(new Font("Tahoma", Font.BOLD, 18));
        label.setHorizontalAlignment(JLabel.CENTER);

        JButton textButton = new JButton("Change Text");
        JButton colourButton = new JButton("Change Background");
        textButton.setPreferredSize(new Dimension(170, 40));
        colourButton.setPreferredSize(new Dimension(190, 40));

        // Event handling (lambda expressions)
        textButton.addActionListener(e -> changeText());
        colourButton.addActionListener(e -> changeBackground());

        // Layout: label on top, buttons underneath
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0; c.gridy = 0; c.gridwidth = 2;
        c.insets = new Insets(0, 10, 30, 10);
        panel.add(label, c);                                     // label -> panel

        c.gridwidth = 1; c.gridy = 1;
        c.insets = new Insets(0, 10, 0, 10);
        c.gridx = 0; panel.add(textButton, c);
        c.gridx = 1; panel.add(colourButton, c);

        frame.add(panel);                                        // panel -> frame
        frame.setVisible(true);
    }

    // Addition 1: change the text of the JLabel
    private void changeText() {
        messageIndex = (messageIndex + 1) % messages.length;
        label.setText(messages[messageIndex]);
    }

    // Addition 2: change the background of the JPanel (and keep text readable)
    private void changeBackground() {
        colourIndex = (colourIndex + 1) % colours.length;
        Color bg = colours[colourIndex];
        panel.setBackground(bg);
        double luminance = (0.299 * bg.getRed() + 0.587 * bg.getGreen() + 0.114 * bg.getBlue()) / 255;
        label.setForeground(luminance < 0.5 ? Color.WHITE : Color.BLACK);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) { }
        SwingUtilities.invokeLater(SwingAssignment::new);
    }
}
