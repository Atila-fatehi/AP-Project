package model.objectsModel;

import controller.util.Calculator;
import model.collision.Collidable;
import model.movable.movable;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class Squarantine implements movable, Collidable {

    private int HP;
    private double posXHP;
    private double posYHP;
    private double x1, x2, x3, x4;
    private double y1, y2, y3, y4;
    private double constantVelocity;
    private double maxVelocityX;
    private double maxVelocityY;
    private double vx;
    private double vy;
    private double accX;
    private double accY;
    private final java.util.Timer timer;
    private boolean played;
    public Squarantine(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4) {
        this.x1 = x1;
        this.x2 = x2;
        this.x3 = x3;
        this.x4 = x4;
        this.y1 = y1;
        this.y2 = y2;
        this.y3 = y3;
        this.y4 = y4;
        this.HP = 10;
        constantVelocity = 1d;
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

    public void shiftX(double rate) {
        x1 += rate;
        x2 += rate;
        x3 += rate;
        x4 += rate;
    }

    public void shiftY(double rate) {
        y1 += rate;
        y2 += rate;
        y3 += rate;
        y4 += rate;
    }

    public void randomAggression(){

    }
    public void calculateMovingDirection(double x, double y) {
        double angle = Math.atan2(y - (y1 + y3) / 2, x - (x1 + x2) / 2);
//        setVx(constantVelocity * Math.cos(angle));
//        setVy(constantVelocity * Math.sin(angle));

        maxVelocityX = constantVelocity * Math.cos(angle);
        maxVelocityY = constantVelocity * Math.sin(angle);
        accX = Math.cos(angle);
        accY = Math.sin(angle);
    }

    public void move() {
        x1 += vx;
        x2 += vx;
        x3 += vx;
        x4 += vx;
        y1 += vy;
        y2 += vy;
        y3 += vy;
        y4 += vy;

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
            posXHP = x1 + 5;
            posYHP = y1 + 17;
        } else {
            posXHP = x1 + 9;
            posYHP = y1 + 17;
        }
    }

    public Point2D getCenterOfGravity() {
        return new Point2D.Double((x1 + x2) / 2, (y1 + y3) / 2);
    }

    public int[] getXPoints() {
        return new int[]{(int) x1, (int) x2, (int) x3, (int) x4};
    }

    public int[] getYPoints() {
        return new int[]{(int) y1, (int) y2, (int) y3, (int) y4};
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

    public double getX1() {
        return x1;
    }

    public void setX1(double x1) {
        this.x1 = x1;
    }

    public double getX2() {
        return x2;
    }

    public void setX2(double x2) {
        this.x2 = x2;
    }

    public double getX3() {
        return x3;
    }

    public void setX3(double x3) {
        this.x3 = x3;
    }

    public double getX4() {
        return x4;
    }

    public void setX4(double x4) {
        this.x4 = x4;
    }

    public double getY1() {
        return y1;
    }

    public void setY1(double y1) {
        this.y1 = y1;
    }

    public double getY2() {
        return y2;
    }

    public void setY2(double y2) {
        this.y2 = y2;
    }

    public double getY3() {
        return y3;
    }

    public void setY3(double y3) {
        this.y3 = y3;
    }

    public double getY4() {
        return y4;
    }

    public void setY4(double y4) {
        this.y4 = y4;
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
}
