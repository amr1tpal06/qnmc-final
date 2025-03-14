package qnmc;

import javax.swing.*;

public class MintermValidation {

    public static void validateBits(String bitInput){
        try {
            MenuBar.bits= Integer.parseInt(bitInput);
        } catch (NumberFormatException e) {

            MenuBar.bits= UIBitsValues.DEFAULT_BITS;
        }

        if (MenuBar.bits< UIBitsValues.MIN_BITS || MenuBar.bits> UIBitsValues.MAX_BITS) {
            showBitsError();
        }
    }

    public static void validateMinterm(String minText){
        int maxMintermValue = getMaxValue(MenuBar.bits);
        if (GUI.minterm < 0 || GUI.minterm > maxMintermValue) {
            showMintermError(maxMintermValue);
        } else {
            Controller.validatedMinterm = minText;
        }
    }

    public static int getMaxValue(int bits) {
        return (1 << bits) - 1; // 2^bits - 1
    };

    public static void showBitsError(){
        JOptionPane.showMessageDialog(null,
                UIValidationErrorMessages.WRONG_INPUT, UIValidationErrorMessages.ERROR,
                JOptionPane.ERROR_MESSAGE, null);
    }

    public static void showMintermError(int maxMintermValue){
        JOptionPane.showMessageDialog(null,
                UIValidationErrorMessages.MINTERM_OUT_OF_BOUNDS + maxMintermValue + UIValidationErrorMessages.ENTER_VALID_BITS,
                UIValidationErrorMessages.ERROR, JOptionPane.ERROR_MESSAGE, null);
    }
}
