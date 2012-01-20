/**
 * 
 */
package com.application.classloader;

import java.io.File;
import java.io.FileInputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;

/**
 * @author Fabio Falci
 * 
 */
public class ClasspathInspector {

	private Map<File, String> hashs = new HashMap<File, String>();
	private File root = new File("./target/classes");

	public ClasspathInspector() {
		createHashs();
	}

	public void createHashs() {
		Collection<File> files = FileUtils.listFiles(root,
				new String[] { "class" }, true);
		for (File file : files) {
			try {
				FileInputStream fis = new FileInputStream(file);
				String md5 = DigestUtils.md5Hex(fis);
				hashs.put(file, md5);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public boolean hasChanged() {
		if (hasChangedInternal()) {
			System.out.println("has changed");
			createHashs();
			return true;
		}
		System.out.println("hasn't changed");
		return false;
	}
	
	private boolean hasChangedInternal() {
		Collection<File> files = FileUtils.listFiles(root,
				new String[] { "class" }, true);
		for (File file : files) {
			if (!hashs.containsKey(file)) {
				return true;
			}
			try {
				FileInputStream fis = new FileInputStream(file);
				String md5 = DigestUtils.md5Hex(fis);
				String currentMd5 = hashs.get(file);
				if (!md5.equals(currentMd5)) {
					return true;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return false;
	}
}
