package qnmc;

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


public class GUI extends JFrame implements Observer {

	private static JTextField mintermInputField;
	private final JButton nextButton;
	private static JTextArea resultTextArea;
	private final JButton calculateButton;
	private Controller controller;

    public static int minterm =0;
	public static Set<String> mintermlist;

	public void setNextButtonActionListener(){
		nextButton.addActionListener(e-> controller.handleNextButton());
	}

	public void setCalculateButtonListener(){
		calculateButton.addActionListener(e -> controller.handleCalculateButton());
	}

	public void setMintermInputFieldListener(){
		mintermInputField.addKeyListener(new KeyAdapter() { //KeyAdapter to override only the methods you care about (in this case, keyReleased)
			@Override
			public void keyReleased(KeyEvent e) {
				controller.handleMintermInputField(mintermInputField);
			}
		});
	}

	public String getMintermInput() {
		return mintermInputField.getText();
	}

	public static void setInputField(){
		mintermInputField.setText("");
	}

	public static void updateResultArea(String result){
		resultTextArea.setText(result);
	}


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
	public static void setLookAndFeel(){
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
	}

	public static void main(String[] args) {
		setLookAndFeel();

		String bitInput = JOptionPane
				.showInputDialog(UILabels.BITS_INPUT_DIALOG);

		MintermValidation.validateBits(bitInput);

		GUI gui = new GUI();
		Controller controller = new Controller(gui);
		gui.setController(controller);
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void setController(Controller controller){
		this.controller=controller;

	}

	@Override
	public void update(String result) {
		resultTextArea.setText(result);
	}
}


