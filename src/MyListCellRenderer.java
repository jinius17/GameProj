
import java.awt.Color;
import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

public class MyListCellRenderer extends DefaultListCellRenderer {
	private static final long serialVersionUID = 1L;

	private int selectedListItem;
	private GameFrame frame;

	public MyListCellRenderer(GameFrame gameFrame) {
		this.frame = gameFrame;
	}

	// Colors the cells of the list

	@Override
	public Component getListCellRendererComponent(
			@SuppressWarnings("rawtypes") JList list, Object value, int index,
			boolean isSelected, boolean cellHasFocus) {
		Component c = super.getListCellRendererComponent(list, value, index,
				isSelected, cellHasFocus);

		selectedListItem = 14 - frame.getCorrectAnswers();

		if (selectedListItem == index) {
			setForeground(Color.BLACK);
			setBackground(new Color(255, 128, 0));
		} else if (index > selectedListItem) {
			if (index == 5 || index == 10 || index == 0) {
				setForeground(Color.GREEN);
			} else {
				setForeground(Color.YELLOW);
			}
			// setBackground(new Color(30,179,63));
		} else if (index == 5 || index == 10 || index == 0) {
			setForeground(Color.white);
		}
		return c;
	}

}
