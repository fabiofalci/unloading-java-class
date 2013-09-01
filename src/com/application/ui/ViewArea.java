package com.application.ui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

/**
 * @author Fabio Falci
 * 
 *
 *   FIX:
 *        - added close button functionality
 *        - added close all functionality
 *        - make it possible to call non blocking close
 *
 */
public class ViewArea {

	private List<JPanel> opened;
	private JTabbedPane tabbedPane;
	private ViewLoader viewLoader;

	public ViewArea() {
		opened = new ArrayList<JPanel>();
		viewLoader = new ViewLoader();
	}

	public void open(String name) {
		final JPanel panel = (JPanel) viewLoader.loadView(name);

		JButton button = new JButton("Close");
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent evt) {

				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						ViewArea.this.close(panel);
					}
				});
			}
		});
		panel.add(button);

		opened.add(panel);
		tabbedPane.addTab(panel.getName(), panel);
		tabbedPane.setSelectedComponent(panel);
	}

	public void close(JPanel panel) {
		System.out.println("CLOSING...");
		opened.remove(panel);
		tabbedPane.remove(panel);
	}

	public void close() {
		Object[] array = opened.toArray();
		for (int i = 0; i < array.length; i++) {
			opened.remove(array[i]);
			tabbedPane.remove((Component) array[i]);
		}
	}

	public Component getViewComponent() {
		if (tabbedPane == null) {
			tabbedPane = new JTabbedPane();
		}
		return tabbedPane;
	}
}
