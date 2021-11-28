package io.github.kavahub.learnjava.recordsound;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;

/**
 * 程序入口
 * 
 * @author PinWei Wan
 * @since 1.0.0
 */
public class RecorderMain {
    
    public static void main(String[] args) throws Exception {

        AudioFormat format = buildAudioFormatInstance();

        SoundRecorder soundRecorder = new SoundRecorder();
        soundRecorder.build(format);

        System.out.println("Start recording ....");
        soundRecorder.start();
        Thread.sleep(20000);
        soundRecorder.stop();
        System.out.println("record end ....");

        WaveDataUtil wd = new WaveDataUtil();
        Thread.sleep(3000);
        wd.saveToFile("SoundClip", AudioFileFormat.Type.WAVE, soundRecorder.getAudioInputStream());
    }

    public static AudioFormat buildAudioFormatInstance() {
        AudioFormat.Encoding encoding = Properties.ENCODING;
        float rate = Properties.RATE;
        int channels = Properties.CHANNELS;
        int sampleSize = Properties.SAMPLE_SIZE;
        boolean bigEndian = Properties.BIG_ENDIAN;

        return new AudioFormat(encoding, rate, sampleSize, channels, (sampleSize / 8) * channels, rate, bigEndian);
    } 
}
