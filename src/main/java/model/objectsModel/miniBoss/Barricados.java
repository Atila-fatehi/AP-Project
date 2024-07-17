package model.objectsModel.miniBoss;

import controller.logic.GameState;
import controller.util.Constants;
import model.Paintable.Paintable;
import model.collision.Collidable;
import view.gameGUI.GameFrame;
import view.gameGUI.GamePanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.util.TimerTask;

public class Barricados implements Paintable, Collidable {

    private double x;
    private double y;
    private final double size = 200;
    private boolean played;
    private BarriPanel panel;
    private Image image;
    private java.util.Timer timer = new java.util.Timer();

    public Barricados(double x, double y) {
        this.x = x;
        this.y = y;

        try {
            Image yourImage = (Image) ImageIO.read(Constants.BARRI_PIC);
            image = yourImage.getScaledInstance((int) size, (int) size, Image.SCALE_DEFAULT);

        } catch (Exception e) {
            System.out.println("exception in barri paint");
        }
        panel = new BarriPanel();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                selfDestruct();
                timer.cancel();
            }
        }, 1000 * 60 * 2, 1111);

    }

    void selfDestruct(){
        GameState.barricados.remove(this);
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
            g.drawImage(image, (int) x - locX, (int) y - locY, this);
            panel.setLocation((int) x, (int) y);

            g.dispose();
        }
    }

    @Override
    public void selfPaint(Graphics g) {
        int locX = GamePanel.getInstance().getLocationX();
        int locY = GamePanel.getInstance().getLocationY();
        g.drawImage(image, (int) x - locX, (int) y - locY, GamePanel.getInstance());

        locX = panel.getX();
        locY = panel.getY();
        Graphics g2 = panel.getGraphics();
        g2.drawImage(image, (int) x - locX, (int) y - locY, panel);
        panel.setLocation((int) x, (int) y);

        for (int i = 0; i < GameState.panels.size(); i++) {
            locX = GameState.panels.get(i).getX();
            locY = GameState.panels.get(i).getY();
            g2 = GameState.panels.get(i).getGraphics();
            g2.drawImage(image, (int) x - locX, (int) y - locY, GameState.panels.get(i));
        }
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
        return new int[0];
    }

    @Override
    public int[] getRelativeYPoints(JPanel panel) {
        return new int[0];
    }
}
