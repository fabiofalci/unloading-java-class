package com.application.ui.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * @author Fabio Falci
 * 
 */
public class ViewA extends JPanel {

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

	}
}
