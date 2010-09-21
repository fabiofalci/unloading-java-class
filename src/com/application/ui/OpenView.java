package com.application.ui;

import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 * @author Fabio Falci
 *
 */
public class OpenView {

	private List<JPanel> opened;
	private JTabbedPane tabbedPane;
	private ViewLoader viewLoader;
	
	public void open(String name) {
		JPanel panel = (JPanel) viewLoader.loadView(name);
		opened.add(panel);
		tabbedPane.addTab(panel.getName(), panel);
		tabbedPane.setSelectedComponent(panel);
	}
	
	public void setOpened(List<JPanel> opened) {
		this.opened = opened;
	}
	
	public void setTabbedPane(JTabbedPane tabbedPane) {
		this.tabbedPane = tabbedPane;
	}
	
	public void setViewLoader(ViewLoader viewLoader) {
		this.viewLoader = viewLoader;
	}
	
	public static void main(String[] args) {
		System.out.println("teste");
	}
}
