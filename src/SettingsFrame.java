import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.*;

public class SettingsFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JLabel soundLabel, backgroundLabel;
	private JButton eraseHighScoresButton, backButton;
	private JCheckBox soundControl;
	private JPanel mainPanel;
	private MainWindow window;
	public static final String Stats_FILE_NAME = "stats.ser";
	public static final String GAME_FOLDER = "Game_Data";
	private Stats stat;

	public SettingsFrame(MainWindow window, Stats stat) {

		super("Settings");

		soundLabel = new JLabel("Ήχοι:");
		backgroundLabel = new JLabel(new ImageIcon(this.getClass().getResource(
				"Images/back1_blur.png")));
		ImageIcon back = new ImageIcon(this.getClass().getResource(
				"Images/Back.jpg"));

		this.window = window;
		this.stat = stat;

		eraseHighScoresButton = new JButton(new ImageIcon(this.getClass().getResource("/Images/Delete_Stat.jpg")));
		eraseHighScoresButton.addActionListener(this);
		eraseHighScoresButton.setContentAreaFilled(false);
		eraseHighScoresButton.setFocusPainted(false);
		eraseHighScoresButton.setBorderPainted(false);

		backButton = new JButton(back);
		backButton.addActionListener(this);

		soundControl = new JCheckBox();
		if (AudioManager.isMute() == false) {
			soundControl.setSelected(true);
		}
		soundControl.addActionListener(this);

		Font arial = new Font("Arial", Font.BOLD, 25);

		mainPanel = new JPanel();
		mainPanel.setLayout(null);

		soundLabel.setFont(arial);
		soundLabel.setForeground(Color.BLACK);

		soundLabel.setBounds(80, 100, 80, 20);
		soundControl.setBounds(180, 100, 25, 20);
		eraseHighScoresButton.setBounds(80, 150, 80, 35);
		backgroundLabel.setBounds(0, 0, 400, 400);
		backButton.setBounds(60, 300, 50, 20);

		mainPanel.add(eraseHighScoresButton);
		mainPanel.add(soundLabel);
		mainPanel.add(soundControl);
		mainPanel.add(backButton);
		mainPanel.add(backgroundLabel);

		this.setContentPane(mainPanel);
		this.setVisible(true);
		centerFrame();
		this.setSize(400, 400);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == soundControl) {
			if (soundControl.isSelected()) {
				AudioManager.setMute(false);
				AudioManager.loop("intro");
			} else {
				AudioManager.setMute(true);
				AudioManager.stop("intro");
			}
		} else if (e.getSource() == backButton) {
			this.dispose();
			window.setVisible(true);
		} else {
			stat.eraseList();
			serializing();

		}
	}

	public void serializing() {

		try {
			File outDir = new File(AddQuestionFrame.GAME_FOLDER);
			if (!outDir.exists()) {
				outDir.mkdir();
			}
			FileOutputStream fos = new FileOutputStream(outDir + File.separator
					+ "stats.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			if (stat != null)
				oos.writeObject(stat);
			oos.close();
			fos.close();
		} catch (Exception ex) {
			System.out.println("Saving not completed");

		}

	}

}
