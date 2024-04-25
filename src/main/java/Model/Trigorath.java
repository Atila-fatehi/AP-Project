package Model;

import util.cal;

import java.awt.*;

public class Trigorath implements movable {
    private int HP;
    private double posXHP;
    private double posYHP;
    private double x1, x2, x3;
    private double y1, y2, y3;
    private static final double constantVelocity = 1.5;
    private double vx;
    private double vy;
    cal cal;

    public Trigorath(double x1, double y1, double x2, double y2, double x3, double y3) {
        this.x1 = x1;
        this.x2 = x2;
        this.x3 = x3;
        this.y1 = y1;
        this.y2 = y2;
        this.y3 = y3;
        this.HP = 15;
        cal = new cal();
    }
    public int onTrigorathCollision(double x1, double x2, double x3, double y1, double y2, double y3) {
        int[] xPoints = {(int) x1, (int) x2, (int) x3};
        int[] yPoints = {(int) y1, (int) y2, (int) y3};

        Polygon trigorath = new Polygon(xPoints , yPoints , 3);
        if(trigorath.contains(this.x1,this.y1)){
            return 1;
        }
        if(trigorath.contains(this.x2,this.y2)){
            return 2;
        }
        if(trigorath.contains(this.x3,this.y3)){
            return 3;
        }
        return 0;
    }
    public int onEpsilonCollision(double x, double y, double radius) {
        if (Math.sqrt(Math.pow(x1 - x, 2) + Math.pow(y1 - y, 2)) <= radius) {
            return 1;
        }
        if (Math.sqrt(Math.pow(x2 - x, 2) + Math.pow(y2 - y, 2)) <= radius) {
            return 2;
        }
        if (Math.sqrt(Math.pow(x3 - x, 2) + Math.pow(y3 - y, 2)) <= radius) {
            return 3;
        }
        if (cal.circleLineCollision(x, y, radius, x1, y1, x2, y2)) {
            return 4;
        }
        if (cal.circleLineCollision(x, y, radius, x2, y2, x3, y3)) {
            return 5;
        }
        if (cal.circleLineCollision(x, y, radius, x3, y3, x1, y1)) {
            return 6;
        }
        return 0;
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
        setVx(constantVelocity * Math.cos(angle));
        setVy(constantVelocity * Math.sin(angle));
    }

    public void move() {
        x1 += vx;
        x2 += vx;
        x3 += vx;
        y1 += vy;
        y2 += vy;
        y3 += vy;
        if (HP >= 10) {
            posXHP = x1 + 7;
            posYHP = y1 - 4;
        } else {
            posXHP = x1 + 12;
            posYHP = y1 - 4;
        }

    }


    public int[] getXPoints(){
        return new int[]{(int) x1,(int) x2,(int) x3};
    }
    public int[] getYPoints(){
        return new int[]{(int) y1,(int) y2,(int) y3};
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

    public void setPosXHP(double posXHP) {
        this.posXHP = posXHP;
    }

    public double getPosYHP() {
        return posYHP;
    }

    public void setPosYHP(double posYHP) {
        this.posYHP = posYHP;
    }
}
