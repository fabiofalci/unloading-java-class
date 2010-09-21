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
	
	public ViewA() {
		setName("ViewA");
		add(new JLabel("Test View aaaaaa"));
		
		JButton show = new JButton("Show aaaaa");
		show.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(ViewA.this, "Test aaaa");
			}
		});
		add(show);
		add(new JButton("Close"));
	}
}
