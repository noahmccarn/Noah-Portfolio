/*
 * Copyright (c) 2012 Noah McCarn 
 */

package edu.elon.mvc;


import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;


/**
 * 
 * View gui for the MVC model
 * 
 * @author Noah
 * @version 1.0
 * 
 */
public class CalculatorView extends JFrame implements CalculatorObserver {

    /**
     * 
     * Inner Class that lets the controller know that a button has been pressed
     * 
     * @author Noah
     * @version 1.0
     * 
     */
    public class ButtonActionListener implements ActionListener {

        private final String buttonLabel;

        public ButtonActionListener(String buttonLabel) {
            this.buttonLabel = buttonLabel;
        }

        @Override
        public void actionPerformed(ActionEvent arg0) {
            controller.buttonPressed(buttonLabel);
        }
    }

    /**
     * 
     * Lets the Controller know that an operator button has been pressed
     * 
     * @author Noah
     * @version 1.0
     * 
     */
    public class OperatorListener implements ActionListener {

        private final String buttonAction;
        private final String buttonLabel;

        public OperatorListener(String buttonLabel, String buttonAction) {
            this.buttonLabel = buttonLabel;
            this.buttonAction = buttonAction;
        }

        @Override
        public void actionPerformed(ActionEvent aE) {
            controller.operatorButtonPressed(buttonLabel);
            controller.setOperator(buttonAction);
        }

    }

    private final JButton[][] buttons;
    private String[][] buttonText;
    private final CalculatorControllerInterface controller;

    private final CalculatorModelInterface model;
    private JPanel padPanel;

    private JTextField resultField;

    private JPanel topPanel;

    /**
     * Initializes the global variables and creates the gui by calling
     * createCalculator()
     * 
     * @param model
     * @param controller
     */
    public CalculatorView(CalculatorModelInterface model,
            CalculatorControllerInterface controller) {
        this.model = model;
        this.controller = controller;

        model.registerObserver(this);

        buttons = new JButton[4][4];
        this.createCalculator();
        this.setup();
    }

    /**
     * Creates the calculator gui
     */
    public void createCalculator() {
        topPanel = new JPanel();
        padPanel = new JPanel();

        GridLayout gridLayout = new GridLayout(4, 4);
        gridLayout.setHgap(0);
        gridLayout.setVgap(0);
        padPanel.setLayout(gridLayout);

        String[][] tempButtonText = { { "7", "8", "9", "/" },
                { "4", "5", "6", "*" }, { "1", "2", "3", "-" },
                { "0", ".", "=", "+" } };
        buttonText = tempButtonText;

        for (int i = 0; i < buttonText.length; i++) {
            for (int j = 0; j < buttonText[i].length - 1; j++) {
                JButton button = new JButton(buttonText[i][j]);
                buttons[i][j] = button;
                buttons[i][j].addActionListener(new ButtonActionListener(
                        tempButtonText[i][j]));
            }
        }

        JButton divbutton = new JButton(buttonText[0][3]);
        buttons[0][3] = divbutton;
        buttons[0][3].addActionListener(new OperatorListener(
                tempButtonText[0][3], "divide"));

        JButton mulbutton = new JButton(buttonText[1][3]);
        buttons[1][3] = mulbutton;
        buttons[1][3].addActionListener(new OperatorListener(
                tempButtonText[1][3], "multiply"));

        JButton subbutton = new JButton(buttonText[2][3]);
        buttons[2][3] = subbutton;
        buttons[2][3].addActionListener(new OperatorListener(
                tempButtonText[2][3], "subtract"));

        JButton addbutton = new JButton(buttonText[3][3]);
        buttons[3][3] = addbutton;
        buttons[3][3].addActionListener(new OperatorListener(
                tempButtonText[3][3], "add"));

        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                padPanel.add(buttons[i][j]);
            }
        }

        resultField = new JTextField(28);
        topPanel.add(resultField);

        BorderLayout borderLayout = new BorderLayout();
        borderLayout.setHgap(0);
        borderLayout.setVgap(0);
        setLayout(borderLayout);

        add(padPanel, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);
    }

    /**
     * Sets up all the specifics of the gui such as size, isVisible, etc.
     */
    public void setup() {
        setVisible(true);
        setSize(350, 375);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    /**
     * Updates the gui. In this case, the result text field
     */
    @Override
    public void update() {
        String expression = model.getExpression();
        resultField.setText(expression);
    }

}
