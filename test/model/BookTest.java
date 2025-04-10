package model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BookTest {

    private Book b1;
    private Book b2;


    @BeforeEach
    void beforeEach() {
        b1 = new Book("t1", "a1", "g1", 1);
        b2 = new Book("t2", "a2", "g2", 1);
    }

    @Test
    void increaseQuantitySoldTest() {
        b1.increaseQuantitySold();
        assertEquals(2, b1.getSoldQuantity());
    }

    @Test
    void addQuantityTest() {
        b1.addQuantity();
        assertEquals(2, b1.getQuantity());
    }

    @Test
    void decreaseQuantityTest() {
        b1.addQuantity();
        b1.addQuantity();
        b1.addQuantity();
        b1.decreaseQuantity();
        assertEquals(3, b1.getQuantity());
    }

    @Test
    void getPriceTest() {
        assertEquals(b1.getPrice(), 1);

    }

    @Test
    void getGenreTest() {
        assertEquals("g1", b1.getGenre());
    }

    @Test
    void getAuthorTest() {
        assertEquals("a1", b1.getAuthor());
    }

    @Test
    void getTitleTest() {
        assertEquals("t1", b1.getTitle());
    }

    @Test
    void setQuantityTest() {
        b1.setQuantity(1);
        assertEquals(1, b1.getQuantity());
    }

    @Test
    void setQuantitySoldTest() {
        b1.setQuantitySold(1);
        assertEquals(1, b1.getSoldQuantity());
    }

}

