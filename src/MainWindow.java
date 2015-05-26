import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import javax.swing.*;

public class MainWindow extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JButton startButton, settingsButton, statsButton, aboutUsButton,
			addQuestionsButton;
	private JPanel mainPanel;
	private JLabel backgroundLabel;
	private QuestionManager easy, medium, hard;
	private GameFrame game;
	private Stats stat;
	public static final String e_QUESTIONS_FILE_NAME = "e-questions.ser";
	public static final String m_QUESTIONS_FILE_NAME = "m-questions.ser";
	public static final String h_QUESTIONS_FILE_NAME = "h-questions.ser";
	public static final String Stats_FILE_NAME = "stats.ser";
	public static final String GAME_FOLDER = "Game_Data";

	public MainWindow() {

		super("Millionaire");

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		loadQuestions();
		loadSounds();
		loadStats();
		stat.checkList();
		
		ImageIcon start = new ImageIcon(this.getClass().getResource("/Images/Start.jpg"));
		ImageIcon settings = new ImageIcon(this.getClass().getResource("/Images/Settings.jpg"));
		ImageIcon about = new ImageIcon(this.getClass().getResource("/Images/About.jpg"));
		ImageIcon stats = new ImageIcon(this.getClass().getResource("/Images/Stats.jpg"));

		startButton = new JButton(start);
		settingsButton = new JButton(settings);
		statsButton = new JButton(stats);
		aboutUsButton = new JButton(about);
		addQuestionsButton = new JButton("Add questions");

		aboutUsButton.addActionListener(this);
		addQuestionsButton.addActionListener(this);
		startButton.addActionListener(this);
		statsButton.addActionListener(this);
		settingsButton.addActionListener(this);

		startButton.setBounds(160, 50, 80, 35);
		settingsButton.setBounds(160, 120, 80, 35);
		statsButton.setBounds(160, 190, 80, 35);
		aboutUsButton.setBounds(160, 260, 80, 35);
		addQuestionsButton.setBounds(160, 330, 80, 35);

		ImageIcon background = new ImageIcon(this.getClass().getResource(
				"/Images/back1.png"));
		backgroundLabel = new JLabel(background);
		backgroundLabel.setBounds(0, 0, 400, 400);

		mainPanel = new JPanel();
		mainPanel.setLayout(null);

		mainPanel.add(startButton);
		mainPanel.add(settingsButton);
		mainPanel.add(statsButton);
		mainPanel.add(aboutUsButton);
		//mainPanel.add(addQuestionsButton);
		mainPanel.add(backgroundLabel);

		AudioManager.loop("intro");

		this.setContentPane(mainPanel);
		this.setVisible(true);
		this.setSize(400, 400);
		centerFrame();
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	// Centers the frame to the screen

	public void centerFrame() {
		Dimension frameSize = this.getSize();
		GraphicsEnvironment ge = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		Point centerPoint = ge.getCenterPoint();

		int dx = centerPoint.x - frameSize.width / 2;
		int dy = centerPoint.y - frameSize.height / 2;
		this.setLocation(dx, dy);
	}

	// Initializes the AudioManaer

	private void loadSounds() {
		AudioManager.init();
	}

	// Loads the player's stats

	private void loadStats() {
		try {
			FileInputStream fileInStats = new FileInputStream(GAME_FOLDER
					+ File.separator + Stats_FILE_NAME);
			InputStream bufferIn = new BufferedInputStream(fileInStats);
			ObjectInputStream in = new ObjectInputStream(bufferIn);
			stat = (Stats) in.readObject();
			fileInStats.close();
			in.close();
		} catch (Exception e) {
			System.out.println("Cannot read file " + Stats_FILE_NAME);
		}
		if (stat == null) {
			stat = new Stats();
		}
	}

	private void loadQuestions() {

		// loading easy questions

		try {
			InputStream fileInEasy = this.getClass().getResourceAsStream(
					"/" + GAME_FOLDER + "/" + e_QUESTIONS_FILE_NAME);
			InputStream bufferIn = new BufferedInputStream(fileInEasy);
			ObjectInputStream in = new ObjectInputStream(bufferIn);
			easy = (QuestionManager) in.readObject();
			easy.Build();
			fileInEasy.close();
			in.close();
		} catch (Exception e) {
			System.out.println("Cannot read file " + e_QUESTIONS_FILE_NAME);
		}
		if (easy == null) {
			easy = new QuestionManager();
		}

		// loading Medium questions

		try {
			InputStream fileInMedium = this.getClass().getResourceAsStream(
					"/" + GAME_FOLDER + "/" + m_QUESTIONS_FILE_NAME);
			InputStream bufferIn = new BufferedInputStream(fileInMedium);
			ObjectInputStream in = new ObjectInputStream(bufferIn);
			medium = (QuestionManager) in.readObject();
			medium.Build();
			fileInMedium.close();
			in.close();
		} catch (Exception e) {
			System.out.println("Cannot read file " + m_QUESTIONS_FILE_NAME);
		}
		if (medium == null) {
			medium = new QuestionManager();
		}

		// loading hard questions

		try {
			InputStream fileInHard = this.getClass().getResourceAsStream(
					"/" + GAME_FOLDER + "/" + h_QUESTIONS_FILE_NAME);
			InputStream bufferIn = new BufferedInputStream(fileInHard);
			ObjectInputStream in = new ObjectInputStream(bufferIn);
			hard = (QuestionManager) in.readObject();
			hard.Build();
			fileInHard.close();
			in.close();
		} catch (Exception e) {
			System.out.println("Cannot read file " + h_QUESTIONS_FILE_NAME);
		}
		if (hard == null) {
			hard = new QuestionManager();
		}
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == aboutUsButton) {
			this.setVisible(false);
			new AboutUsFrame(this);
		} else if (e.getSource() == addQuestionsButton) {
			new AddQuestionFrame(easy, medium, hard);
		} else if (e.getSource() == settingsButton) {
			this.setVisible(false);
			new SettingsFrame(this, stat);
		} else if (e.getSource() == statsButton) {
			this.setVisible(false);
			new StatsFrame(this, stat);
		} else {
			AudioManager.stop("intro");
			AudioManager.play("transition");
			this.setVisible(false);
			game = new GameFrame(this, easy, medium, hard, stat);
			easy.Build();
			medium.Build();
			hard.Build();
			game.loadNextQuestion();
		}
	}

}
