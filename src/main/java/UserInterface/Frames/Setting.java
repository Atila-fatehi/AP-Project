package UserInterface.Frames;

import audio.MusicPlayer;

import javax.sound.sampled.FloatControl;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.Scanner;

public class Setting extends JFrame {
    private static final Color back = new Color(0x9A1A03);
    private static final Color fore = new Color(0xFB8B24);

    public Setting() {
        getContentPane().setBackground(new Color(0x011022));
        setTitle("Setting");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 700);
        setLocationRelativeTo(null);
        setLayout(null);
        setVisible(true);
        setResizable(false);

        JLabel label = new JLabel("Sensitivity");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setBounds(100, 10, 300, 100);
        label.setFont(new Font("HelveticaNeue-CondensedBlack", Font.BOLD, 25));
        label.setForeground(new Color(0xFB8B24));
        add(label);
        JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
        slider.setMajorTickSpacing(10);
        slider.setMinorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setBounds(100,100,300,50);
        slider.setBackground(new Color(0x011022));
        add(slider);


        JLabel label1 = new JLabel("Volume");
        label1.setHorizontalAlignment(JLabel.CENTER);
        label1.setBounds(100, 120, 300, 100);
        label1.setFont(new Font("HelveticaNeue-CondensedBlack", Font.BOLD, 25));
        label1.setForeground(new Color(0xFB8B24));
        add(label1);
        JSlider slider1 = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
        slider1.setMajorTickSpacing(10);
        slider1.setMinorTickSpacing(1);
        slider1.setPaintTicks(true);
        slider1.setPaintLabels(true);
        slider1.setBounds(100,200,300,50);
        slider1.setBackground(new Color(0x011022));
        add(slider1);
        slider1.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                float volume = (float) slider1.getValue() / 100;
                if (MusicPlayer.getInstance().getClip() != null) {
                    FloatControl control = (FloatControl) MusicPlayer.getInstance().getClip().getControl(FloatControl.Type.MASTER_GAIN);
                    control.setValue(20f * (float) Math.log10(volume == 0 ? 0.0001 : volume));
                }
            }
        });

        JLabel label2 = new JLabel("Difficulty");
        label2.setHorizontalAlignment(JLabel.CENTER);
        label2.setBounds(100, 220, 300, 100);
        label2.setFont(new Font("HelveticaNeue-CondensedBlack", Font.BOLD, 25));
        label2.setForeground(new Color(0xFB8B24));
        add(label2);
        JSlider slider2 = new JSlider(JSlider.HORIZONTAL, 1, 3, 2);
        slider2.setMajorTickSpacing(1);
        slider2.setMinorTickSpacing(1);
        slider2.setPaintTicks(true);
        slider2.setPaintLabels(true);
        slider2.setBounds(100,300,300,50);
        slider2.setBackground(new Color(0x011022));
        add(slider2);
        JButton button2 = new JButton("Key Binding");
        button2.setBounds(100, 480, 300, 50);
        button2.setFocusable(false);
        button2.setHorizontalAlignment(JButton.CENTER);
        button2.setHorizontalTextPosition(JButton.CENTER);
        button2.setBackground(back);
        button2.setForeground(fore);
        button2.setFont(new Font("HelveticaNeue-CondensedBlack", Font.BOLD, 25));
        add(button2);
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                writeToFile(slider.getValue(), slider2.getValue());
                new KeyBinding();
            }
        });
        JButton button1 = new JButton("Back");
        button1.setBounds(100, 550, 300, 50);
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
                writeToFile(slider.getValue(), slider2.getValue());
                new MainMenu();
            }
        });
    }

    public void writeToFile(int val1 , int val2){
        File file = new File(Paths.get("").toAbsolutePath() + "\\src\\main\\java\\dataBase\\settings.txt");
        try {
            PrintWriter printWriter = new PrintWriter(file);
            printWriter.println(String.valueOf(val1));
            printWriter.println(String.valueOf(val2));
            printWriter.flush();
            printWriter.close();
        }catch (Exception e){

        }


    }
}
