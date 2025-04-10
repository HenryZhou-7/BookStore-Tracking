package model;



import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for the Event class
 */
public class EventTest {
    private Event e;
    private Date d;

    @BeforeEach
    public void runBefore() {
        e = new Event("e1");
        d = Calendar.getInstance().getTime();
    }

    @Test
    public void testEvent() {
        assertEquals("e1", e.getDescription());
        assertEquals(d.getTime(), e.getDate().getTime(), 100);
        assertEquals(d.getDay(), e.getDate().getDay());
        assertEquals(d.getMonth(), e.getDate().getMonth());
        assertEquals(d.getYear(), e.getDate().getYear());
        assertEquals(d.getHours(), e.getDate().getHours());
    }

    @Test
    public void testToString() {
        assertEquals(d.toString() + "\n" + "e1", e.toString());
    }
}
