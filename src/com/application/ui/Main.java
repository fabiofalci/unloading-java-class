package com.application.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * @author Fabio Falci
 *
 * FIX:
 *       - added triggerGC functionality to use with java -verbose:class
 *         to demostrate unloading...  		
 *       - added close-all menu and trigger-gc menu
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

		menuItem = new JMenuItem("Clean Views");
		menu.add(menuItem);
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cleanTabs();
			}
		});

		menuItem = new JMenuItem("Trigger GC");
		menu.add(menuItem);
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						triggerGC();
					}
				});
			}
		});

		menuBar.add(menu);
		frame.setJMenuBar(menuBar);
	}

	protected void openViewB() {
		viewArea.open("com.application.ui.views.ViewB");

	}

	protected void cleanTabs() {
		viewArea.close();
	}

	protected void openViewA() {
		viewArea.open("com.application.ui.views.ViewA");
	}

	protected static final void triggerGC() {
		System.out.println("\n-- Starting GC");
		int c = 50;
		while (c-- > 0)
			System.gc();

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("-- End of GC\n");
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
