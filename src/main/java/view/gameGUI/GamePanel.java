package view.gameGUI;

import controller.util.Constants;
import controller.KeyController;
import controller.MouseController;
import controller.logic.GameState;
import model.Paintable.Paintable;
import model.objectsModel.epsilon.Epsilon;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GamePanel extends JPanel {
    private static GamePanel instance;

    public static void makeInstance(){
        instance = new GamePanel();
    }

    public static GamePanel getInstance() {
        if (instance == null) instance = new GamePanel();
        return instance;
    }

    private int locationX = Constants.INITIAL_PANEL_X;
    private int locationY = Constants.INITIAL_PANEL_Y;
    private int panelWidth = Constants.INITIAL_PANEL_WIDTH;
    private int panelHeight = Constants.INITIAL_PANEL_HEIGHT;

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
        //get components in panel
        g.setFont(Constants.BOLD_15);
        ArrayList<Paintable> paintables = GameState.getPaintables();
        for (Paintable paintable : paintables) {
            paintable.selfPaint(g);
        }

        g.setColor(Constants.STRING_COLOR);
        g.drawString("HP : " + Epsilon.getInstance().getHP() +
                  "       XP : " + Epsilon.getInstance().getXP() +
                  "       WAVE : " + GameState.wave +
                  "       ELAPSED TIME : " + GameState.elapsedTime, 10, 20);
        g.setColor(Constants.ANOTHER_STRING_COLOR);
        //TODO
        g.drawString("Active Skill : " + GameState.getAbility(), 10, 45);
        g.dispose();
    }



    private int shrinkageCounter = 0;

    public void shrink() {
        int shrinkageRate = 2;
        if (shrinkageCounter == shrinkageRate) {
            setLocation(locationX, locationY);
            setSize(panelWidth, panelHeight);
            if (panelWidth >= 500) {
                panelWidth -= 2;
                locationX += 1;
//                Epsilon.getInstance().setX(Epsilon.getInstance().getX() - 1);
            }
            if (panelHeight >= 500) {
                panelHeight -= 2;
                locationY += 1;
//                Epsilon.getInstance().setY(Epsilon.getInstance().getY() - 1);
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
}
