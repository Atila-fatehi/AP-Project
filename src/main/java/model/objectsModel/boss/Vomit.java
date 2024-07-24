package model.objectsModel.boss;

import controller.util.Calculator;
import controller.util.CostumeMap;
import model.Paintable.Paintable;
import model.collision.Drown;
import model.collision.Drownable;
import model.objectsModel.epsilon.Epsilon;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TimerTask;
import java.util.UUID;

public class Vomit implements Serializable, Paintable, Drownable {

    double x;
    double y;
    double radius;
    public static ArrayList<Vomit> vomits = new ArrayList<>();
    private String id;
    private int damage = 10;

    public Vomit(double x, double y) {
        this.x = x;
        this.y = y;
        id = UUID.randomUUID().toString();
        CostumeMap.getInstance().newTimer(id , new HashMap<>());
        vomits.add(this);
        radius = 0;
        new java.util.Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                radius++;
                if (radius == 100) {
                    this.cancel();
                }
            }
        }, 1000, 50);
    }

    @Override
    public void selfPaint(Graphics g, JPanel panel) {
        int locationX = panel.getX();
        int locationY = panel.getY();
        g.setColor(Color.ORANGE);
        g.fillOval((int) x - locationX, (int) y - locationY, (int) radius * 2, (int) radius * 2);
    }

    public void drown() {
        HashMap<String , java.util.Timer> insMaps = CostumeMap.getInstance().getMap().get(id);
        if (Drown.checkEpsilonDrownOnCircle( this, (int) radius)) {
            if (!insMaps.containsKey("EpsilonInVomit")) {
                java.util.Timer ep = new java.util.Timer();
                ep.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Epsilon.getInstance().setHP(Epsilon.getInstance().getHP() - damage);
                    }
                }, 0, 1000);
                insMaps.put("EpsilonInVomit", ep);
            }
        } else {
            if (insMaps.containsKey("EpsilonInVomit")) {
                insMaps.get("EpsilonInVomit").cancel();
                insMaps.remove("EpsilonInVomit");
            }
        }
    }

    @Override
    public int[] getXPoints() {
        return new int[]{(int) (x + radius)};
    }

    @Override
    public int[] getYPoints() {
        return new int[]{(int) (y + radius)};
    }
}
