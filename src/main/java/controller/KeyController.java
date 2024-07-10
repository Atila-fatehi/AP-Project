package controller;

import controller.audio.players.AudioPlayer;
import controller.audio.players.GameMusicPlayer;
import model.logic.GameManager;
import model.objectsModel.Epsilon;
import view.frames.MainMenu;
import view.gameGUI.GameFrame;
import view.gameGUI.ShopFrame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyController implements KeyListener {

    public static int w;
    public static int a;
    public static int s;
    public static int d;
    public static int shop;
    public static int ability;

    public static void initiateKeyCodes(){
        int[] codes = FileController.readKeyCodes();
        assert codes != null;
        w = codes[0];
        a = codes[1];
        s = codes[2];
        d = codes[3];
        shop = codes[4];
        ability = codes[5];
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode ==  w) {
            Epsilon.getInstance().setAccU(true);
            Epsilon.getInstance().setDecU(false);
        }
        if (keyCode == s) {
            Epsilon.getInstance().setAccD(true);
            Epsilon.getInstance().setDecD(false);
        }
        if (keyCode == a) {
            Epsilon.getInstance().setAccL(true);
            Epsilon.getInstance().setDecL(false);
        }
        if (keyCode == d) {
            Epsilon.getInstance().setAccR(true);
            Epsilon.getInstance().setDecR(false);
        }
        if (keyCode == shop) {
            GameManager.getInstance().setPaused(! GameManager.getInstance().isPaused());
            AudioPlayer.play(AudioPlayer.PAUSE);
            new ShopFrame();
        }
        if (keyCode == ability) {
            GameManager.getInstance().activateAbility();
        }
        if (keyCode == KeyEvent.VK_ESCAPE) {
            GameMusicPlayer.getInstance().getClip().stop();
            GameMusicPlayer.getInstance().setPlaying(false);
            GameFrame.getInstance().dispose();
            GameManager.getInstance().setPaused(true);
            GameManager.getInstance().getModelTimer().cancel();
            GameManager.getInstance().getViewTimer().cancel();
            new MainMenu();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == w) {
            Epsilon.getInstance().setAccU(false);
            Epsilon.getInstance().setDecU(true);
        }
        if (keyCode == s) {
            Epsilon.getInstance().setAccD(false);
            Epsilon.getInstance().setDecD(true);
        }
        if (keyCode == a) {
            Epsilon.getInstance().setAccL(false);
            Epsilon.getInstance().setDecL(true);
        }
        if (keyCode == d) {
            Epsilon.getInstance().setAccR(false);
            Epsilon.getInstance().setDecR(true);
        }
    }

}
