package Controller;

import Model.Bullet;
import Model.Epsilon;
import Model.Squarantine;
import Model.Trigorath;
import UserInterface.GameGUI.GameFrame;
import UserInterface.GameGUI.GamePanel;

import javax.swing.*;
import java.awt.*;
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
    private final ArrayList<Squarantine> squarantines = new ArrayList<>();

    public GameManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        epsilon = new Epsilon(350, 350, 13);
        gamePanel.setEpsilon(epsilon);
        trigoraths.add(new Trigorath(100, 100, 100 + 30, 100, 100 + 15, 100 - 25));
        trigoraths.add(new Trigorath(150, 150, 150 + 30, 150, 150 + 15, 150 - 25));
//        trigoraths.add(new Trigorath(500, 500, 500 + 30, 500, 500 + 15, 500 - 25.5));
//        trigoraths.add(new Trigorath(500, 300, 500 + 30, 300, 500 + 15, 300 - 25.5));
        squarantines.add(new Squarantine(500, 500, 500 + 25, 500, 500 + 25, 500 + 25, 500, 500 + 25));

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
        gamePanel.setSquarantines(squarantines);
        gamePanel.repaint();
    }

    public void updateModel() {
        //Bullet stuff
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).move();
            for (int j = 0; j < trigoraths.size(); j++) {
                int trigorathCollisionNum = bullets.get(i).onTrigorathCollision(trigoraths.get(j).getX1(), trigoraths.get(j).getX2(), trigoraths.get(j).getX3(), trigoraths.get(j).getY1(), trigoraths.get(j).getY2(), trigoraths.get(j).getY3());
                if (trigorathCollisionNum != 0) {
                    trigoraths.get(j).setHP(trigoraths.get(j).getHP() - 5);
                    bullets.remove(i);
                    i--;
                    break;
                }
            }
        }
        for (int i = 0; i < bullets.size(); i++) {
            for (int j = 0; j < squarantines.size(); j++) {
                int squarantineCollisionNum = bullets.get(i).onSquarantineCollision(squarantines.get(j).getX1(), squarantines.get(j).getX2(), squarantines.get(j).getX3(), squarantines.get(j).getX4(), squarantines.get(j).getY1(), squarantines.get(j).getY2(), squarantines.get(j).getY3(), squarantines.get(j).getY4());
                if (squarantineCollisionNum != 0) {
                    squarantines.get(j).setHP(squarantines.get(j).getHP() - 5);
                    bullets.remove(i);
                    i--;
                    break;
                }
            }
        }
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
            for (int j = 0; j < trigoraths.size(); j++) {
                if (i != j) {
                    if (trigoraths.get(i).onTrigorathCollision(trigoraths.get(j).getX1(), trigoraths.get(j).getX2(), trigoraths.get(j).getX3(), trigoraths.get(j).getY1(), trigoraths.get(j).getY2(), trigoraths.get(j).getY3()) != 0) {

                    }
                }
            }
//            int epsilonCollisionNum = trigoraths.get(i).onEpsilonCollision(epsilon.getX(), epsilon.getY(), epsilon.getRadius());
//            if (epsilonCollisionNum == 1) {
//                epsilon.setVx(-10);
//                epsilon.setVy(10);
//                epsilon.setHP(epsilon.getHP() - 10);
//            }
//            if (epsilonCollisionNum == 2) {
//                epsilon.setVx(10);
//                epsilon.setVy(10);
//                epsilon.setHP(epsilon.getHP() - 10);
//            }
//            if (epsilonCollisionNum == 3) {
//                epsilon.setVy(-14);
//                epsilon.setHP(epsilon.getHP() - 10);
//            }
//            if (epsilonCollisionNum == 4) {
//                epsilon.setVy(14);
//            }
//            if (epsilonCollisionNum == 5) {
//                epsilon.setVx(10);
//                epsilon.setVy(-10);
//            }
//
//            if (epsilonCollisionNum == 6) {
//                epsilon.setVx(-10);
//                epsilon.setVy(-10);
//            }
            if (trigoraths.get(i).getHP() <= 0) {
                trigoraths.remove(i);
                i--;
            }
        }
        //squarantine stuff
        for (int i = 0; i < squarantines.size(); i++) {
            squarantines.get(i).calculateMovingDirection(epsilon.getX(), epsilon.getY());
            squarantines.get(i).move();
//            int epsilonCollisionNum = trigoraths.get(i).onEpsilonCollision(epsilon.getX(), epsilon.getY(), epsilon.getRadius());
//            if (epsilonCollisionNum == 1) {
//                epsilon.setVx(-10);
//                epsilon.setVy(10);
//                epsilon.setHP(epsilon.getHP() - 10);
//            }
//            if (epsilonCollisionNum == 2) {
//                epsilon.setVx(10);
//                epsilon.setVy(10);
//                epsilon.setHP(epsilon.getHP() - 10);
//            }
//            if (epsilonCollisionNum == 3) {
//                epsilon.setVy(-14);
//                epsilon.setHP(epsilon.getHP() - 10);
//            }
//            if (epsilonCollisionNum == 4) {
//                epsilon.setVy(14);
//            }
//            if (epsilonCollisionNum == 5) {
//                epsilon.setVx(10);
//                epsilon.setVy(-10);
//            }
//
//            if (epsilonCollisionNum == 6) {
//                epsilon.setVx(-10);
//                epsilon.setVy(-10);
//            }
            if (squarantines.get(i).getHP() <= 0) {
                squarantines.remove(i);
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
        makeNewBullet(x, y);
    }

    public void makeNewBullet(int x, int y) {
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
