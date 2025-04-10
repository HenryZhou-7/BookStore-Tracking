package model;


import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Represents the management of the inventory, books sold, and profit of books.
public class ManageBook implements Writable {
    private List<Book> inventory;
    private List<Book> booksSold;
    private int profit;

    // Effects: Initializes the inventory, booksSold, and profit.
    public ManageBook() {
        this.inventory = new ArrayList<>();
        this.booksSold = new ArrayList<>();
        this.profit = 0;
    }


    // Effects: Finds the desired book in inventory and returns it.
    //          Returns null if no book is found.
    public Book findBookInInventory(Book b) {
        for (Book book : inventory) {
            if (book.getTitle().equals(b.getTitle()) && (book.getAuthor().equals(b.getAuthor()))) {
                return book;
            }
        }
        return null;
    }


    // Requires: a parameter with a valid book
    // Modifies: this, Book
    // Effects: adds book to inventory; if the book is already there, add the quantity of that book
    public void addBooktoInventory(Book b) {
        Book existingBook = findBookInInventory(b);
        if (existingBook != null) {
            existingBook.addQuantity();
            EventLog.getInstance().logEvent(new Event("Added quantity for " + b));
        } else {
            this.inventory.add(b);
            EventLog.getInstance().logEvent(new Event("Added book to inventory for " + b));
        }
    }

    // Requires: a parameter with a valid book
    // Effects: finds and returns book from booksSold; return null if book not found.
    public Book findBookInBooksSold(Book b) {
        for (Book book : booksSold) {
            if (book.getTitle().equals(b.getTitle()) && (book.getAuthor().equals(b.getAuthor()))) {
                return book;
            }
        }
        return null;
    }

    // Requires: a parameter with a valid book
    // Modifies: this, Book
    // Effects: adds book to booksSold; if already there, add the quantity sold of that book.
    public void addBooktoBooksSold(Book b) {
        Book soldBook = findBookInBooksSold(b);
        if (soldBook != null) {
            soldBook.increaseQuantitySold();
        } else {
            this.booksSold.add(b);
        }
    }

    // Requires: a parameter with a title and author of a book that is already in the inventory
    // Modifies: this , Book
    // Effects: sells the book; removes from inventory if there is one book left; reduces quantity
    // if there is more than one of the same book; records the profit made; add the book to booksSold.
    public void sellBook(String title, String author) {
        for (Book book : inventory) {
            if (book.getTitle().equals(title) && book.getAuthor().equals(author)) {
                if (book.getQuantity() > 1) {
                    book.decreaseQuantity();
                    addBooktoBooksSold(book);
                    this.profit += book.getPrice();
                } else if (book.getQuantity() == 1) {
                    inventory.remove(book);
                    addBooktoBooksSold(book);
                    this.profit += book.getPrice();
                }
                break;
            }
        }
    }



    // Effects: searches through inventory with given genre to find desired books and returns a list of books
    // with that specified genre; return empty list if not found
    public List<Book> searchByGenre(String g) {
        List<Book> booksByGenre = new ArrayList<>();
        for (Book b : inventory) {
            if (b.getGenre().equals(g)) {
                booksByGenre.add(b);
            }
        }
        return booksByGenre;
    }


    // Effects: searches through inventory with given author to find desired books and returns a list of books
    // with that specified author; return empty list if not found
    public List<Book> searchByAuthor(String a) {
        List<Book> booksByAuthor = new ArrayList<>();
        for (Book b : inventory) {
            if (b.getAuthor().equals(a)) {
                booksByAuthor.add(b);
            }
        }
        return booksByAuthor;
    }

    // Effects: searches through inventory with given title to find desired books and returns a list of books
    // with that specified title; return empty list if not found
    public List<Book> searchByTitle(String t) {
        List<Book> booksByTitle = new ArrayList<>();
        for (Book b : inventory) {
            if (b.getTitle().equals(t)) {
                booksByTitle.add(b);
            }
        }
        EventLog.getInstance().logEvent(new Event("Searched for book with title: " + t + " from inventory"));
        return booksByTitle;
    }

    public List<Book> getBooksSold() {
        return this.booksSold;
    }

    public List<Book> getInventory() {
        EventLog.getInstance().logEvent(new Event("Displayed inventory"));
        return this.inventory;
    }

    public int getProfit() {
        return this.profit;
    }

    public void setProfit(int s) {
        this.profit = s;
    }

    // Citation from example project
    // EFFECTS: puts info to json
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("inventory", inventoryToJson());
        json.put("booksSold", booksSoldToJson());
        json.put("profit", profit);
        return json;
    }

    // Citation from example Project
    // Effects: returns inventory in ManageBool as a JSON array
    public JSONArray inventoryToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Book b: inventory) {
            jsonArray.put(b.toJson());
        }
        return jsonArray;
    }

    // Citation from example Project
    // Effects: returns booksSold in ManageBook as a JSON array
    public JSONArray booksSoldToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Book b: booksSold) {
            jsonArray.put(b.toJson());
        }
        return jsonArray;
    }
}