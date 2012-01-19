package com.application.ui;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.application.classloader.ViewClassLoader;

/**
 * @author Fabio Falci
 *
 */
@Component
public class ViewLoader implements ApplicationContextAware {

	private String viewPackage = "com.application.ui.views";
	private ApplicationContext applicationContext;
	
	public ClassLoader createClassLoader() {
		// maybe get rootDir from classe
		return new ViewClassLoader("./target/classes", viewPackage);
	}
	
	public Object loadView(String name) {
		Exception ex = null;
		if (name.startsWith(viewPackage)) {
			try {
//				return createClassLoader().loadClass(name).newInstance();
				return applicationContext.getBean(name);
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
	
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}
}
