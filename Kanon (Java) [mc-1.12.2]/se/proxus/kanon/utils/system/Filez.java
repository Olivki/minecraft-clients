package se.proxus.kanon.utils.system;

import lombok.experimental.UtilityClass;
import net.minecraft.util.Util;

import java.io.File;

@UtilityClass
public final class Filez {
    
    public static File getDirectory(final File directory, final String name) {
        lombok.val queryDir = new File(directory, name);
        
        if (queryDir.exists()) {
            return queryDir;
        } else {
            if (queryDir.mkdirs())
                return queryDir;
            else
                throw new NullPointerException("Couldn't create the directories required for \"" +
                                                queryDir.getAbsolutePath() + "\".");
        }
    }

    public static File createDirectory(final File parent, final String name) {
        final File directory = new File(parent, name);

        directory.mkdirs();

        return directory;
    }

    public static File getAppDir(final String fileName) {
        final String userHome = System.getProperty("user.home");
        final File file;

        switch (Util.getOSType()) {
            case SOLARIS:
                file = new File(userHome, fileName + '/');
                break;

            case WINDOWS:
                final String appData = System.getenv("APPDATA");

                if (appData != null) {
                    file = new File(appData, fileName + '/');
                } else {
                    file = new File(userHome, fileName + '/');
                }

                break;

            case OSX:
                file = new File(userHome, "Library/Application Support/" + fileName);
                break;

            default:
                file = new File(userHome, fileName + '/');
        }

        if (!file.exists() && !file.mkdirs()) {
            throw new RuntimeException("The working DIRECTORY could not be created: " + file);
        } else {
            return file;
        }
    }
    
}
