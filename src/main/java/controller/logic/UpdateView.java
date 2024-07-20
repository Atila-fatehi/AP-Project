package controller.logic;

import view.gameGUI.GamePanel;

import javax.swing.*;
import java.util.ArrayList;

public abstract class UpdateView {
    public static boolean pastTen;
    public static void update() {
        if (!GameManager.getInstance().isGameWon()) {
            if (pastTen) {
                GamePanel.getInstance().shrink();
            }
        }
        GamePanel.getInstance().repaint();
        ArrayList<JPanel> panels = GameState.panels;
        for (JPanel panel : panels) {
            panel.repaint();
        }
    }
}
