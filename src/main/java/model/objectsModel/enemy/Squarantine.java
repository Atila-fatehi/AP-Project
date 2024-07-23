package model.objectsModel.enemy;

import controller.audio.players.AudioPlayer;
import controller.logic.GameState;
import controller.util.Constants;
import controller.util.CostumeTimer;
import model.Paintable.Paintable;
import model.collision.Collidable;
import model.collision.Collision;
import model.collision.CollisionHandler;
import model.movable.Movable;
import model.objectsModel.epsilon.Epsilon;
import view.gameGUI.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class Squarantine implements Movable, Collidable, Paintable, Serializable {

    private int HP = 10;
    private double posXHP;
    private double posYHP;
    private final double[] xPoints;
    private final double[] yPoints;
    private double constantVelocity = 1d;
    private double maxVelocityX;
    private double maxVelocityY;
    private double vx;
    private double vy;
    private double accX;
    private double accY;
    private boolean played;
    String id;

    public Squarantine(double[] x, double[] y) {
        this.xPoints = x;
        this.yPoints = y;
        id = String.valueOf(UUID.randomUUID());
        randomAggression();
    }

    public void randomAggression() {
        Random random = new Random();
        java.util.Timer timer = new java.util.Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (random.nextBoolean()) {
                    constantVelocity = 5d;
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (Exception e) {

                    }
                    constantVelocity = 1d;
                }
            }
        }, 5000, 3000);
        CostumeTimer.getInstance().newTimer(id, timer);
    }

    public void calculateMovingDirection(double x, double y) {
        double angle = Math.atan2(y - (yPoints[0] + yPoints[2]) / 2, x - (xPoints[0] + xPoints[1]) / 2);
        maxVelocityX = constantVelocity * Math.cos(angle);
        maxVelocityY = constantVelocity * Math.sin(angle);
        accX = Math.cos(angle);
        accY = Math.sin(angle);
    }

    public void move() {
        xPoints[0] += vx;
        xPoints[1] += vx;
        xPoints[2] += vx;
        xPoints[3] += vx;
        yPoints[0] += vy;
        yPoints[1] += vy;
        yPoints[2] += vy;
        yPoints[3] += vy;

        if (maxVelocityX > 0) {
            if (vx < maxVelocityX) {
                vx += accX;
            }
        } else {
            if (vx > maxVelocityX) {
                vx += accX;
            }
        }

        if (maxVelocityY > 0) {
            if (vy < maxVelocityY) {
                vy += accY;
            }
        } else {
            if (vy > maxVelocityY) {
                vy += accY;
            }
        }

        if (HP >= 10) {
            posXHP = xPoints[0] + 5;
            posYHP = yPoints[0] + 17;
        } else {
            posXHP = xPoints[0] + 9;
            posYHP = yPoints[0] + 17;
        }
    }

    public void playAudio() {
        if (getXPoints()[0] >= GamePanel.getInstance().getX() &&
                getXPoints()[0] <= GamePanel.getInstance().getPanelWidth() + GamePanel.getInstance().getX()
                && getYPoints()[0] >= GamePanel.getInstance().getY() &&
                getXPoints()[0] <= GamePanel.getInstance().getPanelHeight() + GamePanel.getInstance().getY()) {
            if (!played) {
                AudioPlayer.play(AudioPlayer.GROAN);
                played = true;
            }
        }
    }

    public void checkCollisions() {
        Point2D epsilonCollisionPoint = Collision.checkEpsilonCollision(this);
        if (epsilonCollisionPoint != null) {
            CollisionHandler.handleCollisionOnPoint(epsilonCollisionPoint);
            Epsilon.getInstance().setHP(Epsilon.getInstance().getHP() - 6);
        }

        for (int j = 0; j < GameState.trigoraths.size(); j++) {
            Point2D collisionPoint = Collision.checkTwoPolyEntityCollision(this, GameState.trigoraths.get(j));
            if (collisionPoint != null) {
                CollisionHandler.handleCollisionOnPoint(collisionPoint);
            }
        }
        for (int j = 0; j < GameState.squarantines.size(); j++) {
            if (GameState.squarantines.get(j) != this) {
                Point2D collisionPoint = Collision.checkTwoPolyEntityCollision(this, GameState.squarantines.get(j));
                if (collisionPoint != null) {
                    CollisionHandler.handleCollisionOnPoint(collisionPoint);
                }
            }
        }
        for (int j = 0; j < GameState.omenocts.size(); j++) {
            Point2D collisionPoint = Collision.checkTwoPolyEntityCollision(this, GameState.omenocts.get(j));
            if (collisionPoint != null) {
                CollisionHandler.handleCollisionOnPoint(collisionPoint);
            }
        }
        for (int j = 0; j < GameState.necropicks.size(); j++) {
            Point2D collisionPoint = Collision.checkTwoPolyEntityCollision(this, GameState.necropicks.get(j));
            if (collisionPoint != null) {
                CollisionHandler.handleCollisionOnPoint(collisionPoint);
            }
        }
        for (int j = 0; j < GameState.wyrms.size(); j++) {
            Point2D collisionPoint = Collision.checkTwoPolyEntityCollision(this, GameState.wyrms.get(j));
            if (collisionPoint != null) {
                CollisionHandler.handleCollisionOnPoint(collisionPoint);
                GameState.wyrms.get(j).changeRotation();
            }
        }
        for (int j = 0; j < GameState.barricados.size(); j++) {
            Point2D collisionPoint = Collision.checkTwoPolyEntityCollision(this, GameState.barricados.get(j));
            if (collisionPoint != null) {
                CollisionHandler.handleCollisionOnPoint(collisionPoint);
            }
        }

    }

    public Point2D getCenterOfGravity() {
        return new Point2D.Double((xPoints[0] + xPoints[1]) / 2, (yPoints[0] + yPoints[2]) / 2);
    }

    public int[] getXPoints() {
        return new int[]{(int) xPoints[0], (int) xPoints[1], (int) xPoints[2], (int) xPoints[3]};
    }

    public int[] getYPoints() {
        return new int[]{(int) yPoints[0], (int) yPoints[1], (int) yPoints[2], (int) yPoints[3]};
    }

    public int[] getRelativeXPoints(JPanel panel) {
        int locationX = panel.getX();
        return new int[]{(int) xPoints[0] - locationX, (int) xPoints[1] - locationX,
                (int) xPoints[2] - locationX, (int) xPoints[3] - locationX};
    }

    public int[] getRelativeYPoints(JPanel panel) {
        int locationY = panel.getY();
        return new int[]{(int) yPoints[0] - locationY, (int) yPoints[1] - locationY,
                (int) yPoints[2] - locationY, (int) yPoints[3] - locationY};
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public void setVx(double vx) {
        this.vx = vx;
    }

    public void setVy(double vy) {
        this.vy = vy;
    }

    public Timer getTimer() {
        if (CostumeTimer.getInstance().getMap().get(id) != null) {
            return CostumeTimer.getInstance().getMap().get(id);
        } else {
            return new Timer();
        }


    }

    @Override
    public void selfPaint(Graphics g, JPanel panel) {
        int locX = panel.getX();
        int locY = panel.getY();

        g.setColor(Constants.SQUA_GREEN);
        g.fillPolygon(getRelativeXPoints(panel), getRelativeYPoints(panel), xPoints.length);
        g.setColor(Color.BLACK);
        g.drawString(String.valueOf(HP), (int) posXHP - locX, (int) posYHP - locY);

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
