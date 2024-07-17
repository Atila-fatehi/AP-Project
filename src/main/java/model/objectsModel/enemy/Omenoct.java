package model.objectsModel.enemy;

import controller.audio.players.AudioPlayer;
import controller.logic.GameState;
import controller.util.Calculator;
import controller.util.Constants;
import model.Paintable.Paintable;
import model.collision.Collidable;
import model.collision.Collision;
import model.collision.CollisionHandler;
import model.movable.Movable;
import model.objectsModel.epsilon.Bullet;
import model.objectsModel.epsilon.Epsilon;
import view.gameGUI.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class Omenoct implements Paintable, Collidable, Movable {
    private int HP = 20;
    private double posXHP;
    private double posYHP;
    private final double[] xPoints;
    private final double[] yPoints;
    private final double constantVelocity = 0.5d;
    private double maxVelocityX;
    private double maxVelocityY;
    private double vx;
    private double vy;
    private double accX;
    private double accY;
    private boolean played;
    private final java.util.Timer shootTimer;

    public Omenoct(double[] xPoints, double[] yPoints) {
        this.xPoints = xPoints;
        this.yPoints = yPoints;
        shootTimer = new java.util.Timer();
        shootTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                shootBullet();
            }
        }, 2000, 1500);
    }

    public void playAudio() {
        if (getXPoints()[0] >= 0 && getXPoints()[0] <= GamePanel.getInstance().getPanelWidth() && getYPoints()[0] >= 0 && getXPoints()[0] <= GamePanel.getInstance().getPanelHeight()) {
            if (!played) {
                AudioPlayer.play(AudioPlayer.GROAN);
                played = true;
            }
        }
    }

    void shootBullet() {
        Bullet bullet = new Bullet(getCenterX(), getCenterY(), false, Constants.OMEN_PINK);
        bullet.calculateMovingDirection(Epsilon.getInstance().getX(), Epsilon.getInstance().getY());
        GameState.bullets.add(bullet);
    }

    @Override
    public void calculateMovingDirection(double x, double y) {
        //get epsilon current frame
        //if current frame changed revalidate
        //if(epsilon changed frame) return;
        int width = GamePanel.getInstance().getPanelWidth();
        int height = GamePanel.getInstance().getPanelHeight();
        int locX = GamePanel.getInstance().getLocationX();
        int locY = GamePanel.getInstance().getLocationY();
        if (calculated) {
            if (destination) {
                destinationX = locX;
                destinationY = locY + height / 2;
            } else {
                destinationX = locX + width;
                destinationY = locY + height / 2;
            }
            double angle = Math.atan2(destinationY - getCenterY(), destinationX - getCenterX());
            maxVelocityX = constantVelocity * Math.cos(angle);
            maxVelocityY = constantVelocity * Math.sin(angle);
            accX = Math.cos(angle);
            accY = Math.sin(angle);
            if (Calculator.distance(getCenterX(), getCenterY(), destinationX, destinationY) > 15) {
                move();
            } else {
                playAudio();
                stickPositionToPanel();
            }

        } else {
            if (Calculator.distance(0, (double) height / 2, getCenterX(), getCenterY()) <= Calculator.distance(width, (double) height / 2, getCenterX(), getCenterY())) {
                destinationX = locX;
                destinationY = locY + height / 2;
                destination = true;
                calculated = true;
            } else {
                destinationX = locX + width;
                destinationY = locY + height / 2;
                destination = false;
                calculated = true;
            }
        }
    }

    @Override
    public void move() {
        xPoints[0] += vx;
        xPoints[1] += vx;
        xPoints[2] += vx;
        xPoints[3] += vx;
        xPoints[4] += vx;
        xPoints[5] += vx;
        xPoints[6] += vx;
        xPoints[7] += vx;

        yPoints[0] += vy;
        yPoints[1] += vy;
        yPoints[2] += vy;
        yPoints[3] += vy;
        yPoints[4] += vy;
        yPoints[5] += vy;
        yPoints[6] += vy;
        yPoints[7] += vy;

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
        if (destination) {
            posXHP = xPoints[1] - 3;
            posYHP = yPoints[3] - 5;
        } else {
            posXHP = xPoints[0] - 3;
            posYHP = yPoints[6] - 5;
        }

    }

    boolean calculated = false;
    int destinationX;
    int destinationY;
    boolean destination;


    public void stickPositionToPanel() {
        int width = GamePanel.getInstance().getPanelWidth();
        int height = GamePanel.getInstance().getPanelHeight();
        int locX = GamePanel.getInstance().getLocationX();
        int locY = GamePanel.getInstance().getLocationY();
        if (destination) {
            xPoints[0] = locX + (double) (-1 * Constants.OMENOCT_SIZE) / 2;
        } else {
            xPoints[0] = locX + width - Constants.OMENOCT_SIZE;
        }
        xPoints[1] = xPoints[0] + Constants.OMENOCT_SIZE;
        xPoints[2] = xPoints[1] + Constants.OMENOCT_SIZE;
        xPoints[3] = xPoints[2];
        xPoints[4] = xPoints[3] - Constants.OMENOCT_SIZE;
        xPoints[5] = xPoints[4] - Constants.OMENOCT_SIZE;
        xPoints[6] = xPoints[5] - Constants.OMENOCT_SIZE;
        xPoints[7] = xPoints[6];

        yPoints[0] = locY + (double) height / 2 - Constants.OMENOCT_SIZE - (double) Constants.OMENOCT_SIZE / 2;
        yPoints[1] = yPoints[0];
        yPoints[2] = yPoints[1] + Constants.OMENOCT_SIZE;
        yPoints[3] = yPoints[2] + Constants.OMENOCT_SIZE;
        yPoints[4] = yPoints[3] + Constants.OMENOCT_SIZE;
        yPoints[5] = yPoints[4];
        yPoints[6] = yPoints[5] - Constants.OMENOCT_SIZE;
        yPoints[7] = yPoints[6] - Constants.OMENOCT_SIZE;

        if (destination) {
            posXHP = xPoints[1] - 3;
            posYHP = yPoints[3] - 5;
        } else {
            posXHP = xPoints[0] - 3;
            posYHP = yPoints[6] - 5;
        }
    }

    public int getCenterX() {
        return (int) (xPoints[0] + Constants.OMENOCT_SIZE / 2);
    }

    public int getCenterY() {
        return (int) (yPoints[7] + Constants.OMENOCT_SIZE / 2);
    }

    public void checkCollisions() {
        Point2D epsilonCollisionPoint = Collision.checkEpsilonCollision(this);
        if (epsilonCollisionPoint != null) {
            CollisionHandler.handleCollisionOnPoint(epsilonCollisionPoint);
            Epsilon.getInstance().setHP(Epsilon.getInstance().getHP() - 10);
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
    }

    @Override
    public int[] getXPoints() {
        return new int[]{(int) xPoints[0], (int) xPoints[1], (int) xPoints[2], (int) xPoints[3], (int) xPoints[4],
                (int) xPoints[5], (int) xPoints[6], (int) xPoints[7]};
    }

    @Override
    public int[] getYPoints() {
        return new int[]{(int) yPoints[0], (int) yPoints[1], (int) yPoints[2], (int) yPoints[3], (int) yPoints[4],
                (int) yPoints[5], (int) yPoints[6], (int) yPoints[7]};
    }

    public int[] getRelativeXPoints(JPanel panel) {
        int locationX = panel.getX();
        return new int[]{(int) xPoints[0] - locationX, (int) xPoints[1] - locationX, (int) xPoints[2] - locationX, (int) xPoints[3] - locationX,
                (int) xPoints[4] - locationX, (int) xPoints[5] - locationX, (int) xPoints[6] - locationX, (int) xPoints[7] - locationX};
    }

    public int[] getRelativeYPoints(JPanel panel) {
        int locationY = panel.getY();
        return new int[]{(int) yPoints[0] - locationY, (int) yPoints[1] - locationY, (int) yPoints[2] - locationY, (int) yPoints[3] - locationY,
                (int) yPoints[4] - locationY, (int) yPoints[5] - locationY, (int) yPoints[6] - locationY, (int) yPoints[7] - locationY};
    }

    public Timer getShootTimer() {
        return shootTimer;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public boolean isDestination() {
        return destination;
    }

    @Override
    public void selfPaint(Graphics g) {
        int locX = GamePanel.getInstance().getLocationX();
        int locY = GamePanel.getInstance().getLocationY();
        g.setColor(Constants.OMEN_PINK);
        g.fillPolygon(getRelativeXPoints(GamePanel.getInstance()), getRelativeYPoints(GamePanel.getInstance()), xPoints.length);
        g.setColor(Color.BLACK);
        g.drawString(String.valueOf(HP), (int) posXHP - locX, (int) posYHP - locY);

        for (int i = 0; i < GameState.panels.size(); i++) {
            locX = GameState.panels.get(i).getX();
            locY = GameState.panels.get(i).getY();
            Graphics g2 = GameState.panels.get(i).getGraphics();
            g2.setColor(Constants.OMEN_PINK);
            g2.fillPolygon(getRelativeXPoints(GameState.panels.get(i)), getRelativeYPoints(GameState.panels.get(i)), xPoints.length);
            g2.setColor(Color.BLACK);
            g2.drawString(String.valueOf(HP), (int) posXHP - locX, (int) posYHP - locY);
        }
    }

}
