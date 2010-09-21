package com.application.ui;


/**
 * @author Fabio Falci
 * 
 */
public class ViewLoader {

	public Object loadView(String name) {
		try {
			return Class.forName(name).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error on view loader", e);
		}
	}
}
