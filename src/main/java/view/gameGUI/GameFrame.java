package view.gameGUI;

import controller.util.Constants;

import javax.swing.*;

public class GameFrame extends JFrame {

//    private static GameFrame instance;
//
//    public static GameFrame getInstance() {
//        if (instance == null) {
//            instance = new GameFrame();
//        }
//        System.out.println("br");
//        return instance;
//    }
//
//
//    public static void generateNewFrame(){
//        System.out.println("grrr");
//        instance = new GameFrame();
//    }

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

        GamePanel gamePanel = new GamePanel(this);
        gamePanel.setBackground(Constants.DARK_BLUE);
        gamePanel.setBounds(locationX, locationY, initialPanelWidth, initialPanelHeight);
        add(gamePanel);
    }
}
