package view.gameGUI;

import controller.KeyController;
import controller.MouseController;
import controller.logic.GameState;
import controller.util.Constants;
import model.Paintable.Paintable;
import model.objectsModel.epsilon.Epsilon;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GamePanel2 extends JPanel {
    private static GamePanel2 instance;

    public static void makeInstance(){
        instance = new GamePanel2();
    }

    public static GamePanel2 getInstance() {
        if (instance == null) instance = new GamePanel2();
        return instance;
    }

    private int locationX = 0;
    private int locationY = 0;
    private int panelWidth = Constants.INITIAL_PANEL_WIDTH;
    private int panelHeight = Constants.INITIAL_PANEL_HEIGHT;

    public GamePanel2() {
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
        g.drawString("bro thinks he is a frame", 10, 20);
        g.dispose();
    }

}
