package it.unibo.mvc;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * A very simple program using a graphical interface.
 * 
 */
public final class SimpleGUIWithFileChooser {
    private static final int PROPORTION = 2;
    private final JFrame frame = new JFrame();
    private final Controller controller = new Controller();

    /**
     * Creates a new SimpleGUIWithFileChooser.
     */
    public SimpleGUIWithFileChooser() {
        final JPanel canvas = new JPanel(); 
        canvas.setLayout(new BorderLayout());
        // Search panel
        final JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BorderLayout());
        // Search bar
        final JTextField searchBar = new JTextField();
        searchBar.setEditable(false);
        searchBar.setText(controller.getFilepath());
        searchPanel.add(searchBar, BorderLayout.CENTER);
        // Browse button
        final JButton browseButton = new JButton("Browse...");
        searchPanel.add(browseButton, BorderLayout.LINE_END);
        canvas.add(searchPanel, BorderLayout.NORTH);
        // Frame config
        frame.setContentPane(canvas);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        /*
         * Handlers
         */
        browseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final JFileChooser fileChooser = new JFileChooser();
                fileChooser.setSelectedFile(controller.getCurrentFile());
                final int result = fileChooser.showSaveDialog(browseButton);
                if (result == JFileChooser.APPROVE_OPTION) {
                    final File selectedFile = fileChooser.getSelectedFile();
                    controller.setCurrentFile(selectedFile);
                    // Update search bar content
                    searchBar.setText(controller.getFilepath());
                } else {
                    JOptionPane.showMessageDialog(frame, "Could not load file");
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
        new SimpleGUIWithFileChooser().display();
    }
}
