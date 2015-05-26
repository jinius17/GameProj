import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import javax.swing.*;

public class AddQuestionFrame extends JFrame implements ActionListener {
	private static final long serialVersionUID = -2113596838501363996L;

	private JTextField question, ans1, ans2, ans3, ans4;
	private JRadioButton[] radioButtons, questionButton;
	private ButtonGroup group1, group2;
	private JButton addQuestionButton, saveQuestionButton;
	private JPanel questionPanel, masterPanel, questionTypePanel;
	private QuestionManager easyQuestionManager, mediumQuestionManager,
			hardQuestionManager;
	public static final String e_QUESTIONS_FILE_NAME = "e-questions.ser";
	public static final String m_QUESTIONS_FILE_NAME = "m-questions.ser";
	public static final String h_QUESTIONS_FILE_NAME = "h-questions.ser";
	public static final String GAME_FOLDER = "Game_Data";

	public AddQuestionFrame(QuestionManager eQuestionManager,
			QuestionManager mQuestionManager, QuestionManager hQuestionManager) {
		super("Add Questions");

		this.easyQuestionManager = eQuestionManager;
		this.mediumQuestionManager = mQuestionManager;
		this.hardQuestionManager = hQuestionManager;

		questionTypePanel = new JPanel();
		questionPanel = new JPanel();
		masterPanel = new JPanel(new BorderLayout());

		addQuestionButton = new JButton("Make Question");
		saveQuestionButton = new JButton("Save Questions to file");
		addQuestionButton.addActionListener(this);
		saveQuestionButton.addActionListener(this);

		question = new JTextField(50);
		ans1 = new JTextField(50);
		ans2 = new JTextField(50);
		ans3 = new JTextField(50);
		ans4 = new JTextField(50);

		group1 = new ButtonGroup();
		group2 = new ButtonGroup();
		radioButtons = new JRadioButton[4];
		questionButton = new JRadioButton[3];
		for (int i = 0; i < 4; i++) {
			radioButtons[i] = new JRadioButton("Answer " + (i + 1));
			group1.add(radioButtons[i]);
		}
		for (int i = 0; i < 3; i++) {
			questionButton[i] = new JRadioButton(" ");
			group2.add(questionButton[i]);
		}

		questionTypePanel.add(new JLabel("Easy"));
		questionTypePanel.add(questionButton[0]);
		questionTypePanel.add(new JLabel("Medium"));
		questionTypePanel.add(questionButton[1]);
		questionTypePanel.add(new JLabel("Hard"));
		questionTypePanel.add(questionButton[2]);

		questionPanel.add(new JLabel("Question: "));
		questionPanel.add(question);
		questionPanel.add(radioButtons[0]);
		questionPanel.add(ans1);
		questionPanel.add(radioButtons[1]);
		questionPanel.add(ans2);
		questionPanel.add(radioButtons[2]);
		questionPanel.add(ans3);
		questionPanel.add(radioButtons[3]);
		questionPanel.add(ans4);
		questionPanel.add(addQuestionButton);
		questionPanel.add(saveQuestionButton);

		masterPanel.add(questionTypePanel, BorderLayout.NORTH);
		masterPanel.add(questionPanel, BorderLayout.CENTER);

		this.setContentPane(masterPanel);
		this.setSize(750, 300);
		this.setVisible(true);
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);

	}

	// Clears the fields

	public void clearFields() {
		question.setText("");
		ans1.setText("");
		ans2.setText("");
		ans3.setText("");
		ans4.setText("");
		group1.clearSelection();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == addQuestionButton) {
			String q = question.getText();
			String a1 = ans1.getText();
			String a2 = ans2.getText();
			String a3 = ans3.getText();
			String a4 = ans4.getText();
			int correctAns = -1;
			for (int i = 0; i < 4; i++) {
				if (radioButtons[i].isSelected()) {
					correctAns = i;
					break;
				}
			}
			if (correctAns == -1) {
				JOptionPane.showMessageDialog(this, "Please select the"
						+ " correct answer");
			} else {
				if (questionButton[0].isSelected()) {
					Question question = new Question(q, a1, a2, a3, a4,
							correctAns);
					easyQuestionManager.addQuestion(question);
					clearFields();
				} else if (questionButton[1].isSelected()) {
					Question question = new Question(q, a1, a2, a3, a4,
							correctAns);
					mediumQuestionManager.addQuestion(question);
					clearFields();
				} else if (questionButton[2].isSelected()) {
					Question question = new Question(q, a1, a2, a3, a4,
							correctAns);
					hardQuestionManager.addQuestion(question);
					clearFields();
				} else {
					JOptionPane.showMessageDialog(this,
							"Please select type of question");
				}

			}
		} else {
			try {
				File outDir = new File(AddQuestionFrame.GAME_FOLDER);
				if (!outDir.exists()) {
					outDir.mkdir();
				}

				FileOutputStream fos = new FileOutputStream(outDir
						+ File.separator + e_QUESTIONS_FILE_NAME);
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				if (easyQuestionManager != null)
					oos.writeObject(easyQuestionManager);
				oos.close();
				fos.close();
			} catch (Exception ex) {

			}

			try {
				File outDir = new File(AddQuestionFrame.GAME_FOLDER);
				if (!outDir.exists()) {
					outDir.mkdir();
				}
				FileOutputStream fos = new FileOutputStream(outDir
						+ File.separator + m_QUESTIONS_FILE_NAME);
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				if (mediumQuestionManager != null)
					oos.writeObject(mediumQuestionManager);
				oos.close();
				fos.close();
			} catch (Exception ex) {

			}

			try {
				File outDir = new File(AddQuestionFrame.GAME_FOLDER);
				if (!outDir.exists()) {
					outDir.mkdir();
				}
				FileOutputStream fos = new FileOutputStream(outDir
						+ File.separator + h_QUESTIONS_FILE_NAME);
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				if (hardQuestionManager != null)
					oos.writeObject(hardQuestionManager);
				oos.close();
				fos.close();
			} catch (Exception ex) {

			}

		}

	}
}
