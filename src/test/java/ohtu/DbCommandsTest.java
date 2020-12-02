package ohtu;

import java.sql.SQLException;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;

/**
 *
 * @author mahi
 */
public class DbCommandsTest {

    DbCommands dbc;

    public DbCommandsTest() {
    }

    @Before
    public void setUp() throws SQLException, ClassNotFoundException {
        dbc = new DbCommands("jdbc:sqlite:testing.db");
    }

    @After
    public void tearDown() throws SQLException {
        dbc.removeTable("Books");
        dbc.removeTable("Youtube_links");
    }

    @Test
    public void kirjaOlionVoiLisata() throws SQLException {
        Book book = new Book("nimi", "kirjoittaja", 1, 2, "abc");
        dbc.add(book);

        assertTrue(new ReflectionEquals(dbc.listBook().get(0)).matches(book));
    }

    @Test
    public void YoutubeOlionVoiLisata() throws SQLException {
        Youtube yt = new Youtube("aaa", "title", "bbb");
        dbc.add(yt);

        assertTrue(new ReflectionEquals(dbc.listYoutube().get(0)).matches(yt));
    }

    @Test
    public void kirjaaVoiHakeaNimenPerusteella() throws SQLException {
        Book b1 = new Book("kirja1", "kirjailija", 1996, 100, "isbn1");
        Book b2 = new Book("kirja2", "kirjailija2", 1997, 101, "isbn2");
        Book b3 = new Book("kirja3", "kirjailija3", 1998, 102, "isbn3");

        dbc.add(b1);
        dbc.add(b2);
        dbc.add(b3);

        assertTrue(new ReflectionEquals(dbc.searchBook("kirja1").get(0)).matches(b1));
    }

    @Test
    public void kirjaaVoiHakeaKirjailijanPerusteella() throws SQLException {
        Book b1 = new Book("kirja1", "kirjailija", 1996, 100, "isbn1");
        Book b2 = new Book("kirja2", "kirjailija", 1997, 101, "isbn2");
        Book b3 = new Book("kirja3", "kirjailija", 1998, 102, "isbn3");

        dbc.add(b1);
        dbc.add(b2);
        dbc.add(b3);

        assertEquals(dbc.searchBook("kirjailija").size(), 3);
    }

    @Test
    public void youtubeLinkkiaVoiHakeaOtsikonPerusteella() throws SQLException {
        Youtube yt = new Youtube("urli", "otsikko", "a");
        dbc.add(yt);

        assertTrue(new ReflectionEquals(dbc.searchYoutube("otsikko").get(0)).matches(yt));
    }

}
