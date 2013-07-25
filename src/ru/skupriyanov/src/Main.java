package ru.skupriyanov.src;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.sun.jna.NativeLibrary;

public class Main {

	private static final int SLEEPING_TIME_MILLIS = 10000;
	private static final NativeLibrary USER32_LIBRARY_INSTANCE = NativeLibrary
			.getInstance("user32");

	private static native boolean SetCursorPos(int x, int y);

	private static JButton startButton;
	private static JButton stopButton;
	private static boolean continueCycle = true;

	public static void main(String[] args) {
		drawFrame();
	}

	private static void drawFrame() {
		JFrame frame = new JFrame("Cursor mover");
		frame.setSize(200, 100);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		
		JPanel panel = new JPanel();
		
		startButton = new JButton("Start");
		stopButton = new JButton("Stop");
		
		panel.add(startButton);
		panel.add(stopButton);
		frame.add(panel);

		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				moveCursor();
			}
		});

		stopButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				continueCycle = false;
			}
		});

		frame.setVisible(true);
	}

	private static void moveCursor() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Random randomX = new Random(screenSize.width - 1);
		Random randomY = new Random(screenSize.height - 1);
		while (continueCycle) {
			SetCursorPos(randomX.nextInt(), randomY.nextInt());
			try {
				Thread.sleep(SLEEPING_TIME_MILLIS);
			} catch (InterruptedException e) {
				continueCycle = false;
			}
		}
	}

}
