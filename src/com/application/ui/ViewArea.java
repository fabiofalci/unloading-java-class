package com.application.ui;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 * @author Fabio Falci
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
		JPanel panel = (JPanel) viewLoader.loadView(name);
		opened.add(panel);
		tabbedPane.addTab(panel.getName(), panel);
		tabbedPane.setSelectedComponent(panel);

	}

	public void close(JPanel panel) {
		opened.remove(panel);
		tabbedPane.remove(panel);
	}

	public Component getViewComponent() {
		if (tabbedPane == null) {
			tabbedPane = new JTabbedPane();
		}
		return tabbedPane;
	}
}
