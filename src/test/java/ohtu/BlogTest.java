package ohtu;

import java.sql.Date;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class BlogTest {

    Blog blog;

    @Before
    public void setUp() {
        blog = new Blog("www.datavetenskapbloggen", "Vigtig text", "Karl XVI Gustav", new Date(System.currentTimeMillis()));
    }

    @Test
    public void constructorCreatesBlog() {
        assertEquals("www.datavetenskapbloggen", blog.getUrl());
    }

}
