package ui;

import model.Book;
import model.ManageBook;
import persistence.JsonReader;
import persistence.JsonWriter;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

// This class creates the GUI for the bookstore
public class Gui extends JFrame implements ActionListener {
    private JLabel labelSave;
    private JLabel labelLoad;
    private JTextField titleField;
    private JTextField authorField;
    private JTextField genreField;
    private JTextField priceField;
    private JLabel titleLabel;
    private JLabel authorLabel;
    private JLabel genreLabel;
    private JLabel priceLabel;
    private JLabel labelAdded;
    private ManageBook manageBook;
    private static final String JSON_STORE = "./data/managebook.json";
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;

    //Requires: manageBook is not null
    //Modifies: this
    //Effects: Constructs a GUI for the bookstore
    //Citation: Structure from stackOverflow: "Swing JLabel text change on the running application"
    public Gui(ManageBook manageBook) {
        super("BookStore Management Application");
        JWindow splash = new JWindow();
        showSplash(splash);
        waitTwoSeconds();
        splash.dispose();
        this.manageBook = manageBook;
        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800, 200));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
        setLayout(new FlowLayout());
        JLabel spacer = new JLabel();
        spacer.setPreferredSize(new Dimension(700, 0));
        setUpForBook(spacer);
        setUpForButtons(spacer);
        add(spacer);
        JButton loadInventory = loadInventoryButtonHelper();
        add(loadInventory);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    //Effects: creates a load inventory button
    private JButton loadInventoryButtonHelper() {
        JButton loadInventory = new JButton("Display Inventory");
        loadInventory.setActionCommand("loadInv");
        loadInventory.addActionListener(this);
        return loadInventory;
    }

    //Effects: pauses the program for two seconds.
    //Citation: from stack overflow: "How to pause my Java program for 2 seconds"
    private static void waitTwoSeconds() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            ;
        }
    }

    //Effects: displays a splash screen
    //Citation: got structure from Stack Overflow: "How to make a splash screen for GUI?"
    private static void showSplash(JWindow splash) {
        ImageIcon icon = new ImageIcon("data/img.png");
        splash.getContentPane().add(
                new JLabel(icon,  SwingConstants.CENTER));
        splash.setBounds(600, 400, icon.getIconWidth(), icon.getIconHeight());
        splash.setVisible(true);
    }

    //Modifies: this
    //Effects: sets up the buttons for GUI
    private void setUpForButtons(JLabel spacer) {
        JButton addBookBtn = new JButton("Add book");
        addBookBtn.setActionCommand("addBook");
        addBookBtn.addActionListener(this);
        add(addBookBtn);
        labelAdded = new JLabel(" ");
        add(labelAdded);

        add(spacer);

        JButton saveBtn = new JButton("Save");
        saveBtn.setActionCommand("save");
        saveBtn.addActionListener(this);
        labelSave = new JLabel(" ");
        add(saveBtn);
        add(labelSave);

        add(spacer);

        JButton loadBtn = new JButton("Load");
        loadBtn.setActionCommand("load");
        loadBtn.addActionListener(this);
        labelLoad = new JLabel(" ");
        add(loadBtn);
        add(labelLoad);

        add(spacer);

        JButton displayByTitle = new JButton("Display by Title");
        addDisplayByTitleBtn(displayByTitle);
    }

    //Modifies: this
    //Effects: adds a button to gui for displaying books by their title and also adds to actionListener.
    //Citation: Structure from stackOverflow: "Swing JLabel text change on the running application"
    private void addDisplayByTitleBtn(JButton displayByTitle) {
        displayByTitle.setActionCommand("displayTitle");
        displayByTitle.addActionListener(this);
        add(displayByTitle);
    }

    //Modifies: this
    //Effects: sets up book Jfields and JLabels to GUI
    private void setUpForBook(JLabel spacer) {
        titleField = new JTextField(10);
        titleLabel = new JLabel("Title:");
        add(titleLabel);
        add(titleField);

        authorField = new JTextField(10);
        authorLabel = new JLabel("Author:");
        add(authorLabel);
        add(authorField);

        genreField = new JTextField(10);
        genreLabel = new JLabel("Genre:");
        add(genreLabel);
        add(genreField);

        priceField = new JTextField(10);
        priceLabel = new JLabel("Price:");
        add(priceLabel);
        add(priceField);
        add(spacer);
    }

    //Modifies: this
    //Effects: handles the appropriate events given the button pressed.
    //Citation: Structure from stackOverflow: "Swing JLabel text change on the running application"
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("save")) {
            try {
                jsonWriter.open();
                jsonWriter.write(manageBook);
                jsonWriter.close();
            } catch (FileNotFoundException ee) {
                System.out.println("Unable to write to file: " + JSON_STORE);
            }
            labelSave.setText("Saved!");

        }
        actionHelper(e);
        if (e.getActionCommand().equals("loadInv")) {
            displayInventory();
        }
    }

    //Modifies: this
    //Effects: helper method for actionPerformed
    private void actionHelper(ActionEvent e) {
        if (e.getActionCommand().equals("load")) {
            try {
                manageBook = jsonReader.read();
            } catch (IOException ee) {
                System.out.println("Unable to read from file: " + JSON_STORE);
            }
            labelLoad.setText("Loaded!");
        }

        if (e.getActionCommand().equals("addBook")) {
            int price = Integer.valueOf(priceField.getText());
            Book book = new Book(titleField.getText(), authorField.getText(), genreField.getText(), price);
            manageBook.addBooktoInventory(book);
            labelAdded.setText("Added!");
        }

        if (e.getActionCommand().equals("displayTitle")) {
            displayByTitle(titleField.getText());
        }
    }

    //Effects: displays the inventory in a popup window (textarea)
    private void displayInventory() {
        JTextArea inventoryBox = new JTextArea(20, 50);
        inventoryBox.setEditable(false);
        JScrollPane scrollBox = new JScrollPane(inventoryBox);
        StringBuilder inventoryText = new StringBuilder();
        for (Book b : manageBook.getInventory()) {
            inventoryText.append("Title: ").append(b.getTitle()).append("\n");
            inventoryText.append("Author: ").append(b.getAuthor()).append("\n");
            inventoryText.append("Genre: ").append(b.getGenre()).append("\n");
            inventoryText.append("Price: ").append(b.getPrice()).append("\n");
            inventoryText.append("Quantity: ").append(b.getQuantity()).append("\n\n");
        }
        inventoryBox.setText(inventoryText.toString());
        JOptionPane.showMessageDialog(null, scrollBox, "Inventory", JOptionPane.PLAIN_MESSAGE);
    }

    //Effects: displays the books according to title in a popup window (textarea)
    private void displayByTitle(String s) {
        JTextArea titleBox = new JTextArea(20, 50);
        titleBox.setEditable(false);
        JScrollPane scrollBox = new JScrollPane(titleBox);
        StringBuilder inventoryText = new StringBuilder();
        for (Book b : manageBook.searchByTitle(s)) {
            inventoryText.append("Title: ").append(b.getTitle()).append("\n");
            inventoryText.append("Author: ").append(b.getAuthor()).append("\n");
            inventoryText.append("Genre: ").append(b.getGenre()).append("\n");
            inventoryText.append("Price: ").append(b.getPrice()).append("\n");
            inventoryText.append("Quantity: ").append(b.getQuantity()).append("\n\n");
        }
        titleBox.setText(inventoryText.toString());
        JOptionPane.showMessageDialog(null, scrollBox, "Books by Title", JOptionPane.PLAIN_MESSAGE);
    }
}


