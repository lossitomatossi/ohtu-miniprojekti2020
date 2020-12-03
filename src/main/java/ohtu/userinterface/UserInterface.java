package ohtu.userinterface;

import ohtu.Book;
import ohtu.DbCommands;
import ohtu.Youtube;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Class for the application UI
 */
public class UserInterface {

    private final DbCommands db;
    private final BufferedReader br;
    private final boolean mockUI;

    /**
     * Constructor
     *
     * @param dbName Database name for production
     */
    public UserInterface(String dbName) throws SQLException, ClassNotFoundException {
        br = new BufferedReader(new InputStreamReader(System.in));
        db = new DbCommands(dbName);
        mockUI = false;
    }

    /**
     * Constructor for testing
     *
     * @param br Mock command-line reader
     */
    protected UserInterface(BufferedReader br) throws SQLException, ClassNotFoundException {
        this.br = br;
        mockUI = true;
        db = new DbCommands("jdbc:sqlite:testing.db");
    }

    /**
     * Launches the application's command-line UI
     */
    public void commandLine() throws IOException, SQLException {
        String help = "\nNumber or name of the command can be used."
                + "\n0 - exit    | Exits the application"
                + "\n1 - help    | Prints help"
                + "\n2 - book    | Stores book"
                + "\n3 - youtube | Stores YouTube link"
                + "\n4 - list    | Lists objects from specified category"
                + "\n5 - search  | Searches for specified term";

        System.out.println(help);

        while (true) {
            String msg = "";
            System.out.print("\nEnter command: ");
            String command = br.readLine();

            switch (command.toLowerCase()) {
                case "0":
                case "exit":
                    System.out.println("Exiting..");
                    br.close();
                    return;
                case "1":
                case "help":
                    msg = help;
                    break;
                case "2":
                case "book":
                    msg = store(getBook());
                    msg = msg.isEmpty() ? "Book added successfully!" : msg;
                    break;
                case "3":
                case "youtube":
                    msg = store(getYoutube());
                    msg = msg.isEmpty() ? "Youtube link added successfully!" : msg;
                    break;
                case "4":
                case "list":
                    System.out.println("Categories: youtube, book");
                    System.out.print("Enter category to list: ");
                    msg = search(br.readLine(), "");
                    break;
                case "5":
                case "search":
                    System.out.println("Categories: youtube, book");
                    System.out.print("Enter category: ");
                    String category = br.readLine();
                    System.out.print("Enter searchterm: ");
                    String searchTerm = br.readLine();
                    msg = search(category, searchTerm);
                    break;
                default:
                    System.out.println(command.toLowerCase());
                    System.out.println("No such command exists. Enter 'help' to get help.");
            }
            System.out.println(msg);
        }
    }

    /**
     * Gets the data from user for the book recommendation in command-line
     *
     * @return Book object or null
     */
    protected Book getBook() throws IOException {
        System.out.println("Enter title*: ");
        String title = br.readLine();
        if (title.isBlank()) {
            System.out.println("Title cannot be blank.");
            return null;
        }

        System.out.println("Enter author*: ");
        String author = br.readLine();
        if (author.isBlank()) {
            System.out.println("Author cannot be blank.");
            return null;
        }

        System.out.println("Enter year: ");
        int year = -1;
        try {
            year = Integer.parseInt(br.readLine());
        } catch (NumberFormatException ignored) {
        }

        System.out.println("Enter pages: ");
        int pages = -1;
        try {
            pages = Integer.parseInt(br.readLine());
        } catch (NumberFormatException ignored) {
        }

        System.out.println("Enter ISBN: ");
        String isbn = br.readLine();
        if (isbn.isBlank()) {
            isbn = "";
        }

        return new Book(title, author, year, pages, isbn);
    }

    /**
     * Gets the data from user for the YouTube recommendation in command-line
     *
     * @return Youtube object or null
     */
    protected Youtube getYoutube() throws IOException {
        System.out.println("Enter URL*: ");
        String url = br.readLine();
        if (url.isBlank()) {
            System.out.println("URL cannot be blank.");
            return null;
        }

        System.out.println("Enter title*: ");
        String title = br.readLine();
        if (title.isBlank()) {
            title = "";
        }

        System.out.println("Enter description*: ");
        String description = br.readLine();
        if (description.isBlank()) {
            description = "";
        }

        return new Youtube(url, title, description);
    }

    /**
     * Searches for the searchTerm in database. If the searchTerm is empty, the
     * method lists all items in the category.
     *
     * @param category Object can be Book or Youtube
     * @param searchTerm String used for searching
     * @return formatted String of found items
     */
    protected String search(String category, String searchTerm) throws SQLException {
        StringBuilder output = new StringBuilder();

        ArrayList<?> results = null;

        if (category.equalsIgnoreCase("book")) {
            if (!mockUI) {
                results = searchTerm.isEmpty() ? db.listBook() : db.searchBook(searchTerm);
            }

            // Header
            output.append(String.format("%-41s", "Title")).append(" ")
                    .append(String.format("%-21s", "Author")).append(" ")
                    .append(String.format("%-6s", "Year")).append(" ")
                    .append(String.format("%-7s", "Pages")).append(" ")
                    .append("ISBN").append("\n");

        } else if (category.equalsIgnoreCase("youtube")) {
            if (!mockUI) {
                results = searchTerm.isEmpty() ? db.listYoutube() : db.searchYoutube(searchTerm);
            }

            // Header
            output.append(String.format("%-41s", "URL")).append(" ")
                    .append(String.format("%-41s", "Title")).append(" ")
                    .append(String.format("%-6s", "Created")).append(" ")
                    .append("Description").append("\n");
        } else {
            return "No such category.";
        }

        if (results != null) {
            for (Object o : results) {
                output.append(o.toString());
            }
        }

        return output.toString();
    }

    /**
     * Stores the object (Book, Youtube) to the database
     *
     * @param o Object can be Book or Youtube
     * @return String empty string if storing object was successful, otherwise an error
     */
    protected String store(Object o) throws SQLException {
        if (db.contains(o)) {
            return "The recommendation already exists.";
        }

        if (o != null) {
            if (mockUI) {
                System.out.println(o);
            } else {
                db.add(o);
            }
            return "";
        }
        return "An unknown error has occurred.";
    }
}
