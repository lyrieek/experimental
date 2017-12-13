package chars.u;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class TFrame extends JFrame {

	private static final long serialVersionUID = -5258193734779847013L;

	public TFrame() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		Dimension screenSize = getToolkit().getScreenSize();
		int width = (int) screenSize.getWidth();
		int height = (int) screenSize.getHeight();
		int x = width / 4;
		int y = height / 4;
		setBounds(x, y, width / 2, height / 2);
		JTextArea input = new JTextArea("input");
		JTextArea output = new JTextArea("success");
		input.setBounds(x, y, width / 2, height / 4);
		output.setBounds(x / 3, width / 3, width / 2, height / 4);
		input.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		output.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		input.setVisible(true);
		output.setVisible(true);
		add(input);
		add(output);
		input.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (!e.isControlDown()) {
					return;
				}
				if (e.getKeyCode() == 81) {
					String command = JOptionPane.showInputDialog("Input Command");
					System.out.println("command:" + command);
					TFrame.analysis(command);
				}
			}

		});
		setVisible(true);
	}

	protected static void analysis(String command) {
		command = command.trim();
		if (command.startsWith("s")) {
			
		}
	}

	public static void main(String[] args) throws Exception {
		new TFrame();
		// Thread.sleep(700);
		// System.exit(0);
	}

}
