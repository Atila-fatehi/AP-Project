package org.example;

import view.frames.MainMenu;

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