/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.m133.guestbook;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 *
 * @author Yanick Schlatter
 */
public class LoginDAO {

    /**
     * Creates a new instance of LoginDAO
     */
    public LoginDAO() {
    }

    public User checkInput(String name, String password) throws JDOMException, IOException {

        String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("");
        path = path + "\\WEB-INF\\userBase.xml";

        Document document = new SAXBuilder().build(path);
        Element userDataBase = document.getRootElement();

        List userList = userDataBase.getChildren("user");
        for (int i = 0; i < userList.size(); i++) {
            Element user1 = (Element) userList.get(i);
            String username1 = user1.getChildText("name");
            String password1 = user1.getChildText("pwd");

            if (username1.equals(name) && password1.equals(password)) {
                return new User(name, password);
            }
        }

        return null;
    }

    public void setEntry(String e, String user) {
        try {
            Date today = Calendar.getInstance().getTime();
            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
            String datum = formatter.format(today);
            formatter = new SimpleDateFormat("HH:mm");
            String zeit = formatter.format(today);
            String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("");
            path = path + "\\WEB-INF\\guestList.xml";
            
            Document document = new SAXBuilder().build(path);
            Element guestbook = document.getRootElement();
            Element entry = new Element("entry");
            entry.addContent(new Element("id").setText(Integer.toString(this.getXMLid() + 1)));
            entry.addContent(new Element("name").setText(user));
            entry.addContent(new Element("date").setText(datum));
            entry.addContent(new Element("time").setText(zeit));
            entry.addContent(new Element("message").setText(e));
            guestbook.addContent(entry);
            
            XMLOutputter xmlOutput = new XMLOutputter();
            xmlOutput.setFormat(Format.getPrettyFormat());
            xmlOutput.output(document, new FileWriter(new File(path)));
        } catch (IOException | JDOMException ex) {
            Logger.getLogger(LoginDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private int getXMLid() throws JDOMException, IOException {
//        int max=0;
        String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("");
        path = path + "\\WEB-INF\\guestList.xml";

        Document document = new SAXBuilder().build(path);
        Element guestbook = document.getRootElement();

        List userList = guestbook.getChildren("entry");
//        for (int i = 0; i < userList.size(); i++) {
//            max++;
//            System.out.println(max);
//        }
//        System.out.println(max);
        return userList.size();
    }

    public List<Guest> getExistingEntries() throws JDOMException, IOException, ParseException {
        List<Guest> entries = new ArrayList();

        String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("");
        path = path + "\\WEB-INF\\guestList.xml";

        Document document = new SAXBuilder().build(path);
        Element guestbook = document.getRootElement();

        List userList = guestbook.getChildren("entry");
        for (int i = 0; i < userList.size(); i++) {
            Element node = (Element) userList.get(i);
            Guest entry = new Guest();
            entry.setId(Integer.parseInt(node.getChildText("id")));
            entry.setName(node.getChildText("name"));
            entry.setMessage(node.getChildText("message"));

            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");
            String dateInString = node.getChildText("date");
            String timeInString = node.getChildText("time");
            String dateTime = dateInString + " " + timeInString;
            Date date = formatter.parse(dateTime);
            entry.setEntryDate(date);
            entries.add(entry);
        }
        return entries;
    }

}
