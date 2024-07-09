package view.gameGUI;

import controller.GameManager;
import controller.InputController;
import controller.util.Constants;

import javax.swing.*;

public class GameFrame extends JFrame {

    private static GameFrame instance;

    public static void makeInstance(){
        instance = new GameFrame();
    }

    public static GameFrame getInstance() {
        if (instance == null) {
            instance = new GameFrame();
        }
        return instance;
    }

    private static final int locationX = 600;
    private static final int locationY = 200;
    private static final int initialPanelWidth = 700;
    private static final int initialPanelHeight = 700;

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
        GamePanel.getInstance().setBackground(Constants.DARK_BLUE);
        GamePanel.getInstance().setBounds(locationX, locationY, initialPanelWidth, initialPanelHeight);
        GameManager.getInstance();
        add(GamePanel.getInstance());
        add(InputController.getInstance());
        GamePanel.getInstance().startTimer();
    }
}
