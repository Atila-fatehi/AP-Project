package model.collision;

import controller.util.Calculator;
import model.objectsModel.epsilon.Epsilon;

import java.awt.*;

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
    public static boolean checkEpsilonDrownOnCircle(Drownable drownable , int radius) {
        int x = drownable.getXPoints()[0];
        int y = drownable.getYPoints()[0];
        return Calculator.distance(x,y,Epsilon.getInstance().getX(),Epsilon.getInstance().getY()) <= radius - Epsilon.getInstance().getRadius();
    }

    public static boolean checkEpsilonDrownOnPoly(Drownable drownable) {
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
