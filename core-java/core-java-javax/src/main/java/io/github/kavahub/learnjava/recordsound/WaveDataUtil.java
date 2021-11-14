package io.github.kavahub.learnjava.recordsound;

import java.io.File;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

public class WaveDataUtil {

    public boolean saveToFile(String name, AudioFileFormat.Type fileType, AudioInputStream audioInputStream) {
        System.out.println("Saving...");
        if (null == name || null == fileType || audioInputStream == null) {
            return false;
        }

        int i = 0;
        File myFile = new File(name + "." + fileType.getExtension());
        while (myFile.exists()) {
            String temp = "" + i + myFile.getName();
            myFile = new File(temp);
            i++;
        }

        try {
            audioInputStream.reset();
            AudioSystem.write(audioInputStream, fileType, myFile);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        System.out.println("Saved " + myFile.getAbsolutePath());
        return true;
    } 
}
