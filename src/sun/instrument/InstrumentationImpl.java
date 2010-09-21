// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   InstrumentationImpl.java

package sun.instrument;

import java.lang.instrument.ClassDefinition;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.ProtectionDomain;
import java.util.jar.JarFile;

// Referenced classes of package sun.instrument:
//            TransformerManager

public class InstrumentationImpl implements Instrumentation {

	private InstrumentationImpl(long l, boolean flag, boolean flag1) {
		mRetransfomableTransformerManager = null;
		mNativeAgent = l;
		mEnvironmentSupportsRedefineClasses = flag;
		mEnvironmentSupportsRetransformClassesKnown = false;
		mEnvironmentSupportsRetransformClasses = false;
		mEnvironmentSupportsNativeMethodPrefix = flag1;
	}

	public void addTransformer(ClassFileTransformer classfiletransformer) {
		addTransformer(classfiletransformer, false);
	}

	public synchronized void addTransformer(
			ClassFileTransformer classfiletransformer, boolean flag) {
		if (classfiletransformer == null)
			throw new NullPointerException(
					"null passed as 'transformer' in addTransformer");
		if (flag) {
			if (!isRetransformClassesSupported())
				throw new UnsupportedOperationException(
						"adding retransformable transformers is not supported in this environment");
			if (mRetransfomableTransformerManager == null)
				mRetransfomableTransformerManager = new TransformerManager(true);
			mRetransfomableTransformerManager
					.addTransformer(classfiletransformer);
			if (mRetransfomableTransformerManager.getTransformerCount() == 1)
				setHasRetransformableTransformers(mNativeAgent, true);
		} else {
			mTransformerManager.addTransformer(classfiletransformer);
		}
	}

	public synchronized boolean removeTransformer(
			ClassFileTransformer classfiletransformer) {
		if (classfiletransformer == null)
			throw new NullPointerException(
					"null passed as 'transformer' in removeTransformer");
		TransformerManager transformermanager = findTransformerManager(classfiletransformer);
		if (transformermanager != null) {
			transformermanager.removeTransformer(classfiletransformer);
			if (transformermanager.isRetransformable()
					&& transformermanager.getTransformerCount() == 0)
				setHasRetransformableTransformers(mNativeAgent, false);
			return true;
		} else {
			return false;
		}
	}

	public boolean isModifiableClass(Class class1) {
		if (class1 == null)
			throw new NullPointerException(
					"null passed as 'theClass' in isModifiableClass");
		else
			return isModifiableClass0(mNativeAgent, class1);
	}

	public boolean isRetransformClassesSupported() {
		if (!mEnvironmentSupportsRetransformClassesKnown) {
			mEnvironmentSupportsRetransformClasses = isRetransformClassesSupported0(mNativeAgent);
			mEnvironmentSupportsRetransformClassesKnown = true;
		}
		return mEnvironmentSupportsRetransformClasses;
	}

	public void retransformClasses(Class aclass[]) {
		if (!isRetransformClassesSupported()) {
			throw new UnsupportedOperationException(
					"retransformClasses is not supported in this environment");
		} else {
			retransformClasses0(mNativeAgent, aclass);
			return;
		}
	}

	public boolean isRedefineClassesSupported() {
		return mEnvironmentSupportsRedefineClasses;
	}

	public void redefineClasses(ClassDefinition aclassdefinition[])
			throws ClassNotFoundException {
		if (!isRedefineClassesSupported())
			throw new UnsupportedOperationException(
					"redefineClasses is not supported in this environment");
		if (aclassdefinition == null)
			throw new NullPointerException(
					"null passed as 'definitions' in redefineClasses");
		for (int i = 0; i < aclassdefinition.length; i++)
			if (aclassdefinition[i] == null)
				throw new NullPointerException(
						"element of 'definitions' is null in redefineClasses");

		if (aclassdefinition.length == 0) {
			return;
		} else {
			redefineClasses0(mNativeAgent, aclassdefinition);
			return;
		}
	}

	public Class[] getAllLoadedClasses() {
		Class[] c = getAllLoadedClasses0(mNativeAgent);
		System.out.println("getAllLoadedClasses " + c.length);
		return c;
	}

	public Class[] getInitiatedClasses(ClassLoader classloader) {
		return getInitiatedClasses0(mNativeAgent, classloader);
	}

	public long getObjectSize(Object obj) {
		if (obj == null)
			throw new NullPointerException(
					"null passed as 'objectToSize' in getObjectSize");
		else
			return getObjectSize0(mNativeAgent, obj);
	}

	public void appendToBootstrapClassLoaderSearch(JarFile jarfile) {
		appendToClassLoaderSearch0(mNativeAgent, jarfile.getName(), true);
	}

	public void appendToSystemClassLoaderSearch(JarFile jarfile) {
		appendToClassLoaderSearch0(mNativeAgent, jarfile.getName(), false);
	}

