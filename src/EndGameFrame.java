import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.*;

public class EndGameFrame extends JFrame implements ActionListener {

	private TextField nameField;
	private JButton confirmButton,exitButton;
	private JLabel scoreLabel, pictureLabel;
	private JPanel mainPanel;
	private MainWindow window;
	private ArrayList<String> moneyList;
	private int moneyEarned;
	private Stats stats;
	private GameFrame game;

	public EndGameFrame(int i, MainWindow window, int moneyEarned, Stats stats,
			GameFrame game) {
		super("GAME OVER");

		this.moneyList = new ArrayList();
		makeList();
		Collections.reverse(moneyList);

		this.moneyEarned = moneyEarned;
		this.stats = stats;
		this.game = game;

		this.window = window;
		ImageIcon save = new ImageIcon(this.getClass().getResource(
				"Images/Save.jpg"));
		nameField = new TextField("Όνομα");
		confirmButton = new JButton(save);
		confirmButton.setContentAreaFilled(false);
		confirmButton.setFocusPainted(false);
		confirmButton.setBorderPainted(false);
		scoreLabel = new JLabel(money(i));
		pictureLabel = new JLabel(new ImageIcon(this.getClass().getResource(
				"Images/back1_blur.png")));
		mainPanel = new JPanel();
		
		exitButton = new JButton(new ImageIcon(this.getClass().getResource("/Images/exit.jpg")));
		exitButton.setContentAreaFilled(false);
		exitButton.setFocusPainted(false);
		exitButton.setBorderPainted(false);
		mainPanel.setLayout(null);
		Font arial = new Font("Arial", Font.BOLD, 17);

		confirmButton.addActionListener(this);
		exitButton.addActionListener(this);
		scoreLabel.setFont(arial);
		scoreLabel.setForeground(Color.ORANGE);

		scoreLabel.setBounds(50, 50, 350, 40);
		nameField.setBounds(130, 140, 100, 25);
		pictureLabel.setBounds(0, 0, 400, 400);
		confirmButton.setBounds(130, 200, 90, 40);
		exitButton.setBounds(130, 250, 80, 35);

		mainPanel.add(scoreLabel);
		mainPanel.add(nameField);
		mainPanel.add(confirmButton);
		mainPanel.add(exitButton);
		mainPanel.add(pictureLabel);

		this.setContentPane(mainPanel);
		this.setVisible(true);
		this.setResizable(false);
		this.setSize(400, 400);
	}

	// Money that the player has earned

	private String money(int i) {
		if (i < 5) {
			return "Δυστυχώς κέρδισες 0 €";
		} else {
			return "Συγχαρητήρια! Κέρδισες " + moneyList.get(moneyEarned - 1);
		}

	}

	// Creation of the money list

	private void makeList() {
		moneyList.add("1.000.000 €");
		moneyList.add("500.000 €");
		moneyList.add("250.000 €");
		moneyList.add("125.000 €");
		moneyList.add("64.000 €");
		moneyList.add("32.000 €");
		moneyList.add("16.000 €");
		moneyList.add("8.000 €");
		moneyList.add("4.000 €");
		moneyList.add("2.000 €");
		moneyList.add("1.000 €");
		moneyList.add("0 €");
		moneyList.add("0 €");
		moneyList.add("0 €");
		moneyList.add("0 €");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == confirmButton){
			stats.addPlayer(new Player(nameField.getText(), moneyEarned,
					moneyList.get(moneyEarned - 1)));
			stats.sortList();
			serializing();
			window.setVisible(true);
			this.dispose();
			game.dispose();
		}
		else{
			window.setVisible(true);
			this.dispose();
			game.dispose();
		}

	}

	// Saves the stat object to a file

	public void serializing() {

		try {
			File outDir = new File(AddQuestionFrame.GAME_FOLDER);
			if (!outDir.exists()) {
				outDir.mkdir();
			}
			FileOutputStream fos = new FileOutputStream(outDir + File.separator
					+ "stats.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			if (stats != null)
				oos.writeObject(stats);
			oos.close();
			fos.close();
		} catch (Exception ex) {
			System.out.println("Saving not completed");

		}

	}

}
