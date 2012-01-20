package com.application.ui;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @author Fabio Falci
 * 
 */
@Component
public class ViewLoader implements ApplicationContextAware {

	private AbstractApplicationContext applicationContext;

	public Object loadView(String name) {
		return applicationContext.getBean(name);
	}

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = (AbstractApplicationContext) applicationContext;
	}
}
