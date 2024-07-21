package view.gameGUI;

import controller.InputController;
import controller.logic.GameState;
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


    public void addOtherPanel() {

//        Panel22 panel22 = new Panel22();
//        panel22.setBackground(Constants.DARK_BLUE);
//        panel22.setBounds(50, 50, 500,500);
//        add(panel22);

//        Panel22 panel23 = new Panel22();
//        panel23.setBackground(Constants.DARK_BLUE);
//        panel23.setBounds(1350, 50, 500,500);
//        add(panel23);
//        Panel22 panel24 = new Panel22();
//        panel24.setBackground(Constants.DARK_BLUE);
//        panel24.setBounds(0, 700, 500,500);
//        add(panel24);
//        Panel22 panel25 = new Panel22();
//        panel25.setBackground(Constants.DARK_BLUE);
//        panel25.setBounds(1000, 700, 500,500);
//        add(panel25);
    }
}
