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
		setName("ViewA333");
		add(new JLabel("Test ViewA2"));
		
		JButton show = new JButton("ShowA2");
		show.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(ViewA.this, "Test2");
			}
		});
		add(show);
//		add(new JButton("Close"));
	}
}
