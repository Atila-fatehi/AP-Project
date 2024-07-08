package view.frames;

import controller.Constants;
import controller.FileController;
import view.Jcomponents.MyButton;

import javax.swing.*;
import java.awt.event.*;

public class KeyBinding extends JFrame {
    private boolean LFU, LFD, LFR, LFL, LFS, LFA;
    private int w = -1, a = -1, s = -1, d = -1, sh = -1, ab = -1;

    public KeyBinding() {
        getContentPane().setBackground(Constants.DARK_BLUE);
        setTitle("Setting");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Constants.SKILL_TREE_FRAME_WIDTH, Constants.SKILL_TREE_FRAME_HEIGHT);
        setLocationRelativeTo(null);
        setLayout(null);
        setVisible(true);
        setResizable(false);

        MyButton up = new MyButton("UP", 200, 100, 110, 110, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        add(up);
        MyButton down = new MyButton("DOWN", 200, 220, 110, 110, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        add(down);
        MyButton right = new MyButton("RIGHT", 320, 220, 110, 110, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        add(right);
        MyButton left = new MyButton("LEFT", 80, 220, 110, 110, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        add(left);

        MyButton shop = new MyButton("SHOP", 520, 220, 110, 110, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        add(shop);

        MyButton ability = new MyButton("ABILITY", 660, 220, 110, 110, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        add(ability);

        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    ability.setEnabled(true);
                    shop.setEnabled(true);
                    left.setEnabled(true);
                    right.setEnabled(true);
                    up.setEnabled(true);
                    down.setEnabled(true);
                } else {
                    if (LFA) {
                        ab = e.getKeyCode();
                    }
                    if (LFS) {
                        sh = e.getKeyCode();
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
                    ability.setEnabled(true);
                    shop.setEnabled(true);
                    left.setEnabled(true);
                    right.setEnabled(true);
                    up.setEnabled(true);
                    down.setEnabled(true);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        ability.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                makeAllFalse();
                LFA = true;
                ability.setEnabled(false);
                shop.setEnabled(true);
                left.setEnabled(true);
                right.setEnabled(true);
                up.setEnabled(true);
                down.setEnabled(true);
            }
        });
        shop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                makeAllFalse();
                LFS = true;
                ability.setEnabled(true);
                shop.setEnabled(false);
                left.setEnabled(true);
                right.setEnabled(true);
                up.setEnabled(true);
                down.setEnabled(true);
            }
        });
        left.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                makeAllFalse();
                LFL = true;
                ability.setEnabled(true);
                shop.setEnabled(true);
                left.setEnabled(false);
                right.setEnabled(true);
                up.setEnabled(true);
                down.setEnabled(true);
            }
        });
        down.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                makeAllFalse();
                LFD = true;
                ability.setEnabled(true);
                shop.setEnabled(true);
                left.setEnabled(true);
                right.setEnabled(true);
                up.setEnabled(true);
                down.setEnabled(false);
            }
        });
        right.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                makeAllFalse();
                LFR = true;
                ability.setEnabled(true);
                shop.setEnabled(true);
                left.setEnabled(true);
                right.setEnabled(false);
                up.setEnabled(true);
                down.setEnabled(true);
            }
        });
        up.addActionListener(e -> {
            makeAllFalse();
            LFU = true;
            ability.setEnabled(true);
            shop.setEnabled(true);
            left.setEnabled(true);
            right.setEnabled(true);
            up.setEnabled(false);
            down.setEnabled(true);
        });

        MyButton back = new MyButton("Back", 300, 550, Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                FileController.writeKeys(w,s,d,a,sh,ab);
                new MainMenu();
            }
        });
        add(back);
    }

    void makeAllFalse(){
        LFU = false;
        LFD = false;
        LFR = false;
        LFL = false;
        LFS = false;
        LFA = false;
    }
}
