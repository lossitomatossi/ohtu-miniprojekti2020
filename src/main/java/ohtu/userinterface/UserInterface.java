package ohtu.userinterface;

import ohtu.database.DbCommands;
import ohtu.domain.Blog;
import ohtu.domain.Book;
import ohtu.domain.Movie;
import ohtu.domain.Youtube;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import ohtu.utilities.InputUtils;

/**
 * Class for the application UI
 */
public class UserInterface {

    private final DbCommands db;
    private final BufferedReader br;
    final InputUtils input;

    /**
     * Constructor
     *
     * @param dbName Database name for production
     */
    public UserInterface(String dbName) throws SQLException, ClassNotFoundException {
        br = new BufferedReader(new InputStreamReader(System.in));
        db = new DbCommands(dbName);
        input = new InputUtils(br);
    }

    /**
     * Constructor for testing
     *
     * @param br Mock command-line reader
     * @param db Test database manager
     */
    protected UserInterface(BufferedReader br, DbCommands db) {
        this.br = br;
        this.db = db;
        this.input = new InputUtils(this.br);
    }

    /**
     * Launches the application's command-line UI
     */
    public void commandLine() throws IOException, SQLException {

        while (true) {
            String msg = "";
            System.out.println(input.getCommands());
            System.out.println("\n\n\n\nEnter nothing to get help.");
            System.out.print("Command: ");
            String command = br.readLine();

            switch (command.toLowerCase()) {
                case "0":
                case "exit":
                    System.out.println("Exiting..");
                    br.close();
                    return;
                case "":
                case "1":
                case "help":
                    msg = input.getCommands();
                    break;
                case "2":
                case "book":
                    msg = store(input.getBook());
                    msg = msg.isEmpty() ? "Book added successfully!" : msg;
                    break;
                case "3":
                case "youtube":
                    msg = store(input.getYoutube());
                    msg = msg.isEmpty() ? "Youtube link added successfully!" : msg;
                    break;
                case "4":
                case "blog":
                    msg = store(input.getBlog());
                    msg = msg.isEmpty() ? "Blog added successfully!" : msg;
                    break;
                case "5":
                case "movie":
                    msg = store(input.getMovie());
                    msg = msg.isEmpty() ? "Movie added successfully!" : msg;
                    break;
                case "6":
                case "list":
                    System.out.println(input.getCategories());
                    System.out.print("Enter category to list: ");
                    msg = search(br.readLine(), "");
                    break;
                case "7":
                case "search":
                    System.out.println(input.getCategories());
                    System.out.print("Enter category: ");
                    String category = br.readLine();
                    System.out.print("Enter search term: ");
                    String searchTerm = br.readLine();
                    msg = search(category, searchTerm);
                    break;
                case "8":
                case "delete":
                    // String searchTerm = br.readLine();
                    // msg = delete(item);
                    break;
                default:
                    System.out.println(command.toLowerCase());
                    System.out.println("No such command exists. Enter 'help' to get help.");
            }
            System.out.println(msg);
        }
    }

    /**
     * Searches for the searchTerm in database. If the searchTerm is empty, the
     * method lists all items in the category.
     *
     * @param category Object can be Book, Youtube, Blog, Movie
     * @param searchTerm String used for searching
     * @return formatted String of found items
     */
    protected String search(String category, String searchTerm) throws SQLException {
        StringBuilder output = new StringBuilder();

        List<?> results;
        int[] lengths = {20, 20, 20};

        if (category.equalsIgnoreCase("book")) {
            results = searchTerm.isEmpty() ? db.listBook() : db.searchBook(searchTerm);

            if (results.isEmpty()) {
                return "Nothing found.";
            }

            for (Object o : results) {
                lengths[0] = Math.max(((Book) o).getTitle().length(), lengths[0]);
                lengths[1] = Math.max(((Book) o).getAuthor().length(), lengths[1]);
            }

            for (Object o: results) {
                ((Book) o).setLengths(lengths[0], lengths[1]);
            }

            // Header
            output.append(String.format("%-" + lengths[0] + "s", "Title")).append(" ")
                    .append(String.format("%-" + lengths[1] + "s", "Author")).append(" ")
                    .append(String.format("%-6s", "Year")).append(" ")
                    .append(String.format("%-7s", "Pages")).append(" ")
                    .append("ISBN").append("\n");

        } else if (category.equalsIgnoreCase("youtube")) {
            results = searchTerm.isEmpty() ? db.listYoutube() : db.searchYoutube(searchTerm);

            if (results.isEmpty()) {
                return "Nothing found.";
            }

            for (Object o : results) {
                lengths[0] = Math.max(((Youtube) o).getUrl().length(), lengths[0]);
                lengths[1] = Math.max(((Youtube) o).getTitle().length(), lengths[1]);
            }

            for (Object o: results) {
                ((Youtube) o).setLengths(lengths[0], lengths[1]);
            }

            // Header
            output.append(String.format("%-" + lengths[0] + "s", "URL")).append(" ")
                    .append(String.format("%-" + lengths[1] + "s", "Title")).append(" ")
                    .append(String.format("%-10s", "Created")).append(" ")
                    .append("Description").append("\n");
        } else if (category.equalsIgnoreCase("movie")) {
            results = searchTerm.isEmpty() ? db.listMovie() : db.searchMovie(searchTerm);

            for (Object o : results) {
                lengths[0] = Math.max(((Movie) o).getTitle().length(), lengths[0]);
                lengths[1] = Math.max(((Movie) o).getDirector().length(), lengths[1]);
            }

            for (Object o: results) {
                ((Movie) o).setLengths(lengths[0], lengths[1]);
            }

            // Header
            output.append(String.format("%-" + lengths[0] + "s", "Title")).append(" ")
                    .append(String.format("%-" + lengths[1] + "s", "Director")).append(" ")
                    .append(String.format("%-6s", "Year")).append(" ")
                    .append("Length (min)").append("\n");
        } else if (category.equalsIgnoreCase("blog")) {
            results = searchTerm.isEmpty() ? db.listBlog() : db.searchBlog(searchTerm);

            for (Object o : results) {
                lengths[0] = Math.max(((Blog) o).getUrl().length(), lengths[0]);
                lengths[1] = Math.max(((Blog) o).getTitle().length(), lengths[1]);
                lengths[2] = Math.max(((Blog) o).getWriter().length(), lengths[2]);
            }

            for (Object o: results) {
                ((Blog) o).setLengths(lengths[0], lengths[1], lengths[2]);
            }

            // Header
            output.append(String.format("%-" + lengths[0] + "s", "URL")).append(" ")
                    .append(String.format("%-" + lengths[1] + "s", "Title")).append(" ")
                    .append(String.format("%-" + lengths[2] + "s", "Writer")).append(" ")
                    .append("Date").append("\n");
        } else {
            return "No such category.";
        }

        for (Object o : results) {
            output.append(o.toString());
        }

        return output.toString();
    }

    /**
     * Stores the object (Book, Youtube) to the database
     *
     * @param o Object can be Book or Youtube
     * @return String empty string if storing object was successful, otherwise
     * an error
     */
    protected String store(Object o) throws SQLException {
        if (db.contains(o)) {
            return "The suggestion already exists.";
        }

        if (o != null) {
            db.add(o);
            return "";
        }
        return "An unknown error has occurred.";
    }
}
