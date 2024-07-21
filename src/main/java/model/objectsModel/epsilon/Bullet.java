package model.objectsModel.epsilon;

import controller.logic.GameState;
import controller.util.Constants;
import model.Paintable.Paintable;
import model.collision.Collidable;
import model.collision.Collision;
import model.collision.WallCollidable;
import model.movable.Movable;
import view.gameGUI.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

public class Bullet implements Movable, WallCollidable, Collidable, Paintable, Serializable {

    private double x;
    private double y;
    private double vx;
    private double vy;
    private double radius;
    private double constantVelocity;
    private Color color;
    private boolean fromEpsilon;
    private int damage;

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

    public Bullet(double x, double y, boolean fromEpsilon, Color color, int damage) {
        this.x = x;
        this.y = y;
        this.fromEpsilon = fromEpsilon;
        this.color = color;
        this.damage = damage;
        if (fromEpsilon) {
            constantVelocity = 15;
            radius = 4;
        } else {
            constantVelocity = 5;
            radius = 8;
        }
    }

    @Override
    public int wallCollision() {
        JPanel panel = Epsilon.getInstance().getCurrentPanel();
        if (x - radius < panel.getX()) {
            boolean access = false;
            for (int i = 0; i < GameState.panels.size(); i++) {
                if (GameState.panels.get(i) != panel && GameState.panels.get(i).getX() + GameState.panels.get(i).getWidth() > panel.getX()
                        && GameState.panels.get(i).getX() < panel.getX()
                        && GameState.panels.get(i).getY() < y - radius
                        && GameState.panels.get(i).getY() + GameState.panels.get(i).getHeight() > y + radius) {
                    access = true;
                }
            }
            if (!access) {
                return 1;
            }
        } else if (x + radius > panel.getWidth() + panel.getX()) {
            boolean access = false;
            for (int i = 0; i < GameState.panels.size(); i++) {
                if (GameState.panels.get(i) != panel && GameState.panels.get(i).getX() < panel.getX() + panel.getWidth()
                        && GameState.panels.get(i).getX() + GameState.panels.get(i).getWidth() > panel.getX() + panel.getWidth()
                        && GameState.panels.get(i).getY() < y - radius
                        && GameState.panels.get(i).getY() + GameState.panels.get(i).getHeight() > y + radius) {
                    access = true;
                }
            }
            if (!access) {
                return 3;
            }
        }

        if (y - radius < panel.getY()) {
            boolean access = false;
            for (int i = 0; i < GameState.panels.size(); i++) {
                if (GameState.panels.get(i) != panel && GameState.panels.get(i).getY() + GameState.panels.get(i).getHeight() > panel.getY()
                        && GameState.panels.get(i).getY() < panel.getY()
                        && GameState.panels.get(i).getX() + GameState.panels.get(i).getWidth() > x + radius
                        && GameState.panels.get(i).getX() < x - radius) {
                    access = true;
                }
            }
            if (!access) {
                return 2;
            }
        } else if (y + radius > panel.getHeight() + panel.getY()) {
            boolean access = false;
            for (int i = 0; i < GameState.panels.size(); i++) {
                if (GameState.panels.get(i) != panel && GameState.panels.get(i).getY() < panel.getY() + panel.getHeight()
                        && GameState.panels.get(i).getY() + GameState.panels.get(i).getHeight() > panel.getY() + panel.getHeight()
                        && GameState.panels.get(i).getX() < x - radius
                        && GameState.panels.get(i).getX() + GameState.panels.get(i).getWidth() > x + radius) {
                    access = true;
                }
            }
            if (!access) {
                return 4;
            }
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

    public boolean isFromEpsilon() {
        return fromEpsilon;
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
    public int[] getRelativeXPoints(JPanel panel) {
        return new int[0];
    }

    @Override
    public int[] getRelativeYPoints(JPanel panel) {
        return new int[0];
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    @Override
    public void selfPaint(Graphics g, JPanel panel) {
        int locX = panel.getX();
        int locY = panel.getY();
        g.setColor(color);
        g.fillOval((int) (x - radius - locX), (int) (y - radius - locY), (int) radius * 2, (int) radius * 2);
    }

}
