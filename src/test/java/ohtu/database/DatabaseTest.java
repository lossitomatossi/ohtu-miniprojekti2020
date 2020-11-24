package ohtu.database;

import java.sql.Connection;
import java.sql.SQLException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class DatabaseTest {

    Connection conn;
    Database database;
    String databaseAddress;

    @Before
    public void setUp() throws SQLException, ClassNotFoundException {
        databaseAddress = "jdbc:sqlite:turha.db";
        database = new Database(databaseAddress);
        conn = database.getConnection();
    }

    @Test
    public void testGetConnection() throws Exception {
        assertEquals(false, conn.isClosed());
        conn.close();
        assertEquals(true, conn.isClosed());
    }
    
    @Test
    public void noSchemaWithoutDatabase() throws ClassNotFoundException, SQLException {
        Database database2 = new Database("jdbc:sqlite:eiole.db");
        conn = database2.getConnection();
        assertEquals(null, conn.getSchema());
    }

}
