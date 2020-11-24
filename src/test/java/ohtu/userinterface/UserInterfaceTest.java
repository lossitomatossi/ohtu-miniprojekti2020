package ohtu.userinterface;

import static org.junit.Assert.*;

import ohtu.Book;
import ohtu.Youtube;
import org.junit.After;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Date;

import org.mockito.Mockito;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;

public class UserInterfaceTest {

    @Test
    public void correctErrorWhenUnknownCommandWithSuccessfulExit() throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        BufferedReader br = Mockito.mock(BufferedReader.class);
        Mockito.when(br.readLine()).thenReturn("halp", "exit");

        UserInterface app = new UserInterface(br);
        app.commandLine();

        String actualOutput = new String(output.toByteArray());
        String expectedError = "No such command exists. Enter 'help' to get help.";

        assertTrue(actualOutput.contains(expectedError) && actualOutput.contains("Exiting.."));
    }

    @Test
    public void bookParsedCorrectly() throws IOException {
        BufferedReader br = Mockito.mock(BufferedReader.class);
        Mockito.when(br.readLine()).thenReturn("Ayy", "Wizard", "1995", "250", "ABC1");

        UserInterface app = new UserInterface(br);

        Book actual = app.getBook();
        Book expected = new Book("Ayy", "Wizard", 1995, 250, "ABC1");

        assertTrue(new ReflectionEquals(expected).matches(actual));
    }

    @Test
    public void youtubeParsedCorrectly() throws IOException {
        BufferedReader br = Mockito.mock(BufferedReader.class);
        Mockito.when(br.readLine()).thenReturn("https://youtu.be/placeholder", "Cat video", "Watch soon!");

        UserInterface app = new UserInterface(br);

        Youtube actual = app.getYoutube();
        Youtube expected = new Youtube("https://youtu.be/placeholder", "Cat video", "Watch soon!");

        // Date has to be manually set to be identical. IntelliJ noticed this, Gradle didn't.
        // As an example, if the test is run at midnight during date change, it would fail.
        expected.setDate(actual.getDate());

        assertTrue(new ReflectionEquals(expected).matches(actual));
    }

    @Test
    public void correctErrorWhenAddingBookWithoutTitle() throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        BufferedReader br = Mockito.mock(BufferedReader.class);
        Mockito.when(br.readLine()).thenReturn("", "Wizard", "1995", "250", "ABC1");

        UserInterface app = new UserInterface(br);
        app.getBook();

        String actualOutput = new String(output.toByteArray());

        assertTrue(actualOutput.contains("Title cannot be blank."));
    }

    @After
    public void returnSystem() {
        System.setOut(System.out);
    }
}
