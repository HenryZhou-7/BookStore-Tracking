package persistence;

import model.Book;
import model.ManageBook;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Citation from example;
class JsonWriterTest extends JsonTest {
    @Test
    void testWriterInvalidFile() {
        try {
            ManageBook mb = new ManageBook();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyManageBook() {
        try {
            ManageBook mb = new ManageBook();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyManageBook.json");
            writer.open();
            writer.write(mb);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyManageBook.json");
            mb = reader.read();

            assertEquals(0, mb.getProfit());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralManageBook() {
        try {
            ManageBook mb = new ManageBook();
            Book b1 = new Book("t1","a1","g1",1);
            Book b2 = new Book("t2","a2","g2",2);
            b1.setQuantity(2);
            b1.setQuantitySold(2);
            b2.setQuantity(2);
            b2.setQuantitySold(2);
            mb.addBooktoBooksSold(b2);
            mb.addBooktoInventory(b1);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralManageBook.json");
            writer.open();
            writer.write(mb);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralManageBook.json");
            mb = reader.read();
            List<Book> books = mb.getInventory();
            assertEquals(1, books.size());
            checkBook("t1", "a1","g1" , 1, 2, 2, books.get(0));
            checkManageBook(0 , mb);

            List<Book> booksSold = mb.getBooksSold();
            assertEquals(1, booksSold.size());
            checkBook("t2", "a2","g2" , 2, 2, 2, booksSold.get(0));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}