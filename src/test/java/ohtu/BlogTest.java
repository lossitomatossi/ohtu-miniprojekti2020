package ohtu;

import java.sql.Date;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class BlogTest {

    Blog blog;
    Date date;

    @Before
    public void setUp() {
        date = new Date(12/06/2020);
        blog = new Blog("www.datavetenskapbloggen", "Viktig text", "Karl XVI Gustav", date);
    }

    @Test
    public void constructorCreatesBlog() {
        assertEquals("www.datavetenskapbloggen", blog.getUrl());
    }
    
    @Test
    public void toStringIsCorrect() {
        assertEquals("www.datavetenskapbloggen Viktig text Karl XVI Gustav " + date + "\n", blog.toString());
    }

}
