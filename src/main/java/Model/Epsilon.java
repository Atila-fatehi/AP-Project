package Model;

public class Epsilon {
    private int HP;
    private int XP;
    private int x;
    private int y;
    private int radius;
    private int vx;
    private int vy;


    public Epsilon(int x, int y, int radius) {
        this.x = x;
        this.y = y;
        setHP(100);
        setXP(0);
        this.radius = radius;
        vx = 0;
        vy = 0;
    }

    public int getXP() {
        return XP;
    }

    public void setXP(int XP) {
        this.XP = XP;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int getVx() {
        return vx;
    }

    public void setVx(int vx) {
        this.vx = vx;
    }

    public int getVy() {
        return vy;
    }

    public void setVy(int vy) {
        this.vy = vy;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }
}


