import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;

import org.jfree.chart.*;
import org.jfree.chart.plot.*;
import org.jfree.data.category.*;

public class AudienceFrame extends JFrame {

	private DefaultCategoryDataset dataset;
	private ChartPanel chartPanel;
	private int[] data = null;
	private char[] alphabet = "ABΓΔ".toCharArray();
	private ArrayList<String> Answers;
	private Question question;
	private int correctAnswers;

	public AudienceFrame(Question currentQuestion, int corrAns,
			ArrayList<Integer> ans) {

		Answers = new ArrayList<>();
		this.question = currentQuestion;
		this.correctAnswers = corrAns;
		for (int answer : ans) {
			Answers.add(Character.toString(alphabet[answer]));
		}

		data = getPercentage(question, correctAnswers);

		dataset = new DefaultCategoryDataset();

		int j = 0;
		for (String answ : Answers) {
			dataset.addValue(data[j], "Series1", answ);
			j++;
		}

		JFreeChart chart = ChartFactory.createBarChart3D("Βοήθεια Κοινού", "",
				"Ποσοστό Κοινού", dataset, PlotOrientation.VERTICAL, false,
				true, false);

		chartPanel = new ChartPanel(chart);

		this.setContentPane(chartPanel);

		this.setVisible(true);
		this.setResizable(false);
		this.setSize(500, 500);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	private int[] getPercentage(Question question, int corrAnswers) {

		int[] data = null;

		if (Answers.size() == 4) {
			if (corrAnswers < 5) {
				data = percentage(question, 10, 60);

			} else if (corrAnswers < 10) {
				data = percentage(question, 17, 40);
			} else {
				data = percentage(question, 16, 35);
			}
			return data;
		}

		if (corrAnswers < 5) {
			data = percentage(question, 10, 70);

		} else if (corrAnswers < 10) {
			data = percentage(question, 17, 45);
		} else {
			data = percentage(question, 14, 40);
		}
		return data;
	}

	private int[] percentage(Question q, int refactor, int upperBound) {
		int[] data = new int[4];

		Random r = new Random();
		int corAns = r.nextInt(refactor) + upperBound;
		int sum = corAns;
		data[q.getCorrectAnswerNumber()] = corAns;
		int i = 0;
		int answerToEdit;
		int previous = -1;

		while (i < 2) {
			answerToEdit = r.nextInt(Answers.size());
			if (answerToEdit != q.getCorrectAnswerNumber()
					&& answerToEdit != previous) {
				data[answerToEdit] = r.nextInt(100 - sum);
				sum += data[answerToEdit];
				previous = answerToEdit;
				i++;
			}
		}
		for (int j = 0; j < 4; j++) {
			if (data[j] == 0) {
				data[j] = 100 - sum;
			}
		}

		return checkData(data, corAns);
	}

	private int[] checkData(int[] data, int corrPerce) {
		int max = data[0];
		int correct = corrPerce;
		for (int i = 0; i < Answers.size(); i++) {
			if (max <= data[i]) {
				max = data[i];
			}
		}
		if (max - correct > 20) {
			data = null;
			data = getPercentage(question, correctAnswers);
		}
		return data;

	}
}