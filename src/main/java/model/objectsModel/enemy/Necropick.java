package model.objectsModel.enemy;

import controller.audio.players.AudioPlayer;
import controller.logic.GameState;
import controller.util.Constants;
import model.Paintable.Paintable;
import model.collision.Collidable;
import model.collision.Collision;
import model.collision.CollisionHandler;
import model.movable.Movable;
import model.objectsModel.epsilon.Bullet;
import model.objectsModel.epsilon.Epsilon;
import org.w3c.dom.Attr;
import view.gameGUI.GamePanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Necropick implements Paintable, Movable, Collidable {
    private int HP = 10;
    private double x;
    private double y;
    private double size = 50;
    private final int radiusFromEpsilon = 180;
    private boolean played;
    private Image image;
    private java.util.Timer timer;
    private boolean disappear;

    public Necropick(double x, double y) {
        this.x = x;
        this.y = y;
        try {
            Image yourImage = (Image) ImageIO.read(Constants.NECRO_PICK);
            image = yourImage.getScaledInstance((int) size, (int) size, Image.SCALE_DEFAULT);

        } catch (Exception e) {
            System.out.println("exception in necropick paint");
        }
        timer = new java.util.Timer();
        timer.schedule(new TimerTask() {
            int count = 0;

            @Override
            public void run() {
                if (disappear) {
                    disappear = false;
                    playAudio();
                    fire();
                } else {
                    if (count % 2 == 0) {
                        disappear = true;
                    }
                    count++;
                }
            }
        }, 0, 4000);
    }

    public void playAudio() {
        if (!played) {
            AudioPlayer.play(AudioPlayer.GROAN);
            played = true;
        }
    }

    @Override
    public void selfPaint(Graphics g, JPanel panel) {
        int locX = panel.getX();
        int locY = panel.getY();
        if (!disappear) g.drawImage(image, (int) x - locX, (int) y - locY, panel);
    }

    @Override
    public void move() {

//        x += vx;
//        y += vy;
//
//        if (maxVelocityX > 0) {
//            if (vx < maxVelocityX) {
//                vx += accX;
//            }
//        } else {
//            if (vx > maxVelocityX) {
//                vx += accX;
//            }
//        }
//
//        if (maxVelocityY > 0) {
//            if (vy < maxVelocityY) {
//                vy += accY;
//            }
//        } else {
//            if (vy > maxVelocityY) {
//                vy += accY;
//            }
//        }
    }

    private boolean calculated;
    private double destinationX;
    private double destinationY;

    @Override
    public void calculateMovingDirection(double x, double y) {
        if (!disappear) {

//            if (!calculated) {
//                if (new Random().nextBoolean()) {
//                    if (new Random().nextBoolean()) {
//                        y += 100;
//                    } else {
//                        y -= 100;
//                    }
//                } else {
//                    if (new Random().nextBoolean()) {
//                        x += 100;
//                    } else {
//                        x -= 100;
//                    }
//                }
//                destinationX = x;
//                destinationY = y;
//            }
//            double angle = Math.atan2(destinationY - this.y, destinationX - this.x);
//            maxVelocityX = constantVelocity * Math.cos(angle);
//            maxVelocityY = constantVelocity * Math.sin(angle);
//            accX = Math.cos(angle);
//            accY = Math.sin(angle);
//            calculated = true;
        } else {
            if (new Random().nextBoolean()) {
                if (new Random().nextBoolean()) {
                    y += radiusFromEpsilon;
                } else {
                    y -= radiusFromEpsilon;
                }
                x -= size / 2;
            } else {
                if (new Random().nextBoolean()) {
                    x += radiusFromEpsilon;
                } else {
                    x -= radiusFromEpsilon;
                }
                y -= size / 2;
            }
            this.x = x;
            this.y = y;
            calculated = false;
        }
    }

    void fire() {
        Bullet bullet = new Bullet(x + size / 2, y + size / 2, false, Color.GRAY,5);
        bullet.calculateMovingDirection(x + size / 2, y + size / 2 + 5);
        GameState.bullets.add(bullet);
        bullet = new Bullet(x + size / 2, y + size / 2, false, Color.GRAY,5);
        bullet.calculateMovingDirection(x + size / 2 + 5, y + size / 2);
        GameState.bullets.add(bullet);
        bullet = new Bullet(x + size / 2, y + size / 2, false, Color.GRAY,5);
        bullet.calculateMovingDirection(x + size / 2 - 5, y + size / 2);
        GameState.bullets.add(bullet);
        bullet = new Bullet(x + size / 2, y + size / 2, false, Color.GRAY,5);
        bullet.calculateMovingDirection(x + size / 2, y + size / 2 - 5);
        GameState.bullets.add(bullet);

        bullet = new Bullet(x + size / 2, y + size / 2, false, Color.GRAY,5);
        bullet.calculateMovingDirection(x + size / 2 - 5, y + size / 2 - 5);
        GameState.bullets.add(bullet);
        bullet = new Bullet(x + size / 2, y + size / 2, false, Color.GRAY,5);
        bullet.calculateMovingDirection(x + size / 2 + 5, y + size / 2 - 5);
        GameState.bullets.add(bullet);
        bullet = new Bullet(x + size / 2, y + size / 2, false, Color.GRAY,5);
        bullet.calculateMovingDirection(x + size / 2 + 5, y + size / 2 + 5);
        GameState.bullets.add(bullet);
        bullet = new Bullet(x + size / 2, y + size / 2, false, Color.GRAY,5);
        bullet.calculateMovingDirection(x + size / 2 - 5, y + size / 2 + 5);
        GameState.bullets.add(bullet);
    }

    public void checkCollisions() {
        Point2D epsilonCollisionPoint = Collision.checkEpsilonCollision(this);
        if (epsilonCollisionPoint != null) {
            CollisionHandler.stopEpsilon(epsilonCollisionPoint);
        }
        for (int j = 0; j < GameState.trigoraths.size(); j++) {
            Point2D collisionPoint = Collision.checkTwoPolyEntityCollision(this, GameState.trigoraths.get(j));
            if (collisionPoint != null) {
                CollisionHandler.handleCollisionOnPoint(collisionPoint);
            }
        }
        for (int j = 0; j < GameState.squarantines.size(); j++) {
            Point2D collisionPoint = Collision.checkTwoPolyEntityCollision(this, GameState.squarantines.get(j));
            if (collisionPoint != null) {
                CollisionHandler.handleCollisionOnPoint(collisionPoint);
            }
        }
        for (int j = 0; j < GameState.omenocts.size(); j++) {
            Point2D collisionPoint = Collision.checkTwoPolyEntityCollision(this, GameState.omenocts.get(j));
            if (collisionPoint != null) {
                disappear = true;
            }
        }
        for (int j = 0; j < GameState.wyrms.size(); j++) {
            Point2D collisionPoint = Collision.checkTwoPolyEntityCollision(this, GameState.wyrms.get(j));
            if (collisionPoint != null) {
                CollisionHandler.handleCollisionOnPoint(collisionPoint);
                GameState.wyrms.get(j).changeRotation();
            }
        }
    }

    @Override
    public int[] getXPoints() {
        return new int[]{(int) (x + 15), (int) (x + 30), (int) (x + 30), (int) (x + 15)};
    }

    @Override
    public int[] getYPoints() {
        return new int[]{(int) (y), (int) (y), (int) (y + size), (int) (y + size)};
    }

    public int[] getRelativeXPoints(JPanel panel) {
        int locationX = panel.getX();
        return new int[]{(int) getXPoints()[0] - locationX, (int) getXPoints()[1] - locationX,
                (int) getXPoints()[2] - locationX, (int) getXPoints()[3] - locationX};
    }

    public int[] getRelativeYPoints(JPanel panel) {
        int locationY = panel.getY();
        return new int[]{(int) getYPoints()[0] - locationY, (int) getYPoints()[1] - locationY,
                (int) getYPoints()[2] - locationY, (int) getYPoints()[3] - locationY};
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public Timer getTimer() {
        return timer;
    }

    public double getSize() {
        return size;
    }
}
