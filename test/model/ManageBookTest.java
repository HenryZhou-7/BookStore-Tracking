package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ManageBookTest {

    private Book b1;
    private Book b2;
    private Book b3;
    private Book b4;
    private Book b5;
    private ManageBook manageBook;

    @BeforeEach
    void runBefore() {
        b1 = new Book("t1", "a1", "g1", 1);
        b2 = new Book("t2", "a2", "g2", 1);
        b3 = new Book("t3", "a1", "g3", 1);
        b4 = new Book("t4", "a2", "g4", 1);
        b5 = new Book("t1", "a1", "g5", 1000);
        manageBook = new ManageBook();
    }

    @Test
    void testFindBookInInventory() {
        assertNull(manageBook.findBookInInventory(b1));
        manageBook.addBooktoInventory(b1);
        assertEquals(1, manageBook.getInventory().size());
        assertEquals(b1, manageBook.findBookInInventory(b1));
        assertEquals(null, manageBook.findBookInInventory(b2));

    }
    @Test
    void testFindBookInInventory3() {
        manageBook.addBooktoInventory(b5);
        assertEquals(b5, manageBook.findBookInInventory(b5));
        manageBook.addBooktoInventory(b1);
        assertEquals(b5, manageBook.findBookInInventory(b1));


    }

    @Test
    public void testAddBookToBooksSold() {
        assertFalse(manageBook.getBooksSold().contains(b1));
        manageBook.addBooktoBooksSold(b1);
        assertTrue(manageBook.getBooksSold().contains(b1));
    }

    @Test
    public void testFindBookInBooksSold() {
        manageBook.addBooktoBooksSold(b1);
        assertEquals(b1, manageBook.findBookInBooksSold(b1));
        assertNull(manageBook.findBookInBooksSold(b2));
    }

    @Test
    public void testAddBooktoInventory() {
        assertFalse(manageBook.getInventory().contains(b1));
        manageBook.addBooktoInventory(b1);
        assertEquals(b1, manageBook.findBookInInventory(b1));
        assertEquals(null, manageBook.findBookInInventory(b2));
        manageBook.addBooktoInventory(b2);
        assertEquals(1, b2.getQuantity());
        assertEquals(true, manageBook.getInventory().contains(b2));
        manageBook.addBooktoInventory(b1);
        assertEquals(2, b1.getQuantity());
    }

    @Test
    public void testSellBook() {
        manageBook.addBooktoInventory(b1);
        manageBook.addBooktoInventory(b1);
        assertEquals(2, b1.getQuantity());
        manageBook.sellBook("t1", "a1");
        assertEquals(b1, manageBook.findBookInBooksSold(b1));
        assertEquals(1, b1.getQuantity());
        assertEquals(1, b1.getSoldQuantity());
        assertEquals(1, manageBook.getProfit());
        manageBook.sellBook("t1", "a1");
        assertEquals(2, b1.getSoldQuantity());


    }

    @Test
    public void testSellBook2() {
        manageBook.addBooktoInventory(b1);
        manageBook.addBooktoInventory(b4);
        manageBook.sellBook("t1", "a1");
        assertEquals(null, manageBook.findBookInBooksSold(b4));
        assertEquals(1, b4.getQuantity());
        assertEquals(1, b4.getSoldQuantity());
        assertEquals(1, manageBook.getProfit());

    }

    @Test
    public void testSellBook3() {
        manageBook.addBooktoInventory(b1);
        manageBook.addBooktoInventory(b1);
        manageBook.addBooktoInventory(b4);
        manageBook.addBooktoInventory(b4);
        manageBook.sellBook("t1", "a1");
        assertEquals("a1",b1.getAuthor());
        assertEquals("t1",b1.getTitle());
        manageBook.sellBook("t1", "a1");
        assertEquals(null, manageBook.findBookInBooksSold(b4));
        assertEquals(2, b4.getQuantity());
        assertEquals(1, b4.getSoldQuantity());
        assertEquals(2, manageBook.getProfit());
    }


    @Test
    public void testSearchByGenre() {
        List<Book> testList = new ArrayList<>();
        testList.add(b1);
        manageBook.addBooktoInventory(b1);
        assertEquals(testList, manageBook.searchByGenre("g1"));
        assertTrue(manageBook.searchByGenre("p").isEmpty());


    }

    @Test
    public void testSearchByAuthor() {
        List<Book> testList = new ArrayList<>();
        testList.add(b1);
        manageBook.addBooktoInventory(b1);
        manageBook.addBooktoInventory(b2);
        assertEquals(testList, manageBook.searchByAuthor("a1"));
        assertEquals(1, manageBook.searchByAuthor("a1").size());
        assertTrue(manageBook.searchByAuthor("p").isEmpty());


    }

    @Test
    public void testSearchByTitle() {
        List<Book> testList = new ArrayList<>();
        testList.add(b1);
        manageBook.addBooktoInventory(b1);
        assertEquals(testList, manageBook.searchByTitle("t1"));
        assertTrue(manageBook.searchByTitle("p").isEmpty());
    }

    @Test
    public void testGetBooksSold() {
        manageBook.addBooktoBooksSold(b1);
        manageBook.addBooktoBooksSold(b2);
        List<Book> testList = new ArrayList<>();
        testList.add(b1);
        testList.add(b2);
        assertEquals(testList, manageBook.getBooksSold());
    }

    @Test
    public void testGetInventory() {
        List<Book> testList2 = new ArrayList<>();
        assertEquals(testList2, manageBook.getInventory());
        manageBook.addBooktoInventory(b1);
        manageBook.addBooktoInventory(b2);
        List<Book> testList = new ArrayList<>();
        testList.add(b1);
        testList.add(b2);
        assertEquals(testList, manageBook.getInventory());
        assertEquals(2, manageBook.getInventory().size());


        List<Book> testList3 = manageBook.getInventory();
        assertNotNull(testList3);
        assertTrue(testList3.contains(b1));
        assertTrue(testList3.contains(b2));
        testList3.remove(b1);
        assertFalse(manageBook.getInventory().contains(b1));
        Book b3 = new Book("t3", "a3", "g3", 1);
        manageBook.addBooktoInventory(b3);
        assertTrue(testList3.contains(b3));
    }


    @Test
    public void testGetProfit() {
        assertEquals(0, manageBook.getProfit());
        manageBook.addBooktoInventory(b1);
        manageBook.addBooktoInventory(b1);
        manageBook.addBooktoInventory(b1);
        manageBook.addBooktoInventory(b1);
        manageBook.sellBook("t1", "a1");
        manageBook.sellBook("t1", "a1");
        manageBook.sellBook("t1", "a1");
        manageBook.addBooktoInventory(b2);
        manageBook.sellBook("t2", "a2");
        assertEquals(4, manageBook.getProfit());
    }

    @Test
    public void testFindBookInInventory2(){
        Book newBook = new Book("harry", "potter","fantasy", 1);
        manageBook.addBooktoInventory(newBook);
        assertEquals("potter",newBook.getAuthor());
        assertEquals("harry", newBook.getTitle());
        assertEquals(newBook, manageBook.findBookInInventory(newBook));
    }
    @Test
    public void testFindBookInBooksSold2(){
        Book newBook = new Book("harry", "potter","fantasy", 1);
        manageBook.addBooktoInventory(newBook);
        manageBook.sellBook("harry", "potter");
        assertEquals("potter",newBook.getAuthor());
        assertEquals("harry", newBook.getTitle());
        assertEquals(newBook, manageBook.findBookInBooksSold(newBook));
    }
    @Test
    public void testSetProfit() {
        manageBook.setProfit(10);
        assertEquals(10, manageBook.getProfit());
    }


    @Test
    public void testFindBookInInventory4() {
        assertNull(manageBook.findBookInInventory(null));
        manageBook.addBooktoInventory(b1);
        assertEquals("t1", b1.getTitle());
        assertEquals("a1", b1.getAuthor());
        assertEquals(b1, manageBook.findBookInInventory(b1));
        manageBook.sellBook("t1","a1");
        assertEquals("t1", b1.getTitle());
        assertEquals("a1", b1.getAuthor());
        assertEquals( b1 ,manageBook.findBookInBooksSold(b1));
    }

}
