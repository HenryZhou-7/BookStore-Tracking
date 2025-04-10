package persistence;


import model.Book;
import org.json.JSONArray;
import org.json.JSONObject;
import model.ManageBook;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.stream.Stream;

// Citation from example
// Represents a reader that reads MangeBook from Json data stored in file.
public class JsonReader {
    private String source;

    // Citation from example
    // Effects: constructs reader to read from source file;
    public JsonReader(String source) {
        this.source = source;
    }

    // Citation from example
    // Effects: reads ManageBook from file and returns it;
    // throws IOException if an error occurs reading data from file
    public ManageBook read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseManageBook(jsonObject);
    }

    // Citation from example
    // Effects: reads source file as string and returns it;
    public String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();
    }

    // Citation from example
    // Effects: parses ManageBook from JSON object and returns it
    public ManageBook parseManageBook(JSONObject jsonObject) {
        ManageBook mb = new ManageBook();
        addBooks(mb, jsonObject);
        addBooksSold(mb, jsonObject);
        addProfit(mb, jsonObject);
        return mb;
    }

    // Modifies: mb
    // EFFECTS: parses the profit from JSON object and adds them to ManageBook.
    public void addProfit(ManageBook mb, JSONObject jsonObject) {
        int profit = jsonObject.getInt("profit");
        mb.setProfit(profit);
    }

    // Citation from example
    // Modifies: mb
    // Effects: parses Books in inventory from JSON object and ands them to MangeBook
    public void addBooks(ManageBook mb, JSONObject jsonObject) {
        JSONArray jsonArrayForInventory = jsonObject.getJSONArray("inventory");
        for (Object json : jsonArrayForInventory) {
            JSONObject nextBook = (JSONObject) json;
            addBookstoInventory(mb, nextBook);
        }

    }

    // Citation from example
    // Modifies: mb
    // Effects: parses Books in booksSold from JSON object and adds them to MangeBook
    public void addBooksSold(ManageBook mb, JSONObject jsonObject) {
        JSONArray jsonArrayForBooksSold = jsonObject.getJSONArray("booksSold");
        for (Object json : jsonArrayForBooksSold) {
            JSONObject nextBookInBooksSold = (JSONObject) json;
            addBooktoBooksSold(mb, nextBookInBooksSold);
        }
    }


    // Effects: parses Book from JSON object and add it to ManageBook (inventory);
    // Modifies: mb
    public void addBookstoInventory(ManageBook mb, JSONObject jsonObject) {
        int price = jsonObject.getInt("price");
        String genre = jsonObject.getString("genre");
        String author = jsonObject.getString("author");
        String title = jsonObject.getString("title");
        int quantity = jsonObject.getInt("quantity");
        int quantitySold = jsonObject.getInt("quantitySold");
        Book book = new Book(title,author,genre,price);
        book.setQuantity(quantity);
        book.setQuantitySold(quantitySold);
        mb.addBooktoInventory(book);

    }

    // Effects: parses Book from JSON object and add it to ManageBook (booksSold);
    // Modifies: mb
    public void addBooktoBooksSold(ManageBook mb, JSONObject jsonObject) {
        int price = jsonObject.getInt("price");
        String genre = jsonObject.getString("genre");
        String author = jsonObject.getString("author");
        String title = jsonObject.getString("title");
        int quantity = jsonObject.getInt("quantity");
        int quantitySold = jsonObject.getInt("quantitySold");
        Book book = new Book(title,author,genre,price);
        book.setQuantity(quantity);
        book.setQuantitySold(quantitySold);
        mb.addBooktoBooksSold(book);
    }


}