/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import ohtu.database.Database;

/**
 *
 * @author mahi
 */
public class DbCommands {

    private Database database;
    private Connection db;
    private Statement s;
    private String databaseAddress;

    public DbCommands(String databaseAddress) throws SQLException, ClassNotFoundException {
        database = new Database(databaseAddress);
        db = database.getConnection();
        s = db.createStatement();
        createTables();


    }

    private  void createTables() throws SQLException {

        Statement s = db.createStatement();

        s.execute("CREATE TABLE Books (id INTEGER PRIMARY KEY, name TEXT NOT NULL, Writer TEXT NOT NULL, year INTEGER, pages INTEGER, isbn TEXT)");

        s.execute("CREATE TABLE Youtube_links (id INTEGER PRIMARY KEY, url TEXT NOT NULL, title TEXT, description TEXT)");
    }

    public  void add(Object o) throws SQLException {
        if (o instanceof Book) {
            addBook((Book) o);
        }

        if (o instanceof Youtube) {
            addYoutube((Youtube) o);
        }

    }

    private  void addBook(Book b) throws SQLException {
        PreparedStatement p = db.prepareStatement("INSERT INTO Books(name,writer, year, pages, isbn) VALUES (?,?,?,?,?)");

             p.setString(1,b.getName());
             p.setString(2,b.getWriter());
             p.setInt(3, b.getYear());
             p.setInt(4, b.getPages());
             p.setString(5,b.getIsbn());

             p.executeUpdate();

    }

    private  void addYoutube(Youtube y) throws SQLException {
        PreparedStatement p = db.prepareStatement("INSERT INTO Youtube_links(url,title, description) VALUES (?,?,?)");

             p.setString(1,y.getUrl());
             p.setString(2,y.getTitle());
             p.setString(3, y.getDescription());


             p.executeUpdate();

    }

    public String BookTable() {
        String book = "";


        try{
        ResultSet r = s.executeQuery("SELECT * FROM Books");

                while (r.next()) {
                book += (r.getString("name") + " " +r.getString("writer") + " " +r.getInt("year") + " " +r.getInt("pages") + " " +r.getString("isbn"));
             }
        }

        catch(Exception e){

        }

       return book;
    }


    public String YoutubeTable() {
        String Youtube = "";


        try{
        ResultSet r = s.executeQuery("SELECT * FROM Youtube_links");

                while (r.next()) {
                Youtube += (r.getString("url") + " " +r.getString("title") + " " + r.getString("description"));
             }
        }

        catch(Exception e){

        }

       return Youtube;
    }

public void removeTable(String name) throws SQLException{
    PreparedStatement p = db.prepareStatement("DROP TABLE " + name);
    p.executeUpdate();
}


}
