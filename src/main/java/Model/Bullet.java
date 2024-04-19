package Model;

public class Bullet implements movable {
    private final double Radius = 7;
    private double x;
    private double y;
    private final double constantVelocity = 17;
    private double vx;
    private double vy;

    public void move() {
        x = x + vx;
        y = y + vy;
    }

    public Bullet(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public int onTrigorathCollision(double x1, double x2, double x3, double y1, double y2, double y3) {
        if (Math.sqrt(Math.pow(x1 - x, 2) + Math.pow(y1 - y, 2)) <= Radius - 2) {
            return 1;
        }
        if (Math.sqrt(Math.pow(x2 - x, 2) + Math.pow(y2 - y, 2)) <= Radius - 2) {
            return 2;
        }
        if (Math.sqrt(Math.pow(x3 - x, 2) + Math.pow(y3 - y, 2)) <= Radius - 2) {
            return 3;
        }
//        if (Math.sqrt(Math.pow(circleX - x, 2) + Math.pow(circleY - y, 2)) <= Radius + circleRadius - 2) {
//            return 4;
//        }
        return 0;
    }

    public int onWallCollision(double w, double h) {
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

    public double getRadius() {
        return Radius;
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

    public double getConstantVelocity() {
        return constantVelocity;
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
}
