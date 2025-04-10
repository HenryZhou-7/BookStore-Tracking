package ui;

import model.EventLog;
import model.Event;
import java.io.FileNotFoundException;


// Citation: from example project
// starts the BookStore program
public class Main {
    // Main method that starts the program
    public static void main(String[] args) {
        // Citation from prairieLearn
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("");
            EventLog eventLog = EventLog.getInstance();
            for (Event event : eventLog) {
                System.out.println(event);
                System.out.println("");
            }
        }));
        try {
            new BookStore();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }
    }
}


