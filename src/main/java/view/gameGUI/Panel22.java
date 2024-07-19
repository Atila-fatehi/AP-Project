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

public class Panel22 extends JPanel {
    private static Panel22 instance;

    public static void makeInstance(){
        instance = new Panel22();
    }

    public static Panel22 getInstance() {
        if (instance == null) instance = new Panel22();
        return instance;
    }

    public Panel22() {
        setFocusable(true);
        setLayout(null);
        KeyController.initiateKeyCodes();
        addMouseListener(new MouseController(this));
        addKeyListener(new KeyController());
        GameState.panels.add(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setFont(Constants.BOLD_15);
        ArrayList<Paintable> paintables = GameState.getPaintables();
        for (Paintable paintable : paintables) {
            paintable.selfPaint(g , this);
        }

        g.setColor(Constants.ANOTHER_STRING_COLOR);
        //TODO
        g.drawString("SECOND", 10, 45);
        g.dispose();
    }
}
