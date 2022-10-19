package kr.nanoit.extension;

import com.google.common.base.StandardSystemProperty;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.stream.Collectors;

public final class PathUtils {
    private PathUtils() {
    }

    public static void print() {
        Enumeration<?> enumeration = System.getProperties().propertyNames();
        while (enumeration.hasMoreElements()) {
            String key = (String) enumeration.nextElement();
            System.out.println(key + "=" + System.getProperty(key));
        }
    }

    public static String myBatisConfigPath() {
        return concat(userDir(), "config", "config.xml");
    }

    public static String dataBaseProperties() {
        return concat(userDir(), "config", "db.properties");
    }

    private static String concat(String... args) {
        return Arrays.stream(args).collect(Collectors.joining(StandardSystemProperty.FILE_SEPARATOR.value()));
    }

    private static String userDir() {
        return StandardSystemProperty.USER_DIR.value();
    }
}
