package view.frames;

import controller.Constants;
import controller.FileController;
import controller.audio.players.GameMusicPlayer;
import audio.players.MenuMusicPlayer;
import view.Jcomponents.MyButton;
import view.Jcomponents.MyLabel;

import javax.sound.sampled.FloatControl;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Setting extends JFrame {

    public Setting() {
        getContentPane().setBackground(Constants.DARK_BLUE);
        setTitle("Setting");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Constants.FRAMES_WIDTH, Constants.FRAMES_HEIGHT);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);
        setVisible(true);

        MyLabel sense = new MyLabel("Sensitivity", 100, 10, Constants.LABEL_WIDTH, Constants.LABEL_HEIGHT);
        MyLabel vol = new MyLabel("Volume", 100, 120, Constants.LABEL_WIDTH, Constants.LABEL_HEIGHT);
        MyLabel dif = new MyLabel("Difficulty", 100, 220, Constants.LABEL_WIDTH, Constants.LABEL_HEIGHT);
        add(sense);
        add(dif);
        add(vol);

        JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
        slider.setMajorTickSpacing(10);
        slider.setMinorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setBounds(100, 100, Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT);
        slider.setBackground(Constants.DARK_BLUE);
        add(slider);

        JSlider slider1 = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
        slider1.setMajorTickSpacing(10);
        slider1.setMinorTickSpacing(1);
        slider1.setPaintTicks(true);
        slider1.setPaintLabels(true);
        slider1.setBounds(100, 200, Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT);
        slider1.setBackground(Constants.DARK_BLUE);
        add(slider1);
        slider1.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                float volume = (float) slider1.getValue() / 100;
                if (GameMusicPlayer.getInstance().getClip() != null) {
                    FloatControl control = (FloatControl) GameMusicPlayer.getInstance().getClip().getControl(FloatControl.Type.MASTER_GAIN);
                    control.setValue(20f * (float) Math.log10(volume == 0 ? 0.0001 : volume));
                }
                if (MenuMusicPlayer.getInstance().getClip() != null) {
                    FloatControl control = (FloatControl) MenuMusicPlayer.getInstance().getClip().getControl(FloatControl.Type.MASTER_GAIN);
                    control.setValue(20f * (float) Math.log10(volume == 0 ? 0.0001 : volume));
                }
            }
        });

        JSlider slider2 = new JSlider(JSlider.HORIZONTAL, 1, 3, 2);
        slider2.setMajorTickSpacing(1);
        slider2.setMinorTickSpacing(1);
        slider2.setPaintTicks(true);
        slider2.setPaintLabels(true);
        slider2.setBounds(100, 300, Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT);
        slider2.setBackground(Constants.DARK_BLUE);
        add(slider2);

        MyButton keyBinding = new MyButton("Key Binding", 100, 480, Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                FileController.writeSettings(String.valueOf(slider.getValue()), String.valueOf(slider2.getValue()));
                new KeyBinding();
            }
        });

        MyButton back = new MyButton("Back", 100, 550, Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                FileController.writeSettings(String.valueOf(slider.getValue()), String.valueOf(slider2.getValue()));
                new MainMenu();
            }
        });
        add(keyBinding);
        add(back);
    }
}
