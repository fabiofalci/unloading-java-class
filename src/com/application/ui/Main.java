package com.application.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @author Fabio Falci
 * 
 */
@Component
public class Main extends JPanel {

	@Autowired
	public ViewArea viewArea;
	private JFrame frame;
	private JMenuBar menuBar;

	public void start() {
		buildMenu();
		setLayout(new BorderLayout());
		add(viewArea.getViewComponent());
		show();
	}

	private void buildMenu() {
		menuBar = new JMenuBar();
		JMenu menu = new JMenu("Views");

		JMenuItem menuItem = new JMenuItem("View A");
		menu.add(menuItem);
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openViewA();
			}
		});

		menuItem = new JMenuItem("View B");
		menu.add(menuItem);
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openViewB();
			}
		});

		menuBar.add(menu);
	}

	protected void openViewB() {
		viewArea.open("com.application.ui.views.ViewB");

	}

	protected void openViewA() {
		viewArea.open("com.application.ui.views.ViewA");
	}

	public void show() {
		frame = new JFrame("Unloading class");
		frame.setJMenuBar(menuBar);
		frame.setContentPane(this);
		frame.setSize(800, 400);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
	}


	public static void main(String[] args) {
		ApplicationContext appContext = new ClassPathXmlApplicationContext(
				"app-context.xml");
		Main main = appContext.getBean(Main.class);
		main.start();
	}
}
