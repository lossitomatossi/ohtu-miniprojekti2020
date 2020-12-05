package ohtu;

import java.util.Objects;

public class Movie {

    private String title;
    private String director;
    private int year;
    private int length;
    private int titleLength, directorLength;


    public Movie(String title, String director, int year, int length) {
        this.title = title;
        this.director = director;
        this.year = year;
        this.length = length;
        
        titleLength = title.length();
        directorLength = director.length();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getDirector() {
        return director;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getYear() {
        return year;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getLength() {
        return length;
    }

    public void setLengths(int primary, int secondary) {
        titleLength = primary;
        directorLength = secondary;
    }

    
    @Override
    public boolean equals(Object o) {
        if(o == this) {
            return true;
        }
        if(!(o instanceof Movie)) {
            return false;
        }
        Movie other = (Movie) o;
        return other.title.equals(this.title) && other.director.equals(this.director);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 19 * hash + Objects.hashCode(this.title);
        hash = 19 * hash + Objects.hashCode(this.director);
        return hash;
    }

    @Override
    public String toString() {
        String directorFormatted = director.isEmpty() ? "-" : director;
        String yearFormatted = (year == -1) ? "-" : String.valueOf(year);
        String lengthFormatted = (length == -1) ? "-" : String.valueOf(length);

        return String.format("%-" + titleLength + "s", title) + " "
                + String.format("%-" + directorLength + "s", directorFormatted) + " "
                + String.format("%-6s", yearFormatted) + " "
                + lengthFormatted + "\n";
    }
}
