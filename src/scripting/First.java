package scripting;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

/**
 * @author Fabio Falci
 *
 */
public class First {

	public First() {
		ScriptEngineManager manager = new ScriptEngineManager();
		manager.registerEngineName("bc", new BcEngineFactory());
		
		ScriptEngine engine = manager.getEngineByName("bc");
	}
	
	public static void main(String[] args) {
		new First();
	}
}
