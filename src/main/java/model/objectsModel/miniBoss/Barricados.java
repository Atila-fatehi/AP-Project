package model.objectsModel.miniBoss;

import controller.logic.GameState;
import controller.util.Constants;
import controller.util.CostumeTimer;
import model.Paintable.Paintable;
import model.collision.Collidable;
import model.collision.Collision;
import model.collision.CollisionHandler;
import view.gameGUI.GameFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import java.util.TimerTask;
import java.util.UUID;

public class Barricados implements Paintable, Collidable, Serializable {

    private double x;
    private double y;
    public static final double size = 200;
    private boolean played;
    private BarriPanel panel;
    String id;
    public boolean isRigid;

    public Barricados(double x, double y) {
        this.x = x;
        this.y = y;
        id = UUID.randomUUID().toString();
        isRigid = new Random().nextBoolean();
        panel = new BarriPanel();
        java.util.Timer timer = new java.util.Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                selfDestruct();
                timer.cancel();
            }
        }, 1000 * 60 * 2, 1111);
        CostumeTimer.getInstance().newTimer(id, timer);
    }

    public void selfDestruct() {
        GameState.panels.remove(panel);
        GameFrame.getInstance().remove(panel);
        GameFrame.getInstance().repaint();
        GameState.barricados.remove(this);
    }


    public void checkCollision() {
        Point2D epsilonCollisionPoint = Collision.checkEpsilonCollision(this);
        if (epsilonCollisionPoint != null) {
            CollisionHandler.stopEpsilon(epsilonCollisionPoint);
        }
        for (int j = 0; j < GameState.trigoraths.size(); j++) {
            Point2D collisionPoint = Collision.checkTwoPolyEntityCollision(this, GameState.trigoraths.get(j));
            if (collisionPoint != null) {
                CollisionHandler.handleCollisionOnPoint(collisionPoint);
            }
        }
        for (int j = 0; j < GameState.squarantines.size(); j++) {
            Point2D collisionPoint = Collision.checkTwoPolyEntityCollision(this, GameState.squarantines.get(j));
            if (collisionPoint != null) {
                CollisionHandler.handleCollisionOnPoint(collisionPoint);
            }
        }
        for (int j = 0; j < GameState.omenocts.size(); j++) {
            Point2D collisionPoint = Collision.checkTwoPolyEntityCollision(this, GameState.omenocts.get(j));
            if (collisionPoint != null) {
                CollisionHandler.handleCollisionOnPoint(collisionPoint);
            }
        }
        for (int j = 0; j < GameState.necropicks.size(); j++) {
            Point2D collisionPoint = Collision.checkTwoPolyEntityCollision(this, GameState.necropicks.get(j));
            if (collisionPoint != null) {
                CollisionHandler.handleCollisionOnPoint(collisionPoint);
            }
        }
        for (int j = 0; j < GameState.wyrms.size(); j++) {
            Point2D collisionPoint = Collision.checkTwoPolyEntityCollision(this, GameState.wyrms.get(j));
            if (collisionPoint != null) {
                CollisionHandler.handleCollisionOnPoint(collisionPoint);
                GameState.wyrms.get(j).changeRotation();
            }
        }
    }

    class BarriPanel extends JPanel {
        public BarriPanel() {
            setBounds((int) x, (int) y, (int) size, (int) size);
            setBackground(Constants.DARK_BLUE);
            GameState.panels.add(this);
            GameFrame.getInstance().add(this);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            int locX = panel.getX();
            int locY = panel.getY();
            g.drawImage(Constants.BARRI_IMG, (int) x - locX, (int) y - locY, this);
            g.setFont(Constants.BOLD_15);
            ArrayList<Paintable> paintables = GameState.getPaintables();
            for (Paintable paintable : paintables) {
                paintable.selfPaint(g, this);
            }
            g.dispose();
        }
    }

    @Override
    public void selfPaint(Graphics g, JPanel panel) {
        int locX = panel.getX();
        int locY = panel.getY();
        g.drawImage(Constants.BARRI_IMG, (int) x - locX, (int) y - locY, panel);

//        locX = panel.getX();
//        locY = panel.getY();
//        Graphics g2 = panel.getGraphics();
//        g2.drawImage(image, (int) x - locX, (int) y - locY, panel);
//        panel.setLocation((int) x, (int) y);
    }

    @Override
    public int[] getXPoints() {
        return new int[]{(int) x, (int) (x + size), (int) (x + size), (int) x};
    }

    @Override
    public int[] getYPoints() {
        return new int[]{(int) y, (int) y, (int) (y + size), (int) (y + size)};
    }

    @Override
    public int[] getRelativeXPoints(JPanel panel) {
        int locX = panel.getX();
        return new int[]{getXPoints()[0] - locX, getXPoints()[1] - locX
                , getXPoints()[2] - locX, getXPoints()[3] - locX};
    }

    @Override
    public int[] getRelativeYPoints(JPanel panel) {
        int locY = panel.getY();
        return new int[]{getYPoints()[0] - locY, getYPoints()[1] - locY
                , getYPoints()[2] - locY, getYPoints()[3] - locY};
    }
}
