package ohtu;

import java.util.Objects;
import java.sql.Date;

public class Blog {

    private String url;
    private String title;
    private String writer;
    private Date date;
    private int urlLength, titleLength, writerLength;

    public Blog(String url, String title, String writer, Date date) {
        this.url = url;
        this.title = title;
        this.writer = writer;
        this.date = date;
        
        this.urlLength = url.length();
        this.titleLength = title.length();
        this.writerLength = writer.length();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
    
    
    @Override
    public boolean equals(Object o) {
        if(o == this) {
            return true;
        }
        if(!(o instanceof Blog)) {
            return false;
        }
        Blog other = (Blog) o;
        return other.url.equals(this.url) && other.title.equals(this.title);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.url);
        hash = 29 * hash + Objects.hashCode(this.title);
        return hash;
    }

    @Override
    public String toString() {
        String writerFormatted = writer.isEmpty() ? "-" : writer;

        return String.format("%-" + urlLength + "s", url) + " "
                + String.format("%-" + titleLength + "s", title) + " "
                + String.format("%-" + writerLength + "s", writer) + " "
                + String.format("%-10s", date) + "\n";
    }
}
