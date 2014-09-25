/*
 * Copyright (c) 2012 Noah McCarn
 */
package edu.elon.contact;

import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * 
 * Controller for the application. Controls the passing of information to the
 * model
 * 
 * @author Noah
 * @version 1.0
 * 
 */
public class ContactController implements ContactControllerInterface {

    private final ContactModelInterface model;

    /**
     * Constructor that takes an instance of the model, so that it can talk to
     * it and pass the neccesary information. Also creates an instance of view.
     * 
     * @param model
     */
    public ContactController(ContactModelInterface model) {
        this.model = model;

        ContactView view = new ContactView(this, model);
    }

    /**
     * Method that passes the neccesary information needed by the model to do
     * the neccessary action
     */
    @Override
    public void preformAction(String aName, JTextField[] fields,
                                    JPanel buttonPanel) {
        model.preformAction(aName, fields, buttonPanel);

    }
}
