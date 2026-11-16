package it.unibo.mvc;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class is a simple application that writes a random number on a file.
 *
 * <p>
 * This application does not exploit the model-view-controller pattern, and as
 * such is just to be used to learn the basics, not as a template for your
 * applications.
 */
public class BadIOGUI {

    private static final String TITLE = "A very simple GUI application";
    private static final String PATH = System.getProperty("user.home")
        + File.separator
        + BadIOGUI.class.getSimpleName() + ".txt";
    private static final int PROPORTION = 5;
    private static final Logger LOGGER = Logger.getLogger(BadIOGUI.class.getName());
    private final Random randomGenerator = new Random();
    private final JFrame frame = new JFrame(TITLE);

    /**
     * Creates a new BadIOGUI.
     */
    public BadIOGUI() {
        final JPanel canvas = new JPanel();
        canvas.setLayout(new BorderLayout());
        // Center config
        final JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.X_AXIS));
        canvas.add(center, BorderLayout.CENTER);
        // Write
        final JButton write = new JButton("Write on file");
        center.add(write);
        // Read
        final JButton read = new JButton("Read from file");
        center.add(read);
        // Frame config
        frame.setContentPane(canvas);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        /*
         * Handlers
         */
        write.addActionListener(
            new ActionListener() {
                @Override
                public void actionPerformed(final ActionEvent ignored) {
                    /*
                     * This would be VERY BAD in a real application.
                     *
                     * This makes the Event Dispatch Thread (EDT) work on an I/O
                     * operation. I/O operations may take a long time, during which
                     * your UI becomes completely unresponsive.
                     */
                    try (PrintStream ps = new PrintStream(PATH, StandardCharsets.UTF_8)) {
                        ps.print(randomGenerator.nextInt());
                    } catch (final IOException ex) {
                        JOptionPane.showMessageDialog(frame, ex, "Error", JOptionPane.ERROR_MESSAGE);
                        LOGGER.log(Level.SEVERE, "Could not write into: " + PATH, ex);
                    }
                }
            }
        );
        read.addActionListener(
            new ActionListener() {
                @Override
                public void actionPerformed(final ActionEvent e) {
                    try {
                        final Path path = Paths.get(PATH);
                        final List<String> contents = Files.readAllLines(path, StandardCharsets.UTF_8);
                        for (final String content : contents) {
                            IO.println(content);
                        }
                    } catch (final IOException ex) {
                        LOGGER.log(Level.SEVERE, "Could not save content into: " + PATH, ex);
                    }
                }
            }
        );
    }

    private void display() {
        /*
         * Make the frame one fifth the resolution of the screen. This very method is
         * enough for a single screen setup. In case of multiple monitors, the
         * primary is selected. In order to deal coherently with multimonitor
         * setups, other facilities exist (see the Java documentation about this
         * issue). It is MUCH better than manually specify the size of a window
         * in pixel: it takes into account the current resolution.
         */
        final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        final int sw = (int) screen.getWidth();
        final int sh = (int) screen.getHeight();
        frame.setSize(sw / PROPORTION, sh / PROPORTION);
        frame.pack();
        /*
         * Instead of appearing at (0,0), upper left corner of the screen, this
         * flag makes the OS window manager take care of the default positioning
         * on screen. Results may vary, but it is generally the best choice.
         */
        frame.setLocationByPlatform(true);
        /*
         * OK, ready to push the frame onscreen
         */
        frame.setVisible(true);
    }

    /**
     * Launches the application.
     */
    public static void main() {
       new BadIOGUI().display();
    }
}
