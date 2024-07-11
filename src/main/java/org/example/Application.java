package org.example;

import controller.FileController;
import controller.audio.players.AudioPlayer;
import controller.audio.players.MenuMusicPlayer;
import view.frames.MainMenu;
import view.gameGUI.GameFrame;

import javax.swing.*;
public class Application implements Runnable{
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Application());
    }

    @Override
    public void run() {
        MainMenu.getInstance();
        FileController.createFiles();
        MenuMusicPlayer.getInstance().start();

//        new GameFrame();
    }

}