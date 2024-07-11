package controller.logic;

import controller.audio.players.AudioPlayer;
import controller.util.Constants;
import model.objectsModel.Bullet;
import model.objectsModel.Epsilon;
import model.objectsModel.Squarantine;
import model.objectsModel.Trigorath;
import view.gameGUI.GamePanel;

import java.util.Random;
import java.util.TimerTask;

public abstract class Generator {

    public static boolean inWait ;

    public static void generateSimpleWave() {
        if (GameState.trigoraths.isEmpty() && GameState.squarantines.isEmpty() && !inWait) {
            GameState.wave++;
            if (GameState.wave == 4) {
                GameManager.getInstance().setGameWon(true);
                GameManager.getInstance().gameWon();
            } else {
                AudioPlayer.play(AudioPlayer.WAVE);
                java.util.Timer timer = new java.util.Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        AudioPlayer.play(AudioPlayer.AWOOGA);
                        Random random = new Random();
                        makeNewSquarantine();
                        makeNewTrigorath();
                        for (int i = 0; i < GameState.wave * GameState.difficulty; i++) {
                            if (random.nextBoolean()) {
                                makeNewSquarantine();
                            }
                        }
                        for (int i = 0; i < GameState.wave * GameState.difficulty; i++) {
                            if (random.nextBoolean()) {
                                makeNewTrigorath();
                            }
                        }
                        inWait = false;
                        timer.cancel();
                    }
                }, 3000, 1111);
                inWait = true;
            }
        }
    }

    public static void makeNewTrigorath() {
        Random random = new Random();
        int initialPositionX = random.nextInt(GamePanel.getInstance().getPanelWidth());
        int initialPositionY = random.nextInt(GamePanel.getInstance().getPanelHeight());
        if (random.nextBoolean()) {
            initialPositionX += GamePanel.getInstance().getPanelWidth();
        } else {
            initialPositionX -= GamePanel.getInstance().getPanelWidth();
        }
        if (random.nextBoolean()) {
            initialPositionY += GamePanel.getInstance().getPanelHeight();
        } else {
            initialPositionY -= GamePanel.getInstance().getPanelHeight();
        }
        GameState.trigoraths.add(new Trigorath(initialPositionX, initialPositionY,
                initialPositionX + Constants.TRIGORATH_SIZE, initialPositionY,
                initialPositionX + (double) Constants.TRIGORATH_SIZE / 2, initialPositionY - 25));
    }

    public static void makeNewSquarantine() {
        Random random = new Random();
        int initialPositionX = random.nextInt(GamePanel.getInstance().getPanelWidth());
        int initialPositionY = random.nextInt(GamePanel.getInstance().getPanelHeight());
        if (random.nextBoolean()) {
            initialPositionX += GamePanel.getInstance().getPanelWidth();
        } else {
            initialPositionX -= GamePanel.getInstance().getPanelWidth();
        }
        if (random.nextBoolean()) {
            initialPositionY += GamePanel.getInstance().getPanelHeight();
        } else {
            initialPositionY -= GamePanel.getInstance().getPanelHeight();
        }
        GameState.squarantines.add(new Squarantine(initialPositionX, initialPositionY,
                initialPositionX + Constants.SQUARANTINE_SIZE, initialPositionY,
                initialPositionX + Constants.SQUARANTINE_SIZE, initialPositionY + Constants.SQUARANTINE_SIZE,
                initialPositionX, initialPositionY + Constants.SQUARANTINE_SIZE));

    }

    public static void makeNewBullet(int x, int y) {
        Bullet bullet = new Bullet(Epsilon.getInstance().getX(), Epsilon.getInstance().getY());
        double angle = Math.atan2(y - Epsilon.getInstance().getY(), x - Epsilon.getInstance().getX());
        bullet.setVx(bullet.getConstantVelocity() * Math.cos(angle));
        bullet.setVy(bullet.getConstantVelocity() * Math.sin(angle));
        GameState.bullets.add(bullet);
    }
}
