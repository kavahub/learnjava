package io.github.kavahub.learnjava;

import org.apache.commons.lang3.SystemUtils;

import lombok.experimental.UtilityClass;

/**
 * 操作系统判定
 * 
 * @author PinWei Wan
 * @since 1.0.0
 */
@UtilityClass
public class DetectOSExample {
    public String getOperatingSystem() {
        String os = System.getProperty("os.name");
        System.out.println("Using System Property: " + os);
        return os;
    }

    public String getOperatingSystemSystemUtils() {
        String os = SystemUtils.OS_NAME;
        System.out.println("Using SystemUtils: " + os);
        return os;
    }  

    public static void main(String[] args) {
        System.out.println("********** DETECT **********");
        DetectOSExample.getOperatingSystem();
        DetectOSExample.getOperatingSystemSystemUtils();
    }
}
