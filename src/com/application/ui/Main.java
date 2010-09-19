package com.application.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

/**
 * @author Fabio Falci
 * 
 */
public class Main extends JPanel {

	private ViewArea viewArea;
	
	public Main() {
		buildMenu();
		viewArea = new ViewArea();
		setLayout(new BorderLayout());
		add(viewArea.getViewComponent());
	}
	private void buildMenu() {
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("Views");
		
		JMenuItem menuItem = new JMenuItem("View A");
		menu.add(menuItem);
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				openViewA();
			}
		});
		
		menuItem = new JMenuItem("View B");
		menu.add(menuItem);
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				openViewB();
			}
		});
		
		menuBar.add(menu);
		frame.setJMenuBar(menuBar);
	}
	
	protected void openViewB() {
		viewArea.open("com.application.ui.views.ViewB");
		
	}
	protected void openViewA() {
		viewArea.open("com.application.ui.views.ViewA");
	}

	static JFrame frame;
	public static void main(String[] args) {
		frame = new JFrame("Unloading class");
		frame.setContentPane(new Main());
		frame.setSize(800, 400);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);

	}
}
