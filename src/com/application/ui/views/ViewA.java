package com.application.ui.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * @author Fabio Falci
 *
 */
public class ViewA extends JPanel {

	public ViewA() {
		setName("ViewA");
		add(new JLabel("Test View A"));
		
		JButton show = new JButton("Show A1");
		show.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(ViewA.this, "Test A");
			}
		});
		add(show);
		add(new JButton("Close"));
	}
}
