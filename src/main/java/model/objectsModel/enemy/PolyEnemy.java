package model.objectsModel.enemy;

public abstract class PolyEnemy {
    String id;
    int HP;
    double posXHP;
    double posYHP;
    double[] xPoints;
    double[] yPoints;

    public PolyEnemy(double[] xPoints, double[] yPoints) {
        this.xPoints = xPoints;
        this.yPoints = yPoints;
    }
}
