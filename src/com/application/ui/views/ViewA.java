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
@Component("com.application.ui.views.ViewA")
@Scope("prototype")
public class ViewA extends JPanel {

	public ViewA() {
		setName("ViewA");
		add(new JLabel("Test View A"));
		
		JButton show = new JButton("Show A 1 2");
		show.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(ViewA.this, "Test A");
			}
		});
		add(show);
		add(new JButton("Close"));
	}
}
