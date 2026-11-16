package it.unibo.mvc;

import java.util.List;

/**
 * This interface represents a controller in the MVC pattern,
 * responsible for managing the flow of data between the model and the view.
 */
public interface Controller {
    /**
     * Set the next string to print.
     * 
     * @param text the string to print
     * @throws NullPointerException if text is null
     */
    void setText(String text);

    /**
     * Get the next string to print.
     *
     * @return the current string
     */
    String getText();

    /**
     * Get the history of the printed strings.
     *
     * @return the history
     */
    List<String> getHistory();

    /**
     * Print the current string.
     *
     * @throws IllegalStateException if no string has been set
     */
    void printCurrentString();
}
