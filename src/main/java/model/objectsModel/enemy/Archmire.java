package model.objectsModel.enemy;

import controller.util.Constants;
import model.Paintable.Paintable;
import model.movable.Movable;

import java.awt.*;
import java.util.ArrayList;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class Archmire implements Paintable, Movable {

    private int HP = 30;
    private double posXHP;
    private double posYHP;
    private double radius_a = 40;
    private double radius_b = 35;
    private double x;
    private double y;
    private double constantVelocity = 0.25d;
    private double maxVelocityX;
    private double maxVelocityY;
    private double vx;
    private double vy;
    private double accX;
    private double accY;
    private boolean played;
    private java.util.Timer timer;
    private ArrayList<Integer> traveledX = new ArrayList<>();
    private ArrayList<Integer> traveledY = new ArrayList<>();


    public Archmire(double x, double y) {
        this.x = x;
        this.y = y;
        startTimer();
    }
    void startTimer(){
        timer = new java.util.Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                traveledX.add((int) x);
                traveledY.add((int) y);
            }
        }, 0, 100);
    }


    @Override
    public void move() {
        x += vx;
        y += vy;

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

        posXHP = x + 14;
        posYHP = y + 22;

    }

    @Override
    public void calculateMovingDirection(double x, double y) {
        double angle = Math.atan2(y - (this.y + radius_b / 2), x - (this.x + radius_a / 2));
        maxVelocityX = constantVelocity * Math.cos(angle);
        maxVelocityY = constantVelocity * Math.sin(angle);
        accX = Math.cos(angle);
        accY = Math.sin(angle);
    }

    @Override
    public void selfPaint(Graphics g) {
        g.setColor(Constants.ARCH_DARKER_RED);
        for (int i = 0; i < traveledX.size(); i++) {
            g.fillOval(traveledX.get(i), traveledY.get(i), (int) radius_a, (int) radius_b);
        }
        g.setColor(Constants.ARCH_RED);
        g.fillOval((int) x, (int) y, (int) radius_a, (int) radius_b);
        g.setColor(Color.BLACK);
        g.drawString(String.valueOf(HP), (int) posXHP, (int) posYHP);
    }

}
