package model.collision;

import controller.audio.players.AudioPlayer;
import controller.logic.GameState;
import model.objectsModel.epsilon.Epsilon;
import view.gameGUI.GamePanel;

import java.util.TimerTask;

public abstract class WallCollisionHandler {

    public static int wallExpansionRate = 1;
    public static int wallExpansionSize = 30;
    public static int wallExpansionPeriod = 10;

    public static void handleWallCollision(int wallNumber){
        if (wallNumber == 1) {
            for (int i = 0; i < GameState.omenocts.size(); i++) {
                if(GameState.omenocts.get(i).isDestination()){
                    AudioPlayer.play(AudioPlayer.SPLAT);
                    GameState.omenocts.get(i).setHP(GameState.omenocts.get(i).getHP() - Epsilon.getInstance().getDamageRate());
                }
            }
            java.util.Timer timer = new java.util.Timer();
            timer.schedule(new TimerTask() {
                int counter = 0;
                @Override
                public void run() {
                    GamePanel.getInstance().setLocation(GamePanel.getInstance().getLocationX() - wallExpansionRate, GamePanel.getInstance().getLocationY());
                    GamePanel.getInstance().setSize(GamePanel.getInstance().getPanelWidth() + wallExpansionRate, GamePanel.getInstance().getPanelHeight());
                    GamePanel.getInstance().setPanelWidth(GamePanel.getInstance().getPanelWidth() + wallExpansionRate);
                    GamePanel.getInstance().setLocationX(GamePanel.getInstance().getLocationX() - wallExpansionRate);
                    counter++;
                    if (counter == wallExpansionSize) {
                        timer.cancel();
                    }
                }
            }, 0, wallExpansionPeriod);
        } else if (wallNumber == 2) {
            java.util.Timer timer = new java.util.Timer();
            timer.schedule(new TimerTask() {
                int counter = 0;

                @Override
                public void run() {
                    GamePanel.getInstance().setLocation(GamePanel.getInstance().getLocationX(), GamePanel.getInstance().getLocationY() - wallExpansionRate);
                    GamePanel.getInstance().setSize(GamePanel.getInstance().getPanelWidth(), GamePanel.getInstance().getPanelHeight() + wallExpansionRate);
                    GamePanel.getInstance().setPanelHeight(GamePanel.getInstance().getPanelHeight() + wallExpansionRate);
                    GamePanel.getInstance().setLocationY(GamePanel.getInstance().getLocationY() - wallExpansionRate);
                    counter++;
                    if (counter == wallExpansionSize) {
                        timer.cancel();
                    }
                }
            }, 0, wallExpansionPeriod);

        } else if (wallNumber == 3) {
            for (int i = 0; i < GameState.omenocts.size(); i++) {
                if(!GameState.omenocts.get(i).isDestination()){
                    AudioPlayer.play(AudioPlayer.SPLAT);
                    GameState.omenocts.get(i).setHP(GameState.omenocts.get(i).getHP() - Epsilon.getInstance().getDamageRate());
                }
            }
            java.util.Timer timer = new java.util.Timer();
            timer.schedule(new TimerTask() {
                int counter = 0;
                @Override
                public void run() {
                    GamePanel.getInstance().setSize(GamePanel.getInstance().getPanelWidth() + wallExpansionRate, GamePanel.getInstance().getPanelHeight());
                    GamePanel.getInstance().setPanelWidth(GamePanel.getInstance().getPanelWidth() + wallExpansionRate);

                    counter++;
                    if (counter == wallExpansionSize) {
                        timer.cancel();
                    }
                }
            }, 0, wallExpansionPeriod);

        } else if (wallNumber == 4) {
            java.util.Timer timer = new java.util.Timer();
            timer.schedule(new TimerTask() {
                int counter = 0;
                @Override
                public void run() {
                    GamePanel.getInstance().setSize(GamePanel.getInstance().getPanelWidth(), GamePanel.getInstance().getPanelHeight() + wallExpansionRate);
                    GamePanel.getInstance().setPanelHeight(GamePanel.getInstance().getPanelHeight() + wallExpansionRate);
                    counter++;
                    if (counter == wallExpansionSize) {
                        timer.cancel();
                    }
                }
            }, 0, wallExpansionPeriod);

        }
    }

    public static void handleEpsilonWallCollision(){
        if (Epsilon.getInstance().getX() - Epsilon.getInstance().getRadius() < GamePanel.getInstance().getLocationX()) {
            Epsilon.getInstance().setX(GamePanel.getInstance().getLocationX() + Epsilon.getInstance().getRadius());
            Epsilon.getInstance().setVx(0);
        } else if (Epsilon.getInstance().getX() + Epsilon.getInstance().getRadius() > GamePanel.getInstance().getPanelWidth() + GamePanel.getInstance().getLocationX()) {
            Epsilon.getInstance().setX(GamePanel.getInstance().getPanelWidth() + GamePanel.getInstance().getLocationX() - Epsilon.getInstance().getRadius());
            Epsilon.getInstance().setVx(0);
        }

        if (Epsilon.getInstance().getY() - Epsilon.getInstance().getRadius() < GamePanel.getInstance().getLocationY()) {
            Epsilon.getInstance().setY(Epsilon.getInstance().getRadius() + GamePanel.getInstance().getLocationY());
            Epsilon.getInstance().setVy(0);
        } else if (Epsilon.getInstance().getY() + Epsilon.getInstance().getRadius() > GamePanel.getInstance().getPanelHeight() + GamePanel.getInstance().getLocationY()) {
            Epsilon.getInstance().setY(GamePanel.getInstance().getPanelHeight() + GamePanel.getInstance().getLocationY() - Epsilon.getInstance().getRadius());
            Epsilon.getInstance().setVy(0);
        }
    }

}
