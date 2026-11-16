package it.unibo.mvc;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * A very simple program using a graphical interface.
 *
 */
public final class SimpleGUI {
    private static final int PROPORTION = 2;
    private static final Logger LOGGER = Logger.getLogger(SimpleGUI.class.getName());
    private final JFrame frame = new JFrame();
    private final SimpleController controller = new SimpleController();

    /**
     * Creates a new SimpleGUI.
     */
    public SimpleGUI() {
        final JPanel canvas = new JPanel();
        canvas.setLayout(new BorderLayout());
        // Text field
        final JTextField textField = new JTextField();
        canvas.add(textField, BorderLayout.NORTH);
        // Text Area
        final JTextArea textArea = new JTextArea();
        canvas.add(textArea, BorderLayout.CENTER);
        // Commands panel 
        final JPanel commandsPanel = new JPanel();
        // Print button
        final JButton printButton = new JButton("Print");
        commandsPanel.add(printButton);
        // Show histroy button
        final JButton showHistoryButton = new JButton("Show history");
        commandsPanel.add(showHistoryButton);
        // Add buttons to command's panel
        canvas.add(commandsPanel, BorderLayout.SOUTH);
        // Frame config
        frame.setContentPane(canvas);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        /*
         * Handlers
         */
        printButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final String textFieldContent = textField.getText();
                try {
                    controller.setText(textFieldContent);
                    controller.printCurrentString();
                } catch (final IllegalStateException ex) {
                    LOGGER.log(Level.SEVERE, "No text to print", ex);
                }
            }
        });
        showHistoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final List<String> history = controller.getHistory();
                for (final String text : history) {
                    textArea.append(text);
                }
            }
        });
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
        new SimpleGUI().display();
    }
}
