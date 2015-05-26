import java.io.Serializable;

public class Player implements Serializable, Comparable<Player> {

	private static final long serialVersionUID = 1L;
	private String name;
	private int correctAnswers;
	private String money;

	public Player(String name, int answer, String money) {
		this.name = name;
		this.correctAnswers = answer;
		this.money = money;
	}

	public String getName() {
		return name;
	}

	public String getMoney() {
		return money;
	}

	public int compareTo(Player p1) {
		if (this.correctAnswers > p1.correctAnswers) {
			return -1;
		} else if (this.correctAnswers < p1.correctAnswers) {
			return 1;
		} else {
			return 0;
		}
	}

}
