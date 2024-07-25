package model.objectsModel.boss;

import controller.MouseController;
import controller.logic.EnemyGenerator;
import controller.logic.GameState;
import controller.util.Calculator;
import controller.util.Constants;
import controller.util.CostumeTimer;
import model.Paintable.Paintable;
import model.collision.Collidable;
import model.collision.Collision;
import model.collision.CollisionHandler;
import model.movable.Movable;
import model.objectsModel.Portal;
import model.objectsModel.epsilon.Bullet;
import model.objectsModel.epsilon.Epsilon;
import model.objectsModel.miniBoss.BlackOrb;
import view.gameGUI.GameFrame;
import view.gameGUI.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.*;
import java.util.Timer;

public class Smiley implements Collidable, Movable, Paintable, Serializable {
    private int HP = 10;
    private double x;
    private double y;
    private double posxHP;
    private double posyHP;
    private double width = 250;
    private double height = 250;
    private boolean damageable;
    private AttackType attackType = AttackType.NAN;
    int vomitCounter;

    private double maxVelocityX;
    private double maxVelocityY;
    private double vx;
    private double vy;
    private double accX;
    private double accY;
    String id;

    double angle;
    boolean acquired;
    boolean linearMovement;
    double acquiredX;
    double acquiredY;
    int radiusFromEpsilon = 600;
    double constantV = 2d;
    double desx;
    double desy;

    private SmileyPanel panel;

    public Smiley(double x, double y) {
        this.x = x;
        this.y = y;
        panel = new SmileyPanel();
        id = UUID.randomUUID().toString();
    }

    @Override
    public int[] getXPoints() {
        return new int[0];
    }

    @Override
    public int[] getYPoints() {
        return new int[0];
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
        if(dead){
            g.drawImage(Constants.DEAD_IMG, (int) ((int) x - locX), (int) ((int) y - locY), panel);
        }else {
            g.drawImage(Constants.SMILEY_IMG, (int) ((int) x - locX), (int) ((int) y - locY), panel);
        }
        g.setColor(Color.ORANGE);
        g.drawString(String.valueOf(HP), (int) posxHP - locX, (int) posyHP - locY);
//        g.setColor(Color.ORANGE);
//        g.fillOval((int) x- locX, (int) y- locY, (int) width,(int) height);
//        locX = panel.getX();
//        locY = panel.getY();
//        Graphics g2 = panel.getGraphics();
//        g2.drawImage(image, (int) ((int) x - locX), (int) ((int) y - locY), panel);
    }

    public void checkCollision() {
        Point2D epsilonCollisionPoint = Collision.checkSmileyCollision(this);
        if (epsilonCollisionPoint != null) {
            CollisionHandler.handleCollisionOnPoint(epsilonCollisionPoint);
        }
    }

    boolean dead;

    public void selfDestruct() {
//        GameState.panels.remove(panel);
//        GameFrame.getInstance().remove(panel);
        dead = true;
        CostumeTimer.getInstance().getMap().get(id).cancel();
    }


    void shootBullet() {
        Bullet bullet = new Bullet(x + width / 2, y + height / 2, false, Color.ORANGE, 10);
        bullet.calculateMovingDirection(Epsilon.getInstance().getX(), Epsilon.getInstance().getY());
        GameState.bullets.add(bullet);
    }

    @Override
    public void move() {
        if (attackType == AttackType.SQUEEZE ||
                attackType == AttackType.VOMIT ||
                attackType == AttackType.SLAP ||
                attackType == AttackType.NAN || attackType == AttackType.RAPID_FIRE
                || attackType == AttackType.QUAKE
                || attackType == AttackType.POWER_PUNCH) {
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
            if (attackType == AttackType.VOMIT) {
                vomitCounter++;
                if (vomitCounter <= 5) new Vomit(EnemyGenerator.randomXonScreen(), EnemyGenerator.randomYonScreen());
            } else {
                for (int i = 0; i < Vomit.vomits.size(); i++) {
                    try {
                        CostumeTimer.getInstance().getMap().remove(Vomit.vomits.get(i).id).cancel();
                    } catch (Exception e) {

                    }

                }
                Vomit.vomits.clear();
                vomitCounter = 0;
                if (!CostumeTimer.getInstance().getMap().containsKey(id)) {
                    java.util.Timer shootTimer = new java.util.Timer();
                    shootTimer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            shootBullet();
                        }
                    }, 3000, 300);
                    CostumeTimer.getInstance().newTimer(id, shootTimer);
                }
            }
        } else if (attackType == AttackType.PROJECTILE) {
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
        posxHP = x + width / 2 - 10;
        posyHP = y + height + 20;
        panel.setLocation((int) x, (int) y);
    }

    @Override
    public void calculateMovingDirection(double x, double y) {
        if (attackType == AttackType.SQUEEZE || attackType == AttackType.VOMIT || attackType == AttackType.SLAP || attackType == AttackType.NAN || attackType == AttackType.RAPID_FIRE
                || attackType == AttackType.POWER_PUNCH || attackType == AttackType.QUAKE) {
            x = 800;
            y = 50;
            double angle = Math.atan2(y - this.y, x - this.x);
            maxVelocityX = constantV * Math.cos(angle);
            maxVelocityY = constantV * Math.sin(angle);
            accX = Math.cos(angle);
            accY = Math.sin(angle);
            desx = x;
            desy = y;
            acquired = false;
        } else if (attackType == AttackType.PROJECTILE) {
            if (!acquired) {
                linearMovement = Calculator.distance(x, y, (this.x + width), (this.y + height / 2)) >= radiusFromEpsilon;
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

    boolean wait = false;

    public void parry() {
        if (!wait) {
            new java.util.Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    wait = false;
                    this.cancel();
                }
            }, 5000, 1111);
            wait = true;
            if (new Random().nextBoolean()) {
                new java.util.Timer().schedule(new TimerTask() {
                    int count = 0;

                    @Override
                    public void run() {
                        x += 5;
                        y -= 5;
                        panel.setLocation((int) x, (int) y);
                        count++;
                        if (count == 100) this.cancel();
                    }
                }, 0, 10);
            } else {
                new java.util.Timer().schedule(new TimerTask() {
                    int count = 0;

                    @Override
                    public void run() {
                        x -= 5;
                        y -= 5;
                        panel.setLocation((int) x, (int) y);
                        count++;
                        if (count == 100) this.cancel();
                    }
                }, 0, 10);
            }
        }
    }


    class SmileyPanel extends JPanel {
        public SmileyPanel() {
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
            g.drawImage(Constants.SMILEY_IMG, (int) x - locX, (int) y - locY, this);
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

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public boolean isDamageable() {
        return damageable;
    }

    public void setDamageable(boolean damageable) {
        this.damageable = damageable;
    }

    public Timer getShootTimer() {
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
