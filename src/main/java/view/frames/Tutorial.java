package view.frames;

import Controller.Constants;
import view.Jcomponents.MyButton;
import view.Jcomponents.MyLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Tutorial extends JFrame {
    public Tutorial() {
        getContentPane().setBackground(Constants.DARK_BLUE);
        setTitle("Skill Tree");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Constants.SKILL_TREE_FRAME_WIDTH, Constants.SKILL_TREE_FRAME_HEIGHT);
        setLocationRelativeTo(null);
        setLayout(null);
        setVisible(true);
        setResizable(false);
        MyLabel label = new MyLabel("RUN" , 300 ,110, 500 , 100 );
        add(label);
        MyButton back = new MyButton("BAck", 400, 550, Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MainMenu();
            }
        });
        add(back);
    }
}
