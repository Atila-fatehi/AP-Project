package controller;

import controller.audio.players.AudioPlayer;
import controller.audio.players.GameMusicPlayer;
import view.frames.MainMenu;
import view.gameGUI.GameFrame;
import view.gameGUI.GamePanel;

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
            GameManager.getInstance().getEpsilon().setAccU(true);
            GameManager.getInstance().getEpsilon().setDecU(false);
        }
        if (keyCode == s) {
            GameManager.getInstance().getEpsilon().setAccD(true);
            GameManager.getInstance().getEpsilon().setDecD(false);
        }
        if (keyCode == a) {
            GameManager.getInstance().getEpsilon().setAccL(true);
            GameManager.getInstance().getEpsilon().setDecL(false);
        }
        if (keyCode == d) {
            GameManager.getInstance().getEpsilon().setAccR(true);
            GameManager.getInstance().getEpsilon().setDecR(false);
        }
        if (keyCode == shop) {
            GameManager.getInstance().setPaused(! GameManager.getInstance().isPaused());
            AudioPlayer.play(AudioPlayer.PAUSE);
            GamePanel.getInstance().openShop();
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
            GameManager.getInstance().getEpsilon().setAccU(false);
            GameManager.getInstance().getEpsilon().setDecU(true);
        }
        if (keyCode == s) {
            GameManager.getInstance().getEpsilon().setAccD(false);
            GameManager.getInstance().getEpsilon().setDecD(true);
        }
        if (keyCode == a) {
            GameManager.getInstance().getEpsilon().setAccL(false);
            GameManager.getInstance().getEpsilon().setDecL(true);
        }
        if (keyCode == d) {
            GameManager.getInstance().getEpsilon().setAccR(false);
            GameManager.getInstance().getEpsilon().setDecR(true);
        }
    }

}
