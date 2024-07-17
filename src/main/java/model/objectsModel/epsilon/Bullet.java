package model.objectsModel.epsilon;

import controller.logic.GameState;
import controller.util.Constants;
import model.Paintable.Paintable;
import model.collision.Collidable;
import model.collision.Collision;
import model.collision.WallCollidable;
import model.movable.Movable;
import view.gameGUI.GamePanel;

import java.awt.*;

public class Bullet implements Movable, WallCollidable, Collidable, Paintable {

    private double x;
    private double y;
    private double vx;
    private double vy;
    private double radius;
    private double constantVelocity;
    private Color color;
    private boolean fromEpsilon;

    public void move() {
        x = x + vx;
        y = y + vy;
    }

    @Override
    public void calculateMovingDirection(double x, double y) {
        double angle = Math.atan2(y - this.y, x - this.x);
        vx = constantVelocity * Math.cos(angle);
        vy = constantVelocity * Math.sin(angle);
    }

    public Bullet(double x, double y , boolean fromEpsilon , Color color) {
        this.x = x;
        this.y = y;
        this.fromEpsilon =fromEpsilon;
        this.color = color;
        if(fromEpsilon){
            constantVelocity = 15;
            radius = 4;
        }else{
            constantVelocity = 5;
            radius = 8;
        }
    }

    @Override
    public int wallCollision() {
        if (x <= GamePanel.getInstance().getLocationX()) {
            return 1;
        }
        if (y <= GamePanel.getInstance().getLocationY()) {
            return 2;
        }
        if (x >= GamePanel.getInstance().getPanelWidth() + GamePanel.getInstance().getLocationX()) {
            return 3;
        }
        if (y >= GamePanel.getInstance().getPanelHeight() + GamePanel.getInstance().getLocationY()) {
            return 4;
        }
        return 0;
    }

    public double getRadius() {
        return radius;
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
    public boolean isFromEpsilon() {
        return fromEpsilon;
    }

    @Override
    public int[] getXPoints() {
        return new int[]{(int) x};
    }

    @Override
    public int[] getYPoints() {
        return new int[]{(int) y};
    }

    @Override
    public void selfPaint(Graphics g) {
        int locX = GamePanel.getInstance().getLocationX();
        int locY = GamePanel.getInstance().getLocationY();
        g.setColor(color);
        g.fillOval((int) (x - radius - locX), (int) (y - radius - locY), (int) radius * 2, (int) radius * 2);

        for (int i = 0; i < GameState.panels.size(); i++) {
            locX = GameState.panels.get(i).getX();
            locY = GameState.panels.get(i).getY();
            Graphics g2 = GameState.panels.get(i).getGraphics();
            g2.setColor(color);
            g2.fillOval((int) (x - radius - locX), (int) (y - radius - locY), (int) radius * 2, (int) radius * 2);
        }
    }
}
