/*
 * Copyright (c) 2012 Noah McCarn
 */
package edu.elon.contact;

import java.awt.CardLayout;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * 
 * Handles the logic for application. Has no knowledge of view or controller.
 * 
 * @author Noah
 * @version 1.0
 * 
 */
public class ContactModel implements ContactModelInterface, Serializable {

    private JPanel buttonPanel;
    private ArrayList<Contact> contacts;
    private JTextField[] fields;
    private File file;
    private int index;
    private boolean isFirst;
    private final ArrayList<ContactView> viewers;

    /**
     * Constructor that initializes the global variables that are not set in
     * actionPreform method
     */
    public ContactModel() {
        index = 0;
        contacts = new ArrayList<Contact>();
        viewers = new ArrayList<ContactView>();
        isFirst = true;
    }

    /**
     * Adds a contact to the ArrayList
     * 
     */
    public void add(Double d) {

        fields[0].setText("");
        fields[1].setText("");
        fields[2].setText("");
        fields[3].setText("");
        fields[4].setText("");

        CardLayout cl = (CardLayout) (buttonPanel.getLayout());
        cl.show(buttonPanel, "okayCancel");

        notifyObservers();
        System.out.println(index);

    }

    /**
     * Cancels the add action. Doesn't add the contact to the ArrayList and
     * display's last viewed contact.
     * 
     * @param d
     */
    public void cancel(Double d) {
        Contact contact = contacts.get(index);
        fields[0].setText(contact.getFirstName());
        fields[1].setText(contact.getMiddleName());
        fields[2].setText(contact.getLastName());
        fields[3].setText(contact.getEmail());
        fields[4].setText(contact.getMajor());

        CardLayout cl = (CardLayout) (buttonPanel.getLayout());
        cl.show(buttonPanel, "nextPrevious");
    }

    /**
     * Exits the program.
     * 
     * @param d
     */
    public void exit(Double d) {
        System.exit(0);
    }

    /**
     * A getter for the JTextFields.
     */
    @Override
    public JTextField[] getFields() {
        return fields;
    }

    /**
     * Creates a new contact. Gives 5 empty text fields for input. Doesn't add
     * contact to ArrayList until okay button is clicked.
     * 
     * @param d
     */
    public void newcontact(Double d) {
        contacts = null;
        contacts = new ArrayList<Contact>();

        fields[0].setText("");
        fields[1].setText("");
        fields[2].setText("");
        fields[3].setText("");
        fields[4].setText("");
    }

    /**
     * Displays the next contact. If contact is last, then will not display next
     * since there is nothing else to display.
     * 
     * @param d
     */
    public void next(Double d) {
        index++;
        if (index < contacts.size()) {
            Contact contact = contacts.get(index);
            fields[0].setText(contact.getFirstName());
            fields[1].setText(contact.getMiddleName());
            fields[2].setText(contact.getLastName());
            fields[3].setText(contact.getEmail());
            fields[4].setText(contact.getMajor());

        } else {
            index--;
        }
    }

    /**
     * Notifies the observers
     */
    @Override
    public void notifyObservers() {
        for (int i = 0; i < viewers.size(); i++) {
            viewers.get(i).update();
        }
    }

    /**
     * Adds the Contact to the ArrayList.
     * 
     * @param d
     */
    public void okay(Double d) {
        Contact contact = new Contact();
        contact.setFirstName(fields[0].getText());
        contact.setMiddleName(fields[1].getText());
        contact.setLastName(fields[2].getText());
        contact.setEmail(fields[3].getText());
        contact.setMajor(fields[4].getText());

        contacts.add(contact);

        if (isFirst == false) {
            index++;
        }
        isFirst = false;
        CardLayout cl = (CardLayout) (buttonPanel.getLayout());
        cl.show(buttonPanel, "nextPrevious");
    }

    /**
     * Opens up a file that stores an ArrayList of Contact. Sets this ArrayList
     * to the one read in the file.
     * 
     * @param d
     */
    public void open(Double d) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.showOpenDialog(null);
        file = fileChooser.getSelectedFile();

