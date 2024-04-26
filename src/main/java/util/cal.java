package util;

import java.awt.geom.Point2D;

public class cal {
    public Point2D circleLineCollision(double circleX, double circleY, double radius, double lineStartX, double lineStartY, double lineEndX, double lineEndY) {
        double deltaX = lineEndX - lineStartX;
        double deltaY = lineEndY - lineStartY;
        double lengthSquared = deltaX * deltaX + deltaY * deltaY;
        double u = ((circleX - lineStartX) * deltaX + (circleY - lineStartY) * deltaY) / lengthSquared;
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
        if (distance(circleX, circleY, closestX, closestY) <= radius) {
            return new Point2D.Double(closestX, closestY);
        } else {
            return null;
        }

    }

    public double distance(double x1, double y1, double x2, double y2) {
        x1 -= x2;
        y1 -= y2;
        return Math.sqrt(x1 * x1 + y1 * y1);
    }
}
