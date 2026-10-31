package com.softwaretestprecision.framework.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/** Reads config.properties from the classpath. System properties (-Dkey=value) win. */
public final class Config {

    private static final Properties PROPS = new Properties();

    static {
        try (InputStream in = Config.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (in == null) {
                throw new IllegalStateException("config.properties not found on the classpath");
            }
            PROPS.load(in);
        } catch (IOException e) {
            throw new IllegalStateException("Could not read config.properties", e);
        }
    }

    private Config() {}

    public static String get(String key) {
        String value = System.getProperty(key, System.getenv(key.toUpperCase()));
        if (value == null) value = PROPS.getProperty(key);
        if (value == null) throw new IllegalArgumentException("Missing config key: " + key);
        return value;
    }

    public static boolean getBool(String key) {
        return Boolean.parseBoolean(get(key));
    }

    public static int getInt(String key) {
        return Integer.parseInt(get(key));
    }
}
