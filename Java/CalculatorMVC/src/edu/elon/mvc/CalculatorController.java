/*
 *Copyright (c) 2012 Noah McCarn 
 */
package edu.elon.mvc;


/**
 * Controller class for the MVC model
 * 
 * @author Noah
 * @version 1.0
 * 
 */
public class CalculatorController implements CalculatorControllerInterface {

    private final CalculatorModelInterface model;
    private final CalculatorView view;
    private boolean isDecimalPresent = false;

    /**
     * Initializes the global varibles, giving an instance of the model and the
     * view
     * 
     * @param model
     */
    public CalculatorController(CalculatorModelInterface model) {
        this.model = model;
        view = new CalculatorView(model, this);
    }

    /**
     * Lets the model know what button was pressed
     */
    @Override
    public void buttonPressed(String button) {
        if (((button.equals(".")) && (!isDecimalPresent))) {
            model.setExpression(button);
            isDecimalPresent = true;
        } else if (!button.equals(".")) {
            model.setExpression(button);
        }
        if (model.getIsNewCalculation()) {
            isDecimalPresent = false;
        }

    }

    /**
     * Resets the isDecimalPresent value because a new number will be entered
     * and that new number is allowed one decimal place
     */
    @Override
    public void operatorButtonPressed(String aButton) {
        isDecimalPresent = false;
        model.setExpression(aButton);
    }

    /**
     * Lets the model know what operator has been pressed
     */
    @Override
    public void setOperator(String aOperator) {
        model.setOperator(aOperator);

    }

}
