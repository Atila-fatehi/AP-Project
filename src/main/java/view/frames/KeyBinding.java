package view.frames;

import controller.util.Constants;
import controller.FileController;
import view.Jcomponents.MyButton;

import javax.swing.*;
import java.awt.event.*;

public class KeyBinding extends JFrame {
    private boolean LFU, LFD, LFR, LFL, LFS, LFA;
    private int w = -1, a = -1, s = -1, d = -1, sh = -1, ab = -1;
    private final MyButton up, down, right, left, shop, ability;

    public KeyBinding() {
        getContentPane().setBackground(Constants.DARK_BLUE);
        setTitle("Setting");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Constants.SKILL_TREE_FRAME_WIDTH, Constants.SKILL_TREE_FRAME_HEIGHT);
        setLocationRelativeTo(null);
        setLayout(null);
        setVisible(true);
        setResizable(false);

        up = new MyButton("UP", 200, 100, 110, 110, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        add(up);
        down = new MyButton("DOWN", 200, 220, 110, 110, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        add(down);
        right = new MyButton("RIGHT", 320, 220, 110, 110, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        add(right);
        left = new MyButton("LEFT", 80, 220, 110, 110, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        add(left);

        shop = new MyButton("SHOP", 520, 220, 110, 110, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        add(shop);

        ability = new MyButton("ABILITY", 660, 220, 110, 110, new ActionListener() {
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
                    makeAllEnabled();
                    changeAllColor();
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
                    makeAllEnabled();
                    changeAllColor();
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
                makeAllEnabled();
                ability.setEnabled(false);
            }
        });
        shop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                makeAllFalse();
                LFS = true;
                makeAllEnabled();
                shop.setEnabled(false);
            }
        });
        left.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                makeAllFalse();
                LFL = true;
                makeAllEnabled();
                left.setEnabled(false);
            }
        });
        down.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                down.changeColor();
                makeAllFalse();
                LFD = true;
                makeAllEnabled();
                down.setEnabled(false);
            }
        });
        right.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                right.changeColor();
                makeAllFalse();
                LFR = true;
                makeAllEnabled();
                right.setEnabled(false);
            }
        });
        up.addActionListener(e -> {
            up.changeColor();
            makeAllFalse();
            LFU = true;
            makeAllEnabled();
            up.setEnabled(false);
        });

        MyButton back = new MyButton("Back", 300, 550, Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                FileController.writeKeys(w, s, d, a, sh, ab);
                new MainMenu();
            }
        });
        add(back);
    }

    void makeAllFalse() {
        LFU = false;
        LFD = false;
        LFR = false;
        LFL = false;
        LFS = false;
        LFA = false;
    }

    void makeAllEnabled() {
        ability.setEnabled(true);
        shop.setEnabled(true);
        left.setEnabled(true);
        right.setEnabled(true);
        up.setEnabled(true);
        down.setEnabled(true);
    }

    void changeAllColor() {
        ability.resetColor();
        shop.resetColor();
        up.resetColor();
        down.resetColor();
        right.resetColor();
        left.resetColor();
    }
}
