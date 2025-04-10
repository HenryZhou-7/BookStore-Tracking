package persistence;

import model.Book;
import model.ManageBook;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class JsonTest {
    protected void checkBook(String title, String author, String genre, int p, int q, int qs ,Book b) {
        assertEquals(title, b.getTitle());
        assertEquals(author, b.getAuthor());
        assertEquals(genre, b.getGenre());
        assertEquals(p , b.getPrice());
        assertEquals(q , b.getQuantity());
        assertEquals(qs , b.getSoldQuantity());
    }

    protected void checkManageBook(int profit, ManageBook mb) {
        assertEquals(profit, mb.getProfit());
    }

}
