package model.objectsModel;


import controller.FileController;
import controller.util.Constants;
import model.collision.Collidable;
import model.movable.movable;

import java.io.File;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Scanner;

public class Epsilon implements movable, Collidable {

    private static Epsilon instance;

    public static Epsilon getInstance() {
        if (instance == null) instance = new Epsilon(Constants.INITIAL_EPSILON_POS, Constants.INITIAL_EPSILON_POS);
        return instance;
    }

    private int HP;
    private int XP;
    private double x;
    private double y;
    private double radius;
    private double vx;
    private double vy;
    private final double MAX_VELOCITY;
    private final double ACCELERATION;
    private int damageRate = 5;
    private final SpecialAbility ability = new SpecialAbility();
    private boolean accU, accD, accR, accL;
    private boolean decU = true, decD = true, decR = true, decL = true;

    public Epsilon(double x, double y) {
        this.x = x;
        this.y = y;
        this.radius = 13;
        this.HP = 100;
        this.XP = Integer.parseInt(FileController.readXP());
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

    private boolean vertex;
    private int vertexesNum;
    private double vertexX;
    private double vertexY;

    public void addVertex() {
        vertex = true;
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
        if (vertex) {
            vertexX = x;
            vertexY = y - radius - 7;
        }
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

    public void setRadius(double radius) {
        this.radius = radius;
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

    public boolean hasVertex() {
        return vertex;
    }

    public int getVertexesNum() {
        return vertexesNum;
    }

    public double getVertexX() {
        return vertexX;
    }

    public double getVertexY() {
        return vertexY;
    }

    public int getDamageRate() {
        return damageRate;
    }

    public void setDamageRate(int damageRate) {
        this.damageRate = damageRate;
    }

    @Override
    public int[] getXPoints() {
        return new int[]{(int) x};
    }

    @Override
    public int[] getYPoints() {
        return new int[]{(int) y};
    }
}