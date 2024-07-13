package model.objectsModel.enemy;

import controller.logic.GameState;
import controller.util.Calculator;
import controller.util.Constants;
import model.Paintable.Paintable;
import model.collision.Collidable;
import model.movable.Movable;
import model.objectsModel.epsilon.Bullet;
import model.objectsModel.epsilon.Epsilon;
import view.gameGUI.GamePanel;

import java.awt.*;
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
    private java.util.Timer shootTimer;
    public Omenoct(double[] xPoints, double[] yPoints) {
        this.xPoints = xPoints;
        this.yPoints = yPoints;
        shootTimer = new java.util.Timer();
        shootTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                shootBullet();
            }
        },2000,1500);
    }


    void shootBullet(){
        Bullet bullet = new Bullet(getCenterX(), getCenterY() , false, Constants.OMEN_PINK);
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
        if (calculated) {
            if (destination) {
                destinationX = 0;
                destinationY = height / 2;
            } else {
                destinationX = width;
                destinationY = height / 2;
            }
            double angle = Math.atan2(destinationY - getCenterY(), destinationX - getCenterX());
            maxVelocityX = constantVelocity * Math.cos(angle);
            maxVelocityY = constantVelocity * Math.sin(angle);
            accX = Math.cos(angle);
            accY = Math.sin(angle);
            if (Calculator.distance(getCenterX(), getCenterY(), destinationX, destinationY) > 15) {
                move();
            } else {
                stickPositionToPanel();
            }

        } else {
            if (Calculator.distance(0, (double) height / 2, getCenterX(), getCenterY()) <= Calculator.distance(width, (double) height / 2, getCenterX(), getCenterY())) {
                destinationX = 0;
                destinationY = height / 2;
                destination = true;
                calculated = true;
            } else {
                destinationX = width;
                destinationY = height / 2;
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
        if(destination){
            posXHP = xPoints[1] - 3;
            posYHP = yPoints[3] - 5;
        }else{
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
        if (destination) {
            xPoints[0] = (double) (-1 * Constants.OMENOCT_SIZE) / 2;
        } else {
            xPoints[0] = width - Constants.OMENOCT_SIZE;
        }
        xPoints[1] = xPoints[0] + Constants.OMENOCT_SIZE;
        xPoints[2] = xPoints[1] + Constants.OMENOCT_SIZE;
        xPoints[3] = xPoints[2];
        xPoints[4] = xPoints[3] - Constants.OMENOCT_SIZE;
        xPoints[5] = xPoints[4] - Constants.OMENOCT_SIZE;
        xPoints[6] = xPoints[5] - Constants.OMENOCT_SIZE;
        xPoints[7] = xPoints[6];

        yPoints[0] = (double) height / 2 - Constants.OMENOCT_SIZE - (double) Constants.OMENOCT_SIZE / 2;
        yPoints[1] = yPoints[0];
        yPoints[2] = yPoints[1] + Constants.OMENOCT_SIZE;
        yPoints[3] = yPoints[2] + Constants.OMENOCT_SIZE;
        yPoints[4] = yPoints[3] + Constants.OMENOCT_SIZE;
        yPoints[5] = yPoints[4];
        yPoints[6] = yPoints[5] - Constants.OMENOCT_SIZE;
        yPoints[7] = yPoints[6] - Constants.OMENOCT_SIZE;

        if(destination){
            posXHP = xPoints[1] - 3;
            posYHP = yPoints[3] - 5;
        }else{
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

    @Override
    public void selfPaint(Graphics g) {
        g.setColor(Constants.OMEN_PINK);
        g.fillPolygon(getXPoints(), getYPoints(), xPoints.length);
        g.setColor(Color.BLACK);
        g.drawString(String.valueOf(HP), (int) posXHP, (int) posYHP);

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
}
