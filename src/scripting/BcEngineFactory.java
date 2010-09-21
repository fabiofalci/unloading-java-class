package scripting;

import java.util.Arrays;
import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;

/**
 * @author Fabio Falci
 *
 */
public class BcEngineFactory implements ScriptEngineFactory {

	@Override
	public String getEngineName() {
		return "bc";
	}

	@Override
	public String getEngineVersion() {
		return "0.0.1";
	}

	@Override
	public List<String> getExtensions() {
		return Arrays.asList("class");
	}

	@Override
	public List<String> getMimeTypes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getLanguageName() {
		return "bytecode";
	}

	@Override
	public String getLanguageVersion() {
		return "0.0.1";
	}

	@Override
	public Object getParameter(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getMethodCallSyntax(String obj, String m, String... args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getOutputStatement(String toDisplay) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getProgram(String... statements) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ScriptEngine getScriptEngine() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
