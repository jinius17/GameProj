import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.Timer;

import javax.swing.*;

public class GameFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private static final int CALL_CORRECT = 1;
	private static final int CALL_REFRESH = 2;
	private static final int CALL_FIND_CORRECT_ANSWER = 3;
	private static final int CALL_WRONG = 4;

	private JButton help50, audienceHelp, telephoneHelp;
	private JButton answerIcon1, answerIcon2, answerIcon3, answerIcon4;
	private JLabel ans1, ans2, ans3, ans4, countdownLabel, hostLabel,
			backgroundLabel, questionIcon, questionArea;
	private JPanel mainPanel;
	private String correctSound, waitingSound;
	private ArrayList<String> moneyList;
	private ArrayList<Integer> availableAnswers;
	private JList winList;
	private MainWindow window;
	private QuestionManager easy, medium, hard;
	private Question currentQuestion;
	private int correctAnswers, numberOfCorrectAnswer, wantToproceed,
			timeOfDelay, timeOfTimer, timeOfTransition;
	private Timer gameTimer, answerTimer;
	private Stats stat;

	public GameFrame(MainWindow window, QuestionManager e_manager,
			QuestionManager m_manager, QuestionManager h_manager, Stats stat) {
		super("Who Wants to be a Millionaire");

		// Font f = new Font("Lucida Bright", Font.BOLD, 20);
		Font arial = new Font("Arial", Font.BOLD, 20);
		Font answerFont = new Font("Arial", Font.PLAIN, 15);
		Font questionFont = new Font("Arial", Font.PLAIN, 20);

		this.window = window;
		this.easy = e_manager;
		this.medium = m_manager;
		this.hard = h_manager;
		this.stat = stat;
		this.correctAnswers = 0;

		mainPanel = new JPanel();
		mainPanel.setLayout(null);

		moneyList = new ArrayList<String>();
		availableAnswers = new ArrayList<Integer>();
		for (int i = 0; i < 4; i++) {
			availableAnswers.add(i);
		}
		makeList();

		this.timeOfDelay = 3000;
		this.timeOfTimer = 15000;
		this.timeOfTransition = 2500;

		winList = new JList(moneyList.toArray());
		winList.setCellRenderer(new MyListCellRenderer(this));
		winList.setFont(arial);
		winList.setForeground(new Color(255, 128, 0));
		winList.setOpaque(false);
		winList.setBackground(new Color(0, 0, 0, 0));
		winList.setSize(130, 400);
		winList.setBounds(640, 37, 160, 400);
		winList.setSelectionModel(new MyListSelectionModel());

		help50 = new JButton();
		audienceHelp = new JButton();
		telephoneHelp = new JButton();

		telephoneHelp.addActionListener(this);
		audienceHelp.addActionListener(this);
		help50.addActionListener(this);

		help50.setBounds(37, 54, 120, 67);
		audienceHelp.setBounds(37, 129, 120, 67);
		telephoneHelp.setBounds(37, 203, 120, 67);

		ImageIcon imageLeft = new ImageIcon(this.getClass().getResource(
				"/Images/answers_left.png"));
		ImageIcon imageRight = new ImageIcon(this.getClass().getResource(
				"/Images/answers_right.png"));
		ImageIcon questionImage = new ImageIcon(this.getClass().getResource(
				"/Images/question.png"));
		ImageIcon help50Icon = new ImageIcon(this.getClass().getResource(
				"/Images/50Help.png"));
		ImageIcon telephoneHelpIcon = new ImageIcon(this.getClass()
				.getResource("/Images/telephoneHelp.png"));
		ImageIcon audienceHelpIcon = new ImageIcon(this.getClass().getResource(
				"/Images/audienceHelp.png"));
		ImageIcon hostIcon = new ImageIcon(this.getClass().getResource(
				"/Images/host.png"));
		ImageIcon background = new ImageIcon(this.getClass().getResource(
				"/Images/background.png"));

		ans1 = new JLabel();
		ans2 = new JLabel();
		ans3 = new JLabel();
		ans4 = new JLabel();
		questionIcon = new JLabel(questionImage);
		questionArea = new JLabel();

		answerIcon1 = new JButton();
		answerIcon2 = new JButton();
		answerIcon3 = new JButton();
		answerIcon4 = new JButton();

		help50.setIcon(help50Icon);
		audienceHelp.setIcon(audienceHelpIcon);
		telephoneHelp.setIcon(telephoneHelpIcon);

		help50.setContentAreaFilled(false);
		help50.setFocusPainted(false);
		help50.setBorderPainted(false);
		audienceHelp.setContentAreaFilled(false);
		audienceHelp.setFocusPainted(false);
		audienceHelp.setBorderPainted(false);
		telephoneHelp.setContentAreaFilled(false);
		telephoneHelp.setFocusPainted(false);
		telephoneHelp.setBorderPainted(false);

		answerIcon1.setIcon(imageLeft);
		answerIcon2.setIcon(imageRight);
		answerIcon3.setIcon(imageLeft);
		answerIcon4.setIcon(imageRight);

		answerIcon1.setContentAreaFilled(false);
		answerIcon1.setFocusPainted(false);
		answerIcon1.setBorderPainted(false);
		answerIcon2.setContentAreaFilled(false);
		answerIcon2.setFocusPainted(false);
		answerIcon2.setBorderPainted(false);
		answerIcon3.setContentAreaFilled(false);
		answerIcon3.setFocusPainted(false);
		answerIcon3.setBorderPainted(false);
		answerIcon4.setContentAreaFilled(false);
		answerIcon4.setFocusPainted(false);
		answerIcon4.setBorderPainted(false);

		questionIcon.setBounds(0, 435, 799, 138);
		answerIcon1.setBounds(0, 575, 400, 96);
		answerIcon2.setBounds(400, 575, 400, 96);
		answerIcon3.setBounds(0, 678, 400, 96);
		answerIcon4.setBounds(400, 678, 400, 96);

		questionArea.setBounds(118, 456, 575, 90);
		ans1.setBounds(125, 587, 255, 60);
		ans2.setBounds(441, 587, 255, 60);
		ans3.setBounds(125, 697, 255, 60);
		ans4.setBounds(441, 697, 255, 60);

		answerIcon1.addActionListener(this);
		answerIcon2.addActionListener(this);
		answerIcon3.addActionListener(this);
		answerIcon4.addActionListener(this);

		questionArea.setForeground(Color.WHITE);
		ans1.setForeground(Color.WHITE);
		ans2.setForeground(Color.WHITE);
		ans3.setForeground(Color.WHITE);
		ans4.setForeground(Color.WHITE);
		questionArea.setFont(questionFont);
		ans1.setFont(answerFont);
		ans2.setFont(answerFont);
		ans3.setFont(answerFont);
		ans4.setFont(answerFont);

		countdownLabel = new JLabel();
		countdownLabel.setBounds(37, 280, 105, 105);

		hostLabel = new JLabel(hostIcon);
		hostLabel.setBounds(290, 80, 231, 308);

		backgroundLabel = new JLabel(background);
		backgroundLabel.setBounds(0, 0, 800, 800);

		correctSound = "correct";
		waitingSound = "waiting";

		mainPanel.add(help50);
		mainPanel.add(audienceHelp);
		mainPanel.add(telephoneHelp);
		mainPanel.add(questionArea);
		mainPanel.add(ans1);
		mainPanel.add(ans2);
		mainPanel.add(ans3);
		mainPanel.add(ans4);
		mainPanel.add(answerIcon1);
		mainPanel.add(answerIcon2);
		mainPanel.add(answerIcon3);
		mainPanel.add(answerIcon4);
		mainPanel.add(questionIcon);
		mainPanel.add(countdownLabel);
		mainPanel.add(hostLabel);
		mainPanel.add(winList);
		mainPanel.add(backgroundLabel);

		this.setContentPane(mainPanel);
		this.setSize(800, 800);
		this.setVisible(true);
		this.setResizable(false);
		this.setLocationRelativeTo(window);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	// Loads the next question

	public void loadNextQuestion() {

		setButtonsVisible();

		if (correctAnswers != 0) {
			timeOfTransition = 1800;
		}

		if (correctAnswers != 15) {
			if (correctAnswers < 5) {
				setAnswersToLabels(easy, timeOfTransition, waitingSound);
			} else if (correctAnswers < 10) {
				if (wantToContinue()) {
					setSounds();
					if (correctAnswers == 5) {
						AudioManager.stop(waitingSound);
						AudioManager.play("transition");
					}
					timeOfDelay += 600;
					timeOfTimer += 15000;
					setAnswersToLabels(medium, timeOfTransition, waitingSound);
				} else {
					new EndGameFrame(correctAnswers, window, numberOfMoney(),
							stat, this);
					stopTimer();
				}
			} else {
				if (wantToContinue()) {
					setSounds();
					if (correctAnswers == 10) {
						AudioManager.stop(waitingSound);
						AudioManager.play("transition");
					}
					timeOfDelay += 700;
					timeOfTimer += 15000;
					setAnswersToLabels(hard, timeOfTransition, waitingSound);
				} else {
					new EndGameFrame(correctAnswers, window, numberOfMoney(),
							stat, this);
					stopTimer();
				}
			}
		} else {
			new EndGameFrame(correctAnswers, window, numberOfMoney(), stat,
					this);
			stopTimer();
		}

	}

	// Sets the values of the sounds

	private void setSounds() {
		if (correctAnswers < 10) {
			correctSound = "correct2";
			waitingSound = "waiting2";
		} else {
			waitingSound = "waiting3";
			if (correctAnswers == 14) {
				correctSound = "correct_f";
			} else {
				correctSound = "correct3";
			}
		}
	}

	// Creation of money list

	private void makeList() {
		moneyList.add("15. 1.000.000 €");
		moneyList.add("14.   500.000 €");
		moneyList.add("13.   250.000 €");
		moneyList.add("12.   125.000 €");
		moneyList.add("11.    64.000 €");
		moneyList.add("10.    32.000 €");
		moneyList.add(" 9.    16.000 €");
		moneyList.add(" 8.     8.000 €");
		moneyList.add(" 7.     4.000 €");
		moneyList.add(" 6.     2.000 €");
		moneyList.add(" 5.     1.000 €");
		moneyList.add(" 4.       500 €");
		moneyList.add(" 3.       300 €");
		moneyList.add(" 2.       200 €");
		moneyList.add(" 1.       100 €");
	}

	// sets the pressed button orange

	private void pending(JButton button, int i) {
		if (i == 0) {
			button.setIcon(new ImageIcon(this.getClass().getResource(
					"/Images/pending_answers_left.png")));
		} else if (i == 1) {
			button.setIcon(new ImageIcon(this.getClass().getResource(
					"/Images/pending_answers_right.png")));
		}

	}

	// Sets the button green

	private void correct(JButton button, int i) {
		if (i == 0) {
			button.setIcon(new ImageIcon(this.getClass().getResource(
					"/Images/correct_answers_left.png")));
		} else if (i == 1) {
			button.setIcon(new ImageIcon(this.getClass().getResource(
					"/Images/correct_answers_right.png")));
		}
	}

	// Refreshes the images of the buttons

	private void refresh(JButton button, int i) {

		if (i == 0) {
			button.setIcon(new ImageIcon(this.getClass().getResource(
					"/Images/answers_left.png")));
		} else if (i == 1) {
			button.setIcon(new ImageIcon(this.getClass().getResource(
					"/Images/answers_right.png")));
		}
		refreshText();
	}

	// Refreshes the text of the labels

	private void refreshText() {
		questionArea.setText("");
		ans1.setText("Α.  ");
		ans2.setText("Β.  ");
		ans3.setText("Γ.  ");
		ans4.setText("Δ.  ");
	}

	// Manages the answer by the player

	private void setColoredAnswer(int call_method, JButton answerButton,
			int time, int i) {
		final JButton button = answerButton;
		final int index = i;
		final int method = call_method;
		answerTimer = new Timer();
		answerTimer.schedule(new TimerTask() {

			@Override
			public void run() {
				AudioManager.stop(waitingSound);
				if (method == GameFrame.CALL_CORRECT) {
					correct(button, index);
					correctAnswers++;
					winList.repaint();
					winList.revalidate();
					AudioManager.play(correctSound);
				} else if (method == GameFrame.CALL_REFRESH) {
					refresh(button, index);
					setHelpActive();
					loadNextQuestion();
					setAnswersActive();
				} else if (method == GameFrame.CALL_FIND_CORRECT_ANSWER) {
					findCorrectAnswer(index);
					AudioManager.play("false");
				} else if (method == GameFrame.CALL_WRONG) {
					wrong();
				}

			}
		}, time);
	}

	// Finds the correct answer

	private void findCorrectAnswer(int i) {
		switch (i) {
		case 0:
			correct(answerIcon1, 0);
			break;
		case 1:
			correct(answerIcon2, 1);
			break;
		case 2:
			correct(answerIcon3, 0);
			break;
		default:
			correct(answerIcon4, 1);
			break;
		}
	}

	// Wrong answer by the player

	private void wrong() {
		findCorrectAnswer(currentQuestion.getCorrectAnswerNumber());
		countdownLabel.setIcon(new ImageIcon(this.getClass().getResource(
				"/Images/00.png")));
		new EndGameFrame(correctAnswers, window, numberOfMoney(), stat, this);
	}

	// Money that a player has won

	public int numberOfMoney() {
		if (wantToproceed == 0 && correctAnswers < 5) {
			return 1;
		} else if (wantToproceed == 1) {
			return correctAnswers;
		} else {
			return lastCushion();
		}
	}

	// Last cushion a player has

	private int lastCushion() {
		if (correctAnswers < 5) {
			return 0;
		} else if (correctAnswers < 10) {
			return 5;
		} else if (correctAnswers < 15) {
			return 10;
		} else {
			return 15;
		}
	}

	// 50-50 help

	private void fiftyHelp() {
		int correctAnswer = currentQuestion.getCorrectAnswerNumber();
		int answerToDelete;
		int deleted = 0;
		int previousItem = -1;
		// availableAnswers.clear();

		Random r = new Random();

		while (deleted < 2) {
			answerToDelete = r.nextInt(4);
			if (answerToDelete != correctAnswer
					&& answerToDelete != previousItem) {
				if (answerToDelete == 0) {
					answerIcon1.setVisible(false);
					ans1.setVisible(false);
				} else if (answerToDelete == 1) {
					answerIcon2.setVisible(false);
					ans2.setVisible(false);
				} else if (answerToDelete == 2) {
					answerIcon3.setVisible(false);
					ans3.setVisible(false);
				} else if (answerToDelete == 3) {
					answerIcon4.setVisible(false);
					ans4.setVisible(false);
				}
				previousItem = answerToDelete;
				deleted++;
				for (int i = 0; i < availableAnswers.size(); i++) {
					if (availableAnswers.get(i) == answerToDelete) {
						availableAnswers.remove(i);
					}
				}
			}
		}
		AudioManager.play("50");
	}

	// Number of correct answers

	public int getCorrectAnswers() {
		return correctAnswers;
	}

	// Activates the buttons

	private void setAnswersActive() {
		answerIcon1.addActionListener(this);
		answerIcon2.addActionListener(this);
		answerIcon3.addActionListener(this);
		answerIcon4.addActionListener(this);

	}

	// Deactivates the buttons

	private void deactivateAnswerButtons() {
		answerIcon1.removeActionListener(this);
		answerIcon2.removeActionListener(this);
		answerIcon3.removeActionListener(this);
		answerIcon4.removeActionListener(this);

	}

	// Activates the help buttons

	private void setHelpActive() {
		telephoneHelp.addActionListener(this);
		help50.addActionListener(this);
		audienceHelp.addActionListener(this);
	}

	// Deactivate the help buttons

	private void deactivateHelp() {
		telephoneHelp.removeActionListener(this);
		help50.removeActionListener(this);
		audienceHelp.removeActionListener(this);
	}

	// Starts the timer

	private void starTimer() {
		countdownLabel.setVisible(true);
		countdownLabel.setIcon(new ImageIcon(this.getClass().getResource(
				"/Images/timer.gif")));
	}

	// Stops the timer

	private void stopTimer() {
		countdownLabel.setVisible(false);
		if (gameTimer != null) {
			gameTimer.cancel();
			gameTimer = null;
		}
	}

	// Checks if the timer is over

	private void checkTime() {
		gameTimer = new Timer();
		gameTimer.schedule(new TimerTask() {

			@Override
			public void run() {
				deactivateAnswerButtons();
				wrong();
			}
		}, 30000);
	}

	// Asks the player if he wants to continue or leave

	private boolean wantToContinue() {
		wantToproceed = JOptionPane.showConfirmDialog(this,
				"Θα ήθελες να συνεχίσεις;", null, JOptionPane.YES_NO_OPTION);

		if (wantToproceed == 0) {
			return true;
		} else {
			return false;
		}

	}

	// Sets the buttons visible to the player

	private void setButtonsVisible() {
		answerIcon1.setVisible(true);
		ans1.setVisible(true);
		answerIcon2.setVisible(true);
		ans2.setVisible(true);
		answerIcon3.setVisible(true);
		ans3.setVisible(true);
		answerIcon4.setVisible(true);
		ans4.setVisible(true);
	}

	// Sets the text for the question and the answers

	private void setAnswersToLabels(QuestionManager manager, int time,
			String soundName1) {
		Timer questionTimer = new Timer();
		currentQuestion = manager.getQuestion();
		questionArea.setText("<html>" + currentQuestion.getQuestion()
				+ "</html>");
		final String soundName = soundName1;
		questionTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				ans1.setText("<html>" + "A.  " + currentQuestion.getAnswer(0)
						+ "</html");
				ans2.setText("<html>" + "Β.  " + currentQuestion.getAnswer(1)
						+ "</html");
				ans3.setText("<html>" + "Γ.  " + currentQuestion.getAnswer(2)
						+ "</html");
				ans4.setText("<html>" + "Δ.  " + currentQuestion.getAnswer(3)
						+ "</html");
				starTimer();
				checkTime();
				AudioManager.loop(soundName);
				// setAnswersActive();
			}
		}, time);
	}

	// The player has selected the correct answer

	private void correctAnswerSelected(JButton button, int side) {

		setColoredAnswer(GameFrame.CALL_CORRECT, button, timeOfDelay, side);
		setColoredAnswer(GameFrame.CALL_REFRESH, button, timeOfDelay + 2000,
				side);
	}

	// The player has selected a wrong answer

	private void wrongAnswerSelected(JButton button) {
		numberOfCorrectAnswer = currentQuestion.getCorrectAnswerNumber();
		setColoredAnswer(GameFrame.CALL_FIND_CORRECT_ANSWER, answerIcon1,
				timeOfDelay, numberOfCorrectAnswer);
		setColoredAnswer(GameFrame.CALL_WRONG, button, timeOfDelay + 3000,
				numberOfCorrectAnswer);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == telephoneHelp) {
			new CallHelpFrame(currentQuestion, this, correctAnswers,
					availableAnswers);
			telephoneHelp.setEnabled(false);
		} else if (e.getSource() == help50) {
			fiftyHelp();
			help50.setEnabled(false);
		} else if (e.getSource() == audienceHelp) {
			new AudienceFrame(currentQuestion, correctAnswers, availableAnswers);
			audienceHelp.setEnabled(false);
		} else if (e.getSource() == answerIcon1) {
			stopTimer();
			deactivateAnswerButtons();
			deactivateHelp();
			pending(answerIcon1, 0);
			if (currentQuestion.getCorrectAnswerNumber() == 0) {
				correctAnswerSelected(answerIcon1, 0);
			} else {
				wrongAnswerSelected(answerIcon1);
			}
		} else if (e.getSource() == answerIcon2) {
			stopTimer();
			deactivateAnswerButtons();
			deactivateHelp();
			pending(answerIcon2, 1);
			if (currentQuestion.getCorrectAnswerNumber() == 1) {
				correctAnswerSelected(answerIcon2, 1);
			} else {
				wrongAnswerSelected(answerIcon2);
			}
		} else if (e.getSource() == answerIcon3) {
			stopTimer();
			deactivateAnswerButtons();
			deactivateHelp();
			pending(answerIcon3, 0);
			if (currentQuestion.getCorrectAnswerNumber() == 2) {
				correctAnswerSelected(answerIcon3, 0);
			} else {
				wrongAnswerSelected(answerIcon3);
			}
		} else if (e.getSource() == answerIcon4) {
			stopTimer();
			deactivateAnswerButtons();
			deactivateHelp();
			pending(answerIcon4, 1);
			if (currentQuestion.getCorrectAnswerNumber() == 3) {
				correctAnswerSelected(answerIcon4, 1);
			} else {
				wrongAnswerSelected(answerIcon4);
			}
		}
	}

}
