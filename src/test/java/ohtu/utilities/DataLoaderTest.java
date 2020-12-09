package ohtu.utilities;

import ohtu.domain.Book;
import ohtu.domain.Blog;
import ohtu.domain.Movie;
import ohtu.domain.Youtube;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import org.mockito.internal.matchers.apachecommons.ReflectionEquals;

public class DataLoaderTest {

    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {
    }

    @Test
    public void bookIsParsedCorrectly() throws ParseException {
        String line = "book;Effective Cybersecurity;William Stallings;2018;800;978-0134772806";
        String[] inputs = line.split(";");

        Object actual = DataLoader.parseRecommendation(inputs);
        Book expected = new Book("Effective Cybersecurity", "William Stallings", 2018, 800, "978-0134772806");

        assertTrue(actual instanceof Book);
        assertTrue(new ReflectionEquals(expected).matches(actual));

    }

    @Test
    public void youtubeIsParsedCorrectly() throws ParseException {
        String line = "youtube;https://www.youtube.com/watch?v=s4thQcgLCqk;Scrum: How to do twice as much in half the time;Jeff Sutherland's TEDx Talk";
        String[] inputs = line.split(";");

        Object actual = DataLoader.parseRecommendation(inputs);
        Youtube expected = new Youtube("https://www.youtube.com/watch?v=s4thQcgLCqk", "Scrum: How to do twice as much in half the time", "Jeff Sutherland's TEDx Talk");

        assertTrue(actual instanceof Youtube);
        assertTrue(new ReflectionEquals(expected).matches(actual));
    }

    @Test
    public void movieIsParsedCorrectly() throws ParseException {
        String line = "movie;Leon;Luc Besson;1994;110";
        String[] inputs = line.split(";");

        Object actual = DataLoader.parseRecommendation(inputs);
        Movie expected = new Movie("Leon", "Luc Besson", 1994, 110);

        assertTrue(actual instanceof Movie);
        assertTrue(new ReflectionEquals(expected).matches(actual));
    }

    @Test
    public void blogIsParsedCorrectly() throws ParseException {
        String line = "blog;https://bit.ly/2JKJ6X6;In 1974 Planarity was O(V) time and could...;William Gasarch;06/12/2020";
        String[] inputs = line.split(";");

        Object actual = DataLoader.parseRecommendation(inputs);
        Blog expected = new Blog("https://bit.ly/2JKJ6X6", "In 1974 Planarity was O(V) time and could...", "William Gasarch", LocalDate.parse("06/12/2020", DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        assertTrue(actual instanceof Blog);
        assertTrue(new ReflectionEquals(expected).matches(actual));
    }
}
