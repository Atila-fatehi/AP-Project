package view.gameGUI;

import controller.GameManager;
import controller.util.Constants;
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
                //TODO NOT CLEAN
                if (e.getKeyCode() == KeyController.shop) {
                    GameManager.getInstance().setPaused(!GameManager.getInstance().isPaused());
                    dispose();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        getContentPane().setBackground(Constants.DARK_BLUE);
        setTitle("SHOP");
        setFocusable(true);
        requestFocus();
        Border border = BorderFactory.createLineBorder(Color.WHITE, 2);
        getRootPane().setBorder(border);
        setUndecorated(true);
        setSize(700, 700);
        setLocationRelativeTo(null);
        setLayout(null);
        setVisible(true);
        setResizable(false);


        MyLabel xp = new MyLabel("XP : " + GameManager.getInstance().getEpsilon().getXP(), 200, 100, 300, 100);
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
                if (GameManager.getInstance().getEpsilon().getXP() >= 100) {
                    GameManager.getInstance().getEpsilon().setXP(GameManager.getInstance().getEpsilon().getXP() - 100);
                    GameManager.getInstance().setPaused(!GameManager.getInstance().isPaused());
                    dispose();
                    GameManager.getInstance().impactOnPointWithoutEpsilon(new Point2D.Double(GameManager.getInstance().getEpsilon().getX(), GameManager.getInstance().getEpsilon().getY()));
                }
            }
        });
        MyButton xp75 = new MyButton("75 XP", 400, 300, Constants.BUTTON_WIDTH - 100, Constants.BUTTON_HEIGHT, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (GameManager.getInstance().getEpsilon().getXP() >= 75) {
                    GameManager.getInstance().getEpsilon().setXP(GameManager.getInstance().getEpsilon().getXP() - 75);
                    GameManager.getInstance().setPaused(!GameManager.getInstance().isPaused());
                    dispose();

                    GameManager.getInstance().setEmpower(true);
                    java.util.Timer timer = new java.util.Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            GameManager.getInstance().setEmpower(false);
                            timer.cancel();
                        }
                    }, 10000, 100);
                }
            }
        });
        MyButton xp50 = new MyButton("50 XP", 400, 375, Constants.BUTTON_WIDTH - 100, Constants.BUTTON_HEIGHT, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (GameManager.getInstance().getEpsilon().getXP() >= 50) {
                    GameManager.getInstance().getEpsilon().setXP(GameManager.getInstance().getEpsilon().getXP() - 50);
                    GameManager.getInstance().getEpsilon().setHP(GameManager.getInstance().getEpsilon().getHP() + 10);
                    GameManager.getInstance().setPaused(!GameManager.getInstance().isPaused());
                    dispose();
                }
            }
        });

        MyButton done = new MyButton("Done", 200, 550, Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameManager.getInstance().setPaused(!GameManager.getInstance().isPaused());
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
