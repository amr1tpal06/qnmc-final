package qnmc;

//max_value
//hardcoded values, redundant event listeners
//components and event listeners separate
//modularise events into methods --> flow of data, separate into mvc
//design patterns
// rough documentation - couple of pages plan and then aim for 15

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Iterator;
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
	private int max_value;

	private JLabel mintermInputLabel;
	private JTextField mintermInputField;
	private JButton nextButton;

	private JTextArea resultTextArea;
	private JButton calculateButton;

	static public int minterm =0;
	static public Set<String> mintermlist;
	public String validatedMinterm;
	GetMintermList item = new GetMintermList();

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

	static public String toThreeBitBinary(String input) {


		String bin[] = { "000", "001", "010", "011", "100", "101", "110", "111" };

		int i = Integer.parseInt(input);



		return bin[i];

	}

	static public String toFourBitBinary(String input) {

		String bin[] = { "0000", "0001", "0010", "0011", "0100", "0101",
				"0110", "0111", "1000", "1001", "1010", "1011", "1100", "1101",
				"1110", "1111" };

		int i = Integer.parseInt(input);

		return bin[i];

	}
	static public String toFiveBitBinary(String input) {

		String bin[] = { "00000", "00001", "00010", "00011", "00100", "00101",
				"00110", "00111", "01000", "01001", "01010", "01011", "01100",
				"01101", "01110", "01111", "10000", "10001", "10010", "10011",
				"10100", "10101", "10110", "10111", "11000", "11001", "11010",
				"11011", "11100", "11101", "11110", "11111" };

		int i = Integer.parseInt(input);

		return bin[i];

	}

	public static int getmaxvalue(int bits) {
		return (1 << bits) - 1; // 2^bits - 1
	}

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

		mintermInputLabel = new JLabel("Enter Minterm list: ");
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

				int bits = MenuBar.bits;

				System.out.println(mintermInputField.getText());
				String mintext = mintermInputField.getText();

				// gets max value, validates minterm()
				try {
					minterm = Integer.parseInt(mintext);
				} catch (NumberFormatException e) {
					minterm = -1;
				}

				max_value = getmaxvalue(bits);

				if (minterm < 0 || minterm > max_value) {
					JOptionPane.showMessageDialog(null,
							"Number should be within 0 to " + max_value + "\nPlease press Next and give your input again",
							"Error", JOptionPane.ERROR_MESSAGE, null);
				} else {
					validatedMinterm = mintermInputField.getText();
				}
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
				//converts to bits, applies quine ()
				Quine quine = new Quine();


				mintermlist = GetMintermList.getMin();

				//applyquine();.
				//iterate through minterm list --> check bits and convert to binary, add to quine, retrieve quine
				try {
					Iterator<String> mintermlistiterator = mintermlist.iterator();

					while (mintermlistiterator.hasNext() == true) {

						String mintermitem = mintermlistiterator.next();

						quine.addTerm(toBinary(MenuBar.bits,mintermitem));

						System.out.println(mintermitem);
					}

					quine.simplify();
					String temp1 = quine.toString();

					resultTextArea.setText(temp1);
				} catch (ExceptionQuine e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

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