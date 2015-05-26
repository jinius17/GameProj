import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class Stats implements Serializable {

	private static final long serialVersionUID = 1L;

	private ArrayList<Player> playerList;

	public Stats() {
		playerList = new ArrayList<Player>();
	}

	public void addPlayer(Player p1) {
		playerList.add(p1);
	}

	// Sorts the list

	public void sortList() {
		Collections.sort(playerList);
	}

	public Player sendPlayer(int i) {
		return playerList.get(i);
	}

	// Checks if the list is empty and fills it with dummy players

	public void checkList() {
		if (playerList.isEmpty()) {
			for (int i = 0; i <= 10; i++) {
				playerList.add(new Player("......", 0, "0 €"));
			}
		}
	}

	public void eraseList() {
		playerList.clear();
	}

	public int sizeOf() {
		return playerList.size();
	}

}
