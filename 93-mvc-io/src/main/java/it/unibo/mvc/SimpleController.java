package it.unibo.mvc;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple implementation of the {@link Controller} interface.
 */
public final class SimpleController implements Controller {
    private String text;
    private final List<String> history;

    /**
     * Creates a new SimpleController.
     */
    public SimpleController() {
        this.history = new ArrayList<>();
    }

    @Override
    public void setText(final String text) {
        if (text == null) {
            throw new IllegalArgumentException("Text cannot be null");
        }

        this.text = text;
    }

    @Override
    public String getText() {
        return this.text;
    }

    @Override
    public List<String> getHistory() {
        return List.copyOf(this.history);
    }

    @Override
    public void printCurrentString() {
        if (this.text == null) {
            throw new IllegalStateException("No text to print");
        }

        IO.println(this.text);
        this.history.add(this.text);
    }
}
