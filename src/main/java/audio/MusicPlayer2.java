package audio;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.nio.file.Paths;
import java.util.Objects;

public class MusicPlayer2 {
    private static MusicPlayer2 instance;
    private Clip clip;
    private boolean isPlaying;

    MusicPlayer2() {

    }

    public static MusicPlayer2 getInstance() {
        if (instance == null) {
            synchronized (MusicPlayer2.class) {
                if (instance == null) {
                    instance = new MusicPlayer2();
                }
            }
        }
        return instance;
    }
    public void play(){
        isPlaying = true;
        try {
            File file = new File(Paths.get("").toAbsolutePath() + "\\src\\main\\java\\audio\\CrazyDave.wav");
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        } catch (Exception e) {

        }
    }

    public Clip getClip() {
        return clip;
    }

    public void setClip(Clip clip) {
        this.clip = clip;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }
}
