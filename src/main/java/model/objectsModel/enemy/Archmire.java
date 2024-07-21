package model.objectsModel.enemy;

import controller.logic.GameState;
import controller.util.Constants;
import model.Paintable.Paintable;
import model.collision.*;
import model.movable.Movable;
import model.objectsModel.epsilon.Epsilon;
import view.gameGUI.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class Archmire implements Paintable, Movable, Drownable, Collidable , Serializable {

    private int HP = 30;
    private int damage = 10;
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
    private HashMap<String ,java.util.Timer> insMaps = new HashMap<>();


    public Archmire(double x, double y) {
        this.x = x;
        this.y = y;
        startTimer();
    }

    void startTimer() {
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
        }, 5000, 100);
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
    public void selfPaint(Graphics g, JPanel panel) {
        int locX = panel.getX();
        int locY = panel.getY();
        g.setColor(Constants.ARCH_DARKER_RED);
        for (int i = 0; i < traveledX.size(); i++) {
            try {
                g.fillOval(traveledX.get(i) - locX, traveledY.get(i) - locY, (int) radius_a * 2, (int) radius_b * 2);
            } catch (Exception e) {

            }
        }
        g.setColor(Constants.ARCH_RED);
        g.fillOval((int) x - locX, (int) y - locY, (int) radius_a * 2, (int) radius_b * 2);

        g.setColor(Color.BLACK);
        g.drawString(String.valueOf(HP), (int) posXHP - locX, (int) posYHP - locY);
    }

    @Override
    public int[] getXPoints() {
        return new int[]{(int) x, (int) x + 27, (int) x + 54, (int) x + 80, (int) x + 54, (int) x + 27};
    }

    @Override
    public int[] getYPoints() {
        return new int[]{(int) y + 35, (int) y, (int) y, (int) y + 35, (int) y + 70, (int) y + 70};
    }


    public int[] getRelativeXPoints(JPanel panel) {
        int locX = panel.getX();
        return new int[]{getXPoints()[0] - locX, getXPoints()[1] - locX, getXPoints()[2] - locX,
                getXPoints()[3] - locX, getXPoints()[4] - locX, getXPoints()[5] - locX};
    }


    public int[] getRelativeYPoints(JPanel panel) {
        int locY = panel.getY();
        return new int[]{getYPoints()[0] - locY, getYPoints()[1] - locY, getYPoints()[2] - locY,
                getYPoints()[3] - locY, getYPoints()[4] - locY, getYPoints()[5] - locY};
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

    public Timer getTimer1() {
        return timer1;
    }

    public double getRadius_a() {
        return radius_a;
    }

    public double getRadius_b() {
        return radius_b;
    }

    public void drown() {
        if(Drown.checkEpsilonDrown(this)){
            if(!insMaps.containsKey("Epsilon")){
                java.util.Timer ep = new java.util.Timer();
                ep.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Epsilon.getInstance().setHP(Epsilon.getInstance().getHP() - damage);
                    }
                } ,1000,1000 );
                insMaps.put("Epsilon" , ep);
            }
        }else{
            if(insMaps.containsKey("Epsilon")){
                insMaps.get("Epsilon").cancel();
                insMaps.remove("Epsilon");
            }
        }

//        for (int j = 0; j < GameState.trigoraths.size(); j++) {
//            if(Drown.checkTwoPolyEntityDrown(this , GameState.trigoraths.get(j))){
//                if(!insMaps.containsKey(GameState.trigoraths.get(j).getId())){
//                    java.util.Timer ep = new java.util.Timer();
//                    insMaps.put(GameState.trigoraths.get(j).getId() , ep);
//                    ep.schedule(new TimerTask() {
//                        @Override
//                        public void run() {
//
//                        }
//                    } ,1000,1000 );
//                }
//            }else{
//                if(insMaps.containsKey("Epsilon")){
//                    insMaps.get("Epsilon").cancel();
//                    insMaps.remove("Epsilon");
//                }
//            }
//        }
//        for (int j = 0; j < GameState.squarantines.size(); j++) {
//            Point2D collisionPoint = Collision.checkTwoPolyEntityCollision(this, GameState.squarantines.get(j));
//            if (collisionPoint != null) {
//                CollisionHandler.handleCollisionOnPoint(collisionPoint);
//            }
//        }
//        for (int j = 0; j < GameState.omenocts.size(); j++) {
//            Point2D collisionPoint = Collision.checkTwoPolyEntityCollision(this, GameState.omenocts.get(j));
//            if (collisionPoint != null) {
//                CollisionHandler.handleCollisionOnPoint(collisionPoint);
//            }
//        }
//        for (int j = 0; j < GameState.necropicks.size(); j++) {
//            Point2D collisionPoint = Collision.checkTwoPolyEntityCollision(this, GameState.necropicks.get(j));
//            if (collisionPoint != null) {
//                CollisionHandler.handleCollisionOnPoint(collisionPoint);
//            }
//        }
//        for (int j = 0; j < GameState.wyrms.size(); j++) {
//            Point2D collisionPoint = Collision.checkTwoPolyEntityCollision(this, GameState.wyrms.get(j));
//            if (collisionPoint != null) {
//                CollisionHandler.handleCollisionOnPoint(collisionPoint);
//                GameState.wyrms.get(j).changeRotation();
//            }
//        }
//        for (int j = 0; j < GameState.barricados.size(); j++) {
//            Point2D collisionPoint = Collision.checkTwoPolyEntityCollision(this, GameState.barricados.get(j));
//            if (collisionPoint != null) {
//                CollisionHandler.handleCollisionOnPoint(collisionPoint);
//            }
//        }
    }
}
