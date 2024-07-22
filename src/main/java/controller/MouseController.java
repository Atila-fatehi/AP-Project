package controller;

import controller.logic.GameManager;
import model.objectsModel.epsilon.Epsilon;
import view.gameGUI.GamePanel;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.TimerTask;

public class MouseController implements MouseListener {

    private JPanel panel;
//    private java.util.Timer timer;

    public MouseController(JPanel panel) {
        this.panel = panel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        GameManager.getInstance().mouseClicked(e.getX() + panel.getX(), e.getY() + panel.getY());
    }

    @Override
    public void mousePressed(MouseEvent e) {
//        timer = new java.util.Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                GameManager.getInstance().mouseClicked(e.getX() + panel.getX(), e.getY() + panel.getY());
//            }
//        } , 0 , 500);
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
