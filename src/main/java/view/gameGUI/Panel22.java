package view.gameGUI;

import controller.KeyController;
import controller.MouseController;
import controller.logic.GameState;

import javax.swing.*;

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
}
