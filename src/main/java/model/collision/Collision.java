package model.collision;

import controller.util.Calculator;
import model.objectsModel.epsilon.Bullet;
import model.objectsModel.enemy.Collectable;
import model.objectsModel.epsilon.Epsilon;
import model.objectsModel.miniBoss.BlackOrb;

import java.awt.*;
import java.awt.geom.Point2D;

public abstract class Collision {

    public static Point2D checkTwoPolyEntityCollision(Collidable collidable1, Collidable collidable2) {
        int[] XPoints1 = collidable1.getXPoints();
        int[] YPoints1 = collidable1.getYPoints();

        int[] XPoints2 = collidable2.getXPoints();
        int[] YPoints2 = collidable2.getYPoints();

        Polygon poly = new Polygon(XPoints1, YPoints1, XPoints1.length);
        for (int i = 0; i < XPoints2.length; i++) {
            if (poly.contains(XPoints2[i], YPoints2[i])) {
                return new Point2D.Double(XPoints2[i], YPoints2[i]);
            }
        }
        return null;
    }

    public static Point2D checkEpsilonCollision(Collidable collidable) {
        int[] xPoints = collidable.getXPoints();
        int[] yPoints = collidable.getYPoints();
        int x = (int) Epsilon.getInstance().getX();
        int y = (int) Epsilon.getInstance().getY();
        int radius = (int) Epsilon.getInstance().getRadius();
        for (int i = 0; i < xPoints.length; i++) {
            if (Calculator.distance(x, y, xPoints[i], yPoints[i]) <= radius) {
                return new Point2D.Double(xPoints[i], yPoints[i]);
            }
        }
        for (int i = 0; i < xPoints.length; i++) {
            Point2D point;
            if (i == xPoints.length - 1) {
                point = Calculator.circleLineCollision(x, y, radius, xPoints[i], yPoints[i], xPoints[0], yPoints[0]);
            } else {
                point = Calculator.circleLineCollision(x, y, radius, xPoints[i], yPoints[i], xPoints[i + 1], yPoints[i + 1]);
            }
            if (point != null) {
                return point;
            }
        }

        return null;
    }

    public static Point2D checkBulletCollision(Bullet bullet , Collidable collidable){
        if(!bullet.isFromEpsilon()) return null;
        int[] xPoints = collidable.getXPoints();
        int[] yPoints = collidable.getYPoints();
        int x = (int) bullet.getX();
        int y = (int) bullet.getY();
        int radius = (int) bullet.getRadius();
        Polygon poly = new Polygon(xPoints, yPoints, xPoints.length);
        if(poly.contains(x,y)){
            return new Point2D.Double(x,y);
        }
        for (int i = 0; i < xPoints.length; i++) {
            if (Calculator.distance(x, y, xPoints[i], yPoints[i]) <= radius) {
                return new Point2D.Double(xPoints[i], yPoints[i]);
            }
        }
        for (int i = 0; i < xPoints.length; i++) {
            Point2D point;
            if (i == xPoints.length - 1) {
                point = Calculator.circleLineCollision(x, y, radius, xPoints[i], yPoints[i], xPoints[0], yPoints[0]);
            } else {
                point = Calculator.circleLineCollision(x, y, radius, xPoints[i], yPoints[i], xPoints[i + 1], yPoints[i + 1]);
            }
            if (point != null) {
                return point;
            }
        }
        return null;
    }

    public static boolean checkCoinCollision(Collectable coin){
        return Calculator.distance(Epsilon.getInstance().getX(), Epsilon.getInstance().getY(), coin.getX(), coin.getY())
                <= coin.getRadius() + Epsilon.getInstance().getRadius() + 20;
    }
    public static Point2D checkCircleCollision(Bullet bullet){
        if(bullet.isFromEpsilon()) return null;
        if(Calculator.distance(Epsilon.getInstance().getX(), Epsilon.getInstance().getY(), bullet.getX(), bullet.getY())
                <= bullet.getRadius() + Epsilon.getInstance().getRadius() + 3){
            return new Point2D.Double(bullet.getX() , bullet.getY());
        }
        return null;
    }
    public static boolean checkPointCollision(Point2D point, Collidable collidable){
        Polygon poly = new Polygon(collidable.getXPoints(), collidable.getYPoints(), collidable.getXPoints().length);
        return poly.contains(point);
    }
    public static boolean checkPointCollision(Point2D point, Polygon poly){
        return poly.contains(point);
    }
    public static Point2D checkOrbCollision(BlackOrb orb , Bullet bullet) {
        double radius = 40;
        if(Calculator.distance(orb.getX() + 10 + radius, orb.getY() + 10 + radius, bullet.getX(), bullet.getY())
                <= bullet.getRadius() + radius + 3){
            return new Point2D.Double(bullet.getX() , bullet.getY());
        }
        return null;
    }

    public static Point2D checkOrbCollision(BlackOrb orb , Collidable collidable) {
        int[] xPoints = collidable.getXPoints();
        int[] yPoints = collidable.getYPoints();
        int radius = 40;
        int x = (int) orb.getX() + radius + 10;
        int y = (int) orb.getY() + radius + 10;
        for (int i = 0; i < xPoints.length; i++) {
            if (Calculator.distance(x, y, xPoints[i], yPoints[i]) <= radius) {
                return new Point2D.Double(xPoints[i], yPoints[i]);
            }
        }
        for (int i = 0; i < xPoints.length; i++) {
            Point2D point;
            if (i == xPoints.length - 1) {
                point = Calculator.circleLineCollision(x, y, radius, xPoints[i], yPoints[i], xPoints[0], yPoints[0]);
            } else {
                point = Calculator.circleLineCollision(x, y, radius, xPoints[i], yPoints[i], xPoints[i + 1], yPoints[i + 1]);
            }
            if (point != null) {
                return point;
            }
        }

        return null;
    }

    public static Point2D checkOrbCollision(BlackOrb orb) {
        double radius = 40;
        if(Calculator.distance(orb.getX() + 10 + radius, orb.getY() + 10 + radius, Epsilon.getInstance().getX(), Epsilon.getInstance().getY())
                <= Epsilon.getInstance().getRadius() + radius + 3){
            return new Point2D.Double(Epsilon.getInstance().getX() , Epsilon.getInstance().getY());
        }
        return null;
    }



    public static Point2D checkEpsilonCollisionWithRadius(Collidable collidable , int radius) {
        int[] xPoints = collidable.getXPoints();
        int[] yPoints = collidable.getYPoints();
        int x = (int) Epsilon.getInstance().getX();
        int y = (int) Epsilon.getInstance().getY();
        for (int i = 0; i < xPoints.length; i++) {
            if (Calculator.distance(x, y, xPoints[i], yPoints[i]) <= radius) {
                return new Point2D.Double(xPoints[i], yPoints[i]);
            }
        }
        for (int i = 0; i < xPoints.length; i++) {
            Point2D point;
            if (i == xPoints.length - 1) {
                point = Calculator.circleLineCollision(x, y, radius, xPoints[i], yPoints[i], xPoints[0], yPoints[0]);
            } else {
                point = Calculator.circleLineCollision(x, y, radius, xPoints[i], yPoints[i], xPoints[i + 1], yPoints[i + 1]);
            }
            if (point != null) {
                return point;
            }
        }

        return null;
    }
}
