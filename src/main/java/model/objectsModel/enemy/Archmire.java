package model.objectsModel.enemy;

import controller.logic.GameState;
import controller.util.Constants;
import model.Paintable.Paintable;
import model.movable.Movable;
import view.gameGUI.GamePanel;

import java.awt.*;
import java.util.ArrayList;
import java.util.Timer;
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
    private java.util.Timer timer1;
    private ArrayList<Integer> traveledX = new ArrayList<>();
    private ArrayList<Integer> traveledY = new ArrayList<>();
    private ArrayList<java.util.Timer> traveledTimer = new ArrayList<>();


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
        timer1 = new java.util.Timer();
        timer1.schedule(new TimerTask() {
            @Override
            public void run() {
                traveledX.removeFirst();
                traveledY.removeFirst();
            }
        } , 5000 , 100);
        traveledTimer.add(timer1);
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

        posXHP = x + 29;
        posYHP = y + 37;

    }

    @Override
    public void calculateMovingDirection(double x, double y) {
        double angle = Math.atan2(y - (this.y + radius_b), x - (this.x + radius_a));
        maxVelocityX = constantVelocity * Math.cos(angle);
        maxVelocityY = constantVelocity * Math.sin(angle);
        accX = Math.cos(angle);
        accY = Math.sin(angle);
    }

    @Override
    public void selfPaint(Graphics g) {
        int locX = GamePanel.getInstance().getLocationX();
        int locY = GamePanel.getInstance().getLocationY();
        g.setColor(Constants.ARCH_DARKER_RED);
        for (int i = 0; i < traveledX.size(); i++) {
            try {
                g.fillOval(traveledX.get(i) - locX, traveledY.get(i) - locY, (int) radius_a * 2, (int) radius_b * 2);
            }catch (Exception e){

            }
        }
        g.setColor(Constants.ARCH_RED);
        g.fillOval((int) x - locX, (int) y - locY, (int) radius_a * 2, (int) radius_b * 2);
        g.setColor(Color.BLACK);
        g.drawString(String.valueOf(HP), (int) posXHP - locX, (int) posYHP - locY);

        for (int i = 0; i < GameState.panels.size(); i++) {
            locX = GameState.panels.get(i).getX();
            locY = GameState.panels.get(i).getY();
            Graphics g2 = GameState.panels.get(i).getGraphics();
            g2.setColor(Constants.ARCH_DARKER_RED);
            for (int j = 0; j < traveledX.size(); j++) {
                try {
                    g2.fillOval(traveledX.get(j) - locX, traveledY.get(j) - locY, (int) radius_a * 2, (int) radius_b * 2);
                }catch (Exception e){

                }
            }
            g2.setColor(Constants.ARCH_RED);
            g2.fillOval((int) x - locX, (int) y - locY, (int) radius_a * 2, (int) radius_b * 2);
            g2.setColor(Color.BLACK);
            g2.drawString(String.valueOf(HP), (int) posXHP - locX, (int) posYHP - locY);
        }
    }

}
