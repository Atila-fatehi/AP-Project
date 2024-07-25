package controller;

import controller.util.Constants;
import model.objectsModel.epsilon.Epsilon;
import view.frames.MainMenu;
import view.gameGUI.GameFrame;
import view.gameGUI.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public abstract class FrameController {

    public static int wallExpansionRate = 2;
    public static int wallExpansionSize = 100;
    public static int wallExpansionPeriod = 10;

    public static void minimizeAllWindows() {
        try {
            Robot robot = new Robot();

            robot.keyPress(KeyEvent.VK_WINDOWS);
            robot.delay(100);

            robot.keyPress(KeyEvent.VK_M);
            robot.delay(100);

            robot.keyRelease(KeyEvent.VK_M);

            robot.keyRelease(KeyEvent.VK_WINDOWS);
        } catch (AWTException e) {
            System.out.println("robot problem");
        }
    }


    public static void reduceFrom(int chosen) {
        if (chosen == 1) {
            new java.util.Timer().schedule(new TimerTask() {
                int count = 0;

                @Override
                public void run() {
                    GamePanel.getInstance().setLocation(GamePanel.getInstance().getLocationX() + wallExpansionRate, GamePanel.getInstance().getLocationY());
                    GamePanel.getInstance().setSize(GamePanel.getInstance().getPanelWidth() - wallExpansionRate, GamePanel.getInstance().getPanelHeight());
                    GamePanel.getInstance().setPanelWidth(GamePanel.getInstance().getPanelWidth() - wallExpansionRate);
                    GamePanel.getInstance().setLocationX(GamePanel.getInstance().getLocationX() + wallExpansionRate);
                    count++;
                    if (count == wallExpansionSize) {
                        this.cancel();
                    }
                }
            }, 0, wallExpansionPeriod);
        }
        if (chosen == 3) {
            new java.util.Timer().schedule(new TimerTask() {
                int count = 0;

                @Override
                public void run() {
                    GamePanel.getInstance().setSize(GamePanel.getInstance().getPanelWidth(), GamePanel.getInstance().getPanelHeight() - wallExpansionRate);
                    GamePanel.getInstance().setPanelHeight(GamePanel.getInstance().getPanelHeight() - wallExpansionRate);
                    count++;
                    if (count == wallExpansionSize) {
                        this.cancel();
                    }
                }
            }, 0, wallExpansionPeriod);
        }
        if (chosen == 2) {
            new java.util.Timer().schedule(new TimerTask() {
                int count = 0;

                @Override
                public void run() {
                    GamePanel.getInstance().setSize(GamePanel.getInstance().getPanelWidth() - wallExpansionRate, GamePanel.getInstance().getPanelHeight());
                    GamePanel.getInstance().setPanelWidth(GamePanel.getInstance().getPanelWidth() - wallExpansionRate);
                    count++;
                    if (count == wallExpansionSize) {
                        this.cancel();
                    }
                }
            }, 0, wallExpansionPeriod);
        }

    }
}
