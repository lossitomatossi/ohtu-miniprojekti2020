package ohtu;

public class Book {

    private String name;
    private String writer;
    private int year;
    private int pages;
    private String isbn;

    public Book(String name, String writer) {
        this.name = name;
        this.writer = writer;
    }
    
    public Book(String name, String writer, int year, int pages, String isbn) {
        this.name = name;
        this.writer = writer;
        this.year = year;
        this.pages = pages;
        this.isbn = isbn;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getWriter() {
        return writer;
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
   

}

