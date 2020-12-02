/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu;

import java.sql.SQLException;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author mahi
 */
public class DbCommandsTest {

    DbCommands dbc;

    public DbCommandsTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
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

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Test
    public void kirjaOlionVoiLisata() throws SQLException {

        Book b = new Book("nimi", "kirjoittaja", 1, 2, "abc");
        dbc.add(b);
        assertEquals(dbc.BookTable(), "nimi kirjoittaja 1 2 abc");

    }

    @Test
    public void YoutubeOlionVoiLisata() throws SQLException {
        Youtube y = new Youtube("aaa", "title", "bbb");
        dbc.add(y);

        assertEquals(dbc.YoutubeTable(), "aaa title bbb");

    }

    @Test
    public void kirjaaVoiHakeaNimenPerusteella() throws SQLException {
        Book b1 = new Book("kirja1", "kirjailija", 1996, 100, "isbn1");
        Book b2 = new Book("kirja2", "kirjailija2", 1997, 101, "isbn2");
        Book b3 = new Book("kirja3", "kirjailija3", 1998, 102, "isbn3");

        dbc.add(b1);
        dbc.add(b2);
        dbc.add(b3);

        assertEquals(dbc.search("book", "kirja1"), "kirja1 kirjailija 1996 100 isbn1 ");

    }

    @Test
    public void kirjaaVoiHakeaKirjailijanPerusteella() throws SQLException {
        Book b1 = new Book("kirja1", "kirjailija", 1996, 100, "isbn1");
        Book b2 = new Book("kirja2", "kirjailija", 1997, 101, "isbn2");
        Book b3 = new Book("kirja3", "kirjailija", 1998, 102, "isbn3");

        dbc.add(b1);
        dbc.add(b2);
        dbc.add(b3);

        assertEquals(dbc.search("book", "kirjailija"), "kirja1 kirjailija 1996 100 isbn1 kirja2 kirjailija 1997 101 isbn2 kirja3 kirjailija 1998 102 isbn3 ");

    }

    @Test
    public void youtubeLinkkiäVoiHakeaOtsikonPerusteella() throws SQLException {
        Youtube yt = new Youtube("urli", "otsikko", "a");
        dbc.add(yt);
        assertEquals(dbc.search("youtube", "otsikko"), "urli otsikko a ");

    }

}
