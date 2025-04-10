package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents a book with a price, title, author, genre, quantity, and quantity sold.
public class Book implements Writable {
    private final int price;
    private final String genre;
    private final String author;
    private final String title;
    private int quantity;
    private int quantitySold;

    // Requires: non-empty inputs for each parameter and the price must >= 0.
    // Effects: The inputs are set to the book. this.quantity = 1 and
    // this.quantitySold = 1 is set.
    public Book(String title, String author, String genre, int p) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.price = p;
        this.quantity = 1;
        this.quantitySold = 1;
    }

    public int getPrice() {
        return this.price;
    }

    public String getGenre() {
        return this.genre;
    }

    public String getAuthor() {
        return this.author;
    }

    public String getTitle() {
        return this.title;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public int getSoldQuantity() {
        return this.quantitySold;
    }

    public void setQuantity(int p) {
        this.quantity = p;
    }

    public void setQuantitySold(int p) {
        this.quantitySold = p;
    }


    // Modifies: this
    // Effects: Increases the quantity sold of the book
    public void increaseQuantitySold() {
        this.quantitySold++;
    }

    // Modifies: this
    // Effects: increases the quantity of the book
    public void addQuantity() {
        this.quantity++;
    }

    // Modifies: this
    // Effects: decreases the quantity of the book
    public void decreaseQuantity() {
        this.quantity--;
    }

    // Citation from example project;
    // EFFECTS: puts info to json
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("price", price);
        json.put("genre", genre);
        json.put("author", author);
        json.put("title", title);
        json.put("quantity", quantity);
        json.put("quantitySold", quantitySold);
        return json;
    }
}
