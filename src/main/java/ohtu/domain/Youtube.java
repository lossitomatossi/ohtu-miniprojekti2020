package ohtu.domain;

import java.time.LocalDate;
import java.util.Objects;

/**
 *
 * @author julinden
 */
public class Youtube {
    private String url, title, description;
    private LocalDate date;
    private int urlLength, titleLength;
    
    public Youtube(String url, String title, String description) {
        this.url = url;
        this.title = title;
        this.date = LocalDate.now();
        this.description = description;

        urlLength = url.length();
        titleLength = title.length();
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLengths(int primary, int secondary) {
        urlLength = primary;
        titleLength = secondary;
    }

    @Override
    public boolean equals(Object o) {
        if(o == this) {
            return true;
        }
        if(!(o instanceof Youtube)) {
            return false;
        }
        Youtube other = (Youtube) o;
        return other.url.equals(this.url) && other.title.equals(this.title);
    } 

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.url);
        hash = 29 * hash + Objects.hashCode(this.title);
        return hash;
    }
    
    @Override
    public String toString() {
        return String.format("%-" + urlLength + "s", url) + " "
                + String.format("%-" + titleLength + "s", title) + " "
                + String.format("%-10s", date) + " "
                + description + "\n";
    }
}
