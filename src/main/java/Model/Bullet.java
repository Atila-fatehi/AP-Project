package Model;

public class Bullet implements movable {
    private final int Radius = 7;
    private int x;
    private int y;
    private final int constantVelocity = 17;
    private int vx;
    private int vy;

    public void move() {
        x = x + vx;
        y = y + vy;
    }

    public Bullet(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int onTrigorathCollision(int x1, int x2, int x3, int y1, int y2, int y3, int circleRadius, int circleX, int circleY) {
        if (Math.sqrt(Math.pow(x1 - x, 2) + Math.pow(y1 - y, 2)) <= Radius - 2) {
            return 1;
        }
        if (Math.sqrt(Math.pow(x2 - x, 2) + Math.pow(y2 - y, 2)) <= Radius - 2) {
            return 2;
        }
        if (Math.sqrt(Math.pow(x3 - x, 2) + Math.pow(y3 - y, 2)) <= Radius - 2) {
            return 3;
        }
        if (Math.sqrt(Math.pow(circleX - x, 2) + Math.pow(circleY - y, 2)) <= Radius + circleRadius - 2) {
            return 4;
        }
        return 0;
    }

    public int onWallCollision(int w, int h) {
        if (x <= 0) {
            return 1;
        }
        if (y <= 0) {
            return 2;
        }
        if (x >= w) {
            return 3;
        }
        if (y >= h) {
            return 4;
        }
        return 0;
    }

    public int getRadius() {
        return Radius;
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

    public int getConstantVelocity() {
        return constantVelocity;
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
}
