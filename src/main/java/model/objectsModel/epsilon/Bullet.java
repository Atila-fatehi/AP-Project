package model.objectsModel.epsilon;

import controller.util.Constants;
import model.Paintable.Paintable;
import model.collision.Collidable;
import model.collision.Collision;
import model.collision.WallCollidable;
import model.movable.Movable;
import view.gameGUI.GamePanel;

import java.awt.*;

public class Bullet implements Movable, WallCollidable, Collidable, Paintable {


    private double x;
    private double y;
    private double vx;
    private double vy;
    private double radius;
    private double constantVelocity;
    private Color color;
    private boolean fromEpsilon;

    public void move() {
        x = x + vx;
        y = y + vy;
    }

    @Override
    public void calculateMovingDirection(double x, double y) {
        double angle = Math.atan2(y - this.y, x - this.x);
        vx = constantVelocity * Math.cos(angle);
        vy = constantVelocity * Math.sin(angle);
    }

    public Bullet(double x, double y , boolean fromEpsilon , Color color) {
        this.x = x;
        this.y = y;
        this.fromEpsilon =fromEpsilon;
        this.color = color;
        if(fromEpsilon){
            constantVelocity = 15;
            radius = 4;
        }else{
            constantVelocity = 5;
            radius = 8;
        }
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
        return constantVelocity;
    }

    public void setConstantVelocity(double constantVelocity) {
        this.constantVelocity = constantVelocity;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isFromEpsilon() {
        return fromEpsilon;
    }

    public void setFromEpsilon(boolean fromEpsilon) {
        this.fromEpsilon = fromEpsilon;
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
        g.setColor(color);
        g.fillOval((int) (x - radius), (int) (y - radius), (int) radius * 2, (int) radius * 2);
    }
}
