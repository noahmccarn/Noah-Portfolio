/*
 * Copyright (c) 2012 Noah McCarn 
 */

package edu.elon.mvc;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.StringTokenizer;


/**
 * 
 * Class that represents the model. Does all the logic for the gui.
 * 
 * @author Noah McCarn
 * @version 1.0
 * 
 */
public class CalculatorModel implements CalculatorModelInterface {

    private String expression;
    private boolean isNewCalculation;
    private final ArrayList<CalculatorView> observers;
    private String operator;

    /**
     * Constructor that initializes the global variables
     */
    public CalculatorModel() {
        observers = new ArrayList<CalculatorView>();
        expression = "";
        isNewCalculation = false;
    }

    /**
     * Adds two numbers together.
     * 
     * @return double that is the result of adding the two numbers together
     */
    @Override
    public double add(Double d) {
        double[] operand = this.getOperands("+");
        return operand[0] + operand[1];
    }

    /**
     * Divides two numbers together.
     * 
     * @return double that is the result of dividing the two numbers together
     */
    @Override
    public double divide(Double d) {
        double[] operand = this.getOperands("/");
        return operand[0] / operand[1];
    }

    /**
     * Getter for the expression
     * 
     * @return String the expression
     */
    @Override
    public String getExpression() {
        return expression;
    }

    public boolean getIsNewCalculation() {
        return isNewCalculation;
    }

    /**
     * Pulls out the operands from the expression
     * 
     * @param operation
     * @return double[] that holds the two operands such that the operator can
     *         be applied
     */
    public double[] getOperands(String operation) {
        double[] operands = new double[2];
        StringTokenizer st = new StringTokenizer(expression.substring(0,
                expression.length() - 1), operation);
        operands[0] = Double.parseDouble(st.nextToken());
        operands[1] = Double.parseDouble(st.nextToken());

        return operands;
    }

    /**
     * Multiplies two numbers together.
     * 
     * @return double that is the result of multiplying the two numbers together
     */
    @Override
    public double multiply(Double d) {
        double[] operand = this.getOperands("*");
        return operand[0] * operand[1];
    }

    /**
     * Iterates over the ArrayList of guis and calls their update function so
     * that the guis will all have updated information
     */
    @Override
    public void notifyObservers() {
        for (int i = 0; i < observers.size(); i++) {
            CalculatorView view = observers.get(i);
            view.update();
        }
    }

    /**
     * Preforms the calculation. Uses reflection to call the right method
     * 
     */
    @Override
    public void preformCalculation() {
        double total = 0;
        Class c = this.getClass();
        try {
            Method m = c.getMethod(operator, Double.class);
            total = (Double) m.invoke(this, new Double(1.0));
            expression = total + "";
            notifyObservers();
            isNewCalculation = true;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    /**
     * Adds an instance of CalculatorView to the ArrayList
     */
    @Override
    public void registerObserver(CalculatorView view) {
        observers.add(view);
    }

    /**
     * Removes an instance of CalculatorView from the ArrayList
     */
    @Override
    public void removeObserver(CalculatorView view) {
        int i = observers.indexOf(view);
        if (i >= 0) {
            observers.remove(i);
        }
    }

    /**
     * Sets the expression and calls preformCalculation() if input is "="
     */
    @Override
    public void setExpression(String aExpression) {
        if (isNewCalculation) {
            expression = "";
            isNewCalculation = false;
        }
        expression += aExpression;
        notifyObservers();
        if (aExpression.equals("=")) {
            this.preformCalculation();
        }
    }

    /**
     * Sets the operator
     */
    @Override
    public void setOperator(String aOperator) {
        operator = aOperator;
    }

    /**
     * Subtracts two numbers together.
     * 
     * @return double that is the result of subtracting the two numbers together
     */
    @Override
    public double subtract(Double d) {
        double[] operand = this.getOperands("-");
        return operand[0] - operand[1];
    }

}
