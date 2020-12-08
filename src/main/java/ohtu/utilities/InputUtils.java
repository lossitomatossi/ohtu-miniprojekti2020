package ohtu.utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import ohtu.domain.Blog;
import ohtu.domain.Book;
import ohtu.domain.Movie;
import ohtu.domain.Youtube;

public class InputUtils {

    private final BufferedReader br;

    public InputUtils(BufferedReader br) {
        this.br = br;
    }

    public String getCommands(){
        return "\nNumber or name of the command can be used."
                + "\n0 - exit    | Exits the application"
                + "\n1 - help    | Prints help"
                + "\n2 - book    | Stores book"
                + "\n3 - youtube | Stores YouTube link"
                + "\n4 - blog    | Stores blog"
                + "\n5 - movie   | Stores movie"
                + "\n6 - list    | Lists suggestions from specified category"
                + "\n7 - search  | Searches for specified suggestion"
                + "\n8 - delete  | Deletes specified suggestion";
    }
    
    public String getCategories(){
        return "Categories: book, youtube, blog, movie";
    }

    /**
     * Gets the data from user for the book suggestion in command-line
     *
     * @return Book object or null
     */
    public Book getBook() throws IOException {
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
            System.out.println("Non-integer argument, no year added");
        }

        System.out.println("Enter pages: ");
        int pages = -1;
        try {
            pages = Integer.parseInt(br.readLine());
        } catch (NumberFormatException ignored) {
            System.out.println("Non-integer argument, no page count added");
        }

        System.out.println("Enter ISBN: ");
        String isbn = br.readLine();
        if (isbn.isBlank()) {
            isbn = "";
        }

        return new Book(title, author, year, pages, isbn);
    }

    /**
     * Gets the data from user for the YouTube suggestion in command-line
     *
     * @return Youtube object or null
     */
    public Youtube getYoutube() throws IOException {
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
     * Gets the data from user for the Blog suggestion in command-line
     *
     * @return Blog object or null
     */
    public Blog getBlog() throws IOException {
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

        System.out.println("Enter writer: ");
        String writer = br.readLine();
        if (writer.isBlank()) {
            writer = "";
        }

        System.out.println("Enter date (year-month-day): ");
        String dateInput = br.readLine();
        LocalDate date;

        if (dateInput.isBlank()) {
            date = LocalDate.now();
        } else {
            try {
                List<Integer> dateArray = Arrays.stream(dateInput.split("-"))
                        .map(Integer::parseInt).collect(Collectors.toList());
                date = LocalDate.of(dateArray.get(0), dateArray.get(1), dateArray.get(2));
            } catch (NumberFormatException e) {
                System.out.println("Date incorrectly formatted! Added current date instead.\n");
                date = LocalDate.now();
            }
        }

        return new Blog(url, title, writer, date);
    }

    /**
     * Gets the data from user for the Movie suggestion in command-line
     *
     * @return Movie object or null
     */
    public Movie getMovie() throws IOException {
        System.out.println("Enter title*: ");
        String title = br.readLine();
        if (title.isBlank()) {
            System.out.println("Title cannot be blank.");
            return null;
        }

        System.out.println("Enter director: ");
        String director = br.readLine();
        if (director.isBlank()) {
            director = "";
        }

        System.out.println("Enter release year: ");
        int year = -1;
        try {
            year = Integer.parseInt(br.readLine());
        } catch (NumberFormatException ignored) {
        }

        System.out.println("Enter duration (min): ");
        int duration = -1;
        try {
            duration = Integer.parseInt(br.readLine());
        } catch (NumberFormatException ignored) {
        }

        return new Movie(title, director, year, duration);
    }

}
