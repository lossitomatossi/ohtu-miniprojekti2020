package ohtu;

import java.sql.Date;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author julinden
 */
public class YoutubeTest {
    Youtube youtube;

    @Before
    public void setUp() {
        youtube = new Youtube("www.com", "title", "description");
    }

    @Test
    public void constructorCreatesYoutube() {
        assertEquals("www.com", youtube.getUrl());
    }
    
    @Test
    public void dateIsCorrect() {
        assertEquals(new Date(System.currentTimeMillis()), youtube.getDate());
    }
    
    @Test
    public void toStringIsCorrect() {
        assertEquals("www.com title " + youtube.getDate() + " description" + "\n", youtube.toString());
    }
}
