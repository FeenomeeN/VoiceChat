package client;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import java.io.DataOutputStream;
import java.io.IOException;

public class VoiceSender extends Thread {
    DataOutputStream out = null;
    public static boolean running = true;

    public void setRunning(boolean running) {
        running = running;
    }

    VoiceSender(DataOutputStream out) {
        this.out = out;
        start();
    }

    @Override
    public void run() {
        AudioFormat format = new AudioFormat(8000.F, 16, 1, true, false);
        TargetDataLine mic = null;
        try {
            mic = AudioSystem.getTargetDataLine(format);
            mic.open(format);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
        System.out.println("Start recording");
        mic.start();

        byte[] bytes = new byte[(int) (mic.getFormat().getSampleRate() * 0.4)];
        while (running) {
            int count = mic.read(bytes, 0, bytes.length);
            if (count > 0) {
                try {
                    out.write(bytes, 0, count);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
