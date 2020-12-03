package ohtu;

import java.util.Objects;

public class Book {

    private String title;
    private String author;
    private int year;
    private int pages;
    private String isbn;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
    }
    
    public Book(String title, String author, int year, int pages, String isbn) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.pages = pages;
        this.isbn = isbn;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getYear() {
        return year;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getPages() {
        return pages;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getIsbn() {
        return isbn;
    }
    
    @Override
    public boolean equals(Object o) {
        if(o == this) {
            return true;
        }
        if(!(o instanceof Book)) {
            return false;
        }
        Book other = (Book) o;
        return other.title.equals(this.title) && other.author.equals(this.author);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + Objects.hashCode(this.title);
        hash = 19 * hash + Objects.hashCode(this.author);
        return hash;
    }

    @Override
    public String toString() {
        String yearFormatted = (year == -1) ? "-" : String.valueOf(year);
        String pagesFormatted= (year == -1) ? "-" : String.valueOf(pages);
        String isbnFormatted = isbn.isEmpty() ? "-" : isbn;

        return String.format("%-41s", title) + " "
                + String.format("%-21s", author) + " "
                + String.format("%-6s", yearFormatted) + " "
                + String.format("%-7s", pagesFormatted) + " "
                + isbnFormatted + "\n";
    }
}
