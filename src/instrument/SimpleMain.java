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
		instrumentation.addTransformer(new SimpleTransformer(), true);
		staticInstrumentation = instrumentation;
	}
}
