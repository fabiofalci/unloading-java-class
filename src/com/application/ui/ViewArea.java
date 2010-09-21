package com.application.ui;

import java.awt.Component;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import javax.script.Invocable;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 * @author Fabio Falci
 *
 */
public class ViewArea {

	private List<JPanel> opened;
	private JTabbedPane tabbedPane;
	private ViewLoader viewLoader;
	
	public ViewArea() {
		opened = new ArrayList<JPanel>();
		viewLoader = new ViewLoader();
	}
	
	public void open(String name) {
//		JPanel panel = (JPanel) viewLoader.loadView(name);

		try {
			executeOpenViewScript(name);
			//JPanel panel = executeOpenViewScript(name);
//			opened.add(panel);
//			tabbedPane.addTab(panel.getName(), panel);
//			tabbedPane.setSelectedComponent(panel);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void close(JPanel panel) {
		opened.remove(panel);
		tabbedPane.remove(panel);
	}

	public Component getViewComponent() {
		if (tabbedPane == null) {
			tabbedPane = new JTabbedPane();
		}
		return tabbedPane;
	}
	
	private void executeOpenViewScript(String name) throws Exception {
		ScriptEngineManager factory =  new ScriptEngineManager();
		ScriptEngine engine = factory.getEngineByName("java");
		
        // evaluate script
//		String scriptName = name.replace('.', File.separatorChar) + ".java";
//		String fileName = "src/" + scriptName;
		//File f = new File("com.application.ui.OpenView.java");
		//Reader r = new FileReader("src/com/application/ui/OpenView.java");
		Reader r = new FileReader("src/com/application/ui/views/ViewA.java");
//		
//		int index = name.lastIndexOf(".") + 1;
		//engine.put(ScriptEngine.FILENAME, name.substring(index));
		engine.put(ScriptEngine.FILENAME, "com.application.ui.views.ViewA.java");
		
		ScriptContext context = engine.getContext();
		context.setAttribute("sourcepath", "src/", ScriptContext.ENGINE_SCOPE);
		context.setAttribute("mainClass", "com.application.ui.OpenView", ScriptContext.ENGINE_SCOPE);
		context.setAttribute("tabbedPane", getViewComponent(), ScriptContext.ENGINE_SCOPE);
        Object obj = engine.eval(r);
        //JPanel panel = (JPanel) obj.getClass().newInstance();
        if (engine instanceof Invocable) {
        	Invocable invocable = (Invocable) engine;
        	invocable.invokeMethod(obj, "open", name);
        }
        //return panel;
	}
}
