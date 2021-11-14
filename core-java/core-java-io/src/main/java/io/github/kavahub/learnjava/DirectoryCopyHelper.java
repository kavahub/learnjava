package io.github.kavahub.learnjava;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;

import lombok.experimental.UtilityClass;

public class DirectoryCopyHelper {

    @UtilityClass
    public static class CoreOld {

        public void copyDirectoryJavaUnder7(File source, File destination) throws IOException {
            if (source.isDirectory()) {
                copyDirectory(source, destination);
            } else {
                copyFile(source, destination);
            }
        }
    
        private void copyDirectory(File sourceDirectory, File destinationDirectory) throws IOException {
            if (!destinationDirectory.exists()) {
                destinationDirectory.mkdir();
            }
            for (String f : sourceDirectory.list()) {
                copyDirectoryJavaUnder7(new File(sourceDirectory, f), new File(destinationDirectory, f));
            }
        }
    
        private void copyFile(File sourceFile, File destinationFile) throws IOException {
            try (InputStream in = new FileInputStream(sourceFile); OutputStream out = new FileOutputStream(destinationFile)) {
                byte[] buf = new byte[1024];
                int length;
                while ((length = in.read(buf)) > 0) {
                    out.write(buf, 0, length);
                }
            }
        }
    }

    @UtilityClass
    public static class JavaNio {

        public void copyDirectory(String sourceDirectoryLocation, String destinationDirectoryLocation) throws IOException {
            Files.walk(Paths.get(sourceDirectoryLocation))
              .forEach(source -> {
                  Path destination = Paths.get(destinationDirectoryLocation, source.toString()
                    .substring(sourceDirectoryLocation.length()));
                  try {
                      Files.copy(source, destination);
                  } catch (IOException e) {
                      e.printStackTrace();
                  }
              });
        }
    }
    
    @UtilityClass
    public static class ApacheCommons {

        public void copyDirectory(String sourceDirectoryLocation, String destinationDirectoryLocation) throws IOException {
            File sourceDirectory = new File(sourceDirectoryLocation);
            File destinationDirectory = new File(destinationDirectoryLocation);
            FileUtils.copyDirectory(sourceDirectory, destinationDirectory);
        }
    }
}
