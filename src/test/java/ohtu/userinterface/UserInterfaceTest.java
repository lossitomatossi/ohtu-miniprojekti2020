package ohtu.userinterface;

import ohtu.Book;
import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;

import org.mockito.Mockito;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;

public class UserInterfaceTest {

    @Test
    public void bookParsedCorrectly() throws IOException {
        BufferedReader br = Mockito.mock(BufferedReader.class);
        Mockito.when(br.readLine()).thenReturn("Ayy", "Wizard", "1995", "250", "ABC1");

        UserInterface app = new UserInterface(br);
        Book expected = new Book("Ayy", "Wizard", 1995, 250, "ABC1");

        Assert.assertTrue(new ReflectionEquals(expected).matches(app.getBook()));
    }
}
