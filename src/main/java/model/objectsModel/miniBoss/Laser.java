package model.objectsModel.miniBoss;

import controller.logic.GameState;
import controller.util.Constants;
import model.Paintable.Paintable;
import model.collision.Collidable;
import model.collision.Drownable;
import view.gameGUI.GamePanel;

import javax.swing.*;
import javax.xml.xpath.XPath;
import java.awt.*;

public class Laser implements Paintable , Drownable , Collidable {
    private double[] xPoints;
    private double[] yPoints;
    int code;

    public Laser(double[] xPoints, double[] yPoints , int code) {
        this.xPoints = xPoints;
        this.yPoints = yPoints;
        this.code = code;
    }

    @Override
    public void selfPaint(Graphics g) {
        g.setColor(Constants.ANOTHER_STRING_COLOR);
        g.fillPolygon(getRelativeXPoints(GamePanel.getInstance()), getRelativeYPoints(GamePanel.getInstance()), xPoints.length);

        for (int i = 0; i < GameState.panels.size(); i++) {
            Graphics g2 = GameState.panels.get(i).getGraphics();
            g2.setColor(Constants.ANOTHER_STRING_COLOR);
            g2.fillPolygon(getRelativeXPoints(GameState.panels.get(i)), getRelativeYPoints(GameState.panels.get(i)), xPoints.length);

        }
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
}
