package controller;

import controller.audio.players.GameMusicPlayer;
import view.frames.MainMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.nio.file.Paths;

public class InputController extends JPanel {
    public InputController() {
//        addMouseListener(new MouseListener() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                gameManager.mouseClicked(e.getX(), e.getY());
//            }
//
//            @Override
//            public void mousePressed(MouseEvent e) {
//
//            }
//
//            @Override
//            public void mouseReleased(MouseEvent e) {
//
//            }
//
//            @Override
//            public void mouseEntered(MouseEvent e) {
//
//            }
//
//            @Override
//            public void mouseExited(MouseEvent e) {
//
//            }
//        });
//        addKeyListener(new KeyListener() {
//            @Override
//            public void keyTyped(KeyEvent e) {
//
//            }
//
//            @Override
//            public void keyPressed(KeyEvent e) {
//                int keyCode = e.getKeyCode();
//                if (keyCode == w) {
//                    epsilon.setAccU(true);
//                    epsilon.setDecU(false);
//                }
//                if (keyCode == s) {
//                    epsilon.setAccD(true);
//                    epsilon.setDecD(false);
//                }
//                if (keyCode == a) {
//                    epsilon.setAccL(true);
//                    epsilon.setDecL(false);
//                }
//                if (keyCode == d) {
//                    epsilon.setAccR(true);
//                    epsilon.setDecR(false);
//                }
//                if (keyCode == shop) {
//                    gameManager.setPaused(!gameManager.isPaused());
//                    audioPlayer.play(new File(Paths.get("").toAbsolutePath() + "\\src\\main\\java\\audio\\pause.wav"));
//                    openShop();
//                }
//                if (keyCode == ability) {
//                    gameManager.activateAbility();
//                }
//                if (keyCode == KeyEvent.VK_ESCAPE) {
//                    GameMusicPlayer.getInstance().getClip().stop();
//                    GameMusicPlayer.getInstance().setPlaying(false);
//                    //TODO
////                    GameFrame.getInstance().dispose();
//                    frame.dispose();
//                    gameManager.setPaused(true);
//                    gameManager.getModelTimer().cancel();
//                    gameManager.getViewTimer().cancel();
//                    new MainMenu();
//                }
//            }
//
//            @Override
//            public void keyReleased(KeyEvent e) {
//                int keyCode = e.getKeyCode();
//                if (keyCode == w) {
//                    epsilon.setAccU(false);
//                    epsilon.setDecU(true);
//                }
//                if (keyCode == s) {
//                    epsilon.setAccD(false);
//                    epsilon.setDecD(true);
//                }
//                if (keyCode == a) {
//                    epsilon.setAccL(false);
//                    epsilon.setDecL(true);
//                }
//                if (keyCode == d) {
//                    epsilon.setAccR(false);
//                    epsilon.setDecR(true);
//                }
//            }
//        });
    }
}