	public boolean isNativeMethodPrefixSupported() {
		return mEnvironmentSupportsNativeMethodPrefix;
	}

	public synchronized void setNativeMethodPrefix(
			ClassFileTransformer classfiletransformer, String s) {
		if (!isNativeMethodPrefixSupported())
			throw new UnsupportedOperationException(
					"setNativeMethodPrefix is not supported in this environment");
		if (classfiletransformer == null)
			throw new NullPointerException(
					"null passed as 'transformer' in setNativeMethodPrefix");
		TransformerManager transformermanager = findTransformerManager(classfiletransformer);
		if (transformermanager == null) {
			throw new IllegalArgumentException(
					"transformer not registered in setNativeMethodPrefix");
		} else {
			transformermanager.setNativeMethodPrefix(classfiletransformer, s);
			String as[] = transformermanager.getNativeMethodPrefixes();
			setNativeMethodPrefixes(mNativeAgent, as,
					transformermanager.isRetransformable());
			return;
		}
	}

	private TransformerManager findTransformerManager(
			ClassFileTransformer classfiletransformer) {
		if (mTransformerManager.includesTransformer(classfiletransformer))
			return mTransformerManager;
		if (mRetransfomableTransformerManager != null
				&& mRetransfomableTransformerManager
						.includesTransformer(classfiletransformer))
			return mRetransfomableTransformerManager;
		else
			return null;
	}

	private native boolean isModifiableClass0(long l, Class class1);

	private native boolean isRetransformClassesSupported0(long l);

	private native void setHasRetransformableTransformers(long l, boolean flag);

	private native void retransformClasses0(long l, Class aclass[]);

	private native void redefineClasses0(long l,
			ClassDefinition aclassdefinition[]) throws ClassNotFoundException;

	private native Class[] getAllLoadedClasses0(long l);

	private native Class[] getInitiatedClasses0(long l, ClassLoader classloader);

	private native long getObjectSize0(long l, Object obj);

	private native void appendToClassLoaderSearch0(long l, String s,
			boolean flag);

	private native void setNativeMethodPrefixes(long l, String as[],
			boolean flag);

	static class MyPrivilegedAction implements PrivilegedAction {
		private AccessibleObject accessibleobject;
		private boolean flag;

		public MyPrivilegedAction(AccessibleObject accessibleobject,
				boolean flag) {
			this.accessibleobject = accessibleobject;
			this.flag = flag;
		}

		public Object run() {
			accessibleobject.setAccessible(flag);
			return null;
		}
	};

	private static void setAccessible(AccessibleObject accessibleobject,
			boolean flag) {
		AccessController.doPrivileged(new MyPrivilegedAction(accessibleobject,
				flag));
	}

	private void loadClassAndStartAgent(String s, String s1, String s2)
			throws Throwable {
		ClassLoader classloader = ClassLoader.getSystemClassLoader();
		Class class1 = classloader.loadClass(s);
		Method method = null;
		NoSuchMethodException nosuchmethodexception = null;
		boolean flag = false;
		try {
			method = class1.getMethod(s1, new Class[] { java.lang.String.class,
					java.lang.instrument.Instrumentation.class });
			flag = true;
		} catch (NoSuchMethodException nosuchmethodexception1) {
			nosuchmethodexception = nosuchmethodexception1;
		}
		if (method == null)
			try {
				method = class1.getMethod(s1,
						new Class[] { java.lang.String.class });
			} catch (NoSuchMethodException nosuchmethodexception2) {
				throw nosuchmethodexception;
			}
		setAccessible(method, true);
		if (flag)
			method.invoke(null, new Object[] { s2, this });
		else
			method.invoke(null, new Object[] { s2 });
		setAccessible(method, false);
	}

	private void loadClassAndCallPremain(String s, String s1) throws Throwable {
		loadClassAndStartAgent(s, "premain", s1);
	}

	private void loadClassAndCallAgentmain(String s, String s1)
			throws Throwable {
		loadClassAndStartAgent(s, "agentmain", s1);
	}

	private byte[] transform(ClassLoader classloader, String s, Class class1,
			ProtectionDomain protectiondomain, byte abyte0[], boolean flag) {
		TransformerManager transformermanager = flag ? mRetransfomableTransformerManager
				: mTransformerManager;
		if (transformermanager == null)
			return null;
		else
			return transformermanager.transform(classloader, s, class1,
					protectiondomain, abyte0);
	}

	private final TransformerManager mTransformerManager = new TransformerManager(
			false);
	private TransformerManager mRetransfomableTransformerManager;
	private final long mNativeAgent;
	private final boolean mEnvironmentSupportsRedefineClasses;
	private volatile boolean mEnvironmentSupportsRetransformClassesKnown;
	private volatile boolean mEnvironmentSupportsRetransformClasses;
	private final boolean mEnvironmentSupportsNativeMethodPrefix;

	static {
		System.loadLibrary("instrument");
	}
}
