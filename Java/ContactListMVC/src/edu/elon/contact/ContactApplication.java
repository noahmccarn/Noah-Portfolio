/*
 * Copyright (c) 2012 Noah McCarn
 */
package edu.elon.contact;

/**
 * 
 * Main Class that initiates the program. Creates instance of the model and
 * passes it to controller
 * 
 * @author Noah
 * @version 1.0
 * 
 */
public class ContactApplication {

    /**
     * @param args
     */
    public static void main(String[] args) {
        ContactModelInterface model = new ContactModel();
        ContactControllerInterface controller = new ContactController(
                                        model);

    }

}
