package model.objectsModel.miniBoss;

import controller.logic.GameState;
import controller.util.Constants;
import model.Paintable.Paintable;
import model.collision.Collidable;
import view.gameGUI.GameFrame;

import javax.swing.*;
import java.awt.*;
import java.util.TimerTask;

public class BlackOrb implements Collidable, Paintable {
    private double x;
    private double y;
    private double[] xPoints = new double[5];
    private double[] yPoints = new double[5];
    private final double size = 100;
    private boolean played;
    private final OrbPanel[] panel = new OrbPanel[5];
    private Image image;
    private final java.util.Timer timer = new java.util.Timer();


    public BlackOrb(double x, double y) {
        this.x = x;
        this.y = y;
        xPoints[0] = x;
        yPoints[0] = y;

        xPoints[1] = x + 300;
        yPoints[1] = y + 300;

        xPoints[2] = x + 150;
        yPoints[2] = y + 600;

        xPoints[3] = x - 150;
        yPoints[3] = y + 600;

        xPoints[4] = x - 300;
        yPoints[4] = y + 300;
        timer.schedule(new TimerTask() {
            int count = 0;

            @Override
            public void run() {
                panel[count] = new OrbPanel((int) xPoints[count], (int) yPoints[count]);
                GameFrame.getInstance().add(panel[count]);
                count++;
                if (count == 5) {
                    timer.cancel();
                }
            }
        }, 1000, 2000);
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
    public void selfPaint(Graphics g) {

    }

    class OrbPanel extends JPanel {
        public OrbPanel(int x, int y) {
            setBounds(x, y, (int) size, (int) size);
            setBackground(Constants.ANOTHER_STRING_COLOR);
            GameState.panels.add(this);
            GameFrame.getInstance().add(this);
        }

    }


}
