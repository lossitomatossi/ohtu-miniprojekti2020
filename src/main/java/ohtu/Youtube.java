package ohtu;

import java.sql.Date;
import java.util.Objects;

/**
 *
 * @author julinden
 */
public class Youtube {
    private String url;
    private String title;
    private Date date;
    private String description;
    
    public Youtube(String url, String title, String description) {
        this.url = url;
        this.title = title;
        this.date = new Date(System.currentTimeMillis());
        this.description = description;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        if(other.url.equals(this.url) && other.title.equals(this.title)) {
            return true;
        }
        return false;
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
        return String.format("%-41s", url) + " "
                + String.format("%-41s", title) + " "
                + String.format("%-6s", date) + " "
                + description + "\n";
    }
}
