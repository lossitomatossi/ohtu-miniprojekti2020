package ohtu.userinterface;

import ohtu.Book;
import ohtu.DbCommands;
import ohtu.Youtube;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

/**
 * Class for the application UI
 */
public class UserInterface {

    private final BufferedReader br;
    private DbCommands db;
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
    protected UserInterface(BufferedReader br) {
        this.br = br;
        mockUI = true;
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
                    msg = store(getBook()) ? "Book added successfully!" : "There was a problem on adding a book.";
                    break;
                case "3":
                case "youtube":
                    msg = store(getYoutube()) ?
                            "Youtube link added successfully!" : "There was a problem on adding an YouTube link.";
                    break;
                case "4":
                case "list":
                    System.out.println("Categories: youtube, book");
                    System.out.print("Enter category to list: ");
                    msg = list(br.readLine());
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
        } catch (NumberFormatException ignored) {}

        System.out.println("Enter pages: ");
        int pages = -1;
        try {
            pages = Integer.parseInt(br.readLine());
        } catch (NumberFormatException ignored) {}

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
    protected Youtube getYoutube() throws IOException{

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
     * Lists all objects of the specified category in a formatted manner
     *
     * @param o String that represents the category which the user wants as a list
     * @return formatted String of all items of the specified category
     */
    protected String list(String o) {
        if (mockUI) {
            return  "Title | Author | Year | Pages | ISBN";
        }

        if (o.toLowerCase().equals("book")) {
            return "Title | Author | Year | Pages | ISBN\n" + db.BookTable();
        } else if (o.toLowerCase().equals("youtube")) {
            return "URL | Title | Date Added | Description\n" + db.YoutubeTable();
        }
        return "No such category.";
    }

    /**
     * Searches for the searchTerm in database
     *
     * @param o Object can be Book or Youtube
     * @param searchTerm String used for searching
     * @return formatted String of found items
     */
    protected String search(String category, String searchTerm) throws SQLException {
        return "Founds items: " + db.search(category, searchTerm);
    }

    /**
     * Stores the object (Book, Youtube) to the database
     *
     * @param o Object can be Book or Youtube
     * @return Boolean true if storing object was successful, otherwise false
     */
    protected boolean store(Object o) throws SQLException {
        if (o != null) {
            if (mockUI) {
                System.out.println(o);
            } else {
                db.add(o);
            }
            return true;
        }
        return false;
    }
}
