package model.objectsModel.epsilon;


import controller.FileController;
import controller.logic.GameState;
import controller.util.Constants;
import model.Paintable.Paintable;
import model.collision.Collidable;
import model.collision.Collision;
import model.collision.WallCollidable;
import model.collision.WallCollisionHandler;
import model.movable.Movable;
import model.objectsModel.Skill;
import view.gameGUI.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.Objects;

public class Epsilon implements Movable, Collidable, Paintable, WallCollidable, Serializable {

    private static Epsilon instance;

    public static void makeInstance() {
        instance = new Epsilon(Constants.INITIAL_EPSILON_POSX, Constants.INITIAL_EPSILON_POSY);
    }

    public static Epsilon getInstance() {
        if (instance == null) instance = new Epsilon(Constants.INITIAL_EPSILON_POSX, Constants.INITIAL_EPSILON_POSY);
        return instance;
    }
    public static void setInstance(Epsilon epsilon) {
        instance = epsilon;
        instance.XP = Integer.parseInt(FileController.readXP());
        if (FileController.readAbilities() == 11) instance.skill.getAttack().replace("Ares", true);
        if (FileController.readAbilities() == 12) instance.skill.getAttack().replace("Astrape", true);
        if (FileController.readAbilities() == 13) instance.skill.getAttack().replace("Cerberus", true);

        if (FileController.readAbilities() == 21) instance.skill.getDefence().replace("Aceso", true);
        if (FileController.readAbilities() == 22) instance.skill.getDefence().replace("Melampus", true);
        if (FileController.readAbilities() == 23) instance.skill.getDefence().replace("Chiron", true);

        if (FileController.readAbilities() == 31) instance.skill.getShapeShift().replace("Proteus", true);
        if (FileController.readAbilities() == 32) instance.skill.getShapeShift().replace("Empusa", true);
        if (FileController.readAbilities() == 33) instance.skill.getShapeShift().replace("Dolus", true);

        if (Objects.requireNonNull(FileController.readSettings())[0] < 33) {
            instance.MAX_VELOCITY = 7;
            instance.ACCELERATION = 0.5;
        } else if (Objects.requireNonNull(FileController.readSettings())[0] > 66) {
            instance.MAX_VELOCITY = 15;
            instance.ACCELERATION = 2;
        } else {
            instance.MAX_VELOCITY = 11;
            instance.ACCELERATION = 1;
        }
        instance.currentPanel = GamePanel.getInstance();
    }

    private int HP = 1000000;
    private int XP;
    private double x;
    private double y;
    private double radius = 13;
    private double vx;
    private double vy;
    private double MAX_VELOCITY;
    private double ACCELERATION;
    private int damageRate = 5;
    private final Skill skill = new Skill();
    private boolean accU, accD, accR, accL;
    private boolean decU = true, decD = true, decR = true, decL = true;
    private boolean hasVertex;
    private int vertexesNum;
    private double vertexX;
    private double vertexY;
    private JPanel currentPanel;
    public boolean savedToCheckPoint;
    public boolean alreadyDead;
    public Epsilon(double x, double y) {
        this.x = x;
        this.y = y;
        this.XP = Integer.parseInt(FileController.readXP());
        if (FileController.readAbilities() == 11) skill.getAttack().replace("Ares", true);
        if (FileController.readAbilities() == 12) skill.getAttack().replace("Astrape", true);
        if (FileController.readAbilities() == 13) skill.getAttack().replace("Cerberus", true);

        if (FileController.readAbilities() == 21) skill.getDefence().replace("Aceso", true);
        if (FileController.readAbilities() == 22) skill.getDefence().replace("Melampus", true);
        if (FileController.readAbilities() == 23) skill.getDefence().replace("Chiron", true);

        if (FileController.readAbilities() == 31) skill.getShapeShift().replace("Proteus", true);
        if (FileController.readAbilities() == 32) skill.getShapeShift().replace("Empusa", true);
        if (FileController.readAbilities() == 33) skill.getShapeShift().replace("Dolus", true);

        if (Objects.requireNonNull(FileController.readSettings())[0] < 33) {
            MAX_VELOCITY = 7;
            ACCELERATION = 0.5;
        } else if (Objects.requireNonNull(FileController.readSettings())[0] > 66) {
            MAX_VELOCITY = 15;
            ACCELERATION = 2;
        } else {
            MAX_VELOCITY = 11;
            ACCELERATION = 1;
        }
        vx = 0;
        vy = 0;
        currentPanel = GamePanel.getInstance();
    }

