package se.proxus.kanon.wrapper;

import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se.proxus.kanon.Kanon;

public class LoggerWrapper {

    private final String name;
    private static LoggerWrapper instance;

    public LoggerWrapper(final String name) {
        this.name = name;
    }

    public void info(final Object out) {
        getLogger().info("[" + getName() + "] " + out);
    }

    public void debug(final Object out) {
        System.out.println("[" + getName() + "] [DEBUG] " + out);
    }

    public void trace(final Object out) {
        getLogger().trace("[" + getName() + "] " + out);
    }

    public void fatal(final Object out) {
        getLogger().fatal("[" + getName() + "] " + out);
    }

    public void error(final Object out) {
        getLogger().error("[" + getName() + "] " + out);
    }

    public void warn(final Object out) {
        getLogger().warn("[" + getName() + "] " + out);
    }

    public void infof(final String out, final Object... args) {
        getLogger().info("[" + getName() + "] " + String.format(out, args));
    }

    public void debugf(final String out, final Object... args) {
        System.out.println("[" + getName() + "] [DEBUG] " + String.format(out, args));
    }

    public void tracef(final String out, final Object... args) {
        getLogger().trace("[" + getName() + "] " + String.format(out, args));
    }

    public void fatalf(final String out, final Object... args) {
        getLogger().fatal("[" + getName() + "] " + String.format(out, args));
    }

    public void errorf(final String out, final Object... args) {
        getLogger().error("[" + getName() + "] " + String.format(out, args));
    }

    public void warnf(final String out, final Object... args) {
        getLogger().warn("[" + getName() + "] " + String.format(out, args));
    }

    private String getName() {
        return name;
    }

    private Logger getLogger() {
        return LogManager.getLogger(getName());
    }

    private Kanon getKanon() {
        return Minecraft.kanon;
    }

    public static LoggerWrapper getInstance(final String name) {
        if (instance == null) {
            instance = new LoggerWrapper(name);
            return instance;
        } else {
            return instance;
        }
    }
}