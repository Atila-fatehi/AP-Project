package model.objectsModel.enemy;

import controller.util.Constants;
import model.movable.Movable;
import view.gameGUI.GameFrame;

import javax.swing.*;

public class Wyrm implements Movable {
    JPanel panel;
    private double x;
    private double y;
    private double size = 50;
    private double constantVelocity = 1d;
    private double maxVelocityX;
    private double maxVelocityY;
    private double vx;
    private double vy;
    private double accX;
    private double accY;


    public Wyrm(double x, double y) {
        this.x = x;
        this.y = y;
        panel = new JPanel();
        panel.setBounds((int) x, (int) y, (int) size, (int) size);
        panel.setBackground(Constants.ANOTHER_STRING_COLOR);
        GameFrame.getInstance().add(panel);
    }

    @Override
    public void move() {
        x += vx;
        y += vy;

        if (maxVelocityX > 0) {
            if (vx < maxVelocityX) {
                vx += accX;
            }
        } else {
            if (vx > maxVelocityX) {
                vx += accX;
            }
        }

        if (maxVelocityY > 0) {
            if (vy < maxVelocityY) {
                vy += accY;
            }
        } else {
            if (vy > maxVelocityY) {
                vy += accY;
            }
        }
        panel.setLocation((int) x, (int) y);
    }

    @Override
    public void calculateMovingDirection(double x, double y) {
        double angle = Math.atan2(y - this.y, x - this.x);
        maxVelocityX = 1 * Math.cos(angle);
        maxVelocityY = 1 * Math.sin(angle);
        accX = Math.cos(angle);
        accY = Math.sin(angle);
    }
}
