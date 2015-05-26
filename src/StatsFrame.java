import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import javax.swing.*;

public class StatsFrame extends JFrame implements ActionListener {

	private ArrayList<JLabel> nameLabel, scoreLabel, posLabel;
	private JPanel mainPanel, picturePanel;
	private JButton backButton;
	private JLabel pictureLabel;
	private MainWindow wind;
	private Stats stat;
	public static final String Stats_FILE_NAME = "stats.ser";
	public static final String GAME_FOLDER = "Game_Data";

	public StatsFrame(MainWindow wind, Stats stat) {
		super("Stats");

		// loadStats();
		this.stat = stat;
		this.wind = wind;
		mainPanel = new JPanel();
		mainPanel.setLayout(null);
		picturePanel = new JPanel();
		ImageIcon back = new ImageIcon(this.getClass().getResource(
				"Images/Back.jpg"));
		backButton = new JButton(back);
		backButton.addActionListener(this);
		backButton.setContentAreaFilled(false);
		backButton.setFocusPainted(false);
		backButton.setBorderPainted(false);

		pictureLabel = new JLabel(new ImageIcon(this.getClass().getResource(
				"Images/back1_blur.png")));

		Font arial = new Font("Arial", Font.BOLD, 15);

		nameLabel = new ArrayList<JLabel>();
		scoreLabel = new ArrayList<JLabel>();
		posLabel = new ArrayList<JLabel>();

		for (int i = 0; i < 10; i++) {
			nameLabel.add(new JLabel());
			scoreLabel.add(new JLabel());
			posLabel.add(new JLabel());
		}

		this.stat.checkList();
		this.stat.sortList();

		for (int i = 0; i < 10; i++) {
			posLabel.get(i).setText(i + 1 + ". ");
			scoreLabel.get(i).setText(" " + stat.sendPlayer(i).getMoney());
			nameLabel.get(i).setText(" " + stat.sendPlayer(i).getName());
			posLabel.get(i).setFont(arial);
			scoreLabel.get(i).setFont(arial);
			nameLabel.get(i).setFont(arial);
			posLabel.get(i).setForeground(Color.ORANGE);
			nameLabel.get(i).setForeground(Color.ORANGE);
			scoreLabel.get(i).setForeground(Color.ORANGE);
		}

		int y = 20;

		for (int i = 0; i < 10; i++) {
			posLabel.get(i).setBounds(20, y, 25, 20);
			scoreLabel.get(i).setBounds(300, y, 80, 20);
			nameLabel.get(i).setBounds(130, y, 250, 21);
			y += 30;
		}

		backButton.setBounds(35, 330, 89, 40);
		pictureLabel.setBounds(0, 0, 400, 400);

		for (int i = 0; i < 10; i++) {
			mainPanel.add(posLabel.get(i));
			mainPanel.add(nameLabel.get(i));
			mainPanel.add(scoreLabel.get(i));
		}

		mainPanel.add(backButton);
		mainPanel.add(pictureLabel);

		this.setContentPane(mainPanel);
		this.setVisible(true);
		centerFrame();
		this.setSize(400, 400);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public void centerFrame() {
		Dimension frameSize = this.getSize();
		GraphicsEnvironment ge = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		Point centerPoint = ge.getCenterPoint();

		int dx = centerPoint.x - frameSize.width / 2;
		int dy = centerPoint.y - frameSize.height / 2;
		this.setLocation(dx, dy);
	}

	public void actionPerformed(ActionEvent e) {
		this.dispose();
		wind.setVisible(true);
	}

}
