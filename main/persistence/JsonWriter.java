package persistence;

import model.ManageBook;
import org.json.JSONObject;
import java.io.*;

// Citation from example
// Represents a reader that writes JSON representation of ManageBook to file
public class JsonWriter {

    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // Citation from example
    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // Citation from example
    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // Citation from example
    // MODIFIES: this
    // EFFECTS: writes JSON representation of ManageBook to file
    public void write(ManageBook mb) {
        JSONObject json = mb.toJson();
        saveToFile(json.toString(TAB));
    }

    // Citation from example
    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // Citation from example
    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}


