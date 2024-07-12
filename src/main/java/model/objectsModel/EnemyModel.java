package model.objectsModel;

public abstract class EnemyModel {
    private double[] xPoints;
    private double[] yPoints;
    private int HP;
    private double posXHP;
    private double posYHP;
    private double maxVelocityX;
    private double maxVelocityY;
    private double vx;
    private double vy;
    private double accX;
    private double accY;
    private boolean played;

    public EnemyModel(double[] xPoints, double[] yPoints, int HP, double posXHP, double posYHP, double maxVelocityX, double maxVelocityY, double vx, double vy, double accX, double accY, boolean played) {
        this.xPoints = xPoints;
        this.yPoints = yPoints;
        this.HP = HP;
        this.posXHP = posXHP;
        this.posYHP = posYHP;
        this.maxVelocityX = maxVelocityX;
        this.maxVelocityY = maxVelocityY;
        this.vx = vx;
        this.vy = vy;
        this.accX = accX;
        this.accY = accY;
        this.played = played;
    }


}
