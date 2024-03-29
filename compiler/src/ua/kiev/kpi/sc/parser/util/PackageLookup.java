package ua.kiev.kpi.sc.parser.util;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;

// TODO: move me to other project
public class PackageLookup {
	public <T> Iterable<Class<? extends T>> getClasses(String pckgname, Class<T> parent)
			throws ClassNotFoundException {
		ArrayList<Class<? extends T>> classes = new ArrayList<Class<? extends T>>();
		// Get a File object for the package
		File directory = null;
		try {
			ClassLoader cld = Thread.currentThread().getContextClassLoader();
			if (cld == null) {
				throw new ClassNotFoundException("Can't get class loader.");
			}
			String path = pckgname.replace('.', '/');
			URL resource = cld.getResource(path);
			if (resource == null) {
				throw new ClassNotFoundException("No resource for " + path);
			}
			directory = new File(resource.getFile());
		} catch (NullPointerException x) {
			throw new ClassNotFoundException(pckgname + " (" + directory
					+ ") does not appear to be a valid package");
		}
		if (directory.exists()) {
			// Get the list of the files contained in the package
			String[] files = directory.list();
			for (int i = 0; i < files.length; i++) {
				// we are only interested in .class files
				if (files[i].endsWith(".class")) {
					// removes the .class extension
					Class<?> clazz = Class.forName(pckgname + '.'
							+ files[i].substring(0, files[i].length() - 6));
					if (parent != null && parent.isAssignableFrom(clazz)) {
						classes.add(clazz.asSubclass(parent));
					}
				}
			}
		} else {
			throw new ClassNotFoundException(pckgname
					+ " does not appear to be a valid package");
		}
		return classes;
	}	
}
