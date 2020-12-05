package ohtu;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class MovieTest {

    Movie movie;

    @Before
    public void setUp() {
        movie = new Movie("Rocky 9", "Sylvester Stallone", 2020, 179);
    }

    @Test
    public void constructorCreatesMovie() {
        assertEquals("Rocky 9", movie.getTitle());
    }

}
