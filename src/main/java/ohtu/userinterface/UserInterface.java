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

        String help = "exit - Close the program.\n"
                      + "book - Save a book recommendation.\n"
                      + "youtube - Save an YouTube recommendation.";

        System.out.println("Get help by entering 'help' or close the program by entering 'exit'.");

        while (true) {
            System.out.print("Enter command: ");
            String command = br.readLine();

            switch (command) {
                case "exit":
                    System.out.println("Exiting..");
                    br.close();
                    return;
                case "help":
                    System.out.println(help);
                    break;
                case "book":
                    Book book = getBook();
                    if (book != null) {
                        if (mockUI) {
                            System.out.println(book);
                        } else {
                            db.add(book);
                            System.out.println("Book added successfully!");
                        }
                    }
                    break;
                case "youtube":
                    Youtube youtube = getYoutube();
                    if (youtube != null) {
                        if (mockUI) {
                            System.out.println(youtube);
                        } else {
                            db.add(youtube);
                            System.out.println("YouTube link added successfully!");
                        }
                    }
                    break;
                default:
                    System.out.println("No such command exists. Enter 'help' to get help.");
            }
        }
    }

    /**
     * Gets the data from user for the book recommendation
     *
     * TODO: Possibly a single method, and pass the type of the entry as argument
     * like "YouTube", "Book" or Tip:Youtube, Tip:Book
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
}
