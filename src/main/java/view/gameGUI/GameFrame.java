package view.gameGUI;

import Controller.Constants;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    private static final int HEIGHT = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    private static final int WIDTH = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
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
        //Adding Panel
        GamePanel gamePanel = new GamePanel(this);
        gamePanel.setBackground(new Color(0x011022));
        gamePanel.setBounds(locationX, locationY, initialPanelWidth, initialPanelHeight);
        add(gamePanel);
    }
}
