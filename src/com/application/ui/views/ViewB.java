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
public class ViewB extends JPanel {

	public ViewB() {
		setName("ViewB");
		add(new JLabel("Test View B5"));
		
		JButton show = new JButton("Show5");
		show.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(ViewB.this, "Test B5");
			}
		});
		add(show);
		add(new JButton("Close"));
	}
}
