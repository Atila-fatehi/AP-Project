package model.objectsModel.miniBoss;

import controller.logic.GameState;
import controller.util.Constants;
import model.Paintable.Paintable;
import model.collision.Collidable;
import model.collision.Drown;
import model.collision.Drownable;
import model.objectsModel.epsilon.Epsilon;
import view.gameGUI.GamePanel;

import javax.swing.*;
import javax.xml.xpath.XPath;
import java.awt.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class Laser implements Paintable , Drownable , Collidable, Serializable {
    private double[] xPoints;
    private double[] yPoints;
    private int code;
    private HashMap<String , Timer> insMaps = new HashMap<>();
    private int damage = 12;

    public Laser(double[] xPoints, double[] yPoints , int code) {
        this.xPoints = xPoints;
        this.yPoints = yPoints;
        this.code = code;
    }

    @Override
    public void selfPaint(Graphics g , JPanel panel) {
        g.setColor(Constants.ANOTHER_STRING_COLOR);
        g.fillPolygon(getRelativeXPoints(panel), getRelativeYPoints(panel), xPoints.length);

    }

    public int[] getXPoints() {
        return new int[]{(int) xPoints[0], (int) xPoints[1], (int) xPoints[2], (int) xPoints[3]};
    }

    public int[] getYPoints() {
        return new int[]{(int) yPoints[0], (int) yPoints[1], (int) yPoints[2], (int) yPoints[3]};
    }

    public int[] getRelativeXPoints(JPanel panel) {
        int locationX = panel.getX();
        return new int[]{(int) xPoints[0] - locationX, (int) xPoints[1] - locationX,
                (int) xPoints[2] - locationX, (int) xPoints[3] - locationX};
    }

    public int[] getRelativeYPoints(JPanel panel) {
        int locationY = panel.getY();
        return new int[]{(int) yPoints[0] - locationY, (int) yPoints[1] - locationY,
                (int) yPoints[2] - locationY, (int) yPoints[3] - locationY};
    }

    public int getCode() {
        return code;
    }

    public void drown() {
        if(Drown.checkEpsilonDrown(this)){
            if(!insMaps.containsKey("Epsilon")){
                java.util.Timer ep = new java.util.Timer();
                ep.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Epsilon.getInstance().setHP(Epsilon.getInstance().getHP() - damage);
                    }
                } ,1000,1000 );
                insMaps.put("Epsilon" , ep);
            }
        }else{
            if(insMaps.containsKey("Epsilon")){
                insMaps.get("Epsilon").cancel();
                insMaps.remove("Epsilon");
            }
        }
    }
}
