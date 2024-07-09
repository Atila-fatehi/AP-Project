package org.example;

import controller.audio.players.AudioPlayer;
import view.frames.MainMenu;
import view.gameGUI.GameFrame;

import javax.swing.*;
public class Application implements Runnable{
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Application());
    }

    @Override
    public void run() {
        new MainMenu();
//        new GameFrame();
    }

}