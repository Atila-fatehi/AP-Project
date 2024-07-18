package controller.logic;

import controller.FileController;
import controller.util.Constants;
import model.collision.Collision;
import model.collision.CollisionHandler;
import model.collision.WallCollisionHandler;
import model.objectsModel.enemy.Collectable;
import model.objectsModel.epsilon.Epsilon;
import model.objectsModel.miniBoss.OrbManager;
import view.frames.MainMenu;
import view.gameGUI.GameFrame;
import view.gameGUI.GamePanel;
import controller.audio.players.AudioPlayer;
import controller.audio.players.GameMusicPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class GameManager {

    private static GameManager instance;

    public static GameManager getInstance() {
        if (instance == null) instance = new GameManager();
        return instance;
    }

    private boolean paused;
    private final java.util.Timer modelTimer;
    private final java.util.Timer viewTimer;
    private boolean gameOver;
    private boolean gameWon;
    private boolean empower;

    public GameManager() {
//        Generator.makeNewSquarantine();Generator.makeNewSquarantine();
//        Generator.makeNewSquarantine();
//        Generator.makeNewSquarantine();
//        Generator.makeNewSquarantine();
//        Generator.makeNewSquarantine();

//        Generator.makeNewTrigorath();
//        Generator.makeNewTrigorath();
//        Generator.makeNewTrigorath();
//        Generator.makeNewTrigorath();
//        Generator.makeNewTrigorath();
//        Generator.makeNewTrigorath();
//        Generator.makeNewTrigorath();
//
//        Generator.makeNewOmenoct();
//        Generator.makeNewWyrm();
//        Generator.makeNewWyrm();
//        Generator.makeNewWyrm();
//        Generator.makeNewNecropick();
//        Generator.makeNewArchmire();
//        Generator.makeNewBarricados();
        Generator.makeNewOrb();
        for (int i = 0; i < GameState.panels.size(); i++) {
            System.out.println(GameState.panels.get(i));
        }
        viewTimer = new java.util.Timer();
        viewTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!paused) {
                    updateView();
                }
                if (gameOver) {
                    viewTimer.cancel();
                }
            }
        }, 0, (int) (double) TimeUnit.SECONDS.toMillis(1) / 60/*GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0].getDisplayMode().getRefreshRate()*/);


        modelTimer = new java.util.Timer();
        modelTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!paused && !gameWon) {
                    updateModel();
                }
                if (gameOver) {
                    modelTimer.cancel();
                }
            }
        }, 0, (int) (double) TimeUnit.SECONDS.toMillis(1) / 100);

    }

    private boolean pastTen;

    public void updateView() {
        if (!gameWon) {
            if (pastTen) {
                GamePanel.getInstance().shrink();
            }
        }
        GamePanel.getInstance().repaint();
        ArrayList<JPanel> panels = GameState.panels;
        for (JPanel panel : panels) {
            panel.repaint();
        }
    }

    public void updateModel() {
        //Tri stuff
        for (int i = 0; i < GameState.trigoraths.size(); i++) {
            GameState.trigoraths.get(i).calculateMovingDirection(Epsilon.getInstance().getX(), Epsilon.getInstance().getY());
            GameState.trigoraths.get(i).move();
            GameState.trigoraths.get(i).playAudio();
            GameState.trigoraths.get(i).checkCollisions();
            if (GameState.trigoraths.get(i).getHP() <= 0) {
                AudioPlayer.play(AudioPlayer.MELON_IMPACT);
                GameState.collectables.add(new Collectable(GameState.trigoraths.get(i).getCenterOfGravity().getX(), GameState.trigoraths.get(i).getCenterOfGravity().getY(), 5, Constants.TRI_YELLOW));
                GameState.collectables.add(new Collectable(GameState.trigoraths.get(i).getCenterOfGravity().getX() + 10, GameState.trigoraths.get(i).getCenterOfGravity().getY() + 10, 5, Constants.TRI_YELLOW));
                GameState.trigoraths.remove(i);
                i--;
            }
        }

        //squarantine stuff
        for (int i = 0; i < GameState.squarantines.size(); i++) {
            GameState.squarantines.get(i).calculateMovingDirection(Epsilon.getInstance().getX(), Epsilon.getInstance().getY());
            GameState.squarantines.get(i).move();
            GameState.squarantines.get(i).playAudio();
            GameState.squarantines.get(i).checkCollisions();
            if (GameState.squarantines.get(i).getHP() <= 0) {
                AudioPlayer.play(AudioPlayer.MELON_IMPACT);
                GameState.collectables.add(new Collectable(GameState.squarantines.get(i).getCenterOfGravity().getX(), GameState.squarantines.get(i).getCenterOfGravity().getY(), 5, Constants.SQUA_GREEN));
                GameState.squarantines.get(i).getTimer().cancel();
                GameState.squarantines.remove(i);
                i--;
            }
        }

        //omenoct stuff
        for (int i = 0; i < GameState.omenocts.size(); i++) {
            GameState.omenocts.get(i).calculateMovingDirection(Epsilon.getInstance().getX(), Epsilon.getInstance().getY());
            GameState.omenocts.get(i).move();
            GameState.omenocts.get(i).checkCollisions();
            if (GameState.omenocts.get(i).getHP() <= 0) {
                AudioPlayer.play(AudioPlayer.MELON_IMPACT);
                for (int j = 0; j < 8; j++) {
                    int randX = (int) (new Random().nextInt(3 * Constants.OMENOCT_SIZE) - 1.5 * Constants.OMENOCT_SIZE);
                    int randY = (int) (new Random().nextInt(3 * Constants.OMENOCT_SIZE) - 1.5 * Constants.OMENOCT_SIZE);
                    GameState.collectables.add(new Collectable(GameState.omenocts.get(i).getCenterX() + randX, GameState.omenocts.get(i).getCenterY() + randY, 4, Constants.OMEN_PINK));
                }
                GameState.omenocts.get(i).getShootTimer().cancel();
                GameState.omenocts.remove(i);
                i--;
            }
        }

        //necropick stuff
        for (int i = 0; i < GameState.necropicks.size(); i++) {
            GameState.necropicks.get(i).calculateMovingDirection(Epsilon.getInstance().getX(), Epsilon.getInstance().getY());
            GameState.necropicks.get(i).move();
            GameState.necropicks.get(i).playAudio();
            GameState.necropicks.get(i).checkCollisions();
            if (GameState.necropicks.get(i).getHP() <= 0) {
                AudioPlayer.play(AudioPlayer.MELON_IMPACT);
                for (int j = 0; j < 4; j++) {
                    int size = (int) GameState.necropicks.get(i).getSize();
                    int randX = (int) (new Random().nextInt(2 * size) - size);
                    int randY = (int) (new Random().nextInt(2 * size) - size);
                    GameState.collectables.add(new Collectable(GameState.necropicks.get(i).getXPoints()[0] + randX, GameState.necropicks.get(i).getYPoints()[0] + randY, 2, Color.GRAY));
                }
                GameState.necropicks.get(i).getTimer().cancel();
                GameState.necropicks.remove(i);
                i--;
            }
        }
        //wyrm stuff
        for (int i = 0; i < GameState.wyrms.size(); i++) {
            GameState.wyrms.get(i).calculateMovingDirection(Epsilon.getInstance().getX(), Epsilon.getInstance().getY());
            GameState.wyrms.get(i).move();
            GameState.wyrms.get(i).checkCollisions();
            if (GameState.wyrms.get(i).getHP() <= 0) {
                AudioPlayer.play(AudioPlayer.MELON_IMPACT);
                GameState.collectables.add(new Collectable(GameState.wyrms.get(i).getXPoints()[0], GameState.wyrms.get(i).getYPoints()[0], 8, Constants.WYRM_PINK));
                GameState.collectables.add(new Collectable(GameState.wyrms.get(i).getXPoints()[0]+ 10, GameState.wyrms.get(i).getYPoints()[0] + 10, 8, Constants.WYRM_PINK));

                GameState.wyrms.get(i).getShootTimer().cancel();
                GameState.wyrms.get(i).selfDestruct();
                GameState.wyrms.remove(i);
                i--;
            }
        }


        //archmire stuff
        for (int i = 0; i < GameState.archmires.size(); i++) {
            GameState.archmires.get(i).calculateMovingDirection(Epsilon.getInstance().getX(), Epsilon.getInstance().getY());
            GameState.archmires.get(i).move();
//            GameState.archmires.get(i).playAudio();
            GameState.archmires.get(i).drown();
            if (GameState.archmires.get(i).getHP() <= 0) {
                AudioPlayer.play(AudioPlayer.MELON_IMPACT);
                for (int j = 0; j < 5; j++) {
                    int rada = (int) GameState.archmires.get(i).getRadius_a();
                    int radb = (int) GameState.archmires.get(i).getRadius_b();
                    int randX = (int) (new Random().nextInt(2 * rada) - rada);
                    int randY = (int) (new Random().nextInt(2 * radb) - radb);
                    GameState.collectables.add(new Collectable(GameState.archmires.get(i).getXPoints()[0] + randX, GameState.archmires.get(i).getYPoints()[0] + randY, 6, Constants.ARCH_RED));
                }
                GameState.archmires.get(i).getTimer().cancel();
                GameState.archmires.get(i).getTimer1().cancel();
                GameState.archmires.remove(i);
                i--;
            }
        }


        //barri stuff
        for (int i = 0; i < GameState.barricados.size(); i++) {
            GameState.barricados.get(i).checkCollision();
        }

        //orb stuff
//        OrbManager.drawLines();
//        OrbManager.drown();
        for (int i = 0; i < GameState.orbs.size(); i++) {
            GameState.orbs.get(i).checkCollision();
            if (GameState.orbs.get(i).getHP() <= 0) {
                AudioPlayer.play(AudioPlayer.MELON_IMPACT);
                OrbManager.destroyLaser(GameState.orbs.get(i).getCode());
                for (int j = 0; j < 5; j++) {
                    int rada = (int) GameState.orbs.get(i).getSize();
                    int radb = (int) GameState.orbs.get(i).getSize();
                    int randX = (int) (new Random().nextInt(2 * rada) - rada);
                    int randY = (int) (new Random().nextInt(2 * radb) - radb);
                    GameState.collectables.add(new Collectable(GameState.orbs.get(i).getX() + randX, GameState.orbs.get(i).getY() + randY, 30, Constants.ANOTHER_STRING_COLOR));
                }
                GameState.orbs.get(i).selfDestruct();
                GameState.orbs.remove(i);
                i--;
            }
        }

        //epsilon stuff
        Epsilon.getInstance().move();
        Epsilon.getInstance().wallCollision();
        for (int i = 0; i < GameState.collectables.size(); i++) {
            if (Collision.checkCoinCollision(GameState.collectables.get(i))) {
                Epsilon.getInstance().setXP(Epsilon.getInstance().getXP() + GameState.collectables.get(i).getXp());
                AudioPlayer.play(AudioPlayer.COIN);
                GameState.collectables.remove(i);
                i--;
            }
        }
        if (Epsilon.getInstance().getHP() <= 0) {
            gameOver();
            paused = true;
        }

        //Bullet stuff

        //Tri collision
        for (int i = 0; i < GameState.bullets.size(); i++) {
            GameState.bullets.get(i).move();
            for (int j = 0; j < GameState.trigoraths.size(); j++) {
                Point2D collisionPoint = Collision.checkBulletCollision(GameState.bullets.get(i), GameState.trigoraths.get(j));
                if (collisionPoint != null) {
                    AudioPlayer.play(AudioPlayer.SPLAT);
                    GameState.trigoraths.get(j).setHP(GameState.trigoraths.get(j).getHP() - Epsilon.getInstance().getDamageRate());
                    CollisionHandler.handleCollisionOnPoint(collisionPoint);
                    GameState.bullets.remove(i);
                    i--;
                    break;
                }
            }
        }
        //Squ collision
        for (int i = 0; i < GameState.bullets.size(); i++) {
            for (int j = 0; j < GameState.squarantines.size(); j++) {
                Point2D collisionPoint = Collision.checkBulletCollision(GameState.bullets.get(i), GameState.squarantines.get(j));
                if (collisionPoint != null) {
                    AudioPlayer.play(AudioPlayer.SPLAT);
                    GameState.squarantines.get(j).setHP(GameState.squarantines.get(j).getHP() - Epsilon.getInstance().getDamageRate());
                    CollisionHandler.handleCollisionOnPoint(collisionPoint);
                    GameState.bullets.remove(i);
                    i--;
                    break;
                }
            }
        }
        //omenoct collision
        for (int i = 0; i < GameState.bullets.size(); i++) {
            for (int j = 0; j < GameState.omenocts.size(); j++) {
                Point2D collisionPoint = Collision.checkBulletCollision(GameState.bullets.get(i), GameState.omenocts.get(j));
                if (collisionPoint != null) {
                    AudioPlayer.play(AudioPlayer.SPLAT);
                    GameState.omenocts.get(j).setHP(GameState.omenocts.get(j).getHP() - Epsilon.getInstance().getDamageRate());
                    CollisionHandler.handleCollisionOnPoint(collisionPoint);
                    GameState.bullets.remove(i);
                    i--;
                    break;
                }
            }
        }
        //necro collision
        for (int i = 0; i < GameState.bullets.size(); i++) {
            for (int j = 0; j < GameState.necropicks.size(); j++) {
                Point2D collisionPoint = Collision.checkBulletCollision(GameState.bullets.get(i), GameState.necropicks.get(j));
                if (collisionPoint != null) {
                    AudioPlayer.play(AudioPlayer.SPLAT);
                    GameState.necropicks.get(j).setHP(GameState.necropicks.get(j).getHP() - Epsilon.getInstance().getDamageRate());
                    CollisionHandler.handleCollisionOnPoint(collisionPoint);
                    GameState.bullets.remove(i);
                    i--;
                    break;
                }
            }
        }
        //wyrm collision
        for (int i = 0; i < GameState.bullets.size(); i++) {
            for (int j = 0; j < GameState.wyrms.size(); j++) {
                Point2D collisionPoint = Collision.checkBulletCollision(GameState.bullets.get(i), GameState.wyrms.get(j));
                if (collisionPoint != null) {
                    AudioPlayer.play(AudioPlayer.SPLAT);
                    GameState.wyrms.get(j).setHP(GameState.wyrms.get(j).getHP() - Epsilon.getInstance().getDamageRate());
                    CollisionHandler.handleCollisionOnPoint(collisionPoint);
                    GameState.bullets.remove(i);
                    i--;
                    break;
                }
            }
        }
        //arch collision
        for (int i = 0; i < GameState.bullets.size(); i++) {
            for (int j = 0; j < GameState.archmires.size(); j++) {
                Point2D collisionPoint = Collision.checkBulletCollision(GameState.bullets.get(i), GameState.archmires.get(j));
                if (collisionPoint != null) {
                    AudioPlayer.play(AudioPlayer.SPLAT);
                    GameState.archmires.get(j).setHP(GameState.archmires.get(j).getHP() - Epsilon.getInstance().getDamageRate());
                    CollisionHandler.handleCollisionOnPoint(collisionPoint);
                    GameState.bullets.remove(i);
                    i--;
                    break;
                }
            }
        }
        //barri collision
        for (int i = 0; i < GameState.bullets.size(); i++) {
            for (int j = 0; j < GameState.barricados.size(); j++) {
                Point2D collisionPoint = Collision.checkBulletCollision(GameState.bullets.get(i), GameState.barricados.get(j));
                if (collisionPoint != null) {
                    GameState.bullets.remove(i);
                    i--;
                    break;
                }
            }
        }
        //orb collision
        for (int i = 0; i < GameState.bullets.size(); i++) {
            for (int j = 0; j < GameState.orbs.size(); j++) {
                Point2D collisionPoint = Collision.checkOrbCollision(GameState.orbs.get(j) , GameState.bullets.get(i));
                if (collisionPoint != null && GameState.orbs.get(i).isDamageable()) {
                    GameState.orbs.get(j).setHP(GameState.orbs.get(j).getHP() - Epsilon.getInstance().getDamageRate());
                    GameState.bullets.remove(i);
                    i--;
                    break;
                }
            }
        }
        //epsilon collision
        for (int i = 0; i < GameState.bullets.size(); i++) {
            Point2D epsilonCollisionPoint = Collision.checkCircleCollision(GameState.bullets.get(i));
            if (epsilonCollisionPoint != null) {
                CollisionHandler.handleCollisionOnPoint(epsilonCollisionPoint);
                Epsilon.getInstance().setHP(Epsilon.getInstance().getHP() - GameState.bullets.get(i).getDamage());
                GameState.bullets.remove(i);
                i--;
            }
        }
        //wall collision
        for (int i = 0; i < GameState.bullets.size(); i++) {
            if (!GameState.bullets.get(i).isFromEpsilon()) continue;
            if (GameState.bullets.get(i).wallCollision() != 0) {
                WallCollisionHandler.handleWallCollision(GameState.bullets.get(i).wallCollision());
                GameState.bullets.remove(i);
                i--;
            }
        }
        //bounds
        for (int i = 0; i < GameState.bullets.size(); i++) {
            if (GameState.bullets.get(i).getX() < 0 || GameState.bullets.get(i).getY() < 0 ||
                    GameState.bullets.get(i).getX() > Constants.SCREEN_WIDTH || GameState.bullets.get(i).getY() > Constants.SCREEN_HEIGHT) {
                GameState.bullets.remove(i);
                i--;
            }
        }

    }

    public void startElapsedTimer() {
        javax.swing.Timer timer = new javax.swing.Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Epsilon.getInstance().getAbility().isAceso() && Epsilon.getInstance().getAbility().isActive()) {
                    Epsilon.getInstance().setHP(Epsilon.getInstance().getHP() + 1);
                }
                GameState.elapsedTime++;
                if (GameState.elapsedTime == 10) {
                    pastTen = true;
                }
            }

        });
        timer.start();
    }

    public void gameWon() {
        GameMusicPlayer.getInstance().getClip().stop();
        GameMusicPlayer.getInstance().setPlaying(false);
        AudioPlayer.play(AudioPlayer.WIN_MUSIC);
        FileController.writeXP(Epsilon.getInstance().getXP());
        java.util.Timer timer = new java.util.Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Epsilon.getInstance().setRadius(Epsilon.getInstance().getRadius() + 5);
                if (Epsilon.getInstance().getRadius() == 800) {
                    timer.cancel();
                }
            }
        }, 200, 40);

        java.util.Timer timer2 = new java.util.Timer();
        timer2.schedule(new TimerTask() {
            @Override
            public void run() {
                GamePanel.getInstance().shrinkToZero();
                if (GamePanel.getInstance().getPanelHeight() <= -10 || GamePanel.getInstance().getPanelWidth() <= -10) {
                    timer2.cancel();
                    String[] responses = {"Main Menu"};
                    if (JOptionPane.showOptionDialog(null, "Your XP = " + Epsilon.getInstance().getXP(), "Game Over", JOptionPane.INFORMATION_MESSAGE, JOptionPane.INFORMATION_MESSAGE, null, responses, 0) != -2) {
                        GameFrame.getInstance().dispose();
                        Epsilon.getInstance().setXP(0);
                        Epsilon.getInstance().setHP(100);
                        new MainMenu();
                    }
                }
            }
        }, 5000, 50);
    }

    public void gameOver() {
        GameMusicPlayer.getInstance().getClip().stop();
        GameMusicPlayer.getInstance().setPlaying(false);
        gameOver = true;
        FileController.writeXP(Epsilon.getInstance().getXP());
        Epsilon.getInstance().setHP(0);
        AudioPlayer.play(AudioPlayer.LOSE_MUSIC);
        AudioPlayer.play(AudioPlayer.SCREAM);
        String[] responses = {"Main Menu"};
        if (JOptionPane.showOptionDialog(null, "Your XP = " + Epsilon.getInstance().getXP(), "Game Over", JOptionPane.INFORMATION_MESSAGE, JOptionPane.INFORMATION_MESSAGE, null, responses, 0) != -2) {
            GameFrame.getInstance().dispose();
            Epsilon.getInstance().setXP(0);
            Epsilon.getInstance().setHP(100);
            new MainMenu();
        }
    }

    public void mouseClicked(int x, int y) {
        if (empower) {
            Generator.makeNewBullet(x, y);

            java.util.Timer timer = new java.util.Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Generator.makeNewBullet(x, y);
                    timer.cancel();
                }
            }, 100, 1111);
            java.util.Timer timer2 = new java.util.Timer();
            timer2.schedule(new TimerTask() {
                @Override
                public void run() {
                    Generator.makeNewBullet(x, y);
                    timer2.cancel();
                }
            }, 200, 1111);
        } else {
            Generator.makeNewBullet(x, y);
        }

    }

    public void activateAbility() {
        if (!Epsilon.getInstance().getAbility().isActive()) {
            Epsilon.getInstance().getAbility().setActive(true);
            java.util.Timer timer = new java.util.Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Epsilon.getInstance().getAbility().setActive(false);
                    timer.cancel();
                }
            }, 5 * 1000, 1111);
            if (Epsilon.getInstance().getAbility().isProteus()) {
                Epsilon.getInstance().addVertex();
                Epsilon.getInstance().setXP(Epsilon.getInstance().getXP() - 100);
            }
            if (Epsilon.getInstance().getAbility().isAceso()) {
                Epsilon.getInstance().setXP(Epsilon.getInstance().getXP() - 100);
            }
            if (Epsilon.getInstance().getAbility().isAres()) {
                Epsilon.getInstance().setDamageRate(7);
                Epsilon.getInstance().setXP(Epsilon.getInstance().getXP() - 100);
            }
        }
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public void setEmpower(boolean empower) {
        this.empower = empower;
    }

    public java.util.Timer getModelTimer() {
        return modelTimer;
    }

    public java.util.Timer getViewTimer() {
        return viewTimer;
    }

    public void setGameWon(boolean gameWon) {
        this.gameWon = gameWon;
    }
}
