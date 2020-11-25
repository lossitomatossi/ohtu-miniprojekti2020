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

dbc = new DbCommands("jdbc:sqlite:testaus.db");
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
    public void kirjaOlionVoiLisata()throws SQLException, ClassNotFoundException{


        Book b = new Book("nimi", "kirjoittaja", 1, 2, "abc");
        dbc.add(b);
        assertEquals(dbc.BookTable(), "nimi kirjoittaja 1 2 abc");
        



    }

    @Test
    public void YoutubeOlionVoiLisata()throws SQLException, ClassNotFoundException{
        Youtube y = new Youtube("aaa", "title", "bbb");
        dbc.add(y);

        assertEquals(dbc.YoutubeTable(),"aaa title bbb");

    }

}
