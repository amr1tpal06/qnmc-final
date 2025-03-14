package qnmc;

import javax.swing.*;
import java.util.Set;

public class Controller {
    private static GUI gui = null;
    static GetMintermList minList = GetMintermList.getInstance();
    public static String validatedMinterm;

    public Controller(GUI gui) {
        this.gui=gui;
        minList.addObserver(gui);
    }

    public void handleMintermInputField(JTextField mintermInputField){
        String mintext = gui.getMintermInput();
        System.out.println(mintermInputField.getText()); //get
        try {
            gui.minterm = Integer.parseInt(mintext); //shouldnt be direct
        } catch (NumberFormatException e) {
            gui.minterm = -1;
        } //handle
        MintermValidation.validateMinterm(mintext); //return
    }

    public void handleNextButton() {
        gui.setInputField(); //set input field
        minList.setMinList(validatedMinterm);
    }

    public void handleCalculateButton() {
        //Set<String> mintermlist = GetMintermList.getMin();
        String result = QuineProcessor.applyQuineMcCluskey(GetMintermList.getMin());
        gui.updateResultArea(result);
    }
}