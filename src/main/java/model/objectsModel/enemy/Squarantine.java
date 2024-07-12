package model.objectsModel.enemy;

import controller.util.Constants;
import model.Paintable.Paintable;
import model.collision.Collidable;
import model.movable.Movable;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class Squarantine implements Movable, Collidable, Paintable {

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
    private java.util.Timer timer;
    public Squarantine(double[] x, double[] y) {
        this.xPoints = x;
        this.yPoints = y;
        randomAggression();
    }

    public void shiftX(double rate) {
        xPoints[0] += rate;
        xPoints[1] += rate;
        xPoints[2] += rate;
        xPoints[3] += rate;
    }

    public void shiftY(double rate) {
        yPoints[0] += rate;
        yPoints[1] += rate;
        yPoints[2] += rate;
        yPoints[3] += rate;
    }

    public void randomAggression(){
        Random random = new Random();
        timer = new java.util.Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(random.nextBoolean()){
                    constantVelocity = 5d;
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    }catch (Exception e){

                    }
                    constantVelocity = 1d;
                }
            }
        },5000,3000);
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

    public Point2D getCenterOfGravity() {
        return new Point2D.Double((xPoints[0] + xPoints[1]) / 2, (yPoints[0] + yPoints[2]) / 2);
    }

    public int[] getXPoints() {
        return new int[]{(int) xPoints[0], (int) xPoints[1], (int) xPoints[2], (int) xPoints[3]};
    }

    public int[] getYPoints() {
        return new int[]{(int) yPoints[0], (int) yPoints[1], (int) yPoints[2], (int) yPoints[3]};
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public double getPosXHP() {
        return posXHP;
    }

    public void setPosXHP(double posXHP) {
        this.posXHP = posXHP;
    }

    public double getPosYHP() {
        return posYHP;
    }

    public void setPosYHP(double posYHP) {
        this.posYHP = posYHP;
    }

    public double getVx() {
        return vx;
    }

    public void setVx(double vx) {
        this.vx = vx;
    }

    public double getVy() {
        return vy;
    }

    public void setVy(double vy) {
        this.vy = vy;
    }

    public Timer getTimer() {
        return timer;
    }

    public boolean isPlayed() {
        return played;
    }

    public void setPlayed(boolean played) {
        this.played = played;
    }

    @Override
    public void selfPaint(Graphics g) {
        g.setColor(Constants.SQUA_GREEN);
        g.fillPolygon(getXPoints(), getYPoints(), 4);
        g.setColor(Color.BLACK);
        g.drawString(String.valueOf(HP), (int) posXHP, (int) posYHP);
    }
}
