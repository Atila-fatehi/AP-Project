package model.objectsModel;

import java.awt.*;

public class Collectable {
    private double x;
    private double y;
    private double radius;
    private int xp;
    private Color color;

    public Collectable(double x, double y, Color color) {
        this.x = x;
        this.y = y;
        this.radius = 6;
        this.xp = 5;
        this.color = color;
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

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
    public void shiftX(double rate) {
        x += rate;
    }

    public void shiftY(double rate) {
        y += rate;
    }
}
