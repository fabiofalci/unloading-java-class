package com.application.ui.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author Fabio Falci
 *
 */
@Component("com.application.ui.views.ViewB")
@Scope("prototype")
public class ViewB extends JPanel {

	public ViewB() {
		setName("ViewB");
		add(new JLabel("Test View B"));
		
		JButton show = new JButton("Show");
		show.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(ViewB.this, "Test B");
			}
		});
		add(show);
		add(new JButton("Close"));
	}
}
