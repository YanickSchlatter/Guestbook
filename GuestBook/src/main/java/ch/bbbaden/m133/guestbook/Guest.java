/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ch.bbbaden.m133.guestbook;

import java.util.Date;

/**
 *
 * @author Yanick Schlatter
 */
public class Guest {
    private String name;
    private String message;
    private int id;
    private Date entryDate;

    public Guest(String name, String message, int id, Date entryDate) {
        this.name = name;
        this.message = message;
        this.id = id;
        this.entryDate = entryDate;
    }

    public Guest() {
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    public int getId() {
        return id;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }
    

   
    

}
