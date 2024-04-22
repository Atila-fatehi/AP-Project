package Model;

import java.awt.geom.Point2D;

public class Bullet implements movable {
    private final double Radius = 7;
    private double x;
    private double y;
    private final double constantVelocity = 17;
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
//
//
//        // Check if any edge of the triangle intersects with the circle
//        for (int i = 0; i < triangleVertices.length; i++) {
//            int nextIndex = (i + 1) % triangleVertices.length;
//            if (lineCircleCollision(circleCenter, radius, triangleVertices[i], triangleVertices[nextIndex])) {
//                return true; // Collision detected
//            }
//        }

    public int onTrigorathCollision(double x1, double x2, double x3, double y1, double y2, double y3) {
        double centerX = x + Radius / 2;
        double centerY = y + Radius / 2;
        if (Math.sqrt((x1 - centerX) * (x1 - centerX) + (y1 - centerY) * (y1 - centerY)) <= Radius) {
            return 1;
        }
        if (Math.sqrt(Math.pow(x2 - centerX, 2) + Math.pow(y2 - centerY, 2)) <= Radius) {
            return 2;
        }
        if (Math.sqrt(Math.pow(x3 - centerX, 2) + Math.pow(y3 - centerY, 2)) <= Radius) {
            return 3;
        }
        if (lineCollision(centerX, centerY, Radius / 2, x1, y1, x2, y2)) {
            return 4;
        }
        if (lineCollision(centerX, centerY, Radius / 2, x2, y2, x3, y3)) {
            return 5;
        }
        if (lineCollision(centerX, centerY, Radius / 2, x1, y1, x3, y3)) {
            return 6;
        }
        return 0;
    }


    public boolean lineCollision(double circleX, double circleY, double radius, double lineStartX, double lineStartY, double lineEndX, double lineEndY) {
        double deltaX = lineEndX - lineStartX;
        double deltaY = lineEndY - lineStartY;
        double lengthSquared = deltaX * deltaX + deltaY * deltaY;
        double u = ((circleX - lineStartX) * deltaX + (circleY - lineStartY) * deltaY) / lengthSquared;

        // Closest point on the line segment to the circle center
        double closestX, closestY;
        if (u < 0) {
            closestX = lineStartX;
            closestY = lineStartY;
        } else if (u > 1) {
            closestX = lineEndX;
            closestY = lineEndY;
        } else {
            closestX = lineStartX + u * deltaX;
            closestY = lineStartY + u * deltaY;
        }

        // Check if the closest point is within the circle
        return distance(circleX, circleY, closestX, closestY) <= radius;
    }

    public double distance(double x1, double y1, double x2, double y2) {
        x1 -= x2;
        y1 -= y2;
        return Math.sqrt(x1 * x1 + y1 * y1);
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
        return Radius;
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

    public double getConstantVelocity() {
        return constantVelocity;
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
}
