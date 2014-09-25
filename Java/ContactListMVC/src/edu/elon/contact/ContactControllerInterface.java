/*
 * Copyright (c) 2012 Noah McCarn
 */
package edu.elon.contact;

import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * 
 * Interface for the Controller. Implements only one method
 * 
 * @author Noah
 * @version 1.0
 * 
 */
public interface ContactControllerInterface {

    /**
     * Method passes the needed information to the model
     * 
     * @param aName
     * @param fields
     * @param buttonPanel
     */
    public void preformAction(String aName, JTextField[] fields,
                                    JPanel buttonPanel);

}
