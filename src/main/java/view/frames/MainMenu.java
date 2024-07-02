package view.frames;

import Controller.Constants;
import Controller.FrameController;
import audio.MusicPlayer;
import audio.MusicPlayer2;
import view.Jcomponents.MyButton;
import view.gameGUI.GameFrame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;

public class MainMenu extends JFrame {


    public static class wallpaper extends JPanel {
        public void paint(Graphics g) {
            super.paintComponent(g);

            try {
                g.drawImage(ImageIO.read(new File(Paths.get("").toAbsolutePath() + "\\src\\main\\java\\view\\images\\wallpaper.jpg")), 0, 0, this);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public MainMenu() {
        setTitle("WindowKill");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);
        wallpaper wallpaper = new wallpaper();
        wallpaper.setBounds(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);

        file();

        MusicPlayer2 musicPlayer2 = MusicPlayer2.getInstance();
        if(!musicPlayer2.isPlaying()) {
            musicPlayer2.play();
        }

        MyButton newGame = new MyButton("New Game", 150, 300, Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                MusicPlayer2.getInstance().getClip().stop();
                MusicPlayer2.getInstance().setPlaying(false);
                MusicPlayer musicPlayer = MusicPlayer.getInstance();
                if(!musicPlayer.isPlaying()) {
                    musicPlayer.play();
                }
                FrameController.minimizeAllWindows();
                new GameFrame();
            }
        });

        MyButton setting= new MyButton("Setting", 150, 370, Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Setting();
            }
        });

        MyButton tutorial= new MyButton("Tutorial", 150, 440, Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Tutorial();
            }
        });

        MyButton skillTree= new MyButton("Skill Tree", 150, 510, Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new SkillTree();
            }
        });

        MyButton credit = new MyButton("Credit", 150, 580, Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        MyButton exit = new MyButton("Exit", 150, 650, Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT, new ActionListener() {
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

    public void file(){
        File file = new File(Paths.get("").toAbsolutePath() + "\\src\\main\\java\\dataBase\\settings.txt");
        if(!file.exists()) {
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
        if(!file.exists()) {
            try {
                PrintWriter printWriter = new PrintWriter(file);
                printWriter.println(0);
                printWriter.flush();
                printWriter.close();
            } catch (Exception e) {

            }
        }
        file = new File(Paths.get("").toAbsolutePath() + "\\src\\main\\java\\dataBase\\XP.txt");
        if(!file.exists()) {
            try {
                PrintWriter printWriter = new PrintWriter(file);
                printWriter.println(0);
                printWriter.flush();
                printWriter.close();
            } catch (Exception e) {

            }
        }
        file = new File(Paths.get("").toAbsolutePath() + "\\src\\main\\java\\dataBase\\Keys.txt");
        if(!file.exists()) {
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