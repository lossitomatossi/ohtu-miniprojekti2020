package ohtu.database;

import java.sql.*;
import java.util.ArrayList;

import ohtu.domain.Blog;
import ohtu.domain.Book;
import ohtu.domain.Movie;
import ohtu.domain.Youtube;

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

    public void createTables() throws SQLException {

        Statement s = db.createStatement();

        try {
            s.execute("CREATE TABLE Books (id INTEGER PRIMARY KEY, name TEXT NOT NULL, Writer TEXT NOT NULL, year INTEGER, pages INTEGER, isbn TEXT)");
            s.execute("CREATE TABLE Youtube_links (id INTEGER PRIMARY KEY, url TEXT NOT NULL, title TEXT, description TEXT, created DATETIME)");
            s.execute("CREATE TABLE Blogs (id INTEGER PRIMARY KEY, url TEXT NOT NULL, title TEXT NOT NULL, writer TEXT, date DATETIME)");
            s.execute("CREATE TABLE Movies (id INTEGER PRIMARY KEY, title TEXT NOT NULL, director TEXT, year INTEGER, length INTEGER)");
        } catch (org.sqlite.SQLiteException e) {
            // table has already been created
        }
    }

    public void add(Object o) throws SQLException {
        if (o instanceof Book) {
            addBook((Book) o);
        } else if (o instanceof Youtube) {
            addYoutube((Youtube) o);
        } else if (o instanceof Blog) {
            addBlog((Blog) o);
        } else {
            addMovie((Movie) o);
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
        p.setDate(4, Date.valueOf(y.getDate()));

        p.executeUpdate();

    }

    private void addBlog(Blog b) throws SQLException {
        PreparedStatement p = db.prepareStatement("INSERT INTO Blogs(url, title, writer, date) VALUES (?,?,?,?)");

        p.setString(1, b.getUrl());
        p.setString(2, b.getTitle());
        p.setString(3, b.getWriter());
        p.setDate(4, Date.valueOf(b.getDate()));

        p.executeUpdate();

    }

    private void addMovie(Movie m) throws SQLException {
        PreparedStatement p = db.prepareStatement("INSERT INTO Movies(title, director, year, length) VALUES (?,?,?,?)");

        p.setString(1, m.getTitle());
        p.setString(2, m.getDirector());
        p.setInt(3, m.getYear());
        p.setInt(4, m.getLength());

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

        } catch (Exception ignored) {
        }

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

                youtubeLink.setDate(r.getDate("created").toLocalDate());
                youtubeLinks.add(youtubeLink);
            }
        } catch (Exception ignored) {
        }

        return youtubeLinks;
    }

    public ArrayList<Blog> listBlog() {
        ArrayList<Blog> blogs = new ArrayList<>();

        try {
            ResultSet r = s.executeQuery("SELECT * FROM Blogs");

            while (r.next()) {
                Blog blog = new Blog(
                        r.getString("url"),
                        r.getString("title"),
                        r.getString("writer"),
                        r.getDate("date").toLocalDate()
                );
                blogs.add(blog);
            }
        } catch (Exception ignored) {
        }

        return blogs;
    }

    public ArrayList<Movie> listMovie() {
        ArrayList<Movie> movies = new ArrayList<>();

        try {
            ResultSet r = s.executeQuery("SELECT * FROM Movies");

            while (r.next()) {
                Movie movie = new Movie(
                        r.getString("title"),
                        r.getString("director"),
                        r.getInt("year"),
                        r.getInt("length"));
                movies.add(movie);
            }
        } catch (Exception ignored) {
        }

        return movies;
    }

    public boolean deleteBook(String searchTerm) throws SQLException {
        int rows = rowCount("Books");
        PreparedStatement p = db.prepareStatement("DELETE FROM Books WHERE name LIKE ? ");
        p.setString(1, '%' + searchTerm + '%');

        p.executeUpdate();

        return rowCount("Books") < rows;
    }

    public boolean deleteYoutube(String searchTerm) throws SQLException {
        int rows = rowCount("Youtube_links");

        PreparedStatement p = db.prepareStatement("DELETE FROM Youtube_links WHERE title LIKE ? ");
        p.setString(1, '%' + searchTerm + '%');

        p.executeUpdate();

        return rowCount("Youtube_links") < rows;
    }

    public boolean deleteBlog(String searchTerm) throws SQLException {
        int rows = rowCount("Blogs");

        PreparedStatement p = db.prepareStatement("DELETE FROM Blogs WHERE title LIKE ? ");
        p.setString(1, '%' + searchTerm + '%');

        p.executeUpdate();

        return rowCount("Blogs") < rows;
    }

    public boolean deleteMovie(String searchTerm) throws SQLException {
        int rows = rowCount("Movies");

        PreparedStatement p = db.prepareStatement("DELETE FROM Movies WHERE title LIKE ? ");
        p.setString(1, '%' + searchTerm + '%');

        p.executeUpdate();

        p.executeUpdate();

        return rowCount("Movies") < rows;
    }

    private int rowCount(String table) throws SQLException {

        PreparedStatement p = db.prepareStatement("SELECT COUNT(*) FROM " + table);

        ResultSet r = p.executeQuery();

        return r.getInt(1);
    }

    public ArrayList<Book> searchBook(String searchTerm) throws SQLException {
        ArrayList<Book> foundBooks = new ArrayList<>();

        PreparedStatement p = db.prepareStatement("SELECT * FROM Books WHERE name LIKE ? OR writer LIKE ?");
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
            youtubeLink.setDate(r.getDate("created").toLocalDate());
            foundYoutubeLinks.add(youtubeLink);
        }

        return foundYoutubeLinks;
    }

    public ArrayList<Blog> searchBlog(String searchTerm) throws SQLException {
        ArrayList<Blog> foundBlogs = new ArrayList<>();

        PreparedStatement p = db.prepareStatement("SELECT * FROM Blogs WHERE title LIKE ? OR writer LIKE ?");
        p.setString(1, '%' + searchTerm + '%');
        p.setString(2, '%' + searchTerm + '%');
        ResultSet r = p.executeQuery();

        while (r.next()) {
            foundBlogs.add(new Blog(
                    r.getString("url"),
                    r.getString("title"),
                    r.getString("writer"),
                    r.getDate("date").toLocalDate()
            ));
        }

        return foundBlogs;
    }

    public ArrayList<Movie> searchMovie(String searchTerm) throws SQLException {
        ArrayList<Movie> foundMovies = new ArrayList<>();

        PreparedStatement p = db.prepareStatement("SELECT * FROM Movies WHERE title LIKE ? OR director LIKE ?");
        p.setString(1, '%' + searchTerm + '%');
        p.setString(2, '%' + searchTerm + '%');
        ResultSet r = p.executeQuery();

        while (r.next()) {
            foundMovies.add(new Movie(
                    r.getString("title"),
                    r.getString("director"),
                    r.getInt("year"),
                    r.getInt("length")
            ));
        }

        return foundMovies;
    }

    public void removeTable(String name) throws SQLException {
        PreparedStatement p = db.prepareStatement("DROP TABLE " + name);
        p.executeUpdate();
    }

    public boolean contains(Object o) throws SQLException {
        if (o instanceof Book) {
            return containsBook((Book) o);
        } else if (o instanceof Youtube) {
            return containsYoutube((Youtube) o);
        } else if (o instanceof Blog) {
            return containsBlog((Blog) o);
        } else {
            return containsMovie((Movie) o);
        }


    }

    protected boolean containsBook(Book b) throws SQLException {
        ArrayList<Book> books = searchBook(b.getTitle());
        return books.contains(b);
    }

    protected boolean containsYoutube(Youtube yt) throws SQLException {
        ArrayList<Youtube> youtubeLinks = searchYoutube(yt.getTitle());
        return youtubeLinks.contains(yt);
    }

    protected boolean containsMovie(Movie m) throws SQLException {
        ArrayList<Movie> movies = searchMovie(m.getTitle());
        return movies.contains(m);
    }

    protected boolean containsBlog(Blog b) throws SQLException {
        ArrayList<Blog> blogs = searchBlog(b.getTitle());
        return blogs.contains(b);
    }

    public void closeDbConnection() throws SQLException {
        db.close();
    }

}
