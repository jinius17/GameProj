import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CallHelpFrame extends JFrame implements ActionListener {

	private JLabel helpLabel, pictureLabel, backgroundLabel;
	private JButton finishButton;
	private JPanel mainPanel;
	private GameFrame game;
	private Question q;
	private int correct;
	private ArrayList<Integer> availiable;

	public CallHelpFrame(Question q, GameFrame gameFrame, int corrAnsw,
			ArrayList<Integer> avail) {

		this.setTitle("Βοήθεια Τηλεφώνου");

		game = gameFrame;
		this.q = q;
		correct = corrAnsw;
		this.availiable = avail;

		helpLabel = new JLabel("<html>"+getName() + ": " + getText()+"</html>");
		pictureLabel = new JLabel(new ImageIcon(this.getClass().getResource(
				"Images/phone.png")));
		backgroundLabel = new JLabel(new ImageIcon(this.getClass().getResource(
				"Images/back1_blur.png")));
		finishButton = new JButton("Ευχαριστώ για την βοήθεια!");
		finishButton.addActionListener(this);

		mainPanel = new JPanel();
		mainPanel.setLayout(null);

		Font arial = new Font("Arial", Font.BOLD, 15);
		helpLabel.setForeground(Color.BLACK);
		helpLabel.setFont(arial);

		backgroundLabel.setBounds(0, 0, 400, 400);
		pictureLabel.setBounds(149, 45, 102, 102);
		helpLabel.setBounds(20, 180, 390, 30);
		finishButton.setBounds(90, 250, 220, 35);

		mainPanel.add(helpLabel);
		mainPanel.add(finishButton);
		mainPanel.add(pictureLabel);
		mainPanel.add(backgroundLabel);

		this.setContentPane(mainPanel);
		this.pack();
		this.setSize(400, 400);
		this.setResizable(false);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(game);
		this.setVisible(true);
	}

	public String getName() {
		Random r = new Random();

		switch (r.nextInt(10)) {
		case 1:
			return "Γιάννης";
		case 2:
			return "Νίκος";
		case 3:
			return "Πλάτωνας";
		case 4:
			return "Γιώργος";
		case 5:
			return "Μαρία";
		case 6:
			return "Δέσποινα";
		case 7:
			return "Ειρήνη";
		case 8:
			return "Στέφανος";
		case 9:
			return "Έφη";
		case 10:
			return "Χρήστος";
		default:
			return "Κατερίνα";

		}
	}

	public String getText() {

		Random r = new Random();
		Random m = new Random();

		int x = r.nextInt(5);

		if (availiable.size() < 4) {
			x = 7;
		}
		String A[] = new String[4];
		A[0] = "A";
		A[1] = "B";
		A[2] = "Γ";
		A[3] = "Δ";

		if (correct < 5) {
			return "Είναι σίγουρα το: " + A[q.getCorrectAnswerNumber()];
		} else if (correct < 10) {
			if (x < 4)
				return "Είναι σίγουρα το: " + A[q.getCorrectAnswerNumber()];
			else {
				if (m.nextInt(1) == 0)
					return "Είναι δύσκολο,είμαι ανάμεσα στο "
							+ A[q.getCorrectAnswerNumber()] + " και το "
							+ A[rand()];
				else
					return "Είναι δύσκολο,είμαι ανάμεσα στο " + A[rand()]
							+ " και το  " + A[q.getCorrectAnswerNumber()];
			}
		} else {
			if (x < 3) {
				if (m.nextInt(1) == 0)
					return "Είναι δύσκολο,είμαι ανάμεσα στο " + A[q.getCorrectAnswerNumber()]
							+ " και το " + A[rand()];
				else
					return "Είναι λίγο δύσκολο,είμαι ανάμεσα στο " + A[rand()] + " και το  "
							+ A[q.getCorrectAnswerNumber()];
			} else
				return "Συγγνώμη...δεν ξέρω την σωστή απάντηση";
		}
	}

	public int rand() {
		Random r = new Random();
		int ok = 0;

		while (ok == 0) {
			int x = r.nextInt(3);
			if (availiable.contains(x)) {
				if (x != q.getCorrectAnswerNumber()) {
					ok = 1;
					return x;
				} else {
					if (x > 0) {
						ok = 1;
						return x - 1;
					} else {
						ok = 1;
						return x + 1;
					}
				}
			}
		}
		return 0;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == finishButton) {
			this.dispose();
		}
	}

}
