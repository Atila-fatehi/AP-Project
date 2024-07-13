package controller;

import controller.logic.GameManager;
import view.gameGUI.GamePanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseController implements MouseListener {

    @Override
    public void mouseClicked(MouseEvent e) {
        GameManager.getInstance().mouseClicked(e.getX() + GamePanel.getInstance().getLocationX(), e.getY() + GamePanel.getInstance().getLocationY());
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