    public void addVertex() {
        hasVertex = true;
        if (vertexesNum != 4) {
            vertexesNum++;
        }
    }

    public void move() {
        x += vx;
        y += vy;
        if (accU) {
            if (vy >= -MAX_VELOCITY) {
                vy -= ACCELERATION;
            }
        }
        if (accD) {
            if (vy <= MAX_VELOCITY) {
                vy += ACCELERATION;
            }
        }
        if (accL) {
            if (vx >= -MAX_VELOCITY) {
                vx -= ACCELERATION;
            }
        }
        if (accR) {
            if (vx <= MAX_VELOCITY) {
                vx += ACCELERATION;
            }
        }
        if (decU) {
            if (vy < 0) {
                vy += ACCELERATION;
            }
        }
        if (decD) {
            if (vy > 0) {
                vy -= ACCELERATION;
            }
        }
        if (decL) {
            if (vx < 0) {
                vx += ACCELERATION;
            }
        }
        if (decR) {
            if (vx > 0) {
                vx -= ACCELERATION;
            }
        }
        if (hasVertex) {
            vertexX = x;
            vertexY = y - radius - 7;
        }
    }

    @Override
    public void calculateMovingDirection(double x, double y) {

    }

    @Override
    public void selfPaint(Graphics g, JPanel panel) {
        int locX = panel.getX();
        int locY = panel.getY();
        g.setColor(Constants.EPSILON_COLOR);
        g.fillOval((int) (x - radius - locX), (int) (y - radius - locY), (int) radius * 2, (int) radius * 2);
        int inner = 4;
        g.setColor(Constants.DARK_BLUE);
        g.fillOval((int) (x - radius - locX) + inner, (int) (y - radius - locY) + inner, (int) (radius - inner) * 2, (int) (radius - inner) * 2);

        if (dismay) {
            g.setColor(Constants.EPSILON_COLOR);
            g.drawOval((int) (x - 150 - locX), (int) (y - 150 - locY), 300, 300);
        }
//        if (hasVertex) {
//            g.drawLine((int) (x - radius), (int) y, (int) vertexX, (int) vertexY);
//            g.drawLine((int) (x + radius), (int) y, (int) vertexX, (int) vertexY);
//            if (vertexesNum >= 2) {
//                g.drawLine((int) (x - radius), (int) y, (int) vertexX, (int) (vertexY + 2 * radius + 14));
//                g.drawLine((int) (x + radius), (int) y, (int) vertexX, (int) (vertexY + 2 * radius + 14));
//            }
//            if (vertexesNum >= 3) {
//                g.drawLine((int) x, (int) (y - radius), (int) (x + radius + 7), (int) y);
//                g.drawLine((int) x, (int) (y + radius), (int) (x + radius + 7), (int) y);
//            }
//            if (vertexesNum >= 4) {
//                g.drawLine((int) x, (int) (y - radius), (int) (x - radius - 7), (int) y);
//                g.drawLine((int) x, (int) (y + radius), (int) (x - radius - 7), (int) y);
//            }
//        }
//        for (int i = 0; i < GameState.panels.size(); i++) {
//            locX = GameState.panels.get(i).getX();
//            locY = GameState.panels.get(i).getY();
//            Graphics g2 = GameState.panels.get(i).getGraphics();
//            g2.setColor(Constants.EPSILON_COLOR);
//            g2.fillOval((int) (x - radius - locX), (int) (y - radius - locY), (int) radius * 2, (int) radius * 2);
//            g2.setColor(Constants.DARK_BLUE);
//            g2.fillOval((int) (x - radius - locX) + inner, (int) (y - radius - locY) + inner, (int) (radius - inner) * 2, (int) (radius - inner) * 2);
//        }
    }

