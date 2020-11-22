package ohtu;

import org.junit.*;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class BookTest {

    Book book;
    Book additionalInfoBook;
    double comparisonAccurary = 0.0001;

    @Before
    public void setUp() {
        book = new Book("Aapinen", "Agricola");
        additionalInfoBook = new Book ("kirjanen", "keijo kirjailija", 2015, 100, "978-0132350884");
    }

    @Test
    public void constructorCreatesBook() {
        assertEquals("Aapinen", book.getName());
    }
    
    @Test
    public void constructorCreatesAdditionalInfoBook() {
        assertEquals(2015, additionalInfoBook.getYear());
    }
}
