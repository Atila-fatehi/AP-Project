package controller.logic;

import controller.audio.players.AudioPlayer;
import controller.util.Constants;
import model.objectsModel.enemy.Omenoct;
import model.objectsModel.epsilon.Bullet;
import model.objectsModel.epsilon.Epsilon;
import model.objectsModel.enemy.Squarantine;
import model.objectsModel.enemy.Trigorath;
import view.gameGUI.GamePanel;

import java.util.Random;
import java.util.TimerTask;

public abstract class Generator {

    public static boolean inWait;

    public static void generateSimpleWave() {
        if (GameState.trigoraths.isEmpty() && GameState.squarantines.isEmpty() && !inWait) {
            //GameState.wave++;
//            if (GameState.wave == 4) {
//                GameManager.getInstance().setGameWon(true);
//                GameManager.getInstance().gameWon();
//            } else {
//                AudioPlayer.play(AudioPlayer.WAVE);
//                java.util.Timer timer = new java.util.Timer();
//                timer.schedule(new TimerTask() {
//                    @Override
//                    public void run() {
//                        AudioPlayer.play(AudioPlayer.AWOOGA);
//                        Random random = new Random();
                        makeNewOmenoct();
//                        makeNewSquarantine();
//                        makeNewTrigorath();
//                        for (int i = 0; i < GameState.wave * GameState.difficulty; i++) {
//                            if (random.nextBoolean()) {
//                                makeNewSquarantine();
//                            }
//                        }
//                        for (int i = 0; i < GameState.wave * GameState.difficulty; i++) {
//                            if (random.nextBoolean()) {
//                                makeNewTrigorath();
//                            }
//                        }
//                        inWait = false;
//                        timer.cancel();
//                    }
//                }, 3000, 1111);
//                inWait = true;
//            }
        }
    }

    public static void makeNewTrigorath() {
        Random random = new Random();
        int initX = random.nextInt(GamePanel.getInstance().getPanelWidth());
        int initY = random.nextInt(GamePanel.getInstance().getPanelHeight());
        if (random.nextBoolean()) {
            initX += GamePanel.getInstance().getPanelWidth();
        } else {
            initX -= GamePanel.getInstance().getPanelWidth();
        }
        if (random.nextBoolean()) {
            initY += GamePanel.getInstance().getPanelHeight();
        } else {
            initY -= GamePanel.getInstance().getPanelHeight();
        }
        int size = Constants.TRIGORATH_SIZE;
        GameState.trigoraths.add(new Trigorath(new double[]{initX, initX + size, initX + (double) size / 2},
                new double[]{initY, initY, initY - 25}));
    }

    public static void makeNewSquarantine() {
        Random random = new Random();
        int initX = random.nextInt(GamePanel.getInstance().getPanelWidth());
        int initY = random.nextInt(GamePanel.getInstance().getPanelHeight());
        if (random.nextBoolean()) {
            initX += GamePanel.getInstance().getPanelWidth();
        } else {
            initX -= GamePanel.getInstance().getPanelWidth();
        }
        if (random.nextBoolean()) {
            initY += GamePanel.getInstance().getPanelHeight();
        } else {
            initY -= GamePanel.getInstance().getPanelHeight();
        }
        int size = Constants.SQUARANTINE_SIZE;
        GameState.squarantines.add(new Squarantine(new double[]{initX, initX + size, initX + size, initX},
                new double[]{initY, initY, initY + size, initY + size}));

    }

    public static void makeNewBullet(int x, int y) {
        Bullet bullet = new Bullet(Epsilon.getInstance().getX(), Epsilon.getInstance().getY() , true, Constants.EPSILON_COLOR);
        bullet.calculateMovingDirection(x, y);
        GameState.bullets.add(bullet);
    }

    public static void makeNewOmenoct() {
        Random random = new Random();
        int initX = random.nextInt(GamePanel.getInstance().getPanelWidth());
        int initY = random.nextInt(GamePanel.getInstance().getPanelHeight());
        if (random.nextBoolean()) {
            initX += GamePanel.getInstance().getPanelWidth();
        } else {
            initX -= GamePanel.getInstance().getPanelWidth();
        }
        if (random.nextBoolean()) {
            initY += GamePanel.getInstance().getPanelHeight();
        } else {
            initY -= GamePanel.getInstance().getPanelHeight();
        }
        int size = Constants.OMENOCT_SIZE;
        GameState.omenocts.add(new Omenoct(new double[]{initX, initX + size, initX + 2 * size, initX + 2 * size, initX + size , initX , initX - size , initX - size},
                new double[]{initY, initY, initY + size, initY + 2 * size, initY + 3 * size , initY + 3 * size , initY + 2 * size , initY + size}));

    }

}