    @Override
    public int[] getXPoints() {
        return new int[]{(int) x};
    }

    @Override
    public int[] getYPoints() {
        return new int[]{(int) y};
    }

    @Override
    public int[] getRelativeXPoints(JPanel panel) {
        return new int[]{getXPoints()[0] - panel.getX()};
    }

    @Override
    public int[] getRelativeYPoints(JPanel panel) {
        return new int[]{getYPoints()[0] - panel.getY()};
    }

    @Override
    public int wallCollision() {
        WallCollisionHandler.handleEpsilonWallCollision();
        return 0;
    }


    public int getXP() {
        return XP;
    }

    public void setXP(int XP) {
        this.XP = XP;
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

    public double getRadius() {
        return radius;
    }

    public void setVx(double vx) {
        this.vx = vx;
    }

    public void setVy(double vy) {
        this.vy = vy;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public void setAccU(boolean accU) {
        this.accU = accU;
    }


    public void setAccD(boolean accD) {
        this.accD = accD;
    }


    public void setAccR(boolean accR) {
        this.accR = accR;
    }


    public void setAccL(boolean accL) {
        this.accL = accL;
    }

    public boolean isAccU() {
        return accU;
    }

    public boolean isAccD() {
        return accD;
    }

    public boolean isAccR() {
        return accR;
    }

    public boolean isAccL() {
        return accL;
    }

    public void setDecU(boolean decU) {
        this.decU = decU;
    }

    public void setDecD(boolean decD) {
        this.decD = decD;
    }


    public void setDecR(boolean decR) {
        this.decR = decR;
    }


    public void setDecL(boolean decL) {
        this.decL = decL;
    }

    public Skill getSkill() {
        return skill;
    }

    public int getDamageRate() {
        return damageRate;
    }

    public void setDamageRate(int damageRate) {
        this.damageRate = damageRate;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    private boolean dismay;

    public void setDismay(boolean b) {
        dismay = b;
    }

    public void setCurrentPanel() {
        boolean in = false;
        int[] xPoints = new int[]{GamePanel.getInstance().getX(), GamePanel.getInstance().getX() + GamePanel.getInstance().getWidth(),
                GamePanel.getInstance().getX() + GamePanel.getInstance().getWidth(), GamePanel.getInstance().getX()};
        int[] yPoints = new int[]{GamePanel.getInstance().getY(), GamePanel.getInstance().getY(),
                GamePanel.getInstance().getX() + GamePanel.getInstance().getHeight(),
                GamePanel.getInstance().getY() + GamePanel.getInstance().getHeight()};
        Polygon polygon = new Polygon(xPoints, yPoints, 4);
        if (Collision.checkPointCollision(new Point2D.Double(x, y), polygon)) {
            currentPanel = GamePanel.getInstance();
            in = true;
        }
        for (int i = 0; i < GameState.panels.size(); i++) {
            xPoints = new int[]{GameState.panels.get(i).getX(), GameState.panels.get(i).getX() + GameState.panels.get(i).getWidth(),
                    GameState.panels.get(i).getX() + GameState.panels.get(i).getWidth(), GameState.panels.get(i).getX()};
            yPoints = new int[]{GameState.panels.get(i).getY(), GameState.panels.get(i).getY(),
                    GameState.panels.get(i).getX() + GameState.panels.get(i).getHeight(),
                    GameState.panels.get(i).getY() + GameState.panels.get(i).getHeight()};
            polygon = new Polygon(xPoints, yPoints, 4);
            if (Collision.checkPointCollision(new Point2D.Double(x, y), polygon)) {
                if (!in) {
                    currentPanel = GameState.panels.get(i);
                } else {

                }
            }
        }

    }

    public JPanel getCurrentPanel() {
        return currentPanel;
    }
}