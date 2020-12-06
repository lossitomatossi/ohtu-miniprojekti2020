package ohtu;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BookTest {

    Book book;
    Book additionalInfoBook;

    @Before
    public void setUp() {
        book = new Book("Aapinen", "Agricola");
        additionalInfoBook = new Book("kirjanen", "keijo kirjailija", 2015, 100, "978-0132350884");
    }

    @Test
    public void constructorCreatesBook() {
        assertEquals("Aapinen", book.getTitle());
    }

    @Test
    public void constructorCreatesAdditionalInfoBook() {
        assertEquals(2015, additionalInfoBook.getYear());
    }
    
    @Test
    public void toStringIsCorrect() {
        book.setYear(-1);
        book.setPages(-1);
        book.setIsbn("");
        assertEquals("Aapinen Agricola -      -       -" + "\n", book.toString());
    }
}
