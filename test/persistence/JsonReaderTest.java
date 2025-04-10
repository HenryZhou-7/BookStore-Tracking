package persistence;

import model.Book;
import model.ManageBook;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            ManageBook wr = reader.read();
            fail("IOException expected");
        } catch (IOException e) {

        }
    }

    @Test
    void testReaderEmptyManageBook() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyManageBook.json");
        try {
            ManageBook mb = reader.read();
            assertEquals(0, mb.getInventory().size());
            assertEquals(0, mb.getProfit());
            assertEquals(0, mb.getBooksSold().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralManageBook() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralManageBook.json");
        try {
            ManageBook mb = reader.read();
            assertEquals(0 ,mb.getProfit());
            List<Book> books = mb.getInventory();
            assertEquals(books.get(0).getPrice(), 1);
            assertEquals(books.get(0).getAuthor(), "a1");
            assertEquals(books.get(0).getTitle(), "t1");
            assertEquals(books.get(0).getGenre(), "g1");
            assertEquals(books.get(0).getQuantity(), 2);
            assertEquals(books.get(0).getSoldQuantity(), 3);
            assertEquals(1, books.size());


            List<Book> booksSold = mb.getBooksSold();
            assertEquals(booksSold.get(0).getPrice(), 1);
            assertEquals(booksSold.get(0).getAuthor(), "a1");
            assertEquals(booksSold.get(0).getTitle(), "t1");
            assertEquals(booksSold.get(0).getGenre(), "g1");
            assertEquals(booksSold.get(0).getQuantity(), 2);
            assertEquals(booksSold.get(0).getSoldQuantity(), 2);
            assertEquals(1, booksSold.size());

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}