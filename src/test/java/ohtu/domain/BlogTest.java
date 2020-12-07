package ohtu.domain;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class BlogTest {

    Blog blog;
    LocalDate date;

    @Before
    public void setUp() {
        date = LocalDate.of(2020,6,12);
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
