package ohtu.userinterface;

import static org.junit.Assert.*;

import ohtu.domain.Blog;
import ohtu.domain.Book;
import ohtu.domain.Movie;
import ohtu.domain.Youtube;
import ohtu.database.DbCommands;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.SQLException;
import java.time.LocalDate;

import org.mockito.Mockito;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;

public class UserInterfaceTest {

    final PrintStream standardOut = System.out;
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
        
        Book actual = app.input.getBook();
        Book expected = new Book("Ayy", "Wizard", 1995, 250, "ABC1");

        assertTrue(new ReflectionEquals(expected).matches(actual));
    }

    @Test
    public void youtubeParsedCorrectly() throws IOException, SQLException, ClassNotFoundException {
        BufferedReader br = Mockito.mock(BufferedReader.class);
        Mockito.when(br.readLine()).thenReturn("https://youtu.be/placeholder", "Cat video", "Watch soon!");

        UserInterface app = new UserInterface(br, dbc);

        Youtube actual = app.input.getYoutube();
        Youtube expected = new Youtube("https://youtu.be/placeholder", "Cat video", "Watch soon!");

        // Date has to be manually set to be identical. IntelliJ noticed this, Gradle didn't.
        // As an example, if the test is run at midnight during date change, it would fail.
        expected.setDate(actual.getDate());

        assertTrue(new ReflectionEquals(expected).matches(actual));
    }

    @Test
    public void movieParsedCorrectly() throws IOException, SQLException, ClassNotFoundException {
        BufferedReader br = Mockito.mock(BufferedReader.class);
        Mockito.when(br.readLine()).thenReturn("Matrix", "The Wachowskis", "1995", "136");

        UserInterface app = new UserInterface(br, dbc);

        Movie actual = app.input.getMovie();
        Movie expected = new Movie("Matrix", "The Wachowskis", 1995, 136);

        assertTrue(new ReflectionEquals(expected).matches(actual));
    }

    @Test
    public void blogParsedCorrectly() throws IOException, SQLException, ClassNotFoundException {
        BufferedReader br = Mockito.mock(BufferedReader.class);
        Mockito.when(br.readLine()).thenReturn("https://example.com", "Blog title", "Suzuki", "2020-2-2");

        UserInterface app = new UserInterface(br, dbc);

        Blog actual = app.input.getBlog();
        Blog expected = new Blog("https://example.com", "Blog title", "Suzuki", LocalDate.of(2020, 2, 2));

        assertTrue(new ReflectionEquals(expected).matches(actual));
    }

    @Test
    public void correctErrorWhenAddingBookWithoutTitle() throws IOException, SQLException, ClassNotFoundException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        BufferedReader br = Mockito.mock(BufferedReader.class);
        Mockito.when(br.readLine()).thenReturn("", "Wizard", "1995", "250", "ABC1");

        UserInterface app = new UserInterface(br, dbc);
        app.input.getBook();

        String actualOutput = output.toString();

        assertTrue(actualOutput.contains("Title cannot be blank."));
    }

    @Test
    public void printedCorrectListings() throws IOException, SQLException, ClassNotFoundException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        Book b = new Book("Refactoring", "Martin Fowler", 2018, 448, "978-0134757599");
        Youtube yt = new Youtube("https://www.youtube.com/watch?v=TRcReyRYIMg", "What is Scrum?", "");

        BufferedReader br = Mockito.mock(BufferedReader.class);
        Mockito.when(br.readLine()).thenReturn("list", "book", "list", "youtube", "exit");

        UserInterface app = new UserInterface(br, dbc);
        app.store(b);
        app.store(yt);
        app.commandLine();

        String actualOutput = output.toString();

        assertTrue(actualOutput.contains("Refactoring          Martin Fowler        2018   448     978-0134757599"));
        assertTrue(actualOutput.contains("https://www.youtube.com/watch?v=TRcReyRYIMg What is Scrum?       " + yt.getDate() + " "));
    }

    @Test
    public void printedCorrectSearchResults() throws IOException, SQLException, ClassNotFoundException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        Youtube yt = new Youtube("https://www.youtube.com/watch?v=TRcReyRYIMg", "What is Scrum?", "");

        BufferedReader br = Mockito.mock(BufferedReader.class);
        Mockito.when(br.readLine()).thenReturn("search", "youtube", "scrum", "exit");

        UserInterface app = new UserInterface(br, dbc);
        app.store(yt);
        app.commandLine();

        String actualOutput = output.toString();

        assertTrue(actualOutput.contains("https://www.youtube.com/watch?v=TRcReyRYIMg What is Scrum?       " + yt.getDate() + " "));
    }

    @After
    public void returnSystem() throws SQLException {
        System.setOut(standardOut);
        dbc.closeDbConnection();
        String msg = new File(testDatabase).delete() ? "" + testDatabase + " deleted succesfully" : "Failed to delete " + testDatabase;
        //System.out.println("***\n" + msg + "\n***");
    }
}
