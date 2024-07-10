package view.gameGUI;

import controller.util.Constants;
import model.logic.GameManager;
import controller.KeyController;
import controller.MouseController;
import model.logic.GameState;
import model.objectsModel.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GamePanel extends JPanel {
    private static GamePanel instance;

    public static GamePanel getInstance() {
        if (instance == null) {
            instance = new GamePanel();
        }
        return instance;
    }

    private int locationX = 600;
    private int locationY = 200;
    private int panelWidth = 700;
    private int panelHeight = 700;
    private static final int shrinkageRate = 2;
    private int shrinkageCounter = 0;
    private ArrayList<Bullet> bullets = new ArrayList<>();
    private ArrayList<Trigorath> trigoraths = new ArrayList<>();
    private ArrayList<Squarantine> squarantines = new ArrayList<>();
    private ArrayList<Collectable> collectables = new ArrayList<>();
    private int elapsedTime;

    public GamePanel() {
        setFocusable(true);
        setLayout(null);
        KeyController.initiateKeyCodes();
        addMouseListener(new MouseController());
        addKeyListener(new KeyController());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //draw Enemies
        g.setFont(Constants.BOLD_15);
        for (Trigorath trigorath : trigoraths) {
            g.setColor(Constants.TRI_YELLOW);
            g.fillPolygon(trigorath.getXPoints(), trigorath.getYPoints(), 3);
            g.setColor(Color.BLACK);
            g.drawString(String.valueOf(trigorath.getHP()), (int) trigorath.getPosXHP(), (int) trigorath.getPosYHP());
        }
        for (Squarantine squarantine : squarantines) {
            g.setColor(Constants.SQUA_GREEN);
            g.fillPolygon(squarantine.getXPoints(), squarantine.getYPoints(), 4);
            g.setColor(Color.BLACK);
            g.drawString(String.valueOf(squarantine.getHP()), (int) squarantine.getPosXHP(), (int) squarantine.getPosYHP());
        }
        //draw collectable
        for (Collectable collectable : collectables) {
            g.setColor(collectable.getColor());
            g.fillOval((int) collectable.getX(), (int) collectable.getY(), (int) collectable.getRadius() * 2, (int) collectable.getRadius() * 2);
        }
        //draw epsilon
        Epsilon epsilon = Epsilon.getInstance();
        g.setColor(Constants.EPSILON_COLOR);
        g.fillOval((int) (epsilon.getX() - epsilon.getRadius()), (int) (epsilon.getY() - epsilon.getRadius()), (int) epsilon.getRadius() * 2, (int) epsilon.getRadius() * 2);
        if (epsilon.hasVertex()) {
            g.drawLine((int) (epsilon.getX() - epsilon.getRadius()), (int) epsilon.getY(), (int) epsilon.getVertexX(), (int) epsilon.getVertexY());
            g.drawLine((int) (epsilon.getX() + epsilon.getRadius()), (int) epsilon.getY(), (int) epsilon.getVertexX(), (int) epsilon.getVertexY());
            if (epsilon.getVertexesNum() >= 2) {
                g.drawLine((int) (epsilon.getX() - epsilon.getRadius()), (int) epsilon.getY(), (int) epsilon.getVertexX(), (int) (epsilon.getVertexY() + 2 * epsilon.getRadius() + 14));
                g.drawLine((int) (epsilon.getX() + epsilon.getRadius()), (int) epsilon.getY(), (int) epsilon.getVertexX(), (int) (epsilon.getVertexY() + 2 * epsilon.getRadius() + 14));
            }
            if (epsilon.getVertexesNum() >= 3) {
                g.drawLine((int) epsilon.getX(), (int) (epsilon.getY() - epsilon.getRadius()), (int) (epsilon.getX() + epsilon.getRadius() + 7), (int) epsilon.getY());
                g.drawLine((int) epsilon.getX(), (int) (epsilon.getY() + epsilon.getRadius()), (int) (epsilon.getX() + epsilon.getRadius() + 7), (int) epsilon.getY());
            }
            if (epsilon.getVertexesNum() >= 4) {
                g.drawLine((int) epsilon.getX(), (int) (epsilon.getY() - epsilon.getRadius()), (int) (epsilon.getX() - epsilon.getRadius() - 7), (int) epsilon.getY());
                g.drawLine((int) epsilon.getX(), (int) (epsilon.getY() + epsilon.getRadius()), (int) (epsilon.getX() - epsilon.getRadius() - 7), (int) epsilon.getY());
            }
        }
        g.setColor(Constants.DARK_BLUE);
        g.fillOval((int) (epsilon.getX() - epsilon.getRadius()) + 4, (int) (epsilon.getY() - epsilon.getRadius()) + 4, (int) epsilon.getRadius() * 2 - 8, (int) epsilon.getRadius() * 2 - 8);
        //draw bullets
        g.setColor(Constants.EPSILON_COLOR);
        for (int i = 0; i < bullets.size(); i++) {
            g.fillOval((int) (bullets.get(i).getX() - bullets.get(i).getRadius()), (int) (bullets.get(i).getY() - bullets.get(i).getRadius()), (int) bullets.get(i).getRadius() * 2, (int) bullets.get(i).getRadius() * 2);
        }
        //draw Strings
        g.setColor(Constants.STRING_COLOR);
        g.drawString("HP : " + epsilon.getHP() +
                  "       XP : " + epsilon.getXP() +
                  "       WAVE : " + GameState.wave +
                  "       ELAPSED TIME : " + elapsedTime, 10, 20);
        g.dispose();
    }

    public void shrink() {
        if (shrinkageCounter == shrinkageRate) {
            setLocation(locationX, locationY);
            setSize(panelWidth, panelHeight);
            if (panelWidth >= 500) {
                panelWidth -= 2;
                locationX += 1;
                Epsilon.getInstance().setX(Epsilon.getInstance().getX() - 1);
            }
            if (panelHeight >= 500) {
                panelHeight -= 2;
                locationY += 1;
                Epsilon.getInstance().setY(Epsilon.getInstance().getY() - 1);
            }
            shrinkageCounter = 0;
        }
        shrinkageCounter++;
    }

    public void shrinkToZero() {
        setLocation(locationX, locationY);
        setSize(panelWidth, panelHeight);
        panelWidth -= 6;
        locationX += 3;
        panelHeight -= 6;
        locationY += 3;
    }

    //GETTERS AND SETTERS

    public int getPanelWidth() {
        return panelWidth;
    }

    public void setPanelWidth(int panelWidth) {
        this.panelWidth = panelWidth;
    }

    public int getPanelHeight() {
        return panelHeight;
    }

    public void setPanelHeight(int panelHeight) {
        this.panelHeight = panelHeight;
    }
    public void setBullets(ArrayList<Bullet> bullets) {
        this.bullets = bullets;
    }

    public int getLocationX() {
        return locationX;
    }

    public void setLocationX(int locationX) {
        this.locationX = locationX;
    }

    public int getLocationY() {
        return locationY;
    }

    public void setLocationY(int locationY) {
        this.locationY = locationY;
    }

    public void setTrigoraths(ArrayList<Trigorath> trigoraths) {
        this.trigoraths = trigoraths;
    }

    public void setSquarantines(ArrayList<Squarantine> squarantines) {
        this.squarantines = squarantines;
    }

    public void setCollectables(ArrayList<Collectable> collectables) {
        this.collectables = collectables;
    }

    public void setElapsedTime(int elapsedTime) {
        this.elapsedTime = elapsedTime;
    }
}
