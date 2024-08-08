package model.objectsModel.enemy;

import controller.logic.EnemyGenerator;

public abstract class Enemy {
    String id;
    int HP;
    double posXHP;
    double posYHP;
    double[] xPoints;
    double[] yPoints;
    double x,y;

    public Enemy(double[] xPoints, double[] yPoints) {
        this.xPoints = xPoints;
        this.yPoints = yPoints;
    }

    public Enemy(double x, double y){
        this.x = x;
        this.y = y;
    }
    public Enemy(){

    }


    public static void selfGenerate(){

    }
}
