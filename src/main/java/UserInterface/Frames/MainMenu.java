package UserInterface.Frames;

import UserInterface.GameGUI.GameFrame;
import audio.MusicPlayer;
import audio.MusicPlayer2;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

public class MainMenu extends JFrame {
    private static final Color back = new Color(0x9A1A03);
    private static final Color fore = new Color(0xFB8B24);
    private static final int HEIGHT = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    private static final int WIDTH = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();

    public static class wallpaper extends JPanel {
        public void paint(Graphics g) {
            super.paintComponent(g);

            try {
                g.drawImage(ImageIO.read(new File(Paths.get("").toAbsolutePath() + "\\src\\main\\java\\UserInterface\\images\\wallpaper.jpg")), 0, 0, this);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public MainMenu() {
        setTitle("WindowKill");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);
        wallpaper wallpaper = new wallpaper();
        wallpaper.setBounds(0, 0, WIDTH, HEIGHT);
        file();

        MusicPlayer2 musicPlayer2 = MusicPlayer2.getInstance();
        if(!musicPlayer2.isPlaying()) {
            musicPlayer2.play();
        }

        JButton button1 = new JButton("New Game");
        button1.setBounds(150, 300, 300, 50);
        button1.setFocusable(false);
        button1.setHorizontalAlignment(JButton.CENTER);
        button1.setHorizontalTextPosition(JButton.CENTER);
        button1.setBackground(back);
        button1.setForeground(fore);
        button1.setFont(new Font("HelveticaNeue-CondensedBlack", Font.BOLD, 25));
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                MusicPlayer2.getInstance().getClip().stop();
                MusicPlayer2.getInstance().setPlaying(false);
                MusicPlayer musicPlayer = MusicPlayer.getInstance();
                if(!musicPlayer.isPlaying()) {
                    musicPlayer.play();
                }
                minimizeAllWindows();
                new GameFrame();
            }
        });

        JButton button2 = new JButton("Setting");
        button2.setBounds(150, 370, 300, 50);
        button2.setFocusable(false);
        button2.setHorizontalAlignment(JButton.CENTER);
        button2.setHorizontalTextPosition(JButton.CENTER);
        button2.setBackground(back);
        button2.setForeground(fore);
        button2.setFont(new Font("HelveticaNeue-CondensedBlack", Font.BOLD, 25));
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Setting();
            }
        });

        JButton button3 = new JButton("Tutorial");
        button3.setBounds(150, 440, 300, 50);
        button3.setFocusable(false);
        button3.setHorizontalAlignment(JButton.CENTER);
        button3.setHorizontalTextPosition(JButton.CENTER);
        button3.setBackground(back);
        button3.setForeground(fore);
        button3.setFont(new Font("HelveticaNeue-CondensedBlack", Font.BOLD, 25));
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Tutorial();
            }
        });

        JButton button4 = new JButton("Skill Tree");
        button4.setBounds(150, 510, 300, 50);
        button4.setFocusable(false);
        button4.setHorizontalAlignment(JButton.CENTER);
        button4.setHorizontalTextPosition(JButton.CENTER);
        button4.setBackground(back);
        button4.setForeground(fore);
        button4.setFont(new Font("HelveticaNeue-CondensedBlack", Font.BOLD, 25));
        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new SkillTree();
            }
        });

        JButton button5 = new JButton("Credit");
        button5.setBounds(150, 580, 300, 50);
        button5.setFocusable(false);
        button5.setHorizontalAlignment(JButton.CENTER);
        button5.setHorizontalTextPosition(JButton.CENTER);
        button5.setBackground(back);
        button5.setForeground(fore);
        button5.setFont(new Font("HelveticaNeue-CondensedBlack", Font.BOLD, 25));
        button5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        JButton button6 = new JButton("Exit");
        button6.setBounds(150, 650, 300, 50);
        button6.setFocusable(false);
        button6.setHorizontalAlignment(JButton.CENTER);
        button6.setHorizontalTextPosition(JButton.CENTER);
        button6.setBackground(back);
        button6.setForeground(fore);
        button6.setFont(new Font("HelveticaNeue-CondensedBlack", Font.BOLD, 25));
        button6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        getContentPane().add(button1);
        getContentPane().add(button2);
        getContentPane().add(button3);
        getContentPane().add(button4);
        getContentPane().add(button5);
        getContentPane().add(button6);
        getContentPane().add(wallpaper);

        setVisible(true);
    }

    public void minimizeAllWindows() {
        try {
            Robot robot = new Robot();

            // Press the Windows key
            robot.keyPress(KeyEvent.VK_WINDOWS);
            robot.delay(100); // Delay to ensure the key press is registered

            // Press the M key
            robot.keyPress(KeyEvent.VK_M);
            robot.delay(100); // Delay

            // Release the M key
            robot.keyRelease(KeyEvent.VK_M);

            // Release the Windows key
            robot.keyRelease(KeyEvent.VK_WINDOWS);
        } catch (AWTException e) {
            e.printStackTrace();
        }
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