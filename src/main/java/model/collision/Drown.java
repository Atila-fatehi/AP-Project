package model.collision;

import controller.util.Calculator;
import model.objectsModel.epsilon.Epsilon;

import java.awt.*;
import java.awt.geom.Point2D;

public abstract class Drown {

    public static boolean checkTwoPolyEntityDrown(Drownable drownable, Collidable collidable) {
        int[] XPoints1 = collidable.getXPoints();
        int[] YPoints1 = collidable.getYPoints();

        int[] XPoints2 = drownable.getXPoints();
        int[] YPoints2 = drownable.getYPoints();

        Polygon poly = new Polygon(XPoints2, YPoints2, XPoints2.length);
        for (int i = 0; i < XPoints1.length; i++) {
            if (!poly.contains(XPoints1[i], YPoints1[i])) {
                return false;
            }
        }
        return true;

    }

    public static boolean checkEpsilonDrown(Drownable drownable) {
        int[] xPoints = drownable.getXPoints();
        int[] yPoints = drownable.getYPoints();
        int x = (int) Epsilon.getInstance().getX();
        int y = (int) Epsilon.getInstance().getY();
        int radius = (int) Epsilon.getInstance().getRadius();
        Polygon poly = new Polygon(xPoints, yPoints, xPoints.length);
        if (!poly.contains(x + radius, y)) {
            return false;
        }
        if (!poly.contains(x - radius, y)) {
            return false;
        }
        if (!poly.contains(x, y + radius)) {
            return false;
        }
        if (!poly.contains(x, y - radius)) {
            return false;
        }

        if (!poly.contains(x + radius, y + radius)) {
            return false;
        }
        if (!poly.contains(x + radius, y - radius)) {
            return false;
        }
        if (!poly.contains(x - radius, y + radius)) {
            return false;
        }
        if (!poly.contains(x - radius, y - radius)) {
            return false;
        }
        return true;
    }
}
