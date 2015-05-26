import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AboutUsFrame extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;

	private JLabel background;
	private JPanel mainPanel;

	private JButton backButton;

	private MainWindow window;

	public AboutUsFrame(MainWindow window) {

		this.window = window;

		ImageIcon back = new ImageIcon(this.getClass().getResource(
				"/Images/aboutBack.png"));
		ImageIcon backBut = new ImageIcon(this.getClass().getResource(
				"/Images/Back.jpg"));

		backButton = new JButton(backBut);
		backButton.addActionListener(this);
		background = new JLabel(back);
		
		backButton.setContentAreaFilled(false);
		backButton.setFocusPainted(false);
		backButton.setBorderPainted(false);

		background.setBounds(0, 0, 400, 400);
		backButton.setBounds(35, 340, 89, 40);

		mainPanel = new JPanel();
		mainPanel.setLayout(null);

		mainPanel.add(backButton);
		mainPanel.add(background);

		this.setContentPane(mainPanel);
		this.setVisible(true);
		// this.setLocationRelativeTo(window);
		this.setSize(400, 400);
		centerFrame();
		this.setTitle("About Us");
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

	public void actionPerformed(ActionEvent e) {

		this.dispose();
		window.setVisible(true);
	}

}
