package instrument;

import java.io.File;
import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import com.application.classloader.ViewClassLoader;

/**
 * @author Fabio Falci
 * 
 */
public class SimpleTransformer implements ClassFileTransformer {

	private ViewClassLoader viewClassLoader = new ViewClassLoader("bin", "testing");
	
	public SimpleTransformer() {
		super();
	}

	public byte[] transform(ClassLoader loader, String className,
			Class redefiningClass, ProtectionDomain domain, byte[] bytes)
			throws IllegalClassFormatException {
		System.out.println("Transformer to Transform Class: " + className);
		if (className.endsWith("Foo")) {
			try {
				String filename = className.replace('.', File.separatorChar) + ".class";
				return viewClassLoader.loadClassData(filename);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return bytes;
	}
}
