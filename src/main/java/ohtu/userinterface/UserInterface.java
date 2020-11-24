package ohtu.userinterface;

import ohtu.Book;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Class for the application UI
 */
public class UserInterface {

    private final BufferedReader br;

    public UserInterface() {
        br = new BufferedReader(new InputStreamReader(System.in));
    }

    /**
     * Constructor for testing
     *
     * @param br Mock command-line reader
     */
    protected UserInterface(BufferedReader br) {
        this.br = br;
    }

    /**
     * Launches the application's command-line UI
     */
    public void commandLine() throws IOException {
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
                    // TODO: Call for database here. Something like this db.store(getBook());
                    // For now, temp print:
                    Book book = getBook();
                    System.out.println("\nTitle: " + book.getName()
                                       + " | Author: " + book.getWriter()
                                       + " | Year: " + book.getYear()
                                       + " | Pages: " + book.getPages()
                                       + " | ISBN: " + book.getIsbn());
                    break;
                case "youtube":
                    // TODO
                    System.out.println("YouTube");
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
        Map<String, String> data = new HashMap<>();

        System.out.println("Enter title*: ");
        String title = br.readLine();
        if (title.isBlank()) {
            System.out.println("Title cannot be blank.");
            return null;
        }
        data.put("title", title);

        System.out.println("Enter author*: ");
        String author = br.readLine();
        if (author.isBlank()) {
            System.out.println("Author cannot be blank.");
            return null;
        }
        data.put("author", author);

        System.out.println("Enter year: ");
        data.put("year", br.readLine());

        System.out.println("Enter pages: ");
        data.put("pages", br.readLine());

        System.out.println("Enter ISBN: ");
        data.put("isbn", br.readLine());

        int year = -1, pages = -1;

        try {
            year = Integer.parseInt(data.get("year"));
        } catch (NumberFormatException ignored) {}

        try {
            pages = Integer.parseInt(data.get("pages"));
        } catch (NumberFormatException ignored) {}

        return new Book(data.get("title"), data.get("author"), year, pages, data.get("isbn"));
    }
}
