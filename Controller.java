package qnmc;

import javax.swing.*;
import java.util.Set;

public class Controller {
    private static GUI gui = null;
    static GetMintermList minList = GetMintermList.getInstance();
    public static String validatedMinterm;

    public Controller(GUI gui) {
        this.gui=gui;
    }

    public static void handleMintermInputField(JTextField mintermInputField){
        String mintext = gui.getMintermInput();
        System.out.println(mintermInputField.getText()); //get
        try {
            gui.minterm = Integer.parseInt(mintext);
        } catch (NumberFormatException e) {
            gui.minterm = -1;
        } //handle
        MintermValidation.validateMinterm(mintext); //return
    }

    public static void handleNextButton() {
        gui.setInputField(); //set input field
        minList.setMinList(validatedMinterm);
    }

    public static void handleCalculateButton() {
        Set<String> mintermlist = GetMintermList.getMin();
        minList.notifyObservers();
    }
}