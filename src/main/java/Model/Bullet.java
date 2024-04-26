package Model;

import util.cal;

import java.awt.*;
import java.awt.geom.Point2D;

public class Bullet implements movable {
    private final double radius = 4;
    private double x;
    private double y;
    private final double constantVelocity = 15;
    private double vx;
    private double vy;
    cal cal;

    public void move() {
        x = x + vx;
        y = y + vy;
    }

    public Bullet(double x, double y) {
        this.x = x;
        this.y = y;
        cal = new cal();
    }

    public Point2D onTrigorathCollision(double x1, double x2, double x3, double y1, double y2, double y3) {
        int[] xPoints = {(int) x1, (int) x2, (int) x3};
        int[] yPoints = {(int) y1, (int) y2, (int) y3};

        Polygon trigorath = new Polygon(xPoints, yPoints, 3);
        if (trigorath.contains(x, y)) {
            return new Point2D.Double(x, y);
        }
        Point2D point1 = cal.circleLineCollision(x, y, radius, x1, y1, x2, y2);
        if (point1 != null) {
            return point1;
        }
        Point2D point2 = cal.circleLineCollision(x, y, radius, x2, y2, x3, y3);
        if (point2 != null) {
            return point2;
        }
        Point2D point3 = cal.circleLineCollision(x, y, radius, x1, y1, x3, y3);
        if (point3 != null) {
            return point3;
        }
        return null;
    }

    public Point2D onSquarantineCollision(double x1, double x2, double x3, double x4, double y1, double y2, double y3, double y4) {
        int[] xPoints = {(int) x1, (int) x2, (int) x3, (int) x4};
        int[] yPoints = {(int) y1, (int) y2, (int) y3, (int) y4};

        Polygon squarantine = new Polygon(xPoints, yPoints, 4);
        if (squarantine.contains(x, y)) {
            return new Point2D.Double(x, y);
        }
        Point2D point1 = cal.circleLineCollision(x, y, radius, x1, y1, x2, y2);
        if (point1 != null) {
            return point1;
        }
        Point2D point2 = cal.circleLineCollision(x, y, radius, x2, y2, x3, y3);
        if (point2 != null) {
            return point2;
        }
        Point2D point3 = cal.circleLineCollision(x, y, radius, x3, y3, x4, y4);
        if (point3 != null) {
            return point3;
        }
        Point2D point4 = cal.circleLineCollision(x, y, radius, x4, y4, x1, y1);
        if (point4 != null) {
            return point4;
        }
        return null;
    }

    public int onWallCollision(double w, double h) {
        if (x <= 0) {
            return 1;
        }
        if (y <= 0) {
            return 2;
        }
        if (x >= w) {
            return 3;
        }
        if (y >= h) {
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
}
