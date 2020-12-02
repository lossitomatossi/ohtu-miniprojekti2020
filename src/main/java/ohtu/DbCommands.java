package ohtu;

import java.sql.*;
import java.util.ArrayList;

import ohtu.database.Database;

/**
 *
 * @author mahi
 */
public class DbCommands {

    private final Connection db;
    private final Statement s;

    public DbCommands(String databaseAddress) throws SQLException, ClassNotFoundException {
        Database database = new Database(databaseAddress);
        db = database.getConnection();
        s = db.createStatement();
        createTables();
    }

    private void createTables() throws SQLException {

        Statement s = db.createStatement();

        try {
            s.execute("CREATE TABLE Books (id INTEGER PRIMARY KEY, name TEXT NOT NULL, Writer TEXT NOT NULL, year INTEGER, pages INTEGER, isbn TEXT)");

            s.execute("CREATE TABLE Youtube_links (id INTEGER PRIMARY KEY, url TEXT NOT NULL, title TEXT, description TEXT, created DATETIME)");

        } catch (org.sqlite.SQLiteException e) {
            // Tables have already been created
        }
    }

    public void add(Object o) throws SQLException {
        if (o instanceof Book) {
            addBook((Book) o);
        }

        if (o instanceof Youtube) {
            addYoutube((Youtube) o);
        }

    }

    private void addBook(Book b) throws SQLException {
        PreparedStatement p = db.prepareStatement("INSERT INTO Books(name, writer, year, pages, isbn) VALUES (?,?,?,?,?)");

        p.setString(1, b.getTitle());
        p.setString(2, b.getAuthor());
        p.setInt(3, b.getYear());
        p.setInt(4, b.getPages());
        p.setString(5, b.getIsbn());

        p.executeUpdate();

    }

    private void addYoutube(Youtube y) throws SQLException {
        PreparedStatement p = db.prepareStatement("INSERT INTO Youtube_links(url, title, description, created) VALUES (?,?,?,?)");

        p.setString(1, y.getUrl());
        p.setString(2, y.getTitle());
        p.setString(3, y.getDescription());
        p.setDate(4, y.getDate());

        p.executeUpdate();

    }

    public ArrayList<Book> listBook() {
        ArrayList<Book> books = new ArrayList<>();

        try {
            ResultSet r = s.executeQuery("SELECT * FROM Books");

            while (r.next()) {
                books.add(new Book(
                        r.getString("name"),
                        r.getString("writer"),
                        r.getInt("year"),
                        r.getInt("pages"),
                        r.getString("isbn")));
            }

        } catch (Exception ignored) {}

        return books;
    }

    public ArrayList<Youtube> listYoutube() {
        ArrayList<Youtube> youtubeLinks = new ArrayList<>();

        try {
            ResultSet r = s.executeQuery("SELECT * FROM Youtube_links");

            while (r.next()) {
                Youtube youtubeLink = new Youtube(
                        r.getString("url"),
                        r.getString("title"),
                        r.getString("description"));

                youtubeLink.setDate(r.getDate("created"));
                youtubeLinks.add(youtubeLink);
            }

        } catch (Exception ignored) {}

        return youtubeLinks;
    }

    public ArrayList<Book> searchBook(String searchTerm) throws SQLException {
        ArrayList<Book> foundBooks = new ArrayList<>();

        PreparedStatement p = db.prepareStatement("SELECT * FROM books WHERE name LIKE ? OR writer LIKE ?");
        p.setString(1, '%' + searchTerm + '%');
        p.setString(2, '%' + searchTerm + '%');
        ResultSet r = p.executeQuery();

        while (r.next()) {
            foundBooks.add(new Book(
                    r.getString("name"),
                    r.getString("writer"),
                    r.getInt("year"),
                    r.getInt("pages"),
                    r.getString("isbn")
            ));
        }

        return foundBooks;
    }

    public ArrayList<Youtube> searchYoutube(String searchTerm) throws SQLException {
        ArrayList<Youtube> foundYoutubeLinks = new ArrayList<>();

        PreparedStatement p = db.prepareStatement("SELECT * FROM Youtube_links WHERE title LIKE ?");
        p.setString(1, '%' + searchTerm + '%');

        ResultSet r = p.executeQuery();

        while (r.next()) {
            Youtube youtubeLink = new Youtube(
                    r.getString("url"),
                    r.getString("title"),
                    r.getString("description")
            );
            youtubeLink.setDate(r.getDate("created"));
            foundYoutubeLinks.add(youtubeLink);
        }

        return foundYoutubeLinks;
    }

    public void removeTable(String name) throws SQLException {
        PreparedStatement p = db.prepareStatement("DROP TABLE " + name);
        p.executeUpdate();
    }

}
