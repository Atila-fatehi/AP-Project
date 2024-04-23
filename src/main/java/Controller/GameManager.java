package Controller;

import Model.Bullet;
import Model.Epsilon;
import Model.Trigorath;
import UserInterface.GameGUI.GameFrame;
import UserInterface.GameGUI.GamePanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class GameManager {
    private final GamePanel gamePanel;
    private final Epsilon epsilon;
    private boolean paused;
    private static final int expandRate = 15;
    private final ArrayList<Bullet> bullets = new ArrayList<>();
    private final ArrayList<Trigorath> trigoraths = new ArrayList<>();

    public GameManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        trigoraths.add(new Trigorath(100, 100 + 20, 100 + 10, 100, 100, 100 - 18));
        epsilon = new Epsilon(350, 350, 13);
        gamePanel.setEpsilon(epsilon);
        new Timer((int) (double) TimeUnit.SECONDS.toMillis(1) / 60/*GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0].getDisplayMode().getRefreshRate()*/, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!paused) {
                    updateView();
                }
            }
        }) {{
            setCoalesce(true);
        }}.start();
        new Timer((int) (double) TimeUnit.SECONDS.toMillis(1) / 100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!paused) {
                    updateModel();
                }
            }
        }) {{
            setCoalesce(true);
        }}.start();

    }

    public void updateView() {
        gamePanel.shrink();
        gamePanel.setBullets(bullets);
        gamePanel.setTrigoraths(trigoraths);
        gamePanel.repaint();
    }

    public void updateModel() {
        //On Trigorath Collision
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).move();
            for (int j = 0; j < trigoraths.size(); j++) {
                int trigorathCollisionNum = bullets.get(i).onTrigorathCollision(trigoraths.get(j).getX1(), trigoraths.get(j).getX2(), trigoraths.get(j).getX3(), trigoraths.get(j).getY1(), trigoraths.get(j).getY2(), trigoraths.get(j).getY3());
                if (trigorathCollisionNum == 1) {
                    trigoraths.get(j).setHP(trigoraths.get(j).getHP() - 5);
                    bullets.remove(i);
                    i--;
                }
                if (trigorathCollisionNum == 2) {
                    trigoraths.get(j).setHP(trigoraths.get(j).getHP() - 5);
                    bullets.remove(i);
                    i--;
                }
                if (trigorathCollisionNum == 3) {
                    trigoraths.get(j).setHP(trigoraths.get(j).getHP() - 5);
                    bullets.remove(i);
                    i--;
                }
                if (trigorathCollisionNum == 4) {
                    trigoraths.get(j).setHP(trigoraths.get(j).getHP() - 5);
                    bullets.remove(i);
                    i--;
                }
            }
        }
        //On Wall Collision
        for (int i = 0; i < bullets.size(); i++) {
            int wallCollisionNum = bullets.get(i).onWallCollision(gamePanel.getScreenWidth(), gamePanel.getScreenHeight());
            if (wallCollisionNum == 1) {
                gamePanel.setLocation(gamePanel.getLocationX() - expandRate, gamePanel.getLocationY());
                gamePanel.setSize(gamePanel.getScreenWidth() + expandRate, gamePanel.getScreenHeight());
                gamePanel.setScreenWidth(gamePanel.getScreenWidth() + expandRate);
                gamePanel.setLocationX(gamePanel.getLocationX() - expandRate);
                epsilon.setX(epsilon.getX() + expandRate);
                for (int j = 0; j < trigoraths.size(); j++) {
                    trigoraths.get(j).shiftX(expandRate);
                }
                bullets.remove(i);
                i--;
            } else if (wallCollisionNum == 2) {
                gamePanel.setLocation(gamePanel.getLocationX(), gamePanel.getLocationY() - expandRate);
                gamePanel.setSize(gamePanel.getScreenWidth(), gamePanel.getScreenHeight() + expandRate);
                gamePanel.setScreenHeight(gamePanel.getScreenHeight() + expandRate);
                gamePanel.setLocationY(gamePanel.getLocationY() - expandRate);
                epsilon.setY(epsilon.getY() + expandRate);
                for (int j = 0; j < trigoraths.size(); j++) {
                    trigoraths.get(j).shiftY(expandRate);
                }
                bullets.remove(i);
                i--;
            } else if (wallCollisionNum == 3) {
                gamePanel.setSize(gamePanel.getScreenWidth() + expandRate, gamePanel.getScreenHeight());
                gamePanel.setScreenWidth(gamePanel.getScreenWidth() + expandRate);
                bullets.remove(i);
                i--;
            } else if (wallCollisionNum == 4) {
                gamePanel.setSize(gamePanel.getScreenWidth(), gamePanel.getScreenHeight() + expandRate);
                gamePanel.setScreenHeight(gamePanel.getScreenHeight() + expandRate);
                bullets.remove(i);
                i--;
            }
        }
        //Tri stuff
        for (int i = 0; i < trigoraths.size(); i++) {
            trigoraths.get(i).calculateMovingDirection(epsilon.getX(), epsilon.getY());
            trigoraths.get(i).move();
            int epsilonCollisionNum = trigoraths.get(i).onEpsilonCollision(epsilon.getX(), epsilon.getY(), epsilon.getRadius());
            if (epsilonCollisionNum == 1) {
                epsilon.setVx(-10);
                epsilon.setVy(10);
                epsilon.setHP(epsilon.getHP() - 10);
            }
            if (epsilonCollisionNum == 2) {
                epsilon.setVx(10);
                epsilon.setVy(10);
                epsilon.setHP(epsilon.getHP() - 10);
            }
            if (epsilonCollisionNum == 3) {
                epsilon.setVy(-14);
                epsilon.setHP(epsilon.getHP() - 10);
            }
            if (trigoraths.get(i).getHP() <= 0) {
                trigoraths.remove(i);
                i--;
            }
        }

        //epsilon stuff
        epsilon.move();

        if (epsilon.getX() - epsilon.getRadius() < 0) {
            epsilon.setX(epsilon.getRadius());
            epsilon.setVx(0);
        } else if (epsilon.getX() + epsilon.getRadius() > gamePanel.getScreenWidth()) {
            epsilon.setX(gamePanel.getScreenWidth() - epsilon.getRadius());
            epsilon.setVx(0);
        }

        if (epsilon.getY() - epsilon.getRadius() < 0) {
            epsilon.setY(epsilon.getRadius());
            epsilon.setVy(0);
        } else if (epsilon.getY() + epsilon.getRadius() > gamePanel.getScreenHeight()) {
            epsilon.setY(gamePanel.getScreenHeight() - epsilon.getRadius());
            epsilon.setVy(0);
        }

    }

    public void mouseClicked(int x, int y) {
        Bullet bullet = new Bullet(epsilon.getX(), epsilon.getY());
        double angle = Math.atan2(y - epsilon.getY(), x - epsilon.getX());
        bullet.setVx(bullet.getConstantVelocity() * Math.cos(angle));
        bullet.setVy(bullet.getConstantVelocity() * Math.sin(angle));
        bullets.add(bullet);
    }


    //GETTER SETTERS

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }
}
