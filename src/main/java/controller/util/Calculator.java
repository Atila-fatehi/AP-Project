package controller.util;

import controller.logic.GameManager;
import controller.logic.GameState;
import controller.logic.WaveGenerator;
import model.objectsModel.epsilon.Epsilon;

import java.awt.geom.Point2D;

public abstract class Calculator {
    public static Point2D circleLineCollision(double circleX, double circleY, double radius, double lineStartX, double lineStartY, double lineEndX, double lineEndY) {
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

    public static double distance(double x1, double y1, double x2, double y2) {
        x1 -= x2;
        y1 -= y2;
        return Math.sqrt(x1 * x1 + y1 * y1);
    }

    public static int progressRate() {
        int sum = 0;
        for (int i = 0; i < WaveGenerator.waveStart.length; i++) {
            if (i != WaveGenerator.waveStart.length - 1) {
                if (WaveGenerator.waveStart[i + 1] == -1) {
                    sum += (i + 1) * (GameState.elapsedTime - WaveGenerator.waveStart[i]);
                    System.out.println(i + 1);
                    break;
                } else {
                    sum += (i + 1) * (WaveGenerator.waveStart[i + 1] - WaveGenerator.waveStart[i]);
                }
            }
        }
        return 10 * Epsilon.getInstance().getXP() * sum / Epsilon.getInstance().getHP();
    }
}
