package qnmc;
//extract ui, extract classes, statics
//mvc, design patterns

import java.awt.Font;
import java.awt.event.*;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

public class GUI extends JFrame {

    private static JTextField mintermInputField;
	private final JButton nextButton;
	private static JTextArea resultTextArea;
	private final JButton calculateButton;

    private static int minterm =0;
	private static String validatedMinterm;
	GetMintermList minlist = new GetMintermList();
	public static Set<String> mintermlist;

	public void setNextButtonActionListener(){
		nextButton.addActionListener(e-> handleNextButton(mintermInputField, minlist));
	}

	public void setCalculateButtonListener(){
		calculateButton.addActionListener(e -> handleCalculateButton());
	}

	public void setMintermInputFieldListener(){
		mintermInputField.addKeyListener(new KeyAdapter() { //KeyAdapter to override only the methods you care about (in this case, keyReleased)
			@Override
			public void keyReleased(KeyEvent e) {
				handleMintermInputField();
			}
		});
	}

	public static void handleMintermInputField(){
		System.out.println(mintermInputField.getText()); //get
		String mintext = mintermInputField.getText();

		// validate number, gets max value, validates minterm()
		try {
			minterm = Integer.parseInt(mintext);
		} catch (NumberFormatException e) {
			minterm = -1;
		} //handle

		validateMinterm(mintext); //return
	}

	public static void handleCalculateButton(){
		mintermlist = GetMintermList.getMin(); //get
        String result = applyQuineMcCluskey(mintermlist); //process
		resultTextArea.setText(result); //return
	}

	public static void handleNextButton(JTextField mintermInputField, GetMintermList item){
		mintermInputField.setText("");
		item.setMinList(validatedMinterm); //return
	}

	public static void validateBits(String bitInput){
		try {
			MenuBar.bits= Integer.parseInt(bitInput);
		} catch (NumberFormatException e) {

			MenuBar.bits= BitsValues.DEFAULT_BITS;
		}

		if (MenuBar.bits< BitsValues.MIN_BITS || MenuBar.bits> BitsValues.MAX_BITS) {
			showBitsError();
		}
	}

	public static String applyQuineMcCluskey(Set<String> mintermlist){
		Quine quine = new Quine();
		try {

			for (String mintermitem : mintermlist) {

				quine.addTerm(toBinary(MenuBar.bits, mintermitem));

				System.out.println(mintermitem);
			}

			quine.simplify();
			return quine.toString();

		} catch (ExceptionQuine e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void validateMinterm(String minText){
		int maxMintermValue = getmaxvalue(MenuBar.bits);
		if (minterm < 0 || minterm > maxMintermValue) {
			showMintermError(maxMintermValue);
		} else {
			validatedMinterm = minText;
		}
	}

	public static void showBitsError(){
		JOptionPane.showMessageDialog(null,
				ValidationErrorMessages.WRONG_INPUT, ValidationErrorMessages.ERROR,
				JOptionPane.ERROR_MESSAGE, null);
	}

	public static void showMintermError(int maxMintermValue){
		JOptionPane.showMessageDialog(null,
				ValidationErrorMessages.MINTERM_OUT_OF_BOUNDS + maxMintermValue + ValidationErrorMessages.ENTER_VALID_BITS,
				ValidationErrorMessages.ERROR, JOptionPane.ERROR_MESSAGE, null);
	}

	static public String toBinary (int bits, String mintermitem){
		int mintermnumber = Integer.parseInt(mintermitem);
		String binary= Integer.toBinaryString(mintermnumber);
		int zeros= bits-binary.length();
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < zeros; i++) {
			result.append("0");
		}
		result.append(binary);
		return result.toString();
	};

	public static int getmaxvalue(int bits) {
		return (1 << bits) - 1; // 2^bits - 1
	};

	public GUI() {

		super(UILabels.QUINE_MCCLUSKEY_TITLE);
		setLayout(null);
		setSize(550, 500);
		setResizable(false);
        JPanel panel = new JPanel();
		panel.setBounds(0, 0, 500, 500);

		panel.setLayout(null);

		MenuBar bar = new MenuBar();
		setJMenuBar(bar);

        JLabel mintermInputLabel = new JLabel(UILabels.MINTERM_INPUT_LABEL_TEXT);
		mintermInputLabel.setBounds(50, 100, 150, 30);
		mintermInputLabel.setFont(new Font(UIConstants.DEFAULT_FONT, Font.BOLD, 14));
		panel.add(mintermInputLabel);

		mintermInputField = new JTextField();
		mintermInputField.setBounds(50, 140, 70, 30);
		panel.add(mintermInputField);
		setMintermInputFieldListener();

		nextButton = new JButton(UILabels.NEXT_BUTTON_TEXT);
		nextButton.setBounds(140, 140, 70, 30);
		panel.add(nextButton);
		setNextButtonActionListener();

		resultTextArea = new JTextArea();
		resultTextArea.setBounds(50, 200, 300, 200);
		resultTextArea.setEditable(false);
		panel.add(resultTextArea);

		calculateButton = new JButton(UILabels.CALCULATE_BUTTON_TEXT);
		calculateButton.setBounds(400, 250, 100, 50);
		panel.add(calculateButton);
		setCalculateButtonListener();

		setVisible(true);
		add(panel);

	}

	public static void main(String[] args) {

		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if (UIConstants.NIMBUS.equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {

			e.printStackTrace();

		}

		String bitInput = JOptionPane
				.showInputDialog(UILabels.BITS_INPUT_DIALOG);

		validateBits(bitInput);

		GUI gui = new GUI();
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
}


