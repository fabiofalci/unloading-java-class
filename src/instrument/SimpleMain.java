package instrument;

import java.lang.instrument.Instrumentation;

/**
 * @author Fabio Falci
 * 
 */
public class SimpleMain {
	public static Instrumentation staticInstrumentation;

	public static void premain(String agentArguments,
			Instrumentation instrumentation) {
		String[] args = agentArguments.split(":");
		instrumentation.addTransformer(new SimpleTransformer(args[0], args[1]), true);
		staticInstrumentation = instrumentation;
	}
}
