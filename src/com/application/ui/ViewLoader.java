package com.application.ui;

import com.application.classloader.ViewClassLoader;
import com.application.ui.views.ViewA;

/**
 * @author Fabio Falci
 *
 */
public class ViewLoader {

	private String viewPackage = "com.application.ui.views";
	
	public ClassLoader createClassLoader() {
		// maybe get rootDir from classe
		return new ViewClassLoader("./bin", viewPackage);
	}
	
	public Object loadView(String name) {
		Exception ex = null;
//		if (name.startsWith(viewPackage)) {
//			try {
//				return createClassLoader().loadClass(name).newInstance();
//			} catch (Exception e) {
//				e.printStackTrace();
//				ex = e;
//			}
//		}
		try {
			return Class.forName(name).newInstance();
//			return new ViewA();
		} catch (Exception e) {
			e.printStackTrace();
			ex = e;
		}
		
		throw new RuntimeException("Error on view loader", ex);
	}
}
