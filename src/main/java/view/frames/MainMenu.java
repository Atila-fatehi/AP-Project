package view.frames;

import Controller.Constants;
import Controller.FrameController;
import audio.MusicPlayer;
import audio.MusicPlayer2;
import view.Jcomponents.MyButton;
import view.gameGUI.GameFrame;
import view.images.WallpaperPainter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.PrintWriter;
import java.nio.file.Paths;

public class MainMenu extends JFrame {

    public MainMenu() {
        setTitle("WindowKill");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);

        WallpaperPainter wallpaper = new WallpaperPainter();
        wallpaper.setBounds(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);

        file();

        MusicPlayer2 musicPlayer2 = MusicPlayer2.getInstance();
        if (!musicPlayer2.isPlaying()) {
            musicPlayer2.play();
        }

        MyButton newGame = new MyButton("New Game", Constants.BUTTON_INITIAL_X, Constants.BUTTON_INITIAL_Y, Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                MusicPlayer2.getInstance().getClip().stop();
                MusicPlayer2.getInstance().setPlaying(false);
                MusicPlayer musicPlayer = MusicPlayer.getInstance();
                if (!musicPlayer.isPlaying()) {
                    musicPlayer.play();
                }
                FrameController.minimizeAllWindows();
                new GameFrame();
            }
        });

        MyButton setting = new MyButton("Setting", Constants.BUTTON_INITIAL_X, Constants.BUTTON_INITIAL_Y + Constants.BUTTON_MARGIN, Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Setting();
            }
        });

        MyButton tutorial = new MyButton("Tutorial", Constants.BUTTON_INITIAL_X, Constants.BUTTON_INITIAL_Y + Constants.BUTTON_MARGIN * 2, Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Tutorial();
            }
        });

        MyButton skillTree = new MyButton("Skill Tree", Constants.BUTTON_INITIAL_X, Constants.BUTTON_INITIAL_Y + Constants.BUTTON_MARGIN * 3, Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new SkillTree();
            }
        });

        MyButton credit = new MyButton("Credit", Constants.BUTTON_INITIAL_X, Constants.BUTTON_INITIAL_Y + Constants.BUTTON_MARGIN * 4, Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        MyButton exit = new MyButton("Exit", Constants.BUTTON_INITIAL_X, Constants.BUTTON_INITIAL_Y + Constants.BUTTON_MARGIN * 5, Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        getContentPane().add(newGame);
        getContentPane().add(setting);
        getContentPane().add(tutorial);
        getContentPane().add(skillTree);
        getContentPane().add(credit);
        getContentPane().add(exit);
        getContentPane().add(wallpaper);

        setVisible(true);
    }

    public void file() {
        File file = new File(Paths.get("").toAbsolutePath() + "\\src\\main\\java\\dataBase\\settings.txt");
        if (!file.exists()) {
            try {
                PrintWriter printWriter = new PrintWriter(file);
                printWriter.println(50);
                printWriter.println(1);
                printWriter.flush();
                printWriter.close();
            } catch (Exception e) {

            }
        }
        file = new File(Paths.get("").toAbsolutePath() + "\\src\\main\\java\\dataBase\\abilityCode.txt");
        if (!file.exists()) {
            try {
                PrintWriter printWriter = new PrintWriter(file);
                printWriter.println(0);
                printWriter.flush();
                printWriter.close();
            } catch (Exception e) {

            }
        }
        file = new File(Paths.get("").toAbsolutePath() + "\\src\\main\\java\\dataBase\\XP.txt");
        if (!file.exists()) {
            try {
                PrintWriter printWriter = new PrintWriter(file);
                printWriter.println(0);
                printWriter.flush();
                printWriter.close();
            } catch (Exception e) {

            }
        }
        file = new File(Paths.get("").toAbsolutePath() + "\\src\\main\\java\\dataBase\\Keys.txt");
        if (!file.exists()) {
            try {
                PrintWriter printWriter = new PrintWriter(file);
                printWriter.println(KeyEvent.VK_W);
                printWriter.println(KeyEvent.VK_A);
                printWriter.println(KeyEvent.VK_S);
                printWriter.println(KeyEvent.VK_D);
                printWriter.println(KeyEvent.VK_SPACE);
                printWriter.println(KeyEvent.VK_R);
                printWriter.flush();
                printWriter.close();
            } catch (Exception e) {

            }
        }
    }
}