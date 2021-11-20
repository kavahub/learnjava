package io.github.kavahub.learnjava.recordsound;

import javax.sound.sampled.AudioFormat;

/**
 * 常量
 */
public class Properties {
    public final static AudioFormat.Encoding ENCODING = AudioFormat.Encoding.PCM_SIGNED;
    public final static float RATE = 44100.0f;
    public final static int CHANNELS = 1;
    public final static int SAMPLE_SIZE = 16;
    public final static boolean BIG_ENDIAN = true;    
}
