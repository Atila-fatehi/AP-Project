package model.objectsModel.miniBoss;

import controller.MouseController;
import controller.logic.GameState;
import controller.util.Constants;
import jdk.jshell.spi.SPIResolutionException;
import model.Paintable.Paintable;
import model.collision.Collidable;
import view.gameGUI.GameFrame;
import view.gameGUI.GamePanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.util.TimerTask;

public class BlackOrb implements Collidable, Paintable {
    private double HP = 30;
    private double x;
    private double y;
    private final double size = 100;
    private boolean damageable;
    private OrbPanel panel;
    private Image image;
    private final java.util.Timer timer = new java.util.Timer();
    private int code ;

    public BlackOrb(double x, double y, int code) {
        this.x = x;
        this.y = y;
        this.code = code;

        panel = new OrbPanel();

        try {
            Image yourImage = (Image) ImageIO.read(Constants.ORB_PIC);
            image = yourImage.getScaledInstance((int) size, (int) size, Image.SCALE_DEFAULT);
        } catch (Exception e) {
            System.out.println("exception in wyrm paint");
        }
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
        int locX = GamePanel.getInstance().getX();
        int locY = GamePanel.getInstance().getY();
        g.drawImage(image, (int) ((int) x - locX), (int) ((int) y - locY), GamePanel.getInstance());
//        g.setColor(Color.ORANGE);
//        g.fillOval((int)x +10 - locX, (int)y+10 - locY, (int)size - 20, (int)size - 20);


        locX = panel.getX();
        locY = panel.getY();
        Graphics g2 = panel.getGraphics();
        g2.drawImage(image, (int) ((int) x - locX), (int) ((int) y - locY), panel);

        for (int i = 0; i < GameState.panels.size(); i++) {
            locX = GameState.panels.get(i).getX();
            locY = GameState.panels.get(i).getY();
            g2 = GameState.panels.get(i).getGraphics();
            g2.drawImage(image, (int) ((int) x - locX), (int) ((int) y - locY), GameState.panels.get(i));
        }
    }

    public void checkCollision() {

    }

    public void selfDestruct() {
        GameState.panels.remove(panel);
        GameFrame.getInstance().remove(panel);
    }

    class OrbPanel extends JPanel {
        public OrbPanel() {
            setBounds((int) x, (int) y, (int) size, (int) size);
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
            g.drawImage(image, (int) x - locX, (int) y - locY, this);

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
