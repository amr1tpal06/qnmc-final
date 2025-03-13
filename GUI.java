package qnmc;
//extra ui, extract action listeners

//error handling

//hardcoded values constants- strings and integers

//mvc, design patterns
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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

	private JPanel panel;
	private static int maxMintermValue;

    private JTextField mintermInputField;
	private JButton nextButton;

	private JTextArea resultTextArea;
	private JButton calculateButton;
	private String result;

	private static  int minterm =0;
	private static String validatedMinterm;
	GetMintermList item = new GetMintermList();
	public static Set<String> mintermlist;

	private static final String QUINE_MCCLUSKEY_TITLE="Quine McCluskey Prime Implicant Generator";
	private static final String NEXT_BUTTON_TEXT = "Next";
	private static final String CALCULATE_BUTTON_TEXT = "Calculate";
	private static final String MINTERM_INPUT_LABEL_TEXT = "Enter Minterm list: ";
	private static final String BITS_INPUT_DIALOG="Enter the boolean bits(3 to 5): ";

	private static final String WRONG_INPUT = "Wrong input. Press File and then NEW.";
	private static final String MINTERM_OUT_OF_BOUNDS = "Number should be within 0 to ";
	private static final String ENTER_VALID_BITS = "\nPlease press Next and give your input again";
	private static final String ERROR = "Error";

	private static final String NIMBUS= "Nimbus";
	private static final String DEFAULT_FONT = "Verdana";

	private static final int DEFAULT_BITS = 2;
	private static final int MIN_BITS = 3;
	private static final int MAX_BITS = 5;



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

	public static void showMintermError(int maxMintermValue){
		JOptionPane.showMessageDialog(null,
				MINTERM_OUT_OF_BOUNDS + maxMintermValue + ENTER_VALID_BITS,
				ERROR, JOptionPane.ERROR_MESSAGE, null);
	}

	public static void validateMinterm(String minText){
		maxMintermValue = getmaxvalue(MenuBar.bits);
		if (minterm < 0 || minterm > maxMintermValue) {
			showMintermError(maxMintermValue);
		} else {
			validatedMinterm = minText;
		}
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

		super(QUINE_MCCLUSKEY_TITLE);
		setLayout(null);
		setSize(550, 500);
		setResizable(false);
		panel = new JPanel();
		panel.setBounds(0, 0, 500, 500);

		panel.setLayout(null);

		MenuBar bar = new MenuBar();
		setJMenuBar(bar);

        JLabel mintermInputLabel = new JLabel(MINTERM_INPUT_LABEL_TEXT);
		mintermInputLabel.setBounds(50, 100, 150, 30);
		mintermInputLabel.setFont(new Font(DEFAULT_FONT, Font.BOLD, 14));
		panel.add(mintermInputLabel);

		mintermInputField = new JTextField();
		mintermInputField.setBounds(50, 140, 70, 30);

		mintermInputField.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				System.out.println(mintermInputField.getText());
				String mintext = mintermInputField.getText();

				// validate number, gets max value, validates minterm()
				try {
					minterm = Integer.parseInt(mintext);
				} catch (NumberFormatException e) {
					minterm = -1;
				}

				validateMinterm(mintext);
			}


			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
		panel.add(mintermInputField);

		nextButton = new JButton(NEXT_BUTTON_TEXT);
		nextButton.setBounds(140, 140, 70, 30);
		nextButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//add minterm
				mintermInputField.setText("");
				item.setMinList(validatedMinterm);
			}
		});
		panel.add(nextButton);


		resultTextArea = new JTextArea();
		resultTextArea.setBounds(50, 200, 300, 200);
		resultTextArea.setEditable(false);
		panel.add(resultTextArea);

		calculateButton = new JButton(CALCULATE_BUTTON_TEXT);
		calculateButton.setBounds(400, 250, 100, 50);
		calculateButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				mintermlist = GetMintermList.getMin();
				result = applyQuineMcCluskey(mintermlist);
				resultTextArea.setText(result);

			}
		});
		panel.add(calculateButton);

		setVisible(true);
		add(panel);

	}

	public static void main(String[] args) {

		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if (NIMBUS.equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {

			e.printStackTrace();

		}



		String bitInput = JOptionPane
				.showInputDialog(BITS_INPUT_DIALOG);

		//validate bits()
		try {
			MenuBar.bits= Integer.parseInt(bitInput);
		} catch (NumberFormatException e) {

			MenuBar.bits= DEFAULT_BITS;
		}

		if (MenuBar.bits< MIN_BITS || MenuBar.bits> MAX_BITS) {
			JOptionPane.showMessageDialog(null,
					WRONG_INPUT, ERROR,
					JOptionPane.ERROR_MESSAGE, null);

		}


		GUI gui = new GUI();
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


	}
}


