package ohtu.database;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class DatabaseTest {

    Connection conn;
    Database database;
    String databaseAddress;

    @Before
    public void setUp() throws SQLException, ClassNotFoundException {
        databaseAddress = "jdbc:sqlite:connection_test.db";
        database = new Database(databaseAddress);
        conn = database.getConnection();
    }

    @After
    public void tearDown() throws SQLException {
        conn.close();

        File dbFile = new File("connection_test.db");
        dbFile.delete();
    }

    @Test
    public void testGetConnection() throws Exception {
        assertFalse(conn.isClosed());
        conn.close();
        assertTrue(conn.isClosed());
    }
    
    @Test
    public void noSchemaWithoutDatabase() throws SQLException, ClassNotFoundException {
        assertNull(conn.getSchema());
    }
}
