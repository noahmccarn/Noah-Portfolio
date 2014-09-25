/*
 * Copyright (c) 2012 Noah McCarn
 */
package edu.elon.contact;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;

/**
 * 
 * Creates the neccesary gui component and puts them together.
 * 
 * @author Noah
 * @version 1.0
 * 
 */
public class ContactView extends JFrame implements Observer {

    /**
     * 
     * Single ActionListener that all buttons and menu items receives
     * 
     * @author Noah
     * @version 1.0
     * 
     */
    public class ButtonListener implements ActionListener {

        private final JPanel buttonPanel;
        private final JTextField[] fields;
        private final String name;

        public ButtonListener(String name, JTextField[] fields,
                                        JPanel panel) {
            this.name = name;
            this.fields = fields;
            this.buttonPanel = panel;
        }

        /**
         * Calls controller's preformAction method and passes the needed
         * information
         */
        @Override
        public void actionPerformed(ActionEvent aArg0) {
            controller.preformAction(name, fields, buttonPanel);
        }
    }

    private JPanel buttonPanel;
    private final ContactControllerInterface controller;
    private JTextField[] fields = new JTextField[5];
    private final ContactModelInterface model;

    private String name;

    /**
     * Constructor that calls the methods that create the gui
     * 
     * @param controller
     * @param model
     */
    public ContactView(ContactControllerInterface controller,
                                    ContactModelInterface model) {
        this.controller = controller;
        this.model = model;
        this.model.registerObserver(this);

        this.setup();
        this.createButtons();
        this.createMainGui();
        this.createMenu();
        this.setup();

    }

    /**
     * Updates the JTextFields
     */
    @Override
    public void update() {
        fields = model.getFields();
    }

    /**
     * Creates all the buttons
     */
    private void createButtons() {
        buttonPanel = new JPanel();
        JButton[] buttons = new JButton[4];

        JPanel nextPrevious = new JPanel();
        JPanel okayCancel = new JPanel();

        buttons[0] = new JButton("Previous");
        buttons[1] = new JButton("Next");
        buttons[2] = new JButton("Okay");
        buttons[3] = new JButton("Cancel");

        for (int i = 0; i < buttons.length; i++) {
            name = buttons[i].getText();
            buttons[i].addActionListener(new ButtonListener(name, fields,
                                            buttonPanel));
        }

        nextPrevious.add(buttons[0]);
        nextPrevious.add(buttons[1]);

        okayCancel.add(buttons[2]);
        okayCancel.add(buttons[3]);

        buttonPanel.setLayout(new CardLayout());
        buttonPanel.add(nextPrevious, "nextPrevious");
        buttonPanel.add(okayCancel, "okayCancel");
        CardLayout cl = (CardLayout) (buttonPanel.getLayout());
        cl.show(buttonPanel, "okayCancel");
        add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Creates all the JTextFields and Labels and adds them to the JFrame
     */
    private void createMainGui() {
        JPanel panel = new JPanel();
        panel.setVisible(true);

        String[] headings = { "First Name", "Middle Name", "Last Name",
                                        "Email", "Major" };

        for (int i = 0; i < 5; i++) {
            panel.setLayout(new GridLayout(5, 1));

            JLabel label = new JLabel(headings[i]);
            label.setHorizontalAlignment(11);
            JTextField field = new JTextField(15);
            fields[i] = field;
            field.setEditable(true);
            label.setLabelFor(field);
            panel.add(label);
            panel.add(field);
        }
        panel.setVisible(true);
        add(panel, BorderLayout.CENTER);

    }

    /**
     * Creates Menu Bar with all neccessary tabs and adds ActionListeners to
     * them
     * 
     */
    private void createMenu() {
        JMenuBar bar = new JMenuBar();
        bar.setVisible(true);

        JMenu filemenu = new JMenu("File");
        filemenu.setMnemonic(KeyEvent.VK_F);

        JMenuItem[] file = new JMenuItem[8];

        JMenuItem newItem = new JMenuItem("New", KeyEvent.VK_N);
        filemenu.add(newItem);
        file[0] = newItem;

        JMenuItem open = new JMenuItem("Open", KeyEvent.VK_O);
        filemenu.add(open);
        file[1] = open;

        JMenuItem save = new JMenuItem("Save", KeyEvent.VK_S);
        filemenu.add(save);
        file[2] = save;

        JMenuItem saveas = new JMenuItem("Save As...");
        filemenu.add(saveas);
        file[3] = saveas;

        filemenu.add(new JSeparator());

        JMenuItem exit = new JMenuItem("Exit", KeyEvent.VK_X);
        filemenu.add(exit);
        file[4] = exit;

        JMenu editmenu = new JMenu("Edit");
        editmenu.setMnemonic(KeyEvent.VK_E);

        JMenuItem add = new JMenuItem("Add", KeyEvent.VK_A);
        editmenu.add(add);
        file[5] = add;

        JMenuItem remove = new JMenuItem("Remove", KeyEvent.VK_R);
        editmenu.add(remove);
        file[6] = remove;

        JMenuItem update = new JMenuItem("Update", KeyEvent.VK_U);
        editmenu.add(update);
        file[7] = update;

        for (int i = 0; i < file.length; i++) {
            name = file[i].getText();
            System.out.println(name);
            if (name.equals("New")) {
                name = "newContact";
            } else if (name.equals("Save As...")) {
                name = "saveas";
            }
            file[i].addActionListener(new ButtonListener(name, fields,
                                            buttonPanel));
        }

        bar.add(filemenu);
        bar.add(editmenu);
        add(bar, BorderLayout.NORTH);
        bar.setVisible(true);
    }

    /**
     * Sets up all the details of the gui (e.g., Title, size, location, etc.)
     */
    private void setup() {
        setTitle("Contact Display View");
        setVisible(true);
        setSize(350, 225);
        setLocation(350, 200);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

}
