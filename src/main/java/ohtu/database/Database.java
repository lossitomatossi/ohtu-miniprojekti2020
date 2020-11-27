package ohtu.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private final String databaseAddress;

    /**
     *
     * @param databaseAddress a String containing the name of the database
     * @throws ClassNotFoundException if the database can't be found
     */
    public Database(String databaseAddress) throws ClassNotFoundException {
        this.databaseAddress = databaseAddress;
    }

    /**
     *
     * @return Passes Java's built in DriverManager to get a connection to a
     * database
     * @throws SQLException if the connections fails
     */
    public Connection getConnection() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException ignored) {
        }
        return DriverManager.getConnection(databaseAddress);
    }

}
