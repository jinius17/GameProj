import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;

public class QuestionManager implements Serializable {

	private static final long serialVersionUID = 1L;

	private ArrayList<Question> questionList;
	private ArrayList<Integer> questionsPassed;

	public QuestionManager() {
		questionList = new ArrayList<Question>();
		Build();
	}

	public void addQuestion(Question q) {
		questionList.add(q);
	}

	// Sends a question

	public Question getQuestion() {
		Question dummy = new Question("Test Question", "A ", " B", "C ", "D ",
				0);
		if (questionList.size() == 0) {
			return dummy;
		} else {
			Random r = new Random();
			int index = r.nextInt(questionList.size());
			if (questionsPassed.size() == 0) {
				questionsPassed.add(index);
				return questionList.get(index);
			} else {
				while (questionsPassed.contains(index)
						&& questionsPassed.size() != questionList.size()) {
					r = new Random();
					index = r.nextInt(questionList.size());
				}
				if (questionsPassed.contains(index) == false) {
					questionsPassed.add(index);
					return questionList.get(index);
				} else if (questionsPassed.size() == questionList.size()) {
					// JOptionPane.showMessageDialog(null, "Out of questions");
				}
			}
			return dummy;

		}

	}

	// Αρχικοποίηση της λίστας με τις ερωτήσεις που έχουν περάσει

	public void Build() {
		questionsPassed = new ArrayList<Integer>();
	}

	public int sizeOf() {
		return questionList.size();
	}

	public String sendQuestions(int i) {
		return questionList.get(i).getQuestion();
	}

	public String sendAnswer(int i, int index) {
		return questionList.get(i).getAnswer(index);
	}

}
