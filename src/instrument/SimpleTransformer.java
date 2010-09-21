package instrument;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.jci.listeners.FileChangeListener;
import org.apache.commons.jci.monitor.FilesystemAlterationListener;
import org.apache.commons.jci.monitor.FilesystemAlterationMonitor;

import com.application.classloader.ViewClassLoader;

/**
 * @author Fabio Falci
 * 
 */
public class SimpleTransformer implements ClassFileTransformer {

	private final FilesystemAlterationMonitor fam = new FilesystemAlterationMonitor();

	private List<Class<?>> reloaded = new ArrayList<Class<?>>();

	private boolean first = true;

	private String classesDir = "bin/";

	public SimpleTransformer() {
		super();

		startNotifier();
	}

	public byte[] transform(ClassLoader loader, String className,
			Class redefiningClass, ProtectionDomain domain, byte[] bytes)
			throws IllegalClassFormatException {
		if (!reloaded.contains(className)
				&& className.startsWith("com/mypackage")) {
			System.out.println("Redefining " + className);
			try {
				String filename = className.replace('.', File.separatorChar)
						+ ".class";
				byte[] b = loadClassData(filename);
				if (redefiningClass != null) {
					reloaded.add(redefiningClass);
				}
				return b;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return bytes;
	}

	private void startNotifier() {
		final FilesystemAlterationListener listener = new FileChangeListener() {
			@Override
			public void onFileChange(File pFile) {
				super.onFileChange(pFile);
				if (first) {
					first = false;
					return;
				}
				if (hasChanged()) {
					String fileName = pFile.toString();
					System.out.println("CHANGE! " + fileName);
					String name = fileName.substring(classesDir.length());
					name = name.replace("/", ".");
					name = name.substring(0, name.lastIndexOf("."));
					try {
						Class<?> reload = Class.forName(name);
						SimpleMain.staticInstrumentation
								.retransformClasses(reload);
						reloaded.clear();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		};

		fam.addListener(new File(classesDir), listener);
		fam.start();
	}

	public byte[] loadClassData(String filename) throws IOException {

		// Create a file object relative to directory provided
		File f = new File(classesDir, filename);

		// Get size of class file
		int size = (int) f.length();

		// Reserve space to read
		byte buff[] = new byte[size];

		// Get stream to read from
		FileInputStream fis = new FileInputStream(f);
		DataInputStream dis = new DataInputStream(fis);

		// Read in data
		dis.readFully(buff);

		// close stream
		dis.close();

		// return data
		return buff;
	}
}
