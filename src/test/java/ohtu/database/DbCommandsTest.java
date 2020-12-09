package ohtu.database;

import java.io.File;
import java.sql.SQLException;
import java.time.LocalDate;

import ohtu.domain.Blog;
import ohtu.domain.Book;
import ohtu.domain.Movie;
import ohtu.domain.Youtube;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
        File dbFile = new File("testing.db");
        dbFile.delete();

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
    public void kirjanVoiPoistaa() throws SQLException {
        Book b1 = new Book("kirja1", "kirjailija", 1996, 100, "isbn1");
        Book b2 = new Book("kirja2", "kirjailija2", 1997, 101, "isbn2");
        Book b3 = new Book("kirja3", "kirjailija3", 1998, 102, "isbn3");

        dbc.add(b1);
        dbc.add(b2);
        dbc.add(b3);

        assertTrue(dbc.deleteBook("kirja1"));

    }

    @Test
    public void olemattomankirjanPoistaminenPalauttaaFalse() throws SQLException {
        Book b1 = new Book("kirja1", "kirjailija", 1996, 100, "isbn1");
        Book b2 = new Book("kirja2", "kirjailija2", 1997, 101, "isbn2");
        Book b3 = new Book("kirja3", "kirjailija3", 1998, 102, "isbn3");

        dbc.add(b1);
        dbc.add(b2);
        dbc.add(b3);

        assertFalse(dbc.deleteBook("kirjaa"));

    }

    @Test
    public void youtubeLinkinVoiPoistaa() throws SQLException {
        Youtube yt = new Youtube("aaa", "title", "bbb");
        dbc.add(yt);

        assertTrue(dbc.deleteYoutube("title"));

    }

    @Test
    public void olemattomanYoutubeLinkinPoistaminenPalauttaaFalse() throws SQLException {
        Youtube yt = new Youtube("aaa", "title", "bbb");
        dbc.add(yt);

        assertFalse(dbc.deleteYoutube("tittle"));

    }

    @Test
    public void bloginVoiPoistaa() throws SQLException {
        Blog b1 = new Blog("url1", "otsikko1", "kirjailija1", LocalDate.of(1996, 2, 2));
        Blog b2 = new Blog("url2", "otsikko2", "kirjailija2", LocalDate.of(1997, 2, 2));
        Blog b3 = new Blog("url3", "otsikko3", "kirjailija3", LocalDate.of(1998, 2, 2));

        dbc.add(b1);
        dbc.add(b2);
        dbc.add(b3);

        assertTrue(dbc.deleteBlog("otsikko1"));

    }

    @Test
    public void olemattomanBloginPoistaminenPalauttaaFalse() throws SQLException {
        Blog b1 = new Blog("url1", "otsikko1", "kirjailija1", LocalDate.of(1996, 2, 2));
        Blog b2 = new Blog("url2", "otsikko2", "kirjailija2", LocalDate.of(1997, 2, 2));
        Blog b3 = new Blog("url3", "otsikko3", "kirjailija3", LocalDate.of(1998, 2, 2));

        dbc.add(b1);
        dbc.add(b2);
        dbc.add(b3);
        assertFalse(dbc.deleteBlog("tittle"));

    }

    @Test
    public void elokuvanVoiPoistaa() throws SQLException {
        Movie m1 = new Movie("nimi1", "David Lynch", 2001, 2);
        Movie m2 = new Movie("nimi2", "David Lynch", 2002, 3);
        Movie m3 = new Movie("nimi3", "David Lynch", 2003, 2);

        dbc.add(m1);
        dbc.add(m2);
        dbc.add(m3);
        assertTrue(dbc.deleteMovie("nimi2"));

    }

    @Test
    public void olemattonmanElokuvanPoistaminenPalauttaaFalse() throws SQLException {
        Movie m1 = new Movie("nimi1", "David Lynch", 2001, 2);
        Movie m2 = new Movie("nimi2", "David Lynch", 2002, 3);
        Movie m3 = new Movie("nimi3", "David Lynch", 2003, 2);

        dbc.add(m1);
        dbc.add(m2);
        dbc.add(m3);
        assertFalse(dbc.deleteMovie("tittle"));

    }

    @Test
    public void blogiaVoiHakeaNimenPerusteella() throws SQLException {
        Blog b1 = new Blog("url1", "otsikko1", "kirjailija1", LocalDate.of(1996, 2, 2));
        Blog b2 = new Blog("url2", "otsikko2", "kirjailija2", LocalDate.of(1997, 2, 2));
        Blog b3 = new Blog("url3", "otsikko3", "kirjailija3", LocalDate.of(1998, 2, 2));

        dbc.add(b1);
        dbc.add(b2);
        dbc.add(b3);

        assertTrue(new ReflectionEquals(dbc.searchBlog("otsikko1").get(0)).matches(b1));
    }

    @Test
    public void blogiaVoiHakeaKirjoittajanPerusteella() throws SQLException {
        Blog b1 = new Blog("url1", "otsikko1", "kirjailija1", LocalDate.of(1996, 2, 2));
        Blog b2 = new Blog("url2", "otsikko2", "kirjailija1", LocalDate.of(1997, 2, 2));
        Blog b3 = new Blog("url3", "otsikko3", "kirjailija1", LocalDate.of(1998, 2, 2));

        dbc.add(b1);
        dbc.add(b2);
        dbc.add(b3);

        assertEquals(dbc.searchBlog("kirjailija1").size(), 3);
    }

    @Test
    public void elokuvaaVoiHakeaNimenPerusteella() throws SQLException {
        Movie m1 = new Movie("nimi1", "David Lynch", 2001, 2);
        Movie m2 = new Movie("nimi2", "David Lynch", 2002, 3);
        Movie m3 = new Movie("nimi3", "David Lynch", 2003, 2);

        dbc.add(m1);
        dbc.add(m2);
        dbc.add(m3);

        assertTrue(new ReflectionEquals(dbc.searchMovie("nimi1").get(0)).matches(m1));
    }

    @Test
    public void elokuvaaVoiHakeaOhjaajanPerusteella() throws SQLException {
        Movie m1 = new Movie("nimi1", "David Lynch", 2001, 2);
        Movie m2 = new Movie("nimi2", "David Lynch", 2002, 3);
        Movie m3 = new Movie("nimi3", "David Lynch", 2003, 2);

        dbc.add(m1);
        dbc.add(m2);
        dbc.add(m3);

        assertEquals(dbc.searchMovie("David Lynch").size(), 3);

    }

    @Test
    public void youtubeLinkkiaVoiHakeaOtsikonPerusteella() throws SQLException {
        Youtube yt = new Youtube("urli", "otsikko", "a");
        dbc.add(yt);

        assertTrue(new ReflectionEquals(dbc.searchYoutube("otsikko").get(0)).matches(yt));
    }

    @Test
    public void aiemminLisattyKirjaLoytyyTietokannasta() throws SQLException {
        Book b1 = new Book("kirja1", "kirjailija", 1950, 100, "isbn1");
        Book b2 = new Book("kirja2", "kirjailija", 1951, 101, "isbn2");

        dbc.add(b1);
        dbc.add(b2);

        assertTrue(dbc.containsBook(b1));
        assertTrue(dbc.containsBook(b2));
    }

    @Test
    public void aiemminLisattyYoutubeLinkkiLoytyyTietokannasta() throws SQLException {
        Youtube yt = new Youtube("urli", "otsikko", "a");

        dbc.add(yt);

        assertTrue(dbc.contains(yt));
    }

    @Test
    public void kirjaaJotaEiOleLisattyEiLoydyTietokannasta() throws SQLException {
        Book b1 = new Book("kirja1", "kirjailija", 1950, 100, "isbn1");

        assertFalse(dbc.containsBook(b1));
    }

    @Test
    public void youtubeLinkkiaJotaEiOleLisattyEiLoydyTietokannasta() throws SQLException {
        Youtube yt1 = new Youtube("urli", "otsikko", "a");

        assertFalse(dbc.contains(yt1));
    }

    @Test
    public void tuntematontaLukuvinkkiaEiLoydyTietokannasta() throws SQLException {
        assertFalse(dbc.contains(new Object()));
    }
}
