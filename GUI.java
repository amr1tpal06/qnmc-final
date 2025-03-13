package qnmc;

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

	static public int minterm =0;
	public static String validatedMinterm;
	GetMintermList item = new GetMintermList();
	static public Set<String> mintermlist;

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
				"Number should be within 0 to " + maxMintermValue + "\nPlease press Next and give your input again",
				"Error", JOptionPane.ERROR_MESSAGE, null);
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


		super("Quine McCluskey Prime Implicant Generator");
		setLayout(null);
		setSize(550, 500);
		setResizable(false);
		panel = new JPanel();
		panel.setBounds(0, 0, 500, 500);

		panel.setLayout(null);

		MenuBar bar = new MenuBar();
		setJMenuBar(bar);

        JLabel mintermInputLabel = new JLabel("Enter Minterm list: ");
		mintermInputLabel.setBounds(50, 100, 150, 30);
		mintermInputLabel.setFont(new Font("Verdana", Font.BOLD, 14));
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

		nextButton = new JButton("Next");
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

		calculateButton = new JButton("Calculate");
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
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {

			e.printStackTrace();

		}



		String bitInput = JOptionPane
				.showInputDialog("Enter the boolean bits(3 to 5): ");

		//validate bits()
		try {
			MenuBar.bits= Integer.parseInt(bitInput);
		} catch (NumberFormatException e) {

			MenuBar.bits= 2;
		}

		if (MenuBar.bits< 3 || MenuBar.bits> 5) {
			JOptionPane.showMessageDialog(null,
					"Wrong input. Press File and then NEW", "Error",
					JOptionPane.ERROR_MESSAGE, null);

		}


		GUI gui = new GUI();
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


	}
}


