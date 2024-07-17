package model.objectsModel.epsilon;


import controller.FileController;
import controller.logic.GameState;
import controller.util.Constants;
import model.Paintable.Paintable;
import model.collision.Collidable;
import model.collision.WallCollidable;
import model.collision.WallCollisionHandler;
import model.movable.Movable;
import model.objectsModel.SpecialAbility;
import view.gameGUI.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Epsilon implements Movable, Collidable, Paintable, WallCollidable {

    private static Epsilon instance;

    public static Epsilon getInstance() {
        if (instance == null) instance = new Epsilon(Constants.INITIAL_EPSILON_POSX, Constants.INITIAL_EPSILON_POSY);
        return instance;
    }

    private int HP = 100;
    private int XP;
    private double x;
    private double y;
    private double radius = 13;
    private double vx;
    private double vy;
    private final double MAX_VELOCITY;
    private final double ACCELERATION;
    private int damageRate = 5;
    private final SpecialAbility ability = new SpecialAbility();
    private boolean accU, accD, accR, accL;
    private boolean decU = true, decD = true, decR = true, decL = true;
    private boolean hasVertex;
    private int vertexesNum;
    private double vertexX;
    private double vertexY;
    private JPanel currentPanel;

    public Epsilon(double x, double y) {
        this.x = x;
        this.y = y;
        this.XP = Integer.parseInt(FileController.readXP());
        currentPanel = GamePanel.getInstance();
        if (FileController.readAbilities() == 11) {
            ability.setAres(true);
        }
        if (FileController.readAbilities() == 21) {
            ability.setAceso(true);
        }
        if (FileController.readAbilities() == 31) {
            ability.setProteus(true);
        }
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
        ability.setActive(false);
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
        currentPanel = GamePanel.getInstance();
    }

    @Override
    public void calculateMovingDirection(double x, double y) {

    }

    @Override
    public void selfPaint(Graphics g) {
        int locX = (int) currentPanel.getLocation().getX();
        int locY = (int) currentPanel.getLocation().getY();
        g.setColor(Constants.EPSILON_COLOR);
        g.fillOval((int) (x - radius - locX), (int) (y - radius - locY), (int) radius * 2, (int) radius * 2);
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

        int inner = 4;
        g.setColor(Constants.DARK_BLUE);
        g.fillOval((int) (x - radius - locX) + inner, (int) (y - radius - locY) + inner, (int) (radius - inner) * 2, (int) (radius - inner) * 2);

        for (int i = 0; i < GameState.panels.size(); i++) {
            locX = GameState.panels.get(i).getX();
            locY = GameState.panels.get(i).getY();
            Graphics g2 = GameState.panels.get(i).getGraphics();
            g2.setColor(Constants.EPSILON_COLOR);
            g2.fillOval((int) (x - radius - locX), (int) (y - radius - locY), (int) radius * 2, (int) radius * 2);
            g2.setColor(Constants.DARK_BLUE);
            g2.fillOval((int) (x - radius - locX) + inner, (int) (y - radius - locY) + inner, (int) (radius - inner) * 2, (int) (radius - inner) * 2);
        }
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

    public SpecialAbility getAbility() {
        return ability;
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

}