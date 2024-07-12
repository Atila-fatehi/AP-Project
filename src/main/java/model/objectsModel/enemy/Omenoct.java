package model.objectsModel.enemy;

import model.Paintable.Paintable;
import model.collision.Collidable;
import model.movable.Movable;
import model.objectsModel.EnemyModel;

import java.awt.*;

public class Omenoct extends EnemyModel implements Paintable{


    public Omenoct(double[] xPoints, double[] yPoints, int HP, double posXHP, double posYHP, double maxVelocityX, double maxVelocityY, double vx, double vy, double accX, double accY, boolean played) {
        super(xPoints, yPoints, HP, posXHP, posYHP, maxVelocityX, maxVelocityY, vx, vy, accX, accY, played);
    }

    @Override
    public void selfPaint(Graphics g) {

    }
}
