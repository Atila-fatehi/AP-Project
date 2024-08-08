package controller.logic;

import controller.audio.players.AudioPlayer;
import controller.util.Constants;
import model.collision.Collision;
import model.objectsModel.boss.Fist;
import model.objectsModel.boss.Hand;
import model.objectsModel.boss.SecondHand;
import model.objectsModel.boss.Smiley;
import model.objectsModel.enemy.*;
import model.objectsModel.epsilon.Bullet;
import model.objectsModel.epsilon.Epsilon;
import model.objectsModel.miniBoss.Barricados;
import model.objectsModel.miniBoss.BlackOrb;
import model.objectsModel.miniBoss.OrbManager;
import view.gameGUI.GamePanel;

import java.awt.geom.Point2D;
import java.util.Random;
import java.util.TimerTask;

public abstract class EnemyGenerator {

    public static void makeNewTrigorath() {
        int initX = randomiseInitialPosX();
        int initY = randomiseInitialPosY();
        int size = Constants.TRIGORATH_SIZE;
        GameState.trigoraths.add(new Trigorath(new double[]{initX, initX + size, initX + (double) size / 2},
                new double[]{initY, initY, initY - 25}));
    }

    public static void makeNewSquarantine() {
        int initX = randomiseInitialPosX();
        int initY = randomiseInitialPosY();
        int size = Constants.SQUARANTINE_SIZE;
        GameState.squarantines.add(new Squarantine(new double[]{initX, initX + size, initX + size, initX},
                new double[]{initY, initY, initY + size, initY + size}));

    }

    public static void makeNewBullet(int x, int y) {
        Bullet bullet;
        if (GameState.slaughter) {
            bullet = new Bullet(Epsilon.getInstance().getX(), Epsilon.getInstance().getY(), true, Constants.GOLD, 50);
            GameState.slaughter = false;
        } else {
            bullet = new Bullet(Epsilon.getInstance().getX(), Epsilon.getInstance().getY(), true, Constants.EPSILON_COLOR, Epsilon.getInstance().getDamageRate());
        }
        bullet.calculateMovingDirection(x, y);
        GameState.bullets.add(bullet);
        GameState.allBulletCount++;
    }

    public static void makeNewOmenoct() {
        int initX = randomiseInitialPosX();
        int initY = randomiseInitialPosY();
        int size = Constants.OMENOCT_SIZE;
        GameState.omenocts.add(new Omenoct(new double[]{initX, initX + size, initX + 2 * size, initX + 2 * size, initX + size, initX, initX - size, initX - size},
                new double[]{initY, initY, initY + size, initY + 2 * size, initY + 3 * size, initY + 3 * size, initY + 2 * size, initY + size}));

    }

    public static void makeNewArchmire() {
        int initX = randomiseInitialPosX();
        int initY = randomiseInitialPosY();
        GameState.archmires.add(new Archmire(initX, initY));
    }

    public static void makeNewNecropick() {
        int initX = randomiseInitialPosX();
        int initY = randomiseInitialPosY();
        GameState.necropicks.add(new Necropick(initX, initY));
    }

    public static void makeNewWyrm() {
        int initX = randomiseInitialPosX();
        int initY = randomiseInitialPosY();
        GameState.wyrms.add(new Wyrm(initX, initY));
    }

    public static void makeNewBarricados() {
        if (new Random().nextBoolean()) {
            int initX = randomXonScreen();
            int initY = randomYonScreen();
            Barricados barricados = new Barricados(initX, initY);
            while (Collision.checkEpsilonCollision(barricados) != null ||
                    Collision.checkPointCollision(new Point2D.Double(Epsilon.getInstance().getX(), Epsilon.getInstance().getY()), barricados)) {
                barricados.selfDestruct();
                initX = randomXonScreen();
                initY = randomYonScreen();
                barricados = new Barricados(initX, initY);
            }
            barricados = new Barricados(initX, initY);
            barricados.isRigid = false;
            GameState.barricados.add(barricados);
        } else {
            int x = randomXoffScreen();
            int y = randomYoffScreen();
            Barricados barricados = new Barricados(x, y);
            barricados.isRigid = true;
            GameState.barricados.add(barricados);
        }
    }

    public static void makeNewOrb() {
        int initX = randomXonScreen();
        int initY = 100;
        java.util.Timer timer = new java.util.Timer();
        timer.schedule(new TimerTask() {
            int count = 0;

            @Override
            public void run() {
                if (count == 0) {
                    GameState.orbs.add(new BlackOrb(initX, initY, 0));
                } else if (count == 1) {
                    GameState.orbs.add(new BlackOrb(initX + 300, initY + 300, 1));
                } else if (count == 2) {
                    GameState.orbs.add(new BlackOrb(initX + 150, initY + 600, 2));
                } else if (count == 3) {
                    GameState.orbs.add(new BlackOrb(initX - 150, initY + 600, 3));
                } else if (count == 4) {
                    GameState.orbs.add(new BlackOrb(initX - 300, initY + 300, 4));
                }
                count++;
                if (count == 5) {
                    timer.cancel();
                    OrbManager.laser();
                }
            }
        }, 1000, 2000);
    }

    public static void makeSmiley() {
        GameState.smilies.add(new Smiley(900, 50));
    }

    public static void makeHand() {
        GameState.hands.add(new Hand(300, 130));
    }

    public static void makeSecondHand() {
        GameState.secondHands.add(new SecondHand(1350, 130));
    }

    public static void makeFist() {
        GameState.fists.add(new Fist(300, 130 + 250 + 350));
    }

    static int randomiseInitialPosX() {
        Random random = new Random();
        int initX = random.nextInt(GamePanel.getInstance().getPanelWidth());
        if (random.nextBoolean()) {
            initX += GamePanel.getInstance().getPanelWidth();
        } else {
            initX -= GamePanel.getInstance().getPanelWidth();
        }
        return initX + GamePanel.getInstance().getLocationX();
    }

    static int randomiseInitialPosY() {
        Random random = new Random();
        int initY = random.nextInt(GamePanel.getInstance().getPanelHeight());
        if (random.nextBoolean()) {
            initY += GamePanel.getInstance().getPanelHeight();
        } else {
            initY -= GamePanel.getInstance().getPanelHeight();
        }
        return initY + GamePanel.getInstance().getLocationY();
    }

    public static int randomXonScreen() {
        return new Random().nextInt(500) + GamePanel.getInstance().getLocationX();
    }

    public static int randomXoffScreen() {
        return new Random().nextInt(GamePanel.getInstance().getLocationX() - 200);
    }

    public static int randomYoffScreen() {
        return new Random().nextInt(Constants.SCREEN_HEIGHT - 200);
    }


    public static int randomYonScreen() {
        return new Random().nextInt(500) + GamePanel.getInstance().getLocationY();
    }


    public static void generateEnemy(Enemy enemy) {
        if (enemy instanceof Trigorath) {
            makeNewTrigorath();
        } else if (enemy instanceof Squarantine) {
            makeNewSquarantine();
        } else if (enemy instanceof Wyrm) {
            makeNewWyrm();
        } else if (enemy instanceof Archmire) {
            makeNewArchmire();
        } else if (enemy instanceof Necropick) {
            makeNewNecropick();
        } else if (enemy instanceof Omenoct) {
            makeNewOmenoct();
        }
    }
}
