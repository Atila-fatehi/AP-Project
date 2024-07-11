package view.gameGUI;

import model.collision.CollisionHandler;
import controller.logic.GameManager;
import controller.util.Constants;
import controller.logic.GameState;
import model.objectsModel.Epsilon;
import view.Jcomponents.MyButton;
import controller.KeyController;
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

public class ShopFrame extends JFrame {
    public ShopFrame() throws HeadlessException {
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
        MyLabel banish = new MyLabel("O' Hephaestus، Banish", 100, 225, 300, 50);
        MyLabel empower = new MyLabel("O’ Athena، Empower", 100, 270, 300, 100);
        MyLabel heal = new MyLabel("O' Apollo Heal", 100, 340, 300, 100);
        add(xp);
        add(heal);
        add(banish);
        add(empower);

        MyButton xp100 = new MyButton("100 XP", 400, 225, Constants.BUTTON_WIDTH - 100, Constants.BUTTON_HEIGHT, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Epsilon.getInstance().getXP() >= 100) {
                    Epsilon.getInstance().setXP(Epsilon.getInstance().getXP() - 100);
                    GameManager.getInstance().setPaused(false);
                    dispose();
                    CollisionHandler.handleCollisionOnPointNoEpsilon(new Point2D.Double(Epsilon.getInstance().getX(), Epsilon.getInstance().getY()));
                }
            }
        });
        MyButton xp75 = new MyButton("75 XP", 400, 300, Constants.BUTTON_WIDTH - 100, Constants.BUTTON_HEIGHT, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Epsilon.getInstance().getXP() >= 75) {
                    Epsilon.getInstance().setXP(Epsilon.getInstance().getXP() - 75);
                    GameManager.getInstance().setPaused(false);
                    dispose();

                    GameManager.getInstance().setEmpower(true);
                    GameState.empower = true;
                    java.util.Timer timer = new java.util.Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            GameManager.getInstance().setEmpower(false);
                            GameState.empower = false;
                            timer.cancel();
                        }
                    }, 10000, 100);
                }
            }
        });
        MyButton xp50 = new MyButton("50 XP", 400, 375, Constants.BUTTON_WIDTH - 100, Constants.BUTTON_HEIGHT, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Epsilon.getInstance().getXP() >= 50) {
                    Epsilon.getInstance().setXP(Epsilon.getInstance().getXP() - 50);
                    Epsilon.getInstance().setHP(Epsilon.getInstance().getHP() + 10);
                    GameManager.getInstance().setPaused(false);
                    dispose();
                }
            }
        });

        MyButton done = new MyButton("Done", 200, 550, Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameManager.getInstance().setPaused(false);
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
