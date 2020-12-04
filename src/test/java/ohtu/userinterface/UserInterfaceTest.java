package ohtu.userinterface;

import static org.junit.Assert.*;

import ohtu.Book;
import ohtu.Youtube;
import ohtu.DbCommands;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.SQLException;

import org.mockito.Mockito;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;

public class UserInterfaceTest {

    final String testDatabase = "testing.db";
    DbCommands dbc;

    @Before
    public void setUp() throws SQLException, ClassNotFoundException {
        dbc = new DbCommands("jdbc:sqlite:" + testDatabase);
    }

    @Test
    public void correctErrorWhenUnknownCommandWithSuccessfulExit() throws IOException, SQLException, ClassNotFoundException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        BufferedReader br = Mockito.mock(BufferedReader.class);
        Mockito.when(br.readLine()).thenReturn("halp", "exit");

        UserInterface app = new UserInterface(br, dbc);
        app.commandLine();

        String actualOutput = output.toString();
        String expectedError = "No such command exists. Enter 'help' to get help.";

        assertTrue(actualOutput.contains(expectedError) && actualOutput.contains("Exiting.."));
    }

    @Test
    public void bookParsedCorrectly() throws IOException, SQLException, ClassNotFoundException {
        BufferedReader br = Mockito.mock(BufferedReader.class);
        Mockito.when(br.readLine()).thenReturn("Ayy", "Wizard", "1995", "250", "ABC1");

        UserInterface app = new UserInterface(br, dbc);

        Book actual = app.getBook();
        Book expected = new Book("Ayy", "Wizard", 1995, 250, "ABC1");

        assertTrue(new ReflectionEquals(expected).matches(actual));
    }

    @Test
    public void youtubeParsedCorrectly() throws IOException, SQLException, ClassNotFoundException {
        BufferedReader br = Mockito.mock(BufferedReader.class);
        Mockito.when(br.readLine()).thenReturn("https://youtu.be/placeholder", "Cat video", "Watch soon!");

        UserInterface app = new UserInterface(br, dbc);

        Youtube actual = app.getYoutube();
        Youtube expected = new Youtube("https://youtu.be/placeholder", "Cat video", "Watch soon!");

        // Date has to be manually set to be identical. IntelliJ noticed this, Gradle didn't.
        // As an example, if the test is run at midnight during date change, it would fail.
        expected.setDate(actual.getDate());

        assertTrue(new ReflectionEquals(expected).matches(actual));
    }

    @Test
    public void correctErrorWhenAddingBookWithoutTitle() throws IOException, SQLException, ClassNotFoundException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        BufferedReader br = Mockito.mock(BufferedReader.class);
        Mockito.when(br.readLine()).thenReturn("", "Wizard", "1995", "250", "ABC1");

        UserInterface app = new UserInterface(br, dbc);
        app.getBook();

        String actualOutput = output.toString();

        assertTrue(actualOutput.contains("Title cannot be blank."));
    }

    @Test
    public void printedCorrectListings() throws IOException, SQLException, ClassNotFoundException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        BufferedReader br = Mockito.mock(BufferedReader.class);
        Mockito.when(br.readLine()).thenReturn("list", "book", "list", "youtube", "exit");

        UserInterface app = new UserInterface(br, dbc);
        app.commandLine();

        String actualOutput = output.toString();

        assertTrue(actualOutput.contains("Title                                     Author                Year   Pages   ISBN"));
    }

    @Test
    public void printedCorrectSearchResults() throws IOException, SQLException, ClassNotFoundException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        BufferedReader br = Mockito.mock(BufferedReader.class);
        Mockito.when(br.readLine()).thenReturn("search", "youtube", "test", "exit");

        UserInterface app = new UserInterface(br, dbc);
        app.commandLine();

        String actualOutput = output.toString();

        assertTrue(actualOutput.contains("URL                                       Title                                     Created Description"));
    }

    @After
    public void returnSystem() throws SQLException {
        System.setOut(System.out);
        dbc.closeDbConnection();
        String msg = new File(testDatabase).delete() ? "" + testDatabase + " deleted succesfully" : "Failed to delete " + testDatabase;
        //System.out.println(msg);
    }
}
