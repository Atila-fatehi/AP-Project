package model.objectsModel;

import model.Paintable.Paintable;

import java.awt.*;

public class Collectable implements Paintable {
    private double x;
    private double y;
    private double radius;
    private int xp;
    private Color color;

    public Collectable(double x, double y, int xp, Color color) {
        this.x = x;
        this.y = y;
        this.radius = 6;
        this.xp = xp;
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

    @Override
    public void selfPaint(Graphics g) {
        g.setColor(color);
        g.fillOval((int) x, (int) y, (int) radius * 2, (int) radius * 2);
    }
}
