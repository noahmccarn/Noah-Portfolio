/*
 * Copyright (c) 2012 Noah McCarn 
 */
package edu.elon.mvc;


/**
 * Interface that defines all the functions for the model class
 * 
 * @author Noah
 * @version 1.0
 * 
 */
public interface CalculatorModelInterface {

    public double add(Double d);

    public double divide(Double d);

    public String getExpression();

    public boolean getIsNewCalculation();

    public double multiply(Double d);

    public void notifyObservers();

    public void preformCalculation();

    public void registerObserver(CalculatorView view);

    public void removeObserver(CalculatorView view);

    public void setExpression(String expression);

    public void setOperator(String operator);

    public double subtract(Double d);
}
