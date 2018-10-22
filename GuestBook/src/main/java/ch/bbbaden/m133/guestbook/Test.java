/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.m133.guestbook;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.jdom2.JDOMException;

/**
 *
 * @author Yamick Schlatter
 */
@Named(value = "Test")
@SessionScoped

public class Test implements Serializable {

    @Pattern(regexp="^[a-zA-Z]*$")
    private String username;
    @Pattern(regexp="^[a-zA-Z0-9]*$")
    private String pwd;
    private boolean loggedIn;
    private User user;
    
    @Size(min=1, max=50)
    @Pattern(regexp="^[a-zA-Z0-9,.!?;: ]*$")
    private String entry;
    
    private List<Guest> entries;

    public String doLogin() throws JDOMException, IOException {
        LoginDAO loginDAO = new LoginDAO();
        this.user=loginDAO.checkInput(this.username, this.pwd);
        
        if(this.user!=null){
            this.loggedIn = true;
            return "/secured/securedPage.xhtml";
        }

        loggedIn = false;
        return "/Login.xhtml";
    }
    
    public void makeEntry() throws JDOMException, IOException{
        new LoginDAO().setEntry(entry, username);
        this.setEntry("");
    }
    
    public String doLogout(){
        loggedIn = false;
        return "/Login.xhtml";
    }

    public List<Guest> getEntries(){
        try {
            this.entries = new LoginDAO().getExistingEntries();
        } catch (JDOMException | IOException | ParseException ex) {
            Logger.getLogger(LoginDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.entries;
    }
    
    public String getUsername() {
        return username;
    }

    public String getPwd() {
        return pwd;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public User getUser() {
        return user;
    }

    public String getEntry() {
        return entry;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setEntry(String entry) {
        this.entry = entry;
    }
}
