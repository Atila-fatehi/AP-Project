package UserInterface.Frames;

import audio.MusicPlayer;

import javax.sound.sampled.FloatControl;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.Scanner;

public class KeyBinding extends JFrame {
    private static final Color back = new Color(0x9A1A03);
    private static final Color fore = new Color(0xFB8B24);
    private boolean LFU, LFD, LFR, LFL, LFS, LFA;
    private int w = -1, a = -1, s = -1, d = -1, shop = -1, ability = -1;

    public KeyBinding() {
        getContentPane().setBackground(new Color(0x011022));
        setTitle("Setting");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);
        setLayout(null);
        setVisible(true);
        setResizable(false);
        JButton buttonU = new JButton("UP");
        buttonU.setBounds(200, 100, 110, 110);
        buttonU.setFocusable(false);
        buttonU.setHorizontalAlignment(JButton.CENTER);
        buttonU.setHorizontalTextPosition(JButton.CENTER);
        buttonU.setBackground(back);
        buttonU.setForeground(fore);
        buttonU.setFont(new Font("HelveticaNeue-CondensedBlack", Font.BOLD, 20));
        add(buttonU);
        JButton buttonD = new JButton("DOWN");
        buttonD.setBounds(200, 220, 110, 110);
        buttonD.setFocusable(false);
        buttonD.setHorizontalAlignment(JButton.CENTER);
        buttonD.setHorizontalTextPosition(JButton.CENTER);
        buttonD.setBackground(back);
        buttonD.setForeground(fore);
        buttonD.setFont(new Font("HelveticaNeue-CondensedBlack", Font.BOLD, 20));
        add(buttonD);
        JButton buttonR = new JButton("RIGHT");
        buttonR.setBounds(320, 220, 110, 110);
        buttonR.setFocusable(false);
        buttonR.setHorizontalAlignment(JButton.CENTER);
        buttonR.setHorizontalTextPosition(JButton.CENTER);
        buttonR.setBackground(back);
        buttonR.setForeground(fore);
        buttonR.setFont(new Font("HelveticaNeue-CondensedBlack", Font.BOLD, 20));
        add(buttonR);
        JButton buttonL = new JButton("LEFT");
        buttonL.setBounds(80, 220, 110, 110);
        buttonL.setFocusable(false);
        buttonL.setHorizontalAlignment(JButton.CENTER);
        buttonL.setHorizontalTextPosition(JButton.CENTER);
        buttonL.setBackground(back);
        buttonL.setForeground(fore);
        buttonL.setFont(new Font("HelveticaNeue-CondensedBlack", Font.BOLD, 20));
        add(buttonL);


        JButton buttonShop = new JButton("SHOP");
        buttonShop.setBounds(520, 220, 110, 110);
        buttonShop.setFocusable(false);
        buttonShop.setHorizontalAlignment(JButton.CENTER);
        buttonShop.setHorizontalTextPosition(JButton.CENTER);
        buttonShop.setBackground(back);
        buttonShop.setForeground(fore);
        buttonShop.setFont(new Font("HelveticaNeue-CondensedBlack", Font.BOLD, 20));
        add(buttonShop);