        try {
            ObjectInputStream input = new ObjectInputStream(
                                            new FileInputStream(file));
            ArrayList<Contact> temp = (ArrayList<Contact>) input
                                            .readObject();
            contacts = null;
            contacts = temp;
            index = 0;
            isFirst = true;

            fields[0].setText(contacts.get(0).getFirstName());
            fields[1].setText(contacts.get(0).getMiddleName());
            fields[2].setText(contacts.get(0).getLastName());
            fields[3].setText(contacts.get(0).getEmail());
            fields[4].setText(contacts.get(0).getMajor());
        } catch (Exception e) {
            System.out.println("Error Writing File");
            e.printStackTrace();
        }
    }

    /**
     * Gets name from controller, when controller calls this method. Uses
     * reflection to call the appropriate method.
     */
    @Override
    public void preformAction(String aName, JTextField[] fields,
                                    JPanel buttonPanel) {
        this.fields = fields;
        this.buttonPanel = buttonPanel;
        String name = aName.toLowerCase();

        try {
            Method m = this.getClass().getMethod(name, Double.class);
            m.invoke(this, new Double(1.0));

        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    /**
     * Displays the previous contact. If contact is first, then will not display
     * previous contact since the contact being viewed is the first one.
     * 
     * @param d
     */
    public void previous(Double d) {
        index--;
        if (index >= 0) {
            Contact contact = contacts.get(index);
            fields[0].setText(contact.getFirstName());
            fields[1].setText(contact.getMiddleName());
            fields[2].setText(contact.getLastName());
            fields[3].setText(contact.getEmail());
            fields[4].setText(contact.getMajor());
        } else {
            index++;
        }
    }

    /**
     * Registers the ContactView Instance to the ArrayList so that view can be
     * updated.
     */
    @Override
    public void registerObserver(ContactView aContactView) {
        viewers.add(aContactView);

    }

    /**
     * Removes contact from ArrayList. Display the next contact if one exists.
     * If not, displays the previous contact. Will display empty text fields, if
     * deleted contact was only one in ArrayList.
     * 
     */
    public void remove(Double d) {
        Contact contact = contacts.get(index);
        contacts.remove(index);
        if (contacts.isEmpty()) {
            fields[0].setText("");
            fields[1].setText("");
            fields[2].setText("");
            fields[3].setText("");
            fields[4].setText("");
            index = 0;
            isFirst = true;
        } else {
            if (index < contacts.size()) {
                contact = contacts.get(index);
            } else {
                index--;
                contact = contacts.get(index);
            }
            fields[0].setText(contact.getFirstName());
            fields[1].setText(contact.getMiddleName());
            fields[2].setText(contact.getLastName());
            fields[3].setText(contact.getEmail());
            fields[4].setText(contact.getMajor());
        }
    }

    /**
     * Saves the ArrayList to a file.
     * 
     * @param d
     */
    public void save(Double d) {
        try {
            if (file != null) {
                ObjectOutputStream output = new ObjectOutputStream(
                                                new FileOutputStream(file));
                output.writeObject(contacts);
            } else {
                this.saveas(d);
            }

        } catch (FileNotFoundException e) {
            System.out.println("Error Writing File");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves the ArrayList to the specified file
     * 
     * @param d
     */
    public void saveas(Double d) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.showSaveDialog(null);
        file = fileChooser.getSelectedFile();

        try {
            ObjectOutputStream output = new ObjectOutputStream(
                                            new FileOutputStream(file));
            output.writeObject(contacts);

        } catch (FileNotFoundException e) {
            System.out.println("Error Writing File");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates the contact to the current information being displayed
     * 
     * @param d
     */
    public void update(Double d) {
        Contact contact = contacts.get(index);

        contact.setFirstName(fields[0].getText());
        contact.setMiddleName(fields[1].getText());
        contact.setLastName(fields[2].getText());
        contact.setEmail(fields[3].getText());
        contact.setMajor(fields[4].getText());
    }
}
