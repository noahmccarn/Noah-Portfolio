/*
 * Copyright (c) 2012 Noah McCarn 
 */
package edu.elon.mvc;


/**
 * Interface that defines all the functions for the controller
 * 
 * @author Noah
 * @version 1.0
 * 
 */
public interface CalculatorControllerInterface {

    public void buttonPressed(String button);

    public void operatorButtonPressed(String button);

    public void setOperator(String operator);

}
