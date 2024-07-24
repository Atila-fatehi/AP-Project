package model.collision;

import controller.audio.players.AudioPlayer;
import controller.logic.GameState;
import model.objectsModel.boss.AttackType;
import model.objectsModel.epsilon.Epsilon;
import view.gameGUI.GamePanel;

import javax.swing.*;
import java.util.TimerTask;

public abstract class WallCollisionHandler {

    public static int wallExpansionRate = 1;
    public static int wallExpansionSize = 30;
    public static int wallExpansionPeriod = 10;

    public static void handleWallCollision(int wallNumber) {
        if (wallNumber == 1) {
            for (int i = 0; i < GameState.omenocts.size(); i++) {
                if (GameState.omenocts.get(i).isDestination() && GameState.omenocts.get(i).isStick()) {
                    AudioPlayer.play(AudioPlayer.SPLAT);
                    GameState.omenocts.get(i).setHP(GameState.omenocts.get(i).getHP() - Epsilon.getInstance().getDamageRate());
                }
            }
            java.util.Timer timer = new java.util.Timer();
            timer.schedule(new TimerTask() {
                int counter = 0;

                @Override
                public void run() {
                    if ((GameState.hands.isEmpty()) || GameState.hands.get(0).getAttackType() != AttackType.SQUEEZE) {
                        GamePanel.getInstance().setLocation(GamePanel.getInstance().getLocationX() - wallExpansionRate, GamePanel.getInstance().getLocationY());
                        GamePanel.getInstance().setSize(GamePanel.getInstance().getPanelWidth() + wallExpansionRate, GamePanel.getInstance().getPanelHeight());
                        GamePanel.getInstance().setPanelWidth(GamePanel.getInstance().getPanelWidth() + wallExpansionRate);
                        GamePanel.getInstance().setLocationX(GamePanel.getInstance().getLocationX() - wallExpansionRate);
                    }
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
                if (!GameState.omenocts.get(i).isDestination() && GameState.omenocts.get(i).isStick()) {
                    AudioPlayer.play(AudioPlayer.SPLAT);
                    GameState.omenocts.get(i).setHP(GameState.omenocts.get(i).getHP() - Epsilon.getInstance().getDamageRate());
                }
            }
            java.util.Timer timer = new java.util.Timer();
            timer.schedule(new TimerTask() {
                int counter = 0;

                @Override
                public void run() {
                    if ((GameState.hands.isEmpty()) || GameState.hands.get(0).getAttackType() != AttackType.SQUEEZE) {
                            GamePanel.getInstance().setSize(GamePanel.getInstance().getPanelWidth() + wallExpansionRate, GamePanel.getInstance().getPanelHeight());
                            GamePanel.getInstance().setPanelWidth(GamePanel.getInstance().getPanelWidth() + wallExpansionRate);

                    }
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

    public static void handleEpsilonWallCollision() {
        double x = Epsilon.getInstance().getX();
        double y = Epsilon.getInstance().getY();
        double radius = Epsilon.getInstance().getRadius();
        JPanel panel = Epsilon.getInstance().getCurrentPanel();
        if (x - radius < panel.getX()) {
            boolean access = false;
            for (int i = 0; i < GameState.panels.size(); i++) {
                if (GameState.panels.get(i) != panel && GameState.panels.get(i).getX() + GameState.panels.get(i).getWidth() > panel.getX()
                        && GameState.panels.get(i).getX() < panel.getX()
                        && GameState.panels.get(i).getY() < y - radius
                        && GameState.panels.get(i).getY() + GameState.panels.get(i).getHeight() > y + radius) {
                    access = true;
                }
            }
            if (!access) {
                Epsilon.getInstance().setX(panel.getX() + radius);
                Epsilon.getInstance().setVx(0);
            }
        } else if (x + radius > panel.getWidth() + panel.getX()) {
            boolean access = false;
            for (int i = 0; i < GameState.panels.size(); i++) {
                if (GameState.panels.get(i) != panel && GameState.panels.get(i).getX() < panel.getX() + panel.getWidth()
                        && GameState.panels.get(i).getX() + GameState.panels.get(i).getWidth() > panel.getX() + panel.getWidth()
                        && GameState.panels.get(i).getY() < y - radius
                        && GameState.panels.get(i).getY() + GameState.panels.get(i).getHeight() > y + radius) {
                    access = true;
                }
            }
            if (!access) {
                Epsilon.getInstance().setX(panel.getWidth() + panel.getX() - radius);
                Epsilon.getInstance().setVx(0);
            }
        }

        if (y - radius < panel.getY()) {
            boolean access = false;
            for (int i = 0; i < GameState.panels.size(); i++) {
                if (GameState.panels.get(i) != panel && GameState.panels.get(i).getY() + GameState.panels.get(i).getHeight() > panel.getY()
                        && GameState.panels.get(i).getY() < panel.getY()
                        && GameState.panels.get(i).getX() + GameState.panels.get(i).getWidth() > x + radius
                        && GameState.panels.get(i).getX() < x - radius) {
                    access = true;
                }
            }
            if (!access) {
                Epsilon.getInstance().setY(radius + panel.getY());
                Epsilon.getInstance().setVy(0);
            }
        } else if (y + radius > panel.getHeight() + panel.getY()) {
            boolean access = false;
            for (int i = 0; i < GameState.panels.size(); i++) {
                if (GameState.panels.get(i) != panel && GameState.panels.get(i).getY() < panel.getY() + panel.getHeight()
                        && GameState.panels.get(i).getY() + GameState.panels.get(i).getHeight() > panel.getY() + panel.getHeight()
                        && GameState.panels.get(i).getX() < x - radius
                        && GameState.panels.get(i).getX() + GameState.panels.get(i).getWidth() > x + radius) {
                    access = true;
                }
            }
            if (!access) {
                Epsilon.getInstance().setY(panel.getHeight() + panel.getY() - radius);
                Epsilon.getInstance().setVy(0);
            }
        }
    }

}
