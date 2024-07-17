package model.objectsModel.enemy;

import controller.logic.GameState;
import controller.util.Calculator;
import controller.util.Constants;
import model.Paintable.Paintable;
import model.movable.Movable;
import model.objectsModel.epsilon.Bullet;
import model.objectsModel.epsilon.Epsilon;
import view.gameGUI.GameFrame;
import view.gameGUI.GamePanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class Wyrm implements Movable, Paintable {
    private double x;
    private double y;
    private double width = 90;
    private double height = 70;
    private double radiusFromEpsilon = 400;
    private double acquiredX;
    private double acquiredY;
    private double angle;
    private boolean acquired;
    private double maxVelocityX;
    private double maxVelocityY;
    private double vx;
    private double vy;
    private double accX;
    private double accY;
    private Panel panel;
    private Image image;
    private boolean linearMovement;
    private final java.util.Timer shootTimer = new java.util.Timer();

    public Wyrm(double x, double y) {
        this.x = x;
        this.y = y;

        panel = new Panel();

        try {
            Image yourImage = (Image) ImageIO.read(Constants.WYRM_PIC);
            image = yourImage.getScaledInstance(90, 70, Image.SCALE_DEFAULT);
        } catch (Exception e) {
            System.out.println("exception in wyrm paint");
        }
        shootTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                shootBullet();
            }
        }, 2000, 1500);
    }

    class Panel extends JPanel {
        public Panel() {
            setBounds((int) x - 10, (int) y - 10, (int) width + 20, (int) height + 20);
            setBackground(Constants.DARK_BLUE);
            GameState.panels.add(this);
            GameFrame.getInstance().add(this);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            int locX = panel.getX();
            int locY = panel.getY();
            g.drawImage(image, (int) x - locX - 45, (int) y - locY - 35, this);
            panel.setLocation((int) x - 45 - 10, (int) y - 35 - 10);

            g.dispose();
        }
    }
    void shootBullet() {
//        Bullet bullet = new Bullet(x, y, false, Constants.WYRM_PINK);
//        bullet.calculateMovingDirection(Epsilon.getInstance().getX(), Epsilon.getInstance().getY());
//        GameState.bullets.add(bullet);
    }

    @Override
    public void move() {
        if (linearMovement) {
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
        } else {
            angle += 0.5;
            angle %= 360;
            x = acquiredX + (int) (radiusFromEpsilon * Math.cos(Math.toRadians(angle)));
            y = acquiredY + (int) (radiusFromEpsilon * Math.sin(Math.toRadians(angle)));
        }
    }

    @Override
    public void calculateMovingDirection(double x, double y) {
        if(!acquired) {
            linearMovement = Calculator.distance(x, y, this.x, this.y) >= radiusFromEpsilon;
            if (linearMovement) {
                double angle = Math.atan2(y - this.y, x - this.x);
                maxVelocityX = 1 * Math.cos(angle);
                maxVelocityY = 1 * Math.sin(angle);
                accX = Math.cos(angle);
                accY = Math.sin(angle);
            } else {
                double deltaY = y - this.y;
                double deltaX = x - this.x;
                double angleInRadians = Math.atan2(deltaY, deltaX);
                angle = Math.toDegrees(angleInRadians);
                if(angle >= 0){
                    angle -= 180;
                    angle = Math.abs(angle);
                }else{
                    angle = 180 + Math.abs(angle);
                }
                angle = 360 - angle;
                acquiredX = x;
                acquiredY = y;
                acquired = true;
            }
        }
    }

    @Override
    public void selfPaint(Graphics g) {
        int locX = GamePanel.getInstance().getX();
        int locY = GamePanel.getInstance().getY();
        g.drawImage(image, (int) x - locX - 45, (int) y - locY - 35, GamePanel.getInstance());
//        g.drawRect((int) (x - locX - 45), (int) (y - locY - 35), 90, 70);

        locX = panel.getX();
        locY = panel.getY();
        Graphics g2 = panel.getGraphics();
        g2.drawImage(image, (int) x - locX - 45, (int) y - locY - 35, panel);
        panel.setLocation((int) x - 45 - 10, (int) y - 35 - 10);
    }


}
