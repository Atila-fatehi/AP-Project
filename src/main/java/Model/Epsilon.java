package Model;

import java.awt.geom.Point2D;

public class Epsilon implements movable{
    private int HP;
    private int XP;

    private Point2D anchor;
    private double x;
    private double y;
    private double radius;
    private double vx;
    private double vy;


    public Epsilon(double x, double y, double radius) {
        this.x = x;
        this.y = y;
        setHP(100);
        setXP(0);
        this.radius = radius;
        vx = 0;
        vy = 0;
    }
    public void move(){
        x += vx;
        y += vy;

    }

    public int getXP() {
        return XP;
    }

    public void setXP(int XP) {
        this.XP = XP;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
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
}


