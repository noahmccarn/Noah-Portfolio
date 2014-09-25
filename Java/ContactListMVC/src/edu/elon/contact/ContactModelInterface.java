/*
 * Copyright (c) 2012 Noah McCarn
 */
package edu.elon.contact;

import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * 
 * Interface that defines model's methods that are needed by other classes
 * 
 * @author Noah
 * @version 1.0
 * 
 */
public interface ContactModelInterface {

    public JTextField[] getFields();

    public void notifyObservers();

    public void preformAction(String aName, JTextField[] fields,
                                    JPanel buttonPanel);

    public void registerObserver(ContactView aContactView);

}
