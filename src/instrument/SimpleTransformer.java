package instrument;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import org.apache.commons.jci.listeners.FileChangeListener;
import org.apache.commons.jci.monitor.FilesystemAlterationListener;
import org.apache.commons.jci.monitor.FilesystemAlterationMonitor;

/**
 * @author Fabio Falci
 * 
 */
public class SimpleTransformer implements ClassFileTransformer {

	private final FilesystemAlterationMonitor fam = new FilesystemAlterationMonitor();

	private boolean first = true;

	private String classesDir = "bin/";
	private String reloadPackage;

	public SimpleTransformer(String classesDir, String reloadPackage) {
		super();
		this.classesDir = classesDir;
		if (!this.classesDir.endsWith("/")) {
			this.classesDir = this.classesDir + "/";
		}
		this.reloadPackage = reloadPackage;
		startNotifier();
	}

	public byte[] transform(ClassLoader loader, String className,
			Class redefiningClass, ProtectionDomain domain, byte[] bytes)
			throws IllegalClassFormatException {
		if (className.startsWith(reloadPackage)) {
			System.out.println("Redefining " + className);
			try {
				String filename = className.replace('.', File.separatorChar)
						+ ".class";
				return loadClassData(filename);
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
		File f = new File(classesDir, filename);
		int size = (int) f.length();
		byte buff[] = new byte[size];
		FileInputStream fis = new FileInputStream(f);
		DataInputStream dis = new DataInputStream(fis);
		dis.readFully(buff);
		dis.close();
		return buff;
	}
}
