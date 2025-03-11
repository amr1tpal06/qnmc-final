package qnmc;


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

	private static final long serialVersionUID = 1L;
	private JPanel panel;
	
	private JLabel minInput;
	private JTextField minIn;
	private JButton nextBt;
	
	private JTextArea resultShow;
	private JButton calBt;
	@SuppressWarnings("unused")
	private int i = 0;

	
	static public int k=0;
	static public Set<String> set;
	public String temp; 
	GetMintermList item = new GetMintermList(); 

	static public String dataThree(String input) {

		
		String bin[] = { "000", "001", "010", "011", "100", "101", "110", "111" };

		int i = Integer.parseInt(input);

	

		return bin[i];

	}

	static public String dataFour(String input) {

		String bin[] = { "0000", "0001", "0010", "0011", "0100", "0101",
				"0110", "0111", "1000", "1001", "1010", "1011", "1100", "1101",
				"1110", "1111" };

		int i = Integer.parseInt(input);

		return bin[i];

	}

	static public String dataFive(String input) {

		String bin[] = { "00000", "00001", "00010", "00011", "00100", "00101",
				"00110", "00111", "01000", "01001", "01010", "01011", "01100",
				"01101", "01110", "01111", "10000", "10001", "10010", "10011",
				"10100", "10101", "10110", "10111", "11000", "11001", "11010",
				"11011", "11100", "11101", "11110", "11111" };

		int i = Integer.parseInt(input);

		return bin[i];

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

		

		minInput = new JLabel("Enter Minterm list: ");
		minInput.setBounds(50, 100, 150, 30);
		minInput.setFont(new Font("Verdana", Font.BOLD, 14));
		panel.add(minInput);

		minIn = new JTextField();
		minIn.setBounds(50, 140, 70, 30);

		minIn.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent arg0) {

				@SuppressWarnings("unused")
				int flag = 0;
				int st = MenuBar.bits;

				System.out.println(minIn.getText());
				String tmp = minIn.getText();

				
				if (st == 3) {
					
					try {
						k = Integer.parseInt(tmp);
					} catch (NumberFormatException e) {
						k = -1;
					}

					if (k < 0 || k > 7) {
						JOptionPane.showMessageDialog(null, "Number should be within 0 to 7\nPlease press Next and give your input again",
								"Error", JOptionPane.ERROR_MESSAGE, null);
					} else
						temp = minIn.getText();
				}
				if (st == 4) {
					
					try {
						k = Integer.parseInt(tmp);
					} catch (NumberFormatException e) {
						k = -1;
					}

					if (k < 0 || k > 15) {
						JOptionPane.showMessageDialog(null, "Number should be within 0 to 15\nPlease press Next and give your input again",
								"Error", JOptionPane.ERROR_MESSAGE, null);
					} else
						temp = minIn.getText();

				}

				if (st == 5) {
					
					try {
						k = Integer.parseInt(tmp);
					} catch (NumberFormatException e) {
						k = -1;
					}

					if (k < 0 || k > 31) {
						JOptionPane.showMessageDialog(null, "Number should be within 0 to 31\nPlease press Next and give your input again",
								"Error", JOptionPane.ERROR_MESSAGE, null);
					} else
						temp = minIn.getText();

				}

			}

			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
		panel.add(minIn);

		nextBt = new JButton("Next");
		nextBt.setBounds(140, 140, 70, 30);
		nextBt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				minIn.setText("");
				item.setMinList(temp);

				
			}
		});
		panel.add(nextBt);

		
		resultShow = new JTextArea();
		resultShow.setBounds(50, 200, 300, 200);
		resultShow.setEditable(false);
		panel.add(resultShow);

		calBt = new JButton("Calculate");
		calBt.setBounds(400, 250, 100, 50);
		calBt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				Quine quine = new Quine();

				
				set = GetMintermList.getMin();
				@SuppressWarnings("unused")
				int len = set.size();
				try {
					Iterator<String> it = set.iterator();

					while (it.hasNext() == true) {

						String str = it.next();

						if (MenuBar.bits == 3)
							quine.addTerm(dataThree(str));
						else if (MenuBar.bits == 4)
							quine.addTerm(dataFour(str));
						else if (MenuBar.bits == 5)
							quine.addTerm(dataFive(str));

						System.out.println(str);
					}

					
					quine.simplify();
					String temp1 = quine.toString();
					
					resultShow.setText(temp1);
				} catch (ExceptionQuine e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		panel.add(calBt);

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
		
		
		
		String s = JOptionPane
				.showInputDialog("Enter the boolean bits(3 to 5): ");
		try {
			MenuBar.bits= Integer.parseInt(s);
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
