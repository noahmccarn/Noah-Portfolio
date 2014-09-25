/*
 * Copyright (c) 2012 Noah McCarn 
 */
package edu.elon.mvc;

public class CalculatorApplication {

    public static void main(String[] args) {
        CalculatorModelInterface model = new CalculatorModel();
        CalculatorControllerInterface controller = new CalculatorController(
                                        model);
    }
}
