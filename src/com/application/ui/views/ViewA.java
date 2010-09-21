package com.application.ui.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 * @author Fabio Falci
 *
 */
public class ViewA extends JPanel {

	private JTabbedPane tabbedPane;
	
	public ViewA() {
		setName("ViewA");
		add(new JLabel("Test View A"));
		
		JButton show = new JButton("Show");
		show.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(ViewA.this, "Test A");
			}
		});
		add(show);
		add(new JButton("Close"));
	}
	
	public static void main(String[] args) {
		System.out.println("Abc ");
	}
	
	public void setTabbedPane(JTabbedPane tabbedPane) {
		this.tabbedPane = tabbedPane;
		System.out.println(tabbedPane);
	}
}
