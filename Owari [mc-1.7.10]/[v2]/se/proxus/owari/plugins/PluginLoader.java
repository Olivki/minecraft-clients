package se.proxus.owari.plugins;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandlerFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Vector;
import java.util.jar.JarFile;

public class PluginLoader extends URLClassLoader {

	protected HashSet<String> setJarFileNames2Close = new HashSet<String>();

	public PluginLoader() {
		super(new URL[] {});
	}

	public PluginLoader(final URL[] urls, final ClassLoader parent) {
		super(urls, parent);
	}

	public PluginLoader(final URL[] urls) {
		super(urls);
	}

	public PluginLoader(final URL[] urls, final ClassLoader parent,
			final URLStreamHandlerFactory factory) {
		super(urls, parent, factory);
	}

	public void addURL(final File file) {
		try {
			super.addURL(file.toURI().toURL());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addURL(final URL url) {
		super.addURL(url);
	}

	@Override
	public void close() {
		setJarFileNames2Close.clear();
		closeClassLoader(this);
		finalizeNativeLibs(this);
		cleanupJarFileFactory();
	}

	/**
	 * cleanup jar file factory cache
	 */
	@SuppressWarnings({ "nls", "unchecked" })
	public boolean cleanupJarFileFactory() {
		boolean res = false;
		Class classJarURLConnection = null;
		try {
			classJarURLConnection = getSystemClassLoader().loadClass(
					"sun.net.www.protocol.jar.JarURLConnection");
		} catch (ClassNotFoundException e) {
			// ignore
		}
		if (classJarURLConnection == null) {
			return res;
		}
		Field f = null;
		try {
			f = classJarURLConnection.getDeclaredField("factory");
		} catch (NoSuchFieldException e) {
			// ignore
		}
		if (f == null) {
			return res;
		}
		f.setAccessible(true);
		Object obj = null;
		try {
			obj = f.get(null);
		} catch (IllegalAccessException e) {
			// ignore
		}
		if (obj == null) {
			return res;
		}
		Class classJarFileFactory = obj.getClass();
		//
		HashMap fileCache = null;
		try {
			f = classJarFileFactory.getDeclaredField("fileCache");
			f.setAccessible(true);
			obj = f.get(null);
			if (obj instanceof HashMap) {
				fileCache = (HashMap) obj;
			}
		} catch (NoSuchFieldException e) {
		} catch (IllegalAccessException e) {
			// ignore
		}
		HashMap urlCache = null;
		try {
			f = classJarFileFactory.getDeclaredField("urlCache");
			f.setAccessible(true);
			obj = f.get(null);
			if (obj instanceof HashMap) {
				urlCache = (HashMap) obj;
			}
		} catch (NoSuchFieldException e) {
		} catch (IllegalAccessException e) {
			// ignore
		}
		if (urlCache != null) {
			HashMap urlCacheTmp = (HashMap) urlCache.clone();
			Iterator it = urlCacheTmp.keySet().iterator();
			while (it.hasNext()) {
				obj = it.next();
				if (!(obj instanceof JarFile)) {
					continue;
				}
				JarFile jarFile = (JarFile) obj;
				if (setJarFileNames2Close.contains(jarFile.getName())) {
					try {
						jarFile.close();
					} catch (IOException e) {
						// ignore
					}
					if (fileCache != null) {
						fileCache.remove(urlCache.get(jarFile));
					}
					urlCache.remove(jarFile);
				}
			}
			res = true;
		} else if (fileCache != null) {
			// urlCache := null
			HashMap fileCacheTmp = (HashMap) fileCache.clone();
			Iterator it = fileCacheTmp.keySet().iterator();
			while (it.hasNext()) {
				Object key = it.next();
				obj = fileCache.get(key);
				if (!(obj instanceof JarFile)) {
					continue;
				}
				JarFile jarFile = (JarFile) obj;
				if (setJarFileNames2Close.contains(jarFile.getName())) {
					try {
						jarFile.close();
					} catch (IOException e) {
						// ignore
					}
					fileCache.remove(key);
				}
			}
			res = true;
		}
		setJarFileNames2Close.clear();
		return res;
	}

	/**
	 * close jar files of cl
	 * 
	 * @param cl
	 * @return
	 */
	@SuppressWarnings({ "nls", "unchecked" })
	public boolean closeClassLoader(final ClassLoader cl) {
		boolean res = false;
		if (cl == null) {
			return res;
		}
		Class classURLClassLoader = URLClassLoader.class;
		Field f = null;
		try {
			f = classURLClassLoader.getDeclaredField("ucp");
		} catch (NoSuchFieldException e1) {
			// ignore
		}
		if (f != null) {
			f.setAccessible(true);
			Object obj = null;
			try {
				obj = f.get(cl);
			} catch (IllegalAccessException e1) {
				// ignore
			}
			if (obj != null) {
				final Object ucp = obj;
				f = null;
				try {
					f = ucp.getClass().getDeclaredField("loaders");
				} catch (NoSuchFieldException e1) {
					// ignore
				}
				if (f != null) {
					f.setAccessible(true);
					ArrayList loaders = null;
					try {
						loaders = (ArrayList) f.get(ucp);
						res = true;
					} catch (IllegalAccessException e1) {
						// ignore
					}
					for (int i = 0; loaders != null && i < loaders.size(); i++) {
						obj = loaders.get(i);
						f = null;
						try {
							f = obj.getClass().getDeclaredField("jar");
						} catch (NoSuchFieldException e) {
							// ignore
						}
						if (f != null) {
							f.setAccessible(true);
							try {
								obj = f.get(obj);
							} catch (IllegalAccessException e1) {
								// ignore
							}
							if (obj instanceof JarFile) {
								final JarFile jarFile = (JarFile) obj;
								setJarFileNames2Close.add(jarFile.getName());
								// try {
								// jarFile.getManifest().clear();
								// } catch (IOException e) {
								// // ignore
								// }
								try {
									jarFile.close();
								} catch (IOException e) {
									// ignore
								}
							}
						}
					}
				}
			}
		}
		return res;
	}

	/**
	 * finalize native libraries
	 * 
	 * @param cl
	 * @return
	 */
	@SuppressWarnings({ "nls", "unchecked" })
	public boolean finalizeNativeLibs(final ClassLoader cl) {
		boolean res = false;
		Class classClassLoader = ClassLoader.class;
		java.lang.reflect.Field nativeLibraries = null;
		try {
			nativeLibraries = classClassLoader.getDeclaredField("nativeLibraries");
		} catch (NoSuchFieldException e1) {
			// ignore
		}
		if (nativeLibraries == null) {
			return res;
		}
		nativeLibraries.setAccessible(true);
		Object obj = null;
		try {
			obj = nativeLibraries.get(cl);
		} catch (IllegalAccessException e1) {
			// ignore
		}
		if (!(obj instanceof Vector)) {
			return res;
		}
		res = true;
		Vector java_lang_ClassLoader_NativeLibrary = (Vector) obj;
		for (Object lib : java_lang_ClassLoader_NativeLibrary) {
			java.lang.reflect.Method finalize = null;
			try {
				finalize = lib.getClass().getDeclaredMethod("finalize", new Class[0]);
			} catch (NoSuchMethodException e) {
				// ignore
			}
			if (finalize != null) {
				finalize.setAccessible(true);
				try {
					finalize.invoke(lib, new Object[0]);
				} catch (IllegalAccessException e) {
				} catch (InvocationTargetException e) {
					// ignore
				}
			}
		}
		return res;
	}
}
