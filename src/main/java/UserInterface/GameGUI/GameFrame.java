package UserInterface.GameGUI;
import Controller.GameManager;
import Model.Epsilon;
import Model.Trigorath;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameFrame extends JFrame {
    private static final int HEIGHT = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    private static final int WIDTH = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    private static final int locationX = 600;
    private static final int locationY = 200;
    private static final int initialPanelWidth = 700;
    private static final int initialPanelHeight = 700;
    public GameFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setLayout(null);
        setUndecorated(true);
        setBackground(new Color(0, 0, 0, 0));
        setResizable(false);
        setVisible(true);
        //Adding Panel
        GamePanel gamePanel = new GamePanel(this);
        gamePanel.setBackground(new Color(0x011022));
        gamePanel.setBounds(locationX, locationY, initialPanelWidth, initialPanelHeight);
        add(gamePanel);
    }
}
