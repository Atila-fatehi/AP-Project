package model.objectsModel.miniBoss;

import controller.MouseController;
import controller.logic.GameState;
import controller.util.Constants;
import jdk.jshell.spi.SPIResolutionException;
import model.Paintable.Paintable;
import model.collision.Collidable;
import model.collision.Collision;
import model.collision.CollisionHandler;
import model.objectsModel.epsilon.Epsilon;
import view.gameGUI.GameFrame;
import view.gameGUI.GamePanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.TimerTask;

public class BlackOrb implements Collidable, Paintable, Serializable {
    private double HP = 30;
    private double x;
    private double y;
    private final double size = 100;
    private boolean damageable;
    private OrbPanel panel;
    private int code;

    public BlackOrb(double x, double y, int code) {
        this.x = x;
        this.y = y;
        this.code = code;

        panel = new OrbPanel();

    }

    @Override
    public int[] getXPoints() {
        return new int[0];
    }

    @Override
    public int[] getYPoints() {
        return new int[0];
    }

    @Override
    public int[] getRelativeXPoints(JPanel panel) {
        return new int[0];
    }

    @Override
    public int[] getRelativeYPoints(JPanel panel) {
        return new int[0];
    }

    @Override
    public void selfPaint(Graphics g, JPanel panel) {
        int locX = panel.getX();
        int locY = panel.getY();
        g.drawImage(Constants.ORB_IMG, (int) ((int) x - locX), (int) ((int) y - locY), panel);

//        locX = panel.getX();
//        locY = panel.getY();
//        Graphics g2 = panel.getGraphics();
//        g2.drawImage(image, (int) ((int) x - locX), (int) ((int) y - locY), panel);
    }

    public void checkCollision() {
        Point2D epsilonCollisionPoint = Collision.checkOrbCollision(this);
        if (epsilonCollisionPoint != null) {
            CollisionHandler.handleCollisionOnPoint(epsilonCollisionPoint);
        }
        for (int j = 0; j < GameState.trigoraths.size(); j++) {
            Point2D collisionPoint = Collision.checkOrbCollision(this, GameState.trigoraths.get(j));
            if (collisionPoint != null) {
                CollisionHandler.handleCollisionOnPoint(collisionPoint);
            }
        }
        for (int j = 0; j < GameState.squarantines.size(); j++) {
            Point2D collisionPoint = Collision.checkOrbCollision(this, GameState.squarantines.get(j));
            if (collisionPoint != null) {
                CollisionHandler.handleCollisionOnPoint(collisionPoint);
            }
        }
        for (int j = 0; j < GameState.omenocts.size(); j++) {
            Point2D collisionPoint = Collision.checkOrbCollision(this, GameState.omenocts.get(j));
            if (collisionPoint != null) {
                CollisionHandler.handleCollisionOnPoint(collisionPoint);
            }
        }
        for (int j = 0; j < GameState.necropicks.size(); j++) {
            Point2D collisionPoint = Collision.checkOrbCollision(this, GameState.necropicks.get(j));
            if (collisionPoint != null) {
                CollisionHandler.handleCollisionOnPoint(collisionPoint);
            }
        }
        for (int j = 0; j < GameState.wyrms.size(); j++) {
            Point2D collisionPoint = Collision.checkOrbCollision(this, GameState.wyrms.get(j));
            if (collisionPoint != null) {
                CollisionHandler.handleCollisionOnPoint(collisionPoint);
                GameState.wyrms.get(j).changeRotation();
            }
        }
        for (int j = 0; j < GameState.barricados.size(); j++) {
            Point2D collisionPoint = Collision.checkOrbCollision(this, GameState.barricados.get(j));
            if (collisionPoint != null) {
                CollisionHandler.handleCollisionOnPoint(collisionPoint);
            }
        }
    }

    public void selfDestruct() {
        GameState.panels.remove(panel);
        GameFrame.getInstance().remove(panel);
    }

    class OrbPanel extends JPanel {
        public OrbPanel() {
            setBounds((int) x - 40, (int) y - 40, (int) size + 80, (int) size + 80);
            setBackground(Constants.DARK_BLUE);
            GameState.panels.add(this);
            GameFrame.getInstance().add(this);
            addMouseListener(new MouseController(this));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            int locX = this.getX();
            int locY = this.getY();
            for (int i = 0; i < GameState.lasers.size(); i++) {
                g.setColor(Constants.ANOTHER_STRING_COLOR);
                g.fillPolygon(GameState.lasers.get(i).getRelativeXPoints(this),
                        GameState.lasers.get(i).getRelativeYPoints(this), GameState.lasers.get(i).getXPoints().length);
            }
            g.drawImage(Constants.ORB_IMG, (int) x - locX, (int) y - locY, this);
            g.setFont(Constants.BOLD_15);
            ArrayList<Paintable> paintables = GameState.getPaintables();
            for (Paintable paintable : paintables) {
                paintable.selfPaint(g, this);
            }
            g.dispose();
        }
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

    public double getSize() {
        return size;
    }

    public double getHP() {
        return HP;
    }

    public void setHP(double HP) {
        this.HP = HP;
    }

    public boolean isDamageable() {
        return damageable;
    }

    public void setDamageable(boolean damageable) {
        this.damageable = damageable;
    }

    public int getCode() {
        return code;
    }
}
