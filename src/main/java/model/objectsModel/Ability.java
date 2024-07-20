package model.objectsModel;

import controller.logic.GameManager;
import controller.logic.GameState;
import model.collision.Collision;
import model.collision.CollisionHandler;
import model.objectsModel.epsilon.Epsilon;

import java.awt.geom.Point2D;
import java.util.TimerTask;

public abstract class Ability {
    public static void heal(){
        Epsilon.getInstance().setHP(Epsilon.getInstance().getHP() + 10);
    }
    
    public static void empower(){
        GameManager.getInstance().setEmpower(true);
        GameState.empower = true;
        java.util.Timer timer = new java.util.Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                GameManager.getInstance().setEmpower(false);
                GameState.empower = false;
                timer.cancel();
            }
        }, 10000, 1111);
    }

    public static void banish() {
        CollisionHandler.handleCollisionOnPointNoEpsilon(new Point2D.Double(Epsilon.getInstance().getX(), Epsilon.getInstance().getY()));
    }


    public static void dismay() {
        java.util.Timer timer = new java.util.Timer();
        timer.schedule(new TimerTask() {
            int count = 0;
            @Override
            public void run() {
                Epsilon.getInstance().setDismay(true);
                GameState.dismay = true;
                for (int i = 0; i < GameState.trigoraths.size(); i++) {
                    Point2D point = Collision.checkEpsilonCollisionWithRadius(GameState.trigoraths.get(i) , 170);
                    if(point != null){
                        CollisionHandler.handleCollisionOnPoint(point);
                    }
                }
                for (int i = 0; i < GameState.squarantines.size(); i++) {
                    Point2D point = Collision.checkEpsilonCollisionWithRadius(GameState.squarantines.get(i) , 170);
                    if(point != null){
                        CollisionHandler.handleCollisionOnPoint(point);
                    }
                }
                for (int i = 0; i < GameState.wyrms.size(); i++) {
                    Point2D point = Collision.checkEpsilonCollisionWithRadius(GameState.wyrms.get(i) , 170);
                    if(point != null){
                        CollisionHandler.handleCollisionOnPoint(point);
                        GameState.wyrms.get(i).changeRotation();
                    }
                }

                count++;
                if(count == 100) {
                    Epsilon.getInstance().setDismay(false);
                    GameState.dismay = false;
                    timer.cancel();
                }
            }
        }, 0, 100);
    }
}
