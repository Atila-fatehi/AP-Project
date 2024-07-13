package view.gameGUI;

import controller.InputController;
import controller.KeyController;
import controller.MouseController;
import controller.util.Constants;

import javax.swing.*;

public class GameFrame extends JFrame {

    private static GameFrame instance;

    public static void makeInstance(){
        instance = new GameFrame();
    }

    public static GameFrame getInstance() {
        if (instance == null) instance = new GameFrame();
        return instance;
    }
    public GameFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        setLocationRelativeTo(null);
        setLayout(null);
        setUndecorated(true);
        setBackground(Constants.TRANSPARENT);
        setResizable(false);
        setVisible(true);
    }

    public void addPanel(){
        GamePanel.makeInstance();
        GamePanel.getInstance().setBackground(Constants.DARK_BLUE);
        GamePanel.getInstance().setBounds(Constants.INITIAL_PANEL_X, Constants.INITIAL_PANEL_Y, Constants.INITIAL_PANEL_WIDTH,Constants.INITIAL_PANEL_HEIGHT);
        add(GamePanel.getInstance());
        add(InputController.getInstance());
    }
}
