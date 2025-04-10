package ui;

import model.Book;
import model.ManageBook;
import java.util.List;
import java.util.Scanner;

import persistence.JsonReader;
import persistence.JsonWriter;
import java.io.FileNotFoundException;
import java.io.IOException;


// BookStore management application
public class BookStore {
    private static final String JSON_STORE = "./data/managebook.json";
    private ManageBook manageBook;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // Citation: took the structure from TellerApp and JsonDemo
    // Modifies: this
    // Effects:  runs the BookStore application and initializes ManageBook
    public BookStore() throws FileNotFoundException {
        this.manageBook = new ManageBook();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runBookStore();
    }

    //Effects: displays bookDetails to user
    private void printBookDetails(Book b) {
        System.out.println("Title: " + b.getTitle());
        System.out.println("Author " + b.getAuthor());
        System.out.println("Genre: " + b.getGenre());
        System.out.println("Price: " + b.getPrice());
        System.out.println("Quantity: " + b.getQuantity());
    }

    // Modifies: this
    // Effects: Displays the menu message after a completed action to user
    private void askToContinue() {
        System.out.println("Press a key to go back to menu:");
        input.next();
    }

    // Citation: took the structure from TellerApp
    // Effects: processes user input and initializes Gui
    private void runBookStore() {
        init();
        new Gui(manageBook);
    }

    // Citation: from TellerApp and Json example
    // Effects: Processes user command
    private void processCommand(String command) {
        if (command.equals("1")) {
            addBookToInventory();
        } else if (command.equals("2")) {
            sellBook();
        } else if (command.equals("3")) {
            displayInventory();
        } else if (command.equals("5")) {
            displayBooksSold();
        } else if (command.equals("6")) {
            displayProfit();
        } else if (command.equals("4")) {
            searchBooks();
        } else if (command.equals("7")) {
            saveManageBook();
        } else if (command.equals("8")) {
            loadManageBook();
        } else {
            System.out.println("Please select a number 1 - 6");
        }
    }

    // Citation from Json example
    // Effects: saves the ManageBook file
    public void saveManageBook() {
        try {
            jsonWriter.open();
            jsonWriter.write(manageBook);
            jsonWriter.close();
            System.out.println("Saved");
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // Citation from Json example
    // Modifies: this;
    // Effects: loads the information from file
    public void loadManageBook() {
        try {
            manageBook = jsonReader.read();
            System.out.println("Loaded");
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }


    // Modifies: this
    // Effects: initializes Scanner
    private void init() {
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // Citation: took the structure from TellerApp
    // Effects: displays the menu to user
    private void displayMenu() {
        System.out.println(" ");
        System.out.println(" ");
        System.out.println("\n      Bookstore Menu        ");
        System.out.println("1. Add Book to Inventory");
        System.out.println("2. Sell Book");
        System.out.println("3. Display Inventory");
        System.out.println("4. Search for Books");
        System.out.println("5. Display Books Sold");
        System.out.println("6. Display Profit");
        System.out.println("7. Save file");
        System.out.println("8. Load file");
        System.out.println("9. Exit");
        System.out.println(" ");
        System.out.print("Enter your choice: ");

    }

    // Modifies: this
    // Effects: adds book to inventory
    private void addBookToInventory() {
        System.out.println("Enter the Title:");
        String bookTitle = input.next();
        System.out.println("Enter the Author:");
        String bookAuthor = input.next();
        System.out.println("Enter the Genre:");
        String bookGenre = input.next();
        System.out.println("Enter the Price:");
        int bookPrice = input.nextInt();
        Book addThisBook = new Book(bookTitle, bookAuthor, bookGenre, bookPrice);
        manageBook.addBooktoInventory(addThisBook);
        System.out.println("Book has been added");
        askToContinue();
    }

    // Modifies: this
    // Effects: sells the Book
    private void sellBook() {
        System.out.println("Enter the title of the Book to sell:");
        String bookTitleToSell = input.next();
        System.out.println("Enter the author:");
        String author = input.next();
        manageBook.sellBook(bookTitleToSell, author);
        System.out.println("Book sold");
        askToContinue();
    }


    // Effects: displays the inventory of books
    private void displayInventory() {

        if (manageBook.getInventory().isEmpty()) {
            System.out.println("Empty Inventory");
        } else {
            System.out.println("    Inventory:    ");
        }
        for (Book b : manageBook.getInventory()) {
            printBookDetails(b);
            System.out.println("----------------");
        }
        askToContinue();
    }


    // Effects: displays the books sold
    private void displayBooksSold() {
        if (manageBook.getBooksSold().isEmpty()) {
            System.out.println("No books sold");
        } else {
            System.out.println("    Books Sold:    ");
        }
        for (Book b : manageBook.getBooksSold()) {
            System.out.println("Title: " + b.getTitle());
            System.out.println("Author " + b.getAuthor());
            System.out.println("Genre: " + b.getGenre());
            System.out.println("Price: " + b.getPrice());
            System.out.println("Quantity Sold: " + b.getSoldQuantity());
            System.out.println("---------------------------------");
        }
        askToContinue();
    }


    // Effects: displays the inventory of books
    private void displayProfit() {
        System.out.println(" ");
        System.out.println(" ");
        System.out.println("The amount of profit made is: " + manageBook.getProfit());
        askToContinue();
    }

    // Modifies: this
    // Effects: Helper method for search books for author
    private void searchBooksHelperAuthor() {
        System.out.println("Enter author:");
        String authorInput = input.next();
        List<Book> authorList = manageBook.searchByAuthor(authorInput);
        System.out.println(" ");
        System.out.println("Books based on Author");
        System.out.println("--------------");
        for (Book b : authorList) {
            printBookDetails(b);
            System.out.println("--------------");
        }

    }

    // Modifies: this
    // Effects: Helper method for search books for title
    private void searchBooksHelperTitle() {
        System.out.println("Enter Title:");
        String titleInput = input.next();
        List<Book> titleList = manageBook.searchByTitle(titleInput);
        System.out.println(" ");
        System.out.println("Books based on Title");
        System.out.println("--------------");
        for (Book b : titleList) {
            printBookDetails(b);
            System.out.println("--------------");
        }
    }

    // Modifies: this
    // Effects: Helper method for search books for genre
    private void searchBooksHelperGenre() {
        System.out.println("Enter Genre:");
        String genreInput = input.next();
        List<Book> genreList = manageBook.searchByGenre(genreInput);
        System.out.println(" ");
        System.out.println("Books based on Genre");
        System.out.println("--------------");
        for (Book b : genreList) {
            printBookDetails(b);
            System.out.println("--------------");
        }
    }

    // Modifies: this
    // Effects: Searches the books in inventory and displays the desired books
    private void searchBooks() {
        System.out.println(" ");
        System.out.println("Search by:");
        System.out.println("1. Genre");
        System.out.println("2. Author");
        System.out.println("3. Title");
        String searchInput = input.next();

        if (searchInput.equals("1")) {
            searchBooksHelperGenre();
            askToContinue();
        } else if (searchInput.equals("2")) {
            searchBooksHelperAuthor();
            askToContinue();
        } else if (searchInput.equals("3")) {
            searchBooksHelperTitle();
            askToContinue();
        }
    }
}




