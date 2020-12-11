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
import java.util.List;
import ohtu.utilities.InputUtils;

/**
 * Text UI for the application
 */
public class TextUI {

    private final DbCommands db;
    private final BufferedReader br;
    final InputUtils input;

    /**
     * Constructor
     *
     * @param dbName Database name for production
     */
    public TextUI(String dbName) throws SQLException, ClassNotFoundException {
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
    protected TextUI(BufferedReader br, DbCommands db) {
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
            System.out.println("\n\nEnter nothing to get help.");
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
                    String categorySearch = br.readLine();
                    System.out.print("Enter search term: ");
                    String searchTerm = br.readLine();
                    msg = search(categorySearch, searchTerm);
                    break;
                case "8":
                case "delete":
                    System.out.println(input.getCategories());
                    System.out.print("Enter category: ");
                    String categoryDelete = br.readLine();
                    System.out.print("Enter the suggestion to be deleted: ");
                    String toDelete = br.readLine();
                    msg = delete(categoryDelete, toDelete);
                    break;
                case "9":
                case "edit":
                    System.out.println("Editable categories: book");
//                    System.out.println(input.getCategories());
                    System.out.println("Enter category from which you want to edit saved items: ");
                    String categoryEdit = br.readLine();
                    edit(categoryEdit);
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
     * Deletes a suggestion
     *
     * @param category Object can be Book, Youtube, Blog, Movie
     * @param searchTerm String used for searching
     * @return String confirmation message
     */
    protected String delete(String category, String searchTerm) throws SQLException {
        boolean deletion;

        if (category.equalsIgnoreCase("book")) {
            deletion = db.deleteBook(searchTerm);
        } else if (category.equalsIgnoreCase("youtube")) {
            deletion = db.deleteYoutube(searchTerm);
        } else if (category.equalsIgnoreCase("movie")) {
            deletion = db.deleteMovie(searchTerm);
        } else if (category.equalsIgnoreCase("blog")) {
            deletion = db.deleteBlog(searchTerm);
        } else {
            return "No such category.";
        }

        if (deletion) {
            return "Suggestion deleted successfully!";
        }

        return "Nothing was deleted.";
    }

    /**
     * Stores the suggestion to the database
     *
     * @param o Object Book, Youtube, Blog, Movie
     * @return String empty string if storing object was successful, otherwise an error
     */
    protected String store(Object o) throws SQLException {
        if (o != null && db.contains(o)) {
            return "The suggestion already exists.";
        }

        if (o != null) {
            db.add(o);
            return "";
        }
        return "An unknown error has occurred.";
    }
    
    protected void edit(String category) throws SQLException, IOException {
        if (category.equalsIgnoreCase("book")) {
            System.out.println(search(category, ""));
            System.out.println("Which item would you like to edit?");
            String editTerm = br.readLine();
            if (!editTerm.isEmpty()) {
            Book former;
                try {
                    former = db.searchBook(editTerm).get(0);
                    System.out.println("Is this what you want to edit? "
                            + "(yes/no/enter to leave) \n"
                            + former);
                    Book editedBook = input.editBook(category, editTerm);
                    boolean editedInDB = db.editBook(editedBook, former);
                    if (editedInDB) {
                        System.out.println("Was: \n" + former);
                        System.out.println("is now: \n" + editedBook);
                    } else {
                        System.out.println("Changes discarded");
                    }                           
                } catch (Exception e) {
                    System.out.println("No such book found");
                }
            } else {
                System.out.println("No search term given");
            }
        } else if (category.equalsIgnoreCase("youtube")) {
            System.out.println("Not supported");
        } else if (category.equalsIgnoreCase("movie")) {
            System.out.println("Not supported");
        } else if (category.equalsIgnoreCase("blog")) {
            System.out.println("Not supported");
        } else {
            System.out.println("No such category.");
        }
        
        
    }
}
