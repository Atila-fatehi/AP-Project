package audio;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.nio.file.Paths;

public class GameMusicPlayer {
    private static GameMusicPlayer instance;
    private Clip clip;
    private boolean isPlaying;

    GameMusicPlayer() {

    }

    public static GameMusicPlayer getInstance() {
        if (instance == null) {
            synchronized (GameMusicPlayer.class) {
                if (instance == null) {
                    instance = new GameMusicPlayer();
                }
            }
        }
        return instance;
    }

    public void start(){
        if (!isPlaying) {
            play();
        }
    }
    public void play(){
        isPlaying = true;
        try {
            File file = new File(Paths.get("").toAbsolutePath() + "\\src\\main\\java\\audio\\WateryGraves.wav");
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
