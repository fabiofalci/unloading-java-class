package com.application.classloader;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * See http://java.sun.com/developer/onlineTraining/Security/Fundamentals/
 * magercises/ClassLoader/solution/FileClassLoader.java
 * 
 * @author Fabio Falci
 * 
 */
public class ViewClassLoader extends ClassLoader {

	private String root;
	private String dynamicPackage;

	public ViewClassLoader(String rootDir, String viewPackage) {
		if (rootDir == null)
			throw new IllegalArgumentException("Null root directory");
		root = rootDir;
		dynamicPackage = viewPackage;
	}

	protected Class loadClass(String name, boolean resolve)
			throws ClassNotFoundException {
		System.out.println("Loading class " + name);
		// Since all support classes of loaded class use same class loader
		// must check subclass cache of classes for things like Object

		Class c = findLoadedClass(name);
		if (c == null && !name.startsWith(dynamicPackage)) {
			try {
				c = findSystemClass(name);
			} catch (Exception e) {
			}
		}

		if (c == null) {
			// Convert class name argument to filename
			// Convert package names into subdirectories
			String filename = name.replace('.', File.separatorChar) + ".class";

			try {
				byte data[] = loadClassData(filename);
				c = defineClass(name, data, 0, data.length);
				if (c == null)
					throw new ClassNotFoundException(name);
			} catch (IOException e) {
				throw new ClassNotFoundException("Error reading file: "
						+ filename);
			}
		}
		if (resolve)
			resolveClass(c);
		return c;
	}

	private byte[] loadClassData(String filename) throws IOException {

		// Create a file object relative to directory provided
		File f = new File(root, filename);

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