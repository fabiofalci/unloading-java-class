package com.application.ui;

import com.application.classloader.ViewClassLoader;

/**
 * @author Fabio Falci
 *
 */
public class ViewLoader {

	private String viewPackage = "com.application.ui.views";
	
	public ClassLoader createClassLoader() {
		// maybe get rootDir from classe
		return new ViewClassLoader("./target/classes", viewPackage);
	}
	
	public Object loadView(String name) {
		Exception ex = null;
		if (name.startsWith(viewPackage)) {
			try {
				return createClassLoader().loadClass(name).newInstance();
			} catch (Exception e) {
				e.printStackTrace();
				ex = e;
			}
		}
		try {
			return Class.forName(name).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			ex = e;
		}
		
		throw new RuntimeException("Error on view loader", ex);
	}
}
