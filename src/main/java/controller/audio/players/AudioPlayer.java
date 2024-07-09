package controller.audio.players;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.nio.file.Paths;

public abstract class AudioPlayer {


    public static final File WAVE = new File(Paths.get("").toAbsolutePath() + "/src/main/java/controller/audio/hugeWave.wav");
    public static final File AWOOGA = new File(Paths.get("").toAbsolutePath() + "/src/main/java/controller/audio/awooga.wav");
    public static final File SPLAT = new File(Paths.get("").toAbsolutePath() + "/src/main/java/controller/audio/splat.wav");
    public static final File GROAN = new File(Paths.get("").toAbsolutePath() + "/src/main/java/controller/audio/groan.wav");
    public static final File MELON_IMPACT = new File(Paths.get("").toAbsolutePath() + "/src/main/java/controller/audio/melonImpact.wav");
    public static final File LOSE_MUSIC = new File(Paths.get("").toAbsolutePath() + "/src/main/java/controller/audio/loseMusic.wav");
    public static final File WIN_MUSIC = new File(Paths.get("").toAbsolutePath() + "/src/main/java/controller/audio/winMusic.wav");
    public static final File SCREAM = new File(Paths.get("").toAbsolutePath() + "/src/main/java/controller/audio/scream.wav");
    public static final File COIN = new File(Paths.get("").toAbsolutePath() + "/src/main/java/controller/audio/coin.wav");
    public static final File PAUSE = new File(Paths.get("").toAbsolutePath() + "/src/main/java/controller/audio/pause.wav");
    public static void play(File file){
        try {
            Clip clip;
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file.getAbsoluteFile());
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        }catch (Exception e){

        }
    }
}
