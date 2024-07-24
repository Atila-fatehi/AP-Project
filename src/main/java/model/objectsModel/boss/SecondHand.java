package model.objectsModel.boss;

import controller.MouseController;
import controller.logic.GameState;
import controller.util.Calculator;
import controller.util.Constants;
import controller.util.CostumeTimer;
import model.Paintable.Paintable;
import model.collision.Collidable;
import model.collision.Collision;
import model.collision.CollisionHandler;
import model.movable.Movable;
import model.objectsModel.epsilon.Bullet;
import model.objectsModel.epsilon.Epsilon;
import view.gameGUI.GameFrame;
import view.gameGUI.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class SecondHand implements Collidable, Movable, Paintable, Serializable {
    private double HP = 100;
    private double x;
    private double y;
    private double width = 200;
    private double height = 250;
    private boolean damageable;
    private AttackType attackType = AttackType.NAN;
    private double maxVelocityX;
    private double maxVelocityY;
    private double vx;
    private double vy;
    private double desx;
    private double desy;
    private double accX;
    private double accY;

    double angle;
    boolean acquired;
    boolean linearMovement;
    double acquiredX;
    double acquiredY;
    int radiusFromEpsilon = 600;
    int constantV = 2;
    String id;
    private SecondHandPanel panel;

    public SecondHand(double x, double y) {
        this.x = x;
        this.y = y;
        id = UUID.randomUUID().toString();
        panel = new SecondHandPanel();
    }

    @Override
    public int[] getXPoints() {
        return new int[]{(int) x, (int) x + (int) width, (int) x + (int) width, (int) x};
    }

    @Override
    public int[] getYPoints() {
        return new int[]{(int) y, (int) y, (int) y + (int) height, (int) y + (int) height};
    }


    @Override
    public int[] getRelativeXPoints(JPanel panel) {
        return new int[0];
    }

    @Override
    public int[] getRelativeYPoints(JPanel panel) {
        return new int[0];
    }

    @Override
    public void selfPaint(Graphics g, JPanel panel) {
        int locX = panel.getX();
        int locY = panel.getY();
        g.drawImage(Constants.HAND_SECOND_IMG, (int) ((int) x - locX), (int) ((int) y - locY), panel);
//        g.setColor(Color.ORANGE);
//        g.fillRect((int) x - locX,(int) y - locY , (int) width, (int) height);
//        locX = panel.getX();
//        locY = panel.getY();
//        Graphics g2 = panel.getGraphics();
//        g2.drawImage(image, (int) ((int) x - locX), (int) ((int) y - locY), panel);
    }

    public void checkCollision() {
        Point2D epsilonCollisionPoint = Collision.checkEpsilonCollision(this);
        if (epsilonCollisionPoint != null) {
            CollisionHandler.handleCollisionOnPoint(epsilonCollisionPoint);
        }
    }

    public void selfDestruct() {
        GameState.panels.remove(panel);
        GameFrame.getInstance().remove(panel);
        CostumeTimer.getInstance().getMap().get(id).cancel();
    }

    @Override
    public void move() {
        if (attackType == AttackType.SQUEEZE || attackType == AttackType.SLAP || attackType == AttackType.NAN) {
            if (Calculator.distance(desx, desy, x, y) >= 10) {
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
            }
        } else if (attackType == AttackType.PROJECTILE) {
            if (!CostumeTimer.getInstance().getMap().containsKey(id)) {
                java.util.Timer shootTimer = new java.util.Timer();
                shootTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        shootBullet();
                    }
                }, 3000, 1000);
                CostumeTimer.getInstance().newTimer(id, shootTimer);
            }
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
        panel.setLocation((int) x, (int) y);
    }

    private void shootBullet() {
        Bullet bullet = new Bullet(x + width / 2, y + height / 2, false, Color.ORANGE, 10);
        bullet.calculateMovingDirection(Epsilon.getInstance().getX(), Epsilon.getInstance().getY());
        GameState.bullets.add(bullet);
    }

    @Override
    public void calculateMovingDirection(double x, double y) {
        if (attackType == AttackType.SQUEEZE || attackType == AttackType.SLAP) {
            if (attackType == AttackType.SQUEEZE) {
                x = GamePanel.getInstance().getX() + GamePanel.getInstance().getPanelWidth();
                y = GamePanel.getInstance().getY() + GamePanel.getInstance().getPanelHeight() / 2;
            } else if (attackType == AttackType.SLAP) {
                x = x - width / 2;
                y = y - height / 2;
            } else if (attackType == AttackType.NAN) {
                x = 1350;
                y = 130;
            }
            double angle = Math.atan2(y - this.y, x - this.x);
            maxVelocityX = 1 * Math.cos(angle);
            maxVelocityY = 1 * Math.sin(angle);
            accX = Math.cos(angle);
            accY = Math.sin(angle);
            desx = x;
            desy = y;
            acquired = false;
        } else if (attackType == AttackType.PROJECTILE) {
            if (!acquired) {
                linearMovement = Calculator.distance(x, y, (this.x + width), (this.y + height)) >= radiusFromEpsilon;
                if (linearMovement) {
                    double angle = Math.atan2(y - (this.y + height), x - (this.x + width));
                    maxVelocityX = constantV * Math.cos(angle);
                    maxVelocityY = constantV * Math.sin(angle);
                    accX = Math.cos(angle);
                    accY = Math.sin(angle);
                } else {
                    double deltaY = y - (this.y + height);
                    double deltaX = x - (this.x + width);
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
    }

    class SecondHandPanel extends JPanel {
        public SecondHandPanel() {
            setBounds((int) x, (int) y, (int) width, (int) height);
            setBackground(Constants.DARK_BLUE);
            GameState.panels.add(this);
            GameFrame.getInstance().add(this);
            addMouseListener(new MouseController(this));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            int locX = this.getX();
            int locY = this.getY();
            g.drawImage(Constants.HAND_SECOND_IMG, (int) x - locX, (int) y - locY, this);
            g.setFont(Constants.BOLD_15);
            ArrayList<Paintable> paintables = GameState.getPaintables();
            for (Paintable paintable : paintables) {
                paintable.selfPaint(g, this);
            }
            g.dispose();
        }
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getHP() {
        return HP;
    }

    public void setHP(double HP) {
        this.HP = HP;
    }

    public boolean isDamageable() {
        return damageable;
    }

    public void setDamageable(boolean damageable) {
        this.damageable = damageable;
    }


    public java.util.Timer getShootTimer() {
        if (CostumeTimer.getInstance().getMap().get(id) == null) {
            return new Timer();
        } else {
            return CostumeTimer.getInstance().getMap().get(id);
        }
    }

    public String getId() {
        return id;
    }

    public AttackType getAttackType() {
        return attackType;
    }

    public void setAttackType(AttackType attackType) {
        this.attackType = attackType;
    }
}