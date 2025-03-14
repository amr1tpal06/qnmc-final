package qnmc;

import javax.swing.*;
import java.util.Set;

public class Controller {
    private static GUI gui = null;
    public static Set<String> mintermlist;
    static GetMintermList minlist = new GetMintermList();
    public static String validatedMinterm;

    public Controller(GUI gui) {
        this.gui=gui;
        this.minlist=minlist;
    }

    public static void handleMintermInputField(JTextField mintermInputField){
        String mintext = gui.getMintermInput();
        System.out.println(mintermInputField.getText()); //get

        try {
            GUI.minterm = Integer.parseInt(mintext);
        } catch (NumberFormatException e) {
            GUI.minterm = -1;
        } //handle

        MintermValidation.validateMinterm(mintext); //return
    }

    public static void handleNextButton() {
        GUI.setInputField(); //set input field
        minlist.setMinList(validatedMinterm);
    }

    public static void handleCalculateButton() {
        Set<String> mintermlist = GetMintermList.getMin();
        String result = QuineProcessor.applyQuineMcCluskey(mintermlist);
        GUI.updateResultArea(result);
    }
}