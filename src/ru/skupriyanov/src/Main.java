package ru.skupriyanov.src;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.sun.jna.NativeLibrary;

public class Main {

	private static final NativeLibrary USER32_LIBRARY_INSTANCE = NativeLibrary
			.getInstance("user32");

	private static JButton startButton;
	private static JButton stopButton;
	private static JLabel stateLabel;
	
	private static final String STATE_RUNNING = "Running";
	private static final String STATE_IDLE = "Idle";
	
	private static final int ES_CONTINUOUS = 0x80000000;
	private static final int ES_SYSTEM_REQUIRED = 0x00000001;

	public static void main(String[] args) {
		drawFrame();
	}

	private static void drawFrame() {
		final JFrame frame = new JFrame("Cursor mover");
		frame.setSize(200, 100);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);

		JPanel panel = new JPanel();

		startButton = new JButton("Start");
		stopButton = new JButton("Stop");
		stateLabel = new JLabel(STATE_IDLE);
		
		panel.add(startButton);
		panel.add(stopButton);
		panel.add(stateLabel);
		frame.add(panel);

		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				stateLabel.setText(STATE_RUNNING);
				Object[] callArguments = {
						ES_CONTINUOUS | ES_SYSTEM_REQUIRED };
				USER32_LIBRARY_INSTANCE.getFunction("SetThreadExecutionState").invoke(
						callArguments);
			}
		});

		stopButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				stateLabel.setText(STATE_IDLE);
			}
		});

		frame.setVisible(true);
	}

}
