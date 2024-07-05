package audio.players;

import Controller.Constants;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class MenuMusicPlayer {
    private static MenuMusicPlayer instance;
    private Clip clip;
    private boolean isPlaying;

    public static MenuMusicPlayer getInstance() {
        if (instance == null) {
            synchronized (MenuMusicPlayer.class) {
                if (instance == null) {
                    instance = new MenuMusicPlayer();
                }
            }
        }
        return instance;
    }

    public void start() {
        if (!isPlaying) {
            play();
        }
    }

    public void stop() {
        MenuMusicPlayer.getInstance().getClip().stop();
        MenuMusicPlayer.getInstance().setPlaying(false);
    }


    public void play() {
        isPlaying = true;
        try {
            File file = new File(Constants.DAVE_PATH);
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
