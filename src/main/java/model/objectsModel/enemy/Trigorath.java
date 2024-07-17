package model.objectsModel.enemy;

import controller.logic.GameState;
import controller.util.Calculator;
import controller.util.Constants;
import model.Paintable.Paintable;
import model.collision.Collidable;
import model.movable.Movable;
import view.gameGUI.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;

public class Trigorath implements Movable, Collidable, Paintable {
    private int HP = 15;
    private double posXHP;
    private double posYHP;
    private final double[] xPoints;
    private final double[] yPoints;
    private double maxVelocityX;
    private double maxVelocityY;
    private double vx;
    private double vy;
    private double accX;
    private double accY;
    private boolean played;

    public Trigorath(double[] x, double[] y) {
        this.xPoints = x;
        this.yPoints = y;
    }

    @Override
    public void calculateMovingDirection(double x, double y) {
        double angle = Math.atan2(y - (yPoints[0] + yPoints[2]) / 2, x - (xPoints[0] + xPoints[1]) / 2);
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
        xPoints[0] += vx;
        xPoints[1] += vx;
        xPoints[2] += vx;
        yPoints[0] += vy;
        yPoints[1] += vy;
        yPoints[2] += vy;

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
            posXHP = xPoints[0] + 7;
        } else {
            posXHP = xPoints[0] + 12;
        }
        posYHP = yPoints[0] - 4;
    }

    public Point2D getCenterOfGravity() {
        return new Point2D.Double((xPoints[0] + xPoints[1]) / 2, (yPoints[0] + yPoints[2]) / 2);
    }

    public int[] getXPoints() {
        return new int[]{(int) xPoints[0], (int) xPoints[1], (int) xPoints[2]};
    }

    public int[] getYPoints() {
        return new int[]{(int) yPoints[0], (int) yPoints[1], (int) yPoints[2]};
    }

    public int[] getRelativeXPoints(JPanel panel) {
        int locationX =panel.getX();
        return new int[]{(int) xPoints[0] - locationX, (int) xPoints[1] - locationX, (int) xPoints[2] - locationX};
    }
    public int[] getRelativeYPoints(JPanel panel) {
        int locationY = panel.getY();
        return new int[]{(int) yPoints[0] - locationY, (int) yPoints[1] - locationY, (int) yPoints[2] - locationY};
    }
    public void setVx(double vx) {
        this.vx = vx;
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

    public boolean isPlayed() {
        return played;
    }

    public void setPlayed(boolean played) {
        this.played = played;
    }


    @Override
    public void selfPaint(Graphics g) {
        int locX = GamePanel.getInstance().getLocationX();
        int locY = GamePanel.getInstance().getLocationY();
        g.setColor(Constants.TRI_YELLOW);
        g.fillPolygon(getRelativeXPoints(GamePanel.getInstance()), getRelativeYPoints(GamePanel.getInstance()), xPoints.length);
        g.setColor(Color.BLACK);
        g.drawString(String.valueOf(HP), (int) posXHP - locX, (int) posYHP - locY);

        for (int i = 0; i < GameState.panels.size(); i++) {
            locX = GameState.panels.get(i).getX();
            locY = GameState.panels.get(i).getY();
            Graphics g2 = GameState.panels.get(i).getGraphics();
            g2.setColor(Constants.TRI_YELLOW);
            g2.fillPolygon(getRelativeXPoints(GameState.panels.get(i)), getRelativeYPoints(GameState.panels.get(i)), xPoints.length);
            g2.setColor(Color.BLACK);
            g2.drawString(String.valueOf(HP), (int) posXHP - locX, (int) posYHP - locY);
        }
    }
}
