package view.gameGUI;

import controller.KeyController;
import controller.logic.GameManager;
import controller.logic.GameState;
import controller.util.Constants;
import model.collision.CollisionHandler;
import model.objectsModel.Ability;
import model.objectsModel.epsilon.Epsilon;
import view.Jcomponents.MyButton;
import view.Jcomponents.MyLabel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Point2D;
import java.util.TimerTask;

public class ShopFramePage2 extends JFrame{
    static boolean inCoolDown = false;
    public ShopFramePage2() throws HeadlessException {
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyController.shop) {
                    GameManager.getInstance().setPaused(false);
                    dispose();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        setTitle("SHOP");
        setFocusable(true);
        requestFocus();
        getContentPane().setBackground(Constants.DARK_BLUE);
        Border border = BorderFactory.createLineBorder(Color.WHITE, 2);
        getRootPane().setBorder(border);
        setUndecorated(true);
        setSize(700, 700);
        setLocationRelativeTo(null);
        setLayout(null);
        setVisible(true);
        setResizable(false);

        MyLabel xp = new MyLabel("XP : " + Epsilon.getInstance().getXP(), 200, 100, 300, 100);
        MyLabel Dismay = new MyLabel("O’ Deimos, Dismay", 100, 225, 300, 50);
        MyLabel Slumber = new MyLabel("O’Hypnos, Slumber", 100, 270, 300, 100);
        MyLabel Slaughter = new MyLabel("O’ Phonoi, Slaughter", 100, 340, 300, 100);
        add(xp);
        add(Slaughter);
        add(Dismay);
        add(Slumber);

        MyButton xp100 = new MyButton("120 XP", 400, 225, Constants.BUTTON_WIDTH - 100, Constants.BUTTON_HEIGHT, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Epsilon.getInstance().getXP() >= 120) {
                    Epsilon.getInstance().setXP(Epsilon.getInstance().getXP() - 120);
                    Ability.dismay();
                    GameManager.getInstance().setPaused(false);
                    dispose();
                }
            }
        });
        MyButton xp75 = new MyButton("150 XP", 400, 300, Constants.BUTTON_WIDTH - 100, Constants.BUTTON_HEIGHT, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Epsilon.getInstance().getXP() >= 150) {
                    Epsilon.getInstance().setXP(Epsilon.getInstance().getXP() - 150);
                    Ability.slumber();
                    GameManager.getInstance().setPaused(false);
                    dispose();
                }
            }
        });
        MyButton xp50 = new MyButton("200 XP", 400, 375, Constants.BUTTON_WIDTH - 100, Constants.BUTTON_HEIGHT, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Epsilon.getInstance().getXP() >= 200 && !inCoolDown) {
                    Epsilon.getInstance().setXP(Epsilon.getInstance().getXP() - 200);
                    inCoolDown = true;
                    Ability.slaughter();
                    java.util.Timer timer = new java.util.Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            inCoolDown = false;
                            timer.cancel();
                        }
                    }, 120000, 1111);
                    GameManager.getInstance().setPaused(false);
                    dispose();
                }
            }
        });

        MyButton done = new MyButton("Prev", 200, 550, Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ShopFrame();
                dispose();
            }
        });
        add(xp50);
        add(xp75);
        add(xp100);
        add(done);
        setVisible(true);
    }
}
