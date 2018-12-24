package se.proxus.kanon.utils.system;

import se.proxus.kanon.Kanon;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public final class Reflectionz {

    /**
     * Loads the class with the given name. This method is used whenever a class contains to be loaded dynamically.
     * It first tries the current thread's context class loader. If this fails, the class loader of this class
     * is tried.
     *
     * @param clsName
     *         the name of the class to be loaded
     * @return the loaded class
     *
     * @throws ClassNotFoundException
     *         if the class cannot be resolved
     * @since 2.0
     */
    public static Class<?> loadClass(final String clsName)
    throws ClassNotFoundException {

        final ClassLoader cl = Thread.currentThread().getContextClassLoader();
        try {
            if (cl != null) {
                return cl.loadClass(clsName);
            }
        } catch (final ClassNotFoundException cnfex) {
            Kanon.LOGGER.info("Could not load class " + clsName + " using CCL. Falling back to default CL.");
        }

        return ClassLoader.getSystemClassLoader().loadClass(clsName);
    }

    public static Field getNonAccessibleField(final Class<?> mainClass, final String fieldName)
            throws NoSuchFieldException, SecurityException {
        final Field field = mainClass.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field;
    }

    /**
     * Scans all classes accessible from the context class loader which belong to the given package and
     * subpackages.
     *
     * @param packageName
     *         The base package
     * @return The classes
     *
     * @throws ClassNotFoundException
     * @throws java.io.IOException
     */
    public static Class[] getClasses(final String packageName)
            throws ClassNotFoundException, IOException {
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        assert classLoader != null;
        final String path = packageName.replace('.', '/');
        final Enumeration<URL> resources = classLoader.getResources(path);
        final List<File> dirs = new ArrayList<File>();

        while (resources.hasMoreElements()) {
            final URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }

        final ArrayList<Class> classes = new ArrayList<Class>();

        for (final File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }

        return classes.toArray(new Class[classes.size()]);
    }

    /**
     * Recursive method used to find all classes in a given DIRECTORY and subdirs.
     *
     * @param directory
     *         The base DIRECTORY
     * @param packageName
     *         The package name for classes found inside the base DIRECTORY
     * @return The classes
     *
     * @throws ClassNotFoundException
     */
    public static List<Class> findClasses(File directory, final String packageName)
            throws ClassNotFoundException {
        final List<Class> classes = new ArrayList<Class>();
        try {

            directory = new File(URLDecoder.decode(directory.getAbsolutePath(), "UTF-8"));
        } catch (final UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (!directory.exists()) {
            Kanon.LOGGER.error("Can't access the packages.");
            return classes;
        }

        final File[] files = directory.listFiles();

        for (final File file : files) {
            if (file.isDirectory()) {
                if (file.getName().contains(".")) {
                    continue;
                }
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                classes.add(Class.forName(packageName + '.' +
                        file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return classes;
    }
}