package instrument;

import java.io.File;
import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.jci.listeners.FileChangeListener;
import org.apache.commons.jci.monitor.FilesystemAlterationListener;
import org.apache.commons.jci.monitor.FilesystemAlterationMonitor;
import org.apache.commons.jci.monitor.FilesystemAlterationObserver;

import com.application.classloader.ViewClassLoader;

/**
 * @author Fabio Falci
 * 
 */
public class SimpleTransformer implements ClassFileTransformer {

	private ViewClassLoader viewClassLoader = new ViewClassLoader("bin",
			"testing");

	private final FilesystemAlterationMonitor fam = new FilesystemAlterationMonitor();

	private List<Class<?>> reloaded = new ArrayList<Class<?>>();

	private boolean first = true;
	private boolean first2 = true;

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
				byte[] b = viewClassLoader.loadClassData(filename);
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
			public void onStop(FilesystemAlterationObserver pObserver) {
				super.onStop(pObserver);
				if (first) {
					first = false;
					return;
				}
				if (hasChanged()) {
					System.out.println("CHANGE!");
					try {
						Class<?>[] c = reloaded.toArray(new Class<?>[reloaded
								.size()]);

						SimpleMain.staticInstrumentation.retransformClasses(c);
						reloaded.clear();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		};

		fam.addListener(new File("bin"), listener);
		
		final FilesystemAlterationListener listener2 = new FileChangeListener() {
			public void onStop(FilesystemAlterationObserver pObserver) {
				super.onStop(pObserver);
				if (first2) {
					first2 = false;
					return;
				}
				if (hasChanged()) {
					System.out.println("CHANGE2!");
					try {
						Class<?>[] c = reloaded.toArray(new Class<?>[reloaded
								.size()]);

						SimpleMain.staticInstrumentation.retransformClasses(c);
						reloaded.clear();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		};

		fam.addListener( new File("war/WEB-INF/classes"), listener2);		
		
		fam.start();
	}
}
