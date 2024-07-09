package controller;

import javax.swing.*;

public class InputController extends JPanel {
    private static InputController instance;

    public static InputController getInstance() {
        if (instance == null) instance = new InputController();
        return instance;
    }

    public InputController() {
        setFocusable(true);
        requestFocus();
        KeyController.initiateKeyCodes();
        addMouseListener(new MouseController());
        addKeyListener(new KeyController());
    }
}
