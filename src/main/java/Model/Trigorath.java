package Model;

public class Trigorath implements movable{
    private int x1,x2,x3;
    private int y1,y2,y3;
    private int innerCircleRadius;
    private static final int constantVelocity = 1;
    private int innerCircleX;
    private int innerCircleY;
    private int vx;
    private int vy;

    public Trigorath(int x1, int x2, int x3, int y1, int y2, int y3, int innerCircleRadius, int innerCircleX, int innerCircleY) {
        this.x1 = x1;
        this.x2 = x2;
        this.x3 = x3;
        this.y1 = y1;
        this.y2 = y2;
        this.y3 = y3;
        this.innerCircleRadius = innerCircleRadius;
        this.innerCircleX = innerCircleX;
        this.innerCircleY = innerCircleY;
    }

    public void calculateMovingDirection(int x , int y){
        double angle = Math.atan2(y - (y1+y3)/2, x - (x1+x2)/2);
        setVx(((int) Math.round(constantVelocity * Math.cos(angle))));
        setVy(((int) Math.round(constantVelocity * Math.sin(angle))));

    }

    public void move(){
        x1 += vx;
        x2 += vx;
        x3 += vx;
        y1 += vy;
        y2 += vy;
        y3 += vy;
    }

    public int getX1() {
        return x1;
    }

    public void setX1(int x1) {
        this.x1 = x1;
    }

    public int getX2() {
        return x2;
    }

    public void setX2(int x2) {
        this.x2 = x2;
    }

    public int getX3() {
        return x3;
    }

    public void setX3(int x3) {
        this.x3 = x3;
    }

    public int getY1() {
        return y1;
    }

    public void setY1(int y1) {
        this.y1 = y1;
    }

    public int getY2() {
        return y2;
    }

    public void setY2(int y2) {
        this.y2 = y2;
    }

    public int getY3() {
        return y3;
    }

    public void setY3(int y3) {
        this.y3 = y3;
    }

    public int getInnerCircleRadius() {
        return innerCircleRadius;
    }

    public void setInnerCircleRadius(int innerCircleRadius) {
        this.innerCircleRadius = innerCircleRadius;
    }

    public int getInnerCircleX() {
        return innerCircleX;
    }

    public void setInnerCircleX(int innerCircleX) {
        this.innerCircleX = innerCircleX;
    }

    public int getInnerCircleY() {
        return innerCircleY;
    }

    public void setInnerCircleY(int innerCircleY) {
        this.innerCircleY = innerCircleY;
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
