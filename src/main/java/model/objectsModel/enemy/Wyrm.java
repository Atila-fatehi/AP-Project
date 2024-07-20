package model.objectsModel.enemy;

import controller.logic.GameState;
import controller.util.Calculator;
import controller.util.Constants;
import model.Paintable.Paintable;
import model.collision.Collidable;
import model.collision.Collision;
import model.collision.CollisionHandler;
import model.movable.Movable;
import model.objectsModel.epsilon.Bullet;
import model.objectsModel.epsilon.Epsilon;
import view.gameGUI.GameFrame;
import view.gameGUI.GamePanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Wyrm implements Movable, Paintable, Collidable , Serializable {
    private double HP = 12;
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
    private WyrmPanel panel;
    private Image image;
    private boolean linearMovement;
    private int clockwise = 1;
    private final java.util.Timer shootTimer = new java.util.Timer();

    public Wyrm(double x, double y) {
        this.x = x;
        this.y = y;

        panel = new WyrmPanel();

        try {
            Image yourImage = (Image) ImageIO.read(Constants.WYRM_PIC);
            image = yourImage.getScaledInstance((int) width, (int) height, Image.SCALE_DEFAULT);
        } catch (Exception e) {
            System.out.println("exception in wyrm paint");
        }
        shootTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                shootBullet();
            }
        }, 3000, 1500);
    }

    public void selfDestruct() {
        GameState.panels.remove(panel);
        GameFrame.getInstance().remove(panel);
    }

    public void checkCollisions() {
        Point2D epsilonCollisionPoint = Collision.checkEpsilonCollision(this);
        if (epsilonCollisionPoint != null) {
            CollisionHandler.handleCollisionOnPoint(epsilonCollisionPoint);
            changeRotation();
        }
        for (int j = 0; j < GameState.trigoraths.size(); j++) {
            Point2D collisionPoint = Collision.checkTwoPolyEntityCollision(this, GameState.trigoraths.get(j));
            if (collisionPoint != null) {
                CollisionHandler.handleCollisionOnPoint(collisionPoint);
                changeRotation();
            }
        }
        for (int j = 0; j < GameState.squarantines.size(); j++) {
            Point2D collisionPoint = Collision.checkTwoPolyEntityCollision(this, GameState.squarantines.get(j));
            if (collisionPoint != null) {
                CollisionHandler.handleCollisionOnPoint(collisionPoint);
                changeRotation();
            }
        }
        for (int j = 0; j < GameState.omenocts.size(); j++) {
            Point2D collisionPoint = Collision.checkTwoPolyEntityCollision(this, GameState.omenocts.get(j));
            if (collisionPoint != null) {
                CollisionHandler.handleCollisionOnPoint(collisionPoint);
                changeRotation();
            }
        }
        for (int j = 0; j < GameState.necropicks.size(); j++) {
            Point2D collisionPoint = Collision.checkTwoPolyEntityCollision(this, GameState.necropicks.get(j));
            if (collisionPoint != null) {
                CollisionHandler.handleCollisionOnPoint(collisionPoint);
                changeRotation();
            }
        }
        for (int j = 0; j < GameState.wyrms.size(); j++) {
            if (GameState.wyrms.get(j) != this) {
                Point2D collisionPoint = Collision.checkTwoPolyEntityCollision(this, GameState.wyrms.get(j));
                if (collisionPoint != null) {
                    CollisionHandler.handleCollisionOnPoint(collisionPoint);
                    GameState.wyrms.get(j).changeRotation();
                }
            }
        }
        for (int i = 0; i < GameState.barricados.size(); i++) {
            Point2D collisionPoint = Collision.checkTwoPolyEntityCollision(this, GameState.barricados.get(i));
            if (collisionPoint != null) {
                CollisionHandler.handleCollisionOnPoint(collisionPoint);
                changeRotation();
            }
        }
    }

    class WyrmPanel extends JPanel {
        public WyrmPanel() {
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
            g.drawImage(image, (int) x - locX, (int) y - locY, this);
            g.setFont(Constants.BOLD_15);
            ArrayList<Paintable> paintables = GameState.getPaintables();
            for (Paintable paintable : paintables) {
                paintable.selfPaint(g, this);
            }
            g.dispose();
    }

}

    void shootBullet() {
        Bullet bullet = new Bullet(x, y, false, Constants.WYRM_PINK, 8);
        bullet.calculateMovingDirection(Epsilon.getInstance().getX(), Epsilon.getInstance().getY());
        GameState.bullets.add(bullet);
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
            angle += 0.5 * clockwise;
            angle %= 360;
            x = acquiredX + (int) (radiusFromEpsilon * Math.cos(Math.toRadians(angle)));
            y = acquiredY + (int) (radiusFromEpsilon * Math.sin(Math.toRadians(angle)));
        }
        panel.setLocation((int) x - 10, (int) y - 10);
    }

    @Override
    public void calculateMovingDirection(double x, double y) {
        if(Calculator.distance(x, y, this.x, this.y) >= radiusFromEpsilon){
            acquired = false;
        }
        if (!acquired) {
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
                if (angle >= 0) {
                    angle -= 180;
                    angle = Math.abs(angle);
                } else {
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
    public void selfPaint(Graphics g , JPanel panel) {
        int locX = panel.getX();
        int locY = panel.getY();
        g.drawImage(image, ((int) x - locX), ((int) y - locY), panel);

//        locX = panel.getX();
//        locY = panel.getY();
//        Graphics g2 = panel.getGraphics();
//        g2.drawImage(image, (int) ((int) x - locX), (int) ((int) y - locY), panel);
//        panel.setLocation((int) ((int) x - 10), (int) ((int) y - 10));
    }

    @Override
    public int[] getXPoints() {
        return new int[]{(int) (x), (int) (x + width), (int) (x + width), (int) (x)};
    }

    @Override
    public int[] getYPoints() {
        return new int[]{(int) (y), (int) (y), (int) (y + height), (int) (y + height)};
    }

    public int[] getRelativeXPoints(JPanel panel) {
        int locationX = panel.getX();
        return new int[]{(int) getXPoints()[0] - locationX, (int) getXPoints()[1] - locationX,
                (int) getXPoints()[2] - locationX, (int) getXPoints()[3] - locationX};
    }

    public int[] getRelativeYPoints(JPanel panel) {
        int locationY = panel.getY();
        return new int[]{(int) getYPoints()[0] - locationY, (int) getYPoints()[1] - locationY,
                (int) getYPoints()[2] - locationY, (int) getYPoints()[3] - locationY};
    }

    public void changeRotation() {
        clockwise *= -1;
    }

    public double getHP() {
        return HP;
    }

    public void setHP(double HP) {
        this.HP = HP;
    }

    public Timer getShootTimer() {
        return shootTimer;
    }
}
