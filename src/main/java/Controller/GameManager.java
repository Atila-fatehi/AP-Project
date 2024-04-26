package Controller;

import Model.Bullet;
import Model.Epsilon;
import Model.Squarantine;
import Model.Trigorath;
import UserInterface.GameGUI.GameFrame;
import UserInterface.GameGUI.GamePanel;
import util.cal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
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
    private cal cal;

    public GameManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        epsilon = new Epsilon(350, 350, 13);
        gamePanel.setEpsilon(epsilon);
        trigoraths.add(new Trigorath(100, 100, 100 + 30, 100, 100 + 15, 100 - 25));
        trigoraths.add(new Trigorath(150, 150, 150 + 30, 150, 150 + 15, 150 - 25));
        trigoraths.add(new Trigorath(500, 500, 500 + 30, 500, 500 + 15, 500 - 25));
        trigoraths.add(new Trigorath(500, 300, 500 + 30, 300, 500 + 15, 300 - 25));
        // squarantines.add(new Squarantine(500, 500, 500 + 25, 500, 500 + 25, 500 + 25, 500, 500 + 25));
        cal = new cal();
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
        //Tri collision
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
        //Squ collision
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
        //wall collision
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
                for (int j = 0; j < squarantines.size(); j++) {
                    squarantines.get(j).shiftX(expandRate);
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
                for (int j = 0; j < squarantines.size(); j++) {
                    squarantines.get(j).shiftY(expandRate);
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
            Point2D epsilonCollisionPoint = trigoraths.get(i).onEpsilonCollision(epsilon.getX(), epsilon.getY(), epsilon.getRadius());
            if (epsilonCollisionPoint != null) {
                impactOnPoint(epsilonCollisionPoint);
            }
            for (int j = 0; j < trigoraths.size(); j++) {
                if (i != j) {
                    Point2D trigorathCollisionPoint = trigoraths.get(i).onTrigorathCollision(trigoraths.get(j).getX1(), trigoraths.get(j).getX2(), trigoraths.get(j).getX3(), trigoraths.get(j).getY1(), trigoraths.get(j).getY2(), trigoraths.get(j).getY3());
                    if (trigorathCollisionPoint != null) {
                        impactOnPoint(trigorathCollisionPoint);
                    }
                }
            }
            if (trigoraths.get(i).getHP() <= 0) {
                trigoraths.remove(i);
                i--;
            }
        }
        //squarantine stuff
        for (int i = 0; i < squarantines.size(); i++) {
            squarantines.get(i).calculateMovingDirection(epsilon.getX(), epsilon.getY());
            squarantines.get(i).move();
            for (int j = 0; j < squarantines.size(); j++) {
                if (i != j) {
                    if (squarantines.get(i).onSquarantineCollision(squarantines.get(j).getX1(), squarantines.get(j).getX2(), squarantines.get(j).getX3(), squarantines.get(j).getX4(), squarantines.get(j).getY1(), squarantines.get(j).getY2(), squarantines.get(j).getY3(), squarantines.get(j).getY4()) != 0) {
                        System.out.println("s s");
                    }
                }
            }
            for (int j = 0; j < trigoraths.size(); j++) {
                if (squarantines.get(i).onTrigorathCollision(trigoraths.get(j).getX1(), trigoraths.get(j).getX2(), trigoraths.get(j).getX3(), trigoraths.get(j).getY1(), trigoraths.get(j).getY2(), trigoraths.get(j).getY3()) != 0) {
                    System.out.println("s t");
                }
            }
            int epsilonCollisionNum = squarantines.get(i).onEpsilonCollision(epsilon.getX(), epsilon.getY(), epsilon.getRadius());
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

    public void impactOnPoint(Point2D collisionPoint) {
        double rate = 12;
        for (int i = 0; i < trigoraths.size(); i++) {
            if (cal.distance(trigoraths.get(i).getCenterOfGravity().getX(), trigoraths.get(i).getCenterOfGravity().getY(), collisionPoint.getX(), collisionPoint.getY()) <= 100) {
                double angle = Math.atan2(collisionPoint.getY() - trigoraths.get(i).getCenterOfGravity().getY(), collisionPoint.getX() - trigoraths.get(i).getCenterOfGravity().getX());
                trigoraths.get(i).setVx(-(rate - 5) * Math.cos(angle));
                trigoraths.get(i).setVy(-(rate - 5) * Math.sin(angle));
            }
        }
        if (cal.distance(epsilon.getX(), epsilon.getY(), collisionPoint.getX(), collisionPoint.getY()) <= 100) {
            if (collisionPoint.getX() >= epsilon.getX() && collisionPoint.getY() >= epsilon.getY()) {
                epsilon.setVx(-rate);
                epsilon.setVy(-rate);
                epsilon.setDecU(true);
                epsilon.setDecL(true);
                epsilon.setDecD(true);
                epsilon.setDecR(true);
            }
            if (collisionPoint.getX() <= epsilon.getX() && collisionPoint.getY() <= epsilon.getY()) {
                epsilon.setVx(rate);
                epsilon.setVy(rate);
                epsilon.setDecU(true);
                epsilon.setDecL(true);
                epsilon.setDecD(true);
                epsilon.setDecR(true);
            }
            if (collisionPoint.getX() <= epsilon.getX() && collisionPoint.getY() >= epsilon.getY()) {
                epsilon.setVx(rate);
                epsilon.setVy(-rate);
                epsilon.setDecU(true);
                epsilon.setDecL(true);
                epsilon.setDecD(true);
                epsilon.setDecR(true);
            }
            if (collisionPoint.getX() >= epsilon.getX() && collisionPoint.getY() <= epsilon.getY()) {
                epsilon.setVx(-rate);
                epsilon.setVy(rate);
                epsilon.setDecU(true);
                epsilon.setDecL(true);
                epsilon.setDecD(true);
                epsilon.setDecR(true);
            }
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
