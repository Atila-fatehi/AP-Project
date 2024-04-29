package Model;


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
        setXP(1000);
        this.radius = radius;
        vx = 0;
        vy = 0;
        ability = new SpecialAbility();
    }
    public void addVertex(){

    }

    public void activateAbility(){
        if(ability.proteus){
            addVertex();
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

    public void setAbility(SpecialAbility ability) {
        this.ability = ability;
    }
}


