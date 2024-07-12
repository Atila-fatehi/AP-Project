package model.objectsModel.enemy;

import controller.util.Constants;
import model.Paintable.Paintable;
import model.collision.Collidable;
import model.movable.Movable;
import model.objectsModel.EnemyModel;

import java.awt.*;

public class Omenoct implements Paintable , Collidable , Movable {
    private int HP = 20;
    private double posXHP;
    private double posYHP;
    private final double[] xPoints;
    private final double[] yPoints;
    private double constantVelocity = 1d;
    private double maxVelocityX;
    private double maxVelocityY;
    private double vx;
    private double vy;
    private double accX;
    private double accY;
    private boolean played;

    public Omenoct(double[] xPoints, double[] yPoints) {
        this.xPoints = xPoints;
        this.yPoints = yPoints;
    }

    @Override
    public void selfPaint(Graphics g) {
        g.setColor(Constants.OMEN_PINK);
        g.fillPolygon(getXPoints(), getYPoints(), xPoints.length);
        g.setColor(Color.BLACK);
        g.drawString(String.valueOf(HP), (int) posXHP, (int) posYHP);
    }

    @Override
    public int[] getXPoints() {
        return new int[]{(int) xPoints[0],(int) xPoints[1],(int) xPoints[2],(int) xPoints[3],(int) xPoints[4],
                (int) xPoints[5],(int) xPoints[6],(int) xPoints[7]};
    }

    @Override
    public int[] getYPoints() {
        return new int[]{(int) yPoints[0],(int) yPoints[1],(int) yPoints[2],(int) yPoints[3],(int) yPoints[4],
                (int) yPoints[5],(int) yPoints[6],(int) yPoints[7]};
    }

    @Override
    public void move() {
        xPoints[0] += vx;
        xPoints[1] += vx;
        xPoints[2] += vx;
        xPoints[3] += vx;
        xPoints[4] += vx;
        xPoints[5] += vx;
        xPoints[6] += vx;
        xPoints[7] += vx;

        yPoints[0] += vy;
        yPoints[1] += vy;
        yPoints[2] += vy;
        yPoints[3] += vy;
        yPoints[4] += vy;
        yPoints[5] += vy;
        yPoints[6] += vy;
        yPoints[7] += vy;

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

    }

    @Override
    public void calculateMovingDirection(double x, double y) {
        double angle = Math.atan2(y - (yPoints[0] + yPoints[2]) / 2, x - (xPoints[0] + xPoints[1]) / 2);
        maxVelocityX = constantVelocity * Math.cos(angle);
        maxVelocityY = constantVelocity * Math.sin(angle);
        accX = Math.cos(angle);
        accY = Math.sin(angle);
    }
}