        JButton buttonAbility = new JButton("ABILITY");
        buttonAbility.setBounds(660, 220, 110, 110);
        buttonAbility.setFocusable(false);
        buttonAbility.setHorizontalAlignment(JButton.CENTER);
        buttonAbility.setHorizontalTextPosition(JButton.CENTER);
        buttonAbility.setBackground(back);
        buttonAbility.setForeground(fore);
        buttonAbility.setFont(new Font("HelveticaNeue-CondensedBlack", Font.BOLD, 20));
        add(buttonAbility);

        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    buttonAbility.setEnabled(true);
                    buttonShop.setEnabled(true);
                    buttonL.setEnabled(true);
                    buttonR.setEnabled(true);
                    buttonU.setEnabled(true);
                    buttonD.setEnabled(true);
                } else {
                    if (LFA) {
                        ability = e.getKeyCode();
                    }
                    if (LFS) {
                        shop = e.getKeyCode();
                    }
                    if (LFD) {
                        s = e.getKeyCode();
                    }
                    if (LFU) {
                        w = e.getKeyCode();
                    }
                    if (LFL) {
                        a = e.getKeyCode();
                    }
                    if (LFR) {
                        d = e.getKeyCode();
                    }
                    buttonAbility.setEnabled(true);
                    buttonShop.setEnabled(true);
                    buttonL.setEnabled(true);
                    buttonR.setEnabled(true);
                    buttonU.setEnabled(true);
                    buttonD.setEnabled(true);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        buttonAbility.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LFA = true;
                LFD = false;
                LFR = false;
                LFL = false;
                LFS = false;
                LFU = false;
                buttonAbility.setEnabled(false);
                buttonShop.setEnabled(true);
                buttonL.setEnabled(true);
                buttonR.setEnabled(true);
                buttonU.setEnabled(true);
                buttonD.setEnabled(true);
            }
        });
        buttonShop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LFS = true;
                LFD = false;
                LFR = false;
                LFL = false;
                LFU = false;
                LFA = false;
                buttonAbility.setEnabled(true);
                buttonShop.setEnabled(false);
                buttonL.setEnabled(true);
                buttonR.setEnabled(true);
                buttonU.setEnabled(true);
                buttonD.setEnabled(true);
            }
        });
        buttonL.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LFL = true;
                LFD = false;
                LFR = false;
                LFU = false;
                LFS = false;
                LFA = false;
                buttonAbility.setEnabled(true);
                buttonShop.setEnabled(true);
                buttonL.setEnabled(false);
                buttonR.setEnabled(true);
                buttonU.setEnabled(true);
                buttonD.setEnabled(true);
            }
        });
        buttonD.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LFD = true;
                LFU = false;
                LFR = false;
                LFL = false;
                LFS = false;
                LFA = false;
                buttonAbility.setEnabled(true);
                buttonShop.setEnabled(true);
                buttonL.setEnabled(true);
                buttonR.setEnabled(true);
                buttonU.setEnabled(true);
                buttonD.setEnabled(false);
            }
        });
        buttonR.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LFR = true;
                LFD = false;
                LFU = false;
                LFL = false;
                LFS = false;
                LFA = false;
                buttonAbility.setEnabled(true);
                buttonShop.setEnabled(true);
                buttonL.setEnabled(true);
                buttonR.setEnabled(false);
                buttonU.setEnabled(true);
                buttonD.setEnabled(true);
            }
        });
        buttonU.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LFU = true;
                LFD = false;
                LFR = false;
                LFL = false;
                LFS = false;
                LFA = false;
                buttonAbility.setEnabled(true);
                buttonShop.setEnabled(true);
                buttonL.setEnabled(true);
                buttonR.setEnabled(true);
                buttonU.setEnabled(false);
                buttonD.setEnabled(true);
            }
        });
        JButton button1 = new JButton("Back");
        button1.setBounds(300, 550, 300, 50);
        button1.setFocusable(false);
        button1.setHorizontalAlignment(JButton.CENTER);
        button1.setHorizontalTextPosition(JButton.CENTER);
        button1.setBackground(back);
        button1.setForeground(fore);
        button1.setFont(new Font("HelveticaNeue-CondensedBlack", Font.BOLD, 25));
        add(button1);

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                writeToFile();
                new MainMenu();
            }
        });
    }

    public void writeToFile() {
        File file = new File(Paths.get("").toAbsolutePath() + "\\src\\main\\java\\dataBase\\Keys.txt");
        try {
            PrintWriter printWriter = new PrintWriter(file);
            if (w != -1) {
                printWriter.println(w);
            }else{
                printWriter.println(KeyEvent.VK_W);
            }
            if (a != -1) {
                printWriter.println(a);
            }else{
                printWriter.println(KeyEvent.VK_A);
            }
            if (s != -1) {
                printWriter.println(s);
            }else{
                printWriter.println(KeyEvent.VK_S);
            }
            if (d != -1) {
                printWriter.println(d);
            }else{
                printWriter.println(KeyEvent.VK_D);
            }
            if (shop != -1) {
                printWriter.println(shop);
            }else{
                printWriter.println(KeyEvent.VK_SPACE);
            }
            if (ability != -1) {
                printWriter.println(ability);
            }else{
                printWriter.println(KeyEvent.VK_R);
            }
            printWriter.flush();
            printWriter.close();
        } catch (Exception e) {

        }
    }
}
