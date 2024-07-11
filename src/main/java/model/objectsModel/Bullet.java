package model.objectsModel;

import controller.util.Calculator;
import controller.util.Constants;
import model.Paintable.Paintable;
import model.collision.Collidable;
import model.collision.WallCollidable;
import model.movable.movable;
import view.gameGUI.GamePanel;

import java.awt.*;
import java.awt.geom.Point2D;

public class Bullet implements movable, WallCollidable, Collidable , Paintable {


    private final double radius = 4;
    private double x;
    private double y;
    private double vx;
    private double vy;

    public void move() {
        x = x + vx;
        y = y + vy;
    }

    public Bullet(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int wallCollision() {
        if (x <= 0) {
            return 1;
        }
        if (y <= 0) {
            return 2;
        }
        if (x >= GamePanel.getInstance().getPanelWidth()) {
            return 3;
        }
        if (y >= GamePanel.getInstance().getPanelHeight()) {
            return 4;
        }
        return 0;
    }

    public double getRadius() {
        return radius;
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

    public double getConstantVelocity() {
        return 15;
    }


    @Override
    public int[] getXPoints() {
        return new int[]{(int) x};
    }

    @Override
    public int[] getYPoints() {
        return new int[]{(int) y};
    }

    @Override
    public void selfPaint(Graphics g) {
        g.setColor(Constants.EPSILON_COLOR);
        g.fillOval((int) (x - radius), (int) (y - radius), (int) radius * 2, (int) radius * 2);
    }
}
