package model.collision;

import controller.util.Calculator;
import controller.logic.GameState;
import model.objectsModel.Epsilon;

import java.awt.geom.Point2D;

public abstract class CollisionHandler {


    public static void handleCollisionOnPoint(Point2D collisionPoint){
        final double RATE = 12;
        for (int i = 0; i < GameState.trigoraths.size(); i++) {
            if (Calculator.distance(GameState.trigoraths.get(i).getCenterOfGravity().getX(), GameState.trigoraths.get(i).getCenterOfGravity().getY(), collisionPoint.getX(), collisionPoint.getY()) <= 70) {
                double angle = Math.atan2(collisionPoint.getY() - GameState.trigoraths.get(i).getCenterOfGravity().getY(), collisionPoint.getX() - GameState.trigoraths.get(i).getCenterOfGravity().getX());
                GameState.trigoraths.get(i).setVx(-(RATE - 3) * Math.cos(angle));
                GameState.trigoraths.get(i).setVy(-(RATE - 3) * Math.sin(angle));
            }
        }
        for (int i = 0; i < GameState.squarantines.size(); i++) {
            if (Calculator.distance(GameState.squarantines.get(i).getCenterOfGravity().getX(), GameState.squarantines.get(i).getCenterOfGravity().getY(), collisionPoint.getX(), collisionPoint.getY()) <= 70) {
                double angle = Math.atan2(collisionPoint.getY() - GameState.squarantines.get(i).getCenterOfGravity().getY(), collisionPoint.getX() - GameState.squarantines.get(i).getCenterOfGravity().getX());
                GameState.squarantines.get(i).setVx(-(RATE - 3) * Math.cos(angle));
                GameState.squarantines.get(i).setVy(-(RATE - 3) * Math.sin(angle));
            }
        }
        if (Calculator.distance(Epsilon.getInstance().getX(), Epsilon.getInstance().getY(), collisionPoint.getX(), collisionPoint.getY()) <= 70) {
            if (collisionPoint.getX() >= Epsilon.getInstance().getX() && collisionPoint.getY() >= Epsilon.getInstance().getY()) {
                Epsilon.getInstance().setVx(-RATE);
                Epsilon.getInstance().setVy(-RATE);
                Epsilon.getInstance().setDecU(true);
                Epsilon.getInstance().setDecL(true);
                Epsilon.getInstance().setDecD(true);
                Epsilon.getInstance().setDecR(true);
            }
            if (collisionPoint.getX() <= Epsilon.getInstance().getX() && collisionPoint.getY() <= Epsilon.getInstance().getY()) {
                Epsilon.getInstance().setVx(RATE);
                Epsilon.getInstance().setVy(RATE);
                Epsilon.getInstance().setDecU(true);
                Epsilon.getInstance().setDecL(true);
                Epsilon.getInstance().setDecD(true);
                Epsilon.getInstance().setDecR(true);
            }
            if (collisionPoint.getX() <= Epsilon.getInstance().getX() && collisionPoint.getY() >= Epsilon.getInstance().getY()) {
                Epsilon.getInstance().setVx(RATE);
                Epsilon.getInstance().setVy(-RATE);
                Epsilon.getInstance().setDecU(true);
                Epsilon.getInstance().setDecL(true);
                Epsilon.getInstance().setDecD(true);
                Epsilon.getInstance().setDecR(true);
            }
            if (collisionPoint.getX() >= Epsilon.getInstance().getX() && collisionPoint.getY() <= Epsilon.getInstance().getY()) {
                Epsilon.getInstance().setVx(-RATE);
                Epsilon.getInstance().setVy(RATE);
                Epsilon.getInstance().setDecU(true);
                Epsilon.getInstance().setDecL(true);
                Epsilon.getInstance().setDecD(true);
                Epsilon.getInstance().setDecR(true);
            }
        }
    }
    public static void handleCollisionOnPointNoEpsilon(Point2D collisionPoint){
        final double RATE = 20;
        for (int i = 0; i < GameState.trigoraths.size(); i++) {
            if (Calculator.distance(GameState.trigoraths.get(i).getCenterOfGravity().getX(), GameState.trigoraths.get(i).getCenterOfGravity().getY(), collisionPoint.getX(), collisionPoint.getY()) <= 70) {
                double angle = Math.atan2(collisionPoint.getY() - GameState.trigoraths.get(i).getCenterOfGravity().getY(), collisionPoint.getX() - GameState.trigoraths.get(i).getCenterOfGravity().getX());
                GameState.trigoraths.get(i).setVx(-(RATE - 3) * Math.cos(angle));
                GameState.trigoraths.get(i).setVy(-(RATE - 3) * Math.sin(angle));
            }
        }
        for (int i = 0; i < GameState.squarantines.size(); i++) {
            if (Calculator.distance(GameState.squarantines.get(i).getCenterOfGravity().getX(), GameState.squarantines.get(i).getCenterOfGravity().getY(), collisionPoint.getX(), collisionPoint.getY()) <= 70) {
                double angle = Math.atan2(collisionPoint.getY() - GameState.squarantines.get(i).getCenterOfGravity().getY(), collisionPoint.getX() - GameState.squarantines.get(i).getCenterOfGravity().getX());
                GameState.squarantines.get(i).setVx(-(RATE - 3) * Math.cos(angle));
                GameState.squarantines.get(i).setVy(-(RATE - 3) * Math.sin(angle));
            }
        }
    }

}
