package org.example;

import view.frames.MainMenu;

import javax.swing.*;
public class Main implements Runnable{
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Main());
    }

    @Override
    public void run() {
        new MainMenu();
//        new GameFrame();
    }

}