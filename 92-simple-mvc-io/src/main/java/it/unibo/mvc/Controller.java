package it.unibo.mvc;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

/**
 * Application controller. Performs the I/O.
 */
public final class Controller {
    private File currentFile;

    /**
     * Controller constructor.
     */
    public Controller() {
        this.currentFile = new File(System.getProperty("user.home"), "output.txt");
    }

    void setCurrentFile(final File file) {
        this.currentFile = file;
    }

    File getCurrentFile() {
        return this.currentFile;
    }

    String getFilepath() {
        return this.currentFile.getPath();
    }

    void save(final String content) throws IOException {
        Files.writeString(this.currentFile.toPath(), content, StandardCharsets.UTF_8);
    }

}
