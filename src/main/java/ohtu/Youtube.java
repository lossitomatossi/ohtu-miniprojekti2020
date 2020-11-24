/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu;

import java.sql.Date;

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
    
    @Override public String toString() {
        return "url: " + this.url + "\ntitle: " + this.title + "\ndate added: " + this.date + "\ndescription: " + this.description;
    }
}

