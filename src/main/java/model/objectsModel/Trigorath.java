package model.objectsModel;

import controller.util.Calculator;
import controller.util.Constants;
import model.Paintable.Paintable;
import model.collision.Collidable;
import model.movable.movable;

import java.awt.*;
import java.awt.geom.Point2D;

public class Trigorath implements movable, Collidable, Paintable {
    private int HP;
    private double posXHP;
    private double posYHP;
    private double x1, x2, x3;
    private double y1, y2, y3;
    private double maxVelocityX;
    private double maxVelocityY;
    private double vx;
    private double vy;
    private double accX;
    private double accY;
    private boolean played;

    public Trigorath(double x1, double y1, double x2, double y2, double x3, double y3) {
        this.x1 = x1;
        this.x2 = x2;
        this.x3 = x3;
        this.y1 = y1;
        this.y2 = y2;
        this.y3 = y3;
        this.HP = 15;
    }

    public void shiftX(double rate) {
        x1 += rate;
        x2 += rate;
        x3 += rate;
    }

    public void shiftY(double rate) {
        y1 += rate;
        y2 += rate;
        y3 += rate;
    }

    public void calculateMovingDirection(double x, double y) {
        double angle = Math.atan2(y - (y1 + y3) / 2, x - (x1 + x2) / 2);
        double constantVelocity;
        if (Calculator.distance(getCenterOfGravity().getX(), getCenterOfGravity().getY(), x, y) >= 400) {
            constantVelocity = 3d;
        } else {
            constantVelocity = 1d;
        }
        maxVelocityX = constantVelocity * Math.cos(angle);
        maxVelocityY = constantVelocity * Math.sin(angle);
        accX = Math.cos(angle);
        accY = Math.sin(angle);
    }

    public void move() {
        x1 += vx;
        x2 += vx;
        x3 += vx;
        y1 += vy;
        y2 += vy;
        y3 += vy;

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
            posXHP = x1 + 7;
        } else {
            posXHP = x1 + 12;
        }
        posYHP = y1 - 4;
    }


    public Point2D getCenterOfGravity() {
        return new Point2D.Double((x1 + x2) / 2, (y1 + y3) / 2);
    }

    public int[] getXPoints() {
        return new int[]{(int) x1, (int) x2, (int) x3};
    }

    public int[] getYPoints() {
        return new int[]{(int) y1, (int) y2, (int) y3};
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

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public double getPosXHP() {
        return posXHP;
    }

    public double getPosYHP() {
        return posYHP;
    }

    public boolean isPlayed() {
        return played;
    }

    public void setPlayed(boolean played) {
        this.played = played;
    }


    @Override
    public void selfPaint(Graphics g) {
        g.setColor(Constants.TRI_YELLOW);
        g.fillPolygon(getXPoints(), getYPoints(), 3);
        g.setColor(Color.BLACK);
        g.drawString(String.valueOf(HP), (int) posXHP, (int) posYHP);
    }
}
