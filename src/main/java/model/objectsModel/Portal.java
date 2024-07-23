package model.objectsModel;

import controller.util.Calculator;
import controller.util.Constants;
import model.Paintable.Paintable;
import model.objectsModel.epsilon.Epsilon;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Portal implements Paintable {

    double x;
    double y;
    double radius;
    boolean collide;
    public static ArrayList<Portal> portals = new ArrayList<>();


    public Portal(double x, double y) {
        this.x = x;
        this.y = y;
        portals.add(this);
        radius = 80;
    }

    @Override
    public void selfPaint(Graphics g, JPanel panel) {
        int locationX = panel.getX();
        int locationY = panel.getY();
        g.setColor(Constants.EPSILON_COLOR);
        g.fillOval((int) x - locationX, (int) y - locationY, (int) radius * 2, (int) radius * 2);
    }

    public boolean epsilonCollision(){
        return Calculator.distance(Epsilon.getInstance().getX(), Epsilon.getInstance().getY(), x, y)
                <= radius + Epsilon.getInstance().getRadius() + 3;
    }
}
