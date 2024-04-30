package Model;


import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Epsilon implements movable {
    private int HP;
    private int XP;
    private double x;
    private double y;
    private double radius;
    private double vx;
    private double vy;
    private double MAX_VELOCITY;
    private double ACCELERATION;
    private SpecialAbility ability;
    private boolean accU, accD, accR, accL;
    private boolean decU = true, decD = true, decR = true, decL = true;

    public Epsilon(double x, double y, double radius) {
        this.x = x;
        this.y = y;
        setHP(100);
        File file = new File(Paths.get("").toAbsolutePath() + "/src/main/java/dataBase/XP.txt");
        try {
            Scanner scanner = new Scanner(file);
            XP = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {

        }
        setXP(XP);
        this.radius = radius;
        vx = 0;
        vy = 0;
        ability = new SpecialAbility();
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

    public void updateVertexPosition() {
        vertexX = x;
        vertexY = y - radius - 7;
    }

    public void updateVertexPosition(double cos, double sin) {
        vertexX = x * sin;
        vertexY = (y - radius - 7) * cos;
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
            updateVertexPosition();
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

    public double getVx() {
        return vx;
    }

    public void setVx(double vx) {
        this.vx = vx;
    }

    public double getVy() {
        return vy;
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

    public boolean isAccU() {
        return accU;
    }

    public void setAccU(boolean accU) {
        this.accU = accU;
    }

    public boolean isAccD() {
        return accD;
    }

    public void setAccD(boolean accD) {
        this.accD = accD;
    }

    public boolean isAccR() {
        return accR;
    }

    public void setAccR(boolean accR) {
        this.accR = accR;
    }

    public boolean isAccL() {
        return accL;
    }

    public void setAccL(boolean accL) {
        this.accL = accL;
    }

    public boolean isDecU() {
        return decU;
    }

    public void setDecU(boolean decU) {
        this.decU = decU;
    }

    public boolean isDecD() {
        return decD;
    }

    public void setDecD(boolean decD) {
        this.decD = decD;
    }

    public boolean isDecR() {
        return decR;
    }

    public void setDecR(boolean decR) {
        this.decR = decR;
    }

    public boolean isDecL() {
        return decL;
    }

    public void setDecL(boolean decL) {
        this.decL = decL;
    }

    public void setMAX_VELOCITY(double MAX_VELOCITY) {
        this.MAX_VELOCITY = MAX_VELOCITY;
    }

    public void setACCELERATION(double ACCELERATION) {
        this.ACCELERATION = ACCELERATION;
    }

    public SpecialAbility getAbility() {
        return ability;
    }

    public boolean hasVertex() {
        return vertex;
    }

    public void setVertex(boolean vertex) {
        this.vertex = vertex;
    }

    public int getVertexesNum() {
        return vertexesNum;
    }

    public void setVertexesNum(int vertexesNum) {
        this.vertexesNum = vertexesNum;
    }

    public double getVertexX() {
        return vertexX;
    }

    public void setVertexX(double vertexX) {
        this.vertexX = vertexX;
    }

    public double getVertexY() {
        return vertexY;
    }

    public void setVertexY(double vertexY) {
        this.vertexY = vertexY;
    }

    public void setAbility(SpecialAbility ability) {
        this.ability = ability;
    }


}


