package controller;

import controller.util.Calculator;
import model.objectsModel.*;
import view.frames.MainMenu;
import view.gameGUI.GameFrame;
import view.gameGUI.GamePanel;
import controller.audio.players.AudioPlayer;
import controller.audio.players.GameMusicPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class GameManager {

    private static GameManager instance;

    public static GameManager getInstance() {
        if (instance == null) instance = new GameManager();
        return instance;
    }


    private final Epsilon epsilon;
    private boolean paused;
    private static final int expandRate = 15;
    private final ArrayList<Bullet> bullets = new ArrayList<>();
    private final ArrayList<Trigorath> trigoraths = new ArrayList<>();
    private final ArrayList<Squarantine> squarantines = new ArrayList<>();
    private final ArrayList<Collectable> collectables = new ArrayList<>();
    private int wave;
    private int difficulty;
    private int sensitivity;
    private int damageRate;
    private final java.util.Timer modelTimer;
    private final java.util.Timer viewTimer;
    private boolean gameOver;
    private boolean gameWon;
    private boolean empower;

    public GameManager() {
        epsilon = new Epsilon(350, 350, 13);
        GamePanel.getInstance().setEpsilon(epsilon);
        damageRate = 5;
        wave = 0;

        int[] codes = FileController.readSettings();
        assert codes != null;
        sensitivity = codes[0];
        difficulty = codes[1];

        if (sensitivity < 30) {
            epsilon.setMAX_VELOCITY(7);
            epsilon.setACCELERATION(0.5);
        } else if (sensitivity > 60) {
            epsilon.setMAX_VELOCITY(15);
            epsilon.setACCELERATION(2);
        } else {
            epsilon.setMAX_VELOCITY(11);
            epsilon.setACCELERATION(1);
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
            GamePanel.getInstance().setBullets(bullets);
            GamePanel.getInstance().setTrigoraths(trigoraths);
            GamePanel.getInstance().setSquarantines(squarantines);
            GamePanel.getInstance().setCollectables(collectables);
        }
        GamePanel.getInstance().repaint();
    }

    private boolean inWait;

    public void updateModel() {
        //generate enemies
        if (trigoraths.isEmpty() && squarantines.isEmpty() && !inWait) {
            wave++;
            if (wave == 4) {
                gameWon = true;
                gameWon();
            } else {
                AudioPlayer.play(AudioPlayer.WAVE);
                java.util.Timer timer = new java.util.Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        AudioPlayer.play(AudioPlayer.AWOOGA);
                        generateWave(wave);
                        inWait = false;
                        timer.cancel();
                    }
                }, 3000, 1111);
                inWait = true;
            }
        }
        //Bullet stuff
        //Tri collision
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).move();
            for (int j = 0; j < trigoraths.size(); j++) {
                Point2D trigorathCollisionPoint = bullets.get(i).onTrigorathCollision(trigoraths.get(j).getX1(), trigoraths.get(j).getX2(), trigoraths.get(j).getX3(), trigoraths.get(j).getY1(), trigoraths.get(j).getY2(), trigoraths.get(j).getY3());
                if (trigorathCollisionPoint != null) {
                    AudioPlayer.play(AudioPlayer.SPLAT);
                    trigoraths.get(j).setHP(trigoraths.get(j).getHP() - damageRate);
                    impactOnPoint(trigorathCollisionPoint);
                    bullets.remove(i);
                    i--;
                    break;
                }
            }
        }
        //Squ collision
        for (int i = 0; i < bullets.size(); i++) {
            for (int j = 0; j < squarantines.size(); j++) {
                Point2D squarantineCollisionPoint = bullets.get(i).onSquarantineCollision(squarantines.get(j).getX1(), squarantines.get(j).getX2(), squarantines.get(j).getX3(), squarantines.get(j).getX4(), squarantines.get(j).getY1(), squarantines.get(j).getY2(), squarantines.get(j).getY3(), squarantines.get(j).getY4());
                if (squarantineCollisionPoint != null) {
                    AudioPlayer.play(AudioPlayer.SPLAT);
                    squarantines.get(j).setHP(squarantines.get(j).getHP() - damageRate);
                    impactOnPoint(squarantineCollisionPoint);
                    bullets.remove(i);
                    i--;
                    break;
                }
            }
        }
        //wall collision
        for (int i = 0; i < bullets.size(); i++) {
            int wallCollisionNum = bullets.get(i).onWallCollision(GamePanel.getInstance().getScreenWidth(), GamePanel.getInstance().getScreenHeight());
            if (wallCollisionNum == 1) {
                GamePanel.getInstance().setLocation(GamePanel.getInstance().getLocationX() - expandRate, GamePanel.getInstance().getLocationY());
                GamePanel.getInstance().setSize(GamePanel.getInstance().getScreenWidth() + expandRate, GamePanel.getInstance().getScreenHeight());
                GamePanel.getInstance().setScreenWidth(GamePanel.getInstance().getScreenWidth() + expandRate);
                GamePanel.getInstance().setLocationX(GamePanel.getInstance().getLocationX() - expandRate);
                epsilon.setX(epsilon.getX() + expandRate);
                for (int j = 0; j < trigoraths.size(); j++) {
                    trigoraths.get(j).shiftX(expandRate);
                }
                for (int j = 0; j < squarantines.size(); j++) {
                    squarantines.get(j).shiftX(expandRate);
                }
                for (int j = 0; j < collectables.size(); j++) {
                    collectables.get(j).shiftX(expandRate);
                }
                bullets.remove(i);
                i--;
            } else if (wallCollisionNum == 2) {
                GamePanel.getInstance().setLocation(GamePanel.getInstance().getLocationX(), GamePanel.getInstance().getLocationY() - expandRate);
                GamePanel.getInstance().setSize(GamePanel.getInstance().getScreenWidth(), GamePanel.getInstance().getScreenHeight() + expandRate);
                GamePanel.getInstance().setScreenHeight(GamePanel.getInstance().getScreenHeight() + expandRate);
                GamePanel.getInstance().setLocationY(GamePanel.getInstance().getLocationY() - expandRate);
                epsilon.setY(epsilon.getY() + expandRate);

                for (int j = 0; j < trigoraths.size(); j++) {
                    trigoraths.get(j).shiftY(expandRate);
                }
                for (int j = 0; j < squarantines.size(); j++) {
                    squarantines.get(j).shiftY(expandRate);
                }
                for (int j = 0; j < collectables.size(); j++) {
                    collectables.get(j).shiftY(expandRate);
                }
                bullets.remove(i);
                i--;
            } else if (wallCollisionNum == 3) {
                GamePanel.getInstance().setSize(GamePanel.getInstance().getScreenWidth() + expandRate, GamePanel.getInstance().getScreenHeight());
                GamePanel.getInstance().setScreenWidth(GamePanel.getInstance().getScreenWidth() + expandRate);
                bullets.remove(i);
                i--;
            } else if (wallCollisionNum == 4) {
                GamePanel.getInstance().setSize(GamePanel.getInstance().getScreenWidth(), GamePanel.getInstance().getScreenHeight() + expandRate);
                GamePanel.getInstance().setScreenHeight(GamePanel.getInstance().getScreenHeight() + expandRate);
                bullets.remove(i);
                i--;
            }
        }

        //Tri stuff
        for (int i = 0; i < trigoraths.size(); i++) {
            trigoraths.get(i).calculateMovingDirection(epsilon.getX(), epsilon.getY());
            trigoraths.get(i).move();
            if (trigoraths.get(i).getX1() >= 0 && trigoraths.get(i).getX1() <= GamePanel.getInstance().getScreenWidth() && trigoraths.get(i).getY1() >= 0 && trigoraths.get(i).getX1() <= GamePanel.getInstance().getScreenHeight()) {
                if (!trigoraths.get(i).isPlayed()) {
                    AudioPlayer.play(AudioPlayer.GROAN);
                    trigoraths.get(i).setPlayed(true);
                }
            }
            if (epsilon.hasVertex()) {
                if (trigoraths.get(i).onPointCollision(epsilon.getVertexX(), epsilon.getVertexY()) != null) {
                    trigoraths.get(i).setHP(trigoraths.get(i).getHP() - 10);
                    AudioPlayer.play(AudioPlayer.SPLAT);
                    impactOnPoint(new Point2D.Double(epsilon.getVertexX(), epsilon.getVertexY()));
                }
                if (epsilon.getVertexesNum() >= 2) {
                    if (trigoraths.get(i).onPointCollision(epsilon.getVertexX(), epsilon.getVertexY() + epsilon.getRadius() * 2 + 14) != null) {
                        trigoraths.get(i).setHP(trigoraths.get(i).getHP() - 10);
                        AudioPlayer.play(AudioPlayer.SPLAT);
                        impactOnPoint(new Point2D.Double(epsilon.getVertexX(), epsilon.getVertexY()));
                    }
                }
                if (epsilon.getVertexesNum() >= 3) {
                    if (trigoraths.get(i).onPointCollision(epsilon.getX() + epsilon.getRadius() + 7, epsilon.getY()) != null) {
                        AudioPlayer.play(AudioPlayer.SPLAT);
                        trigoraths.get(i).setHP(trigoraths.get(i).getHP() - 10);
                        impactOnPoint(new Point2D.Double(epsilon.getX() + epsilon.getRadius() + 7, epsilon.getY()));
                    }
                }
                if (epsilon.getVertexesNum() >= 4) {
                    if (trigoraths.get(i).onPointCollision(epsilon.getX() - epsilon.getRadius() - 7, epsilon.getY()) != null) {
                        AudioPlayer.play(AudioPlayer.SPLAT);
                        trigoraths.get(i).setHP(trigoraths.get(i).getHP() - 10);
                        impactOnPoint(new Point2D.Double(epsilon.getX() - epsilon.getRadius() - 7, epsilon.getY()));
                    }
                }
            }
            Point2D epsilonCollisionPoint = trigoraths.get(i).onEpsilonCollision(epsilon.getX(), epsilon.getY(), epsilon.getRadius());
            if (epsilonCollisionPoint != null) {
                impactOnPoint(epsilonCollisionPoint);
                epsilon.setHP(epsilon.getHP() - 10);
            }
            for (int j = 0; j < trigoraths.size(); j++) {
                if (i != j) {
                    Point2D trigorathCollisionPoint = trigoraths.get(i).onTrigorathCollision(trigoraths.get(j).getX1(), trigoraths.get(j).getX2(), trigoraths.get(j).getX3(), trigoraths.get(j).getY1(), trigoraths.get(j).getY2(), trigoraths.get(j).getY3());
                    if (trigorathCollisionPoint != null) {
                        impactOnPoint(trigorathCollisionPoint);
                    }
                }
            }
            for (int j = 0; j < squarantines.size(); j++) {
                Point2D squarantineCollisionPoint = trigoraths.get(i).onSquarantineCollision(squarantines.get(j).getX1(), squarantines.get(j).getX2(), squarantines.get(j).getX3(), squarantines.get(j).getX4(), squarantines.get(j).getY1(), squarantines.get(j).getY2(), squarantines.get(j).getY3(), squarantines.get(j).getY4());
                if (squarantineCollisionPoint != null) {
                    impactOnPoint(squarantineCollisionPoint);
                }
            }
            if (trigoraths.get(i).getHP() <= 0) {
                AudioPlayer.play(AudioPlayer.MELON_IMPACT);
                collectables.add(new Collectable(trigoraths.get(i).getCenterOfGravity().getX(), trigoraths.get(i).getCenterOfGravity().getY(), new Color(0xFFD900)));
                collectables.add(new Collectable(trigoraths.get(i).getCenterOfGravity().getX() + 10, trigoraths.get(i).getCenterOfGravity().getY() + 10, new Color(0xFFD900)));
                trigoraths.remove(i);
                i--;
            }
        }

        //squarantine stuff
        for (int i = 0; i < squarantines.size(); i++) {
            squarantines.get(i).calculateMovingDirection(epsilon.getX(), epsilon.getY());
            squarantines.get(i).move();
            if (squarantines.get(i).getX1() >= 0 && squarantines.get(i).getX1() <= GamePanel.getInstance().getScreenWidth() && squarantines.get(i).getY1() >= 0 && squarantines.get(i).getX1() <= GamePanel.getInstance().getScreenHeight()) {
                if (!squarantines.get(i).isPlayed()) {
                    AudioPlayer.play(AudioPlayer.GROAN);
                    squarantines.get(i).setPlayed(true);
                }
            }
            if (epsilon.hasVertex()) {
                if (squarantines.get(i).onPointCollision(epsilon.getVertexX(), epsilon.getVertexY()) != null) {
                    squarantines.get(i).setHP(squarantines.get(i).getHP() - 10);
                    AudioPlayer.play(AudioPlayer.SPLAT);
                    impactOnPoint(new Point2D.Double(epsilon.getVertexX(), epsilon.getVertexY()));
                }
                if (epsilon.getVertexesNum() >= 2) {
                    if (squarantines.get(i).onPointCollision(epsilon.getVertexX(), epsilon.getVertexY() + epsilon.getRadius() * 2 + 14) != null) {
                        squarantines.get(i).setHP(squarantines.get(i).getHP() - 10);
                        AudioPlayer.play(AudioPlayer.SPLAT);
                        impactOnPoint(new Point2D.Double(epsilon.getVertexX(), epsilon.getVertexY()));
                    }
                }
                if (epsilon.getVertexesNum() >= 3) {
                    if (squarantines.get(i).onPointCollision(epsilon.getX() + epsilon.getRadius() + 7, epsilon.getY()) != null) {
                        squarantines.get(i).setHP(squarantines.get(i).getHP() - 10);
                        AudioPlayer.play(AudioPlayer.SPLAT);
                        impactOnPoint(new Point2D.Double(epsilon.getX() + epsilon.getRadius() + 7, epsilon.getY()));
                    }
                }
                if (epsilon.getVertexesNum() >= 4) {
                    if (squarantines.get(i).onPointCollision(epsilon.getX() - epsilon.getRadius() - 7, epsilon.getY()) != null) {
                        squarantines.get(i).setHP(squarantines.get(i).getHP() - 10);
                        AudioPlayer.play(AudioPlayer.SPLAT);
                        impactOnPoint(new Point2D.Double(epsilon.getX() - epsilon.getRadius() - 7, epsilon.getY()));
                    }
                }
            }
            Point2D epsilonCollisionPoint = squarantines.get(i).onEpsilonCollision(epsilon.getX(), epsilon.getY(), epsilon.getRadius());
            if (epsilonCollisionPoint != null) {
                impactOnPoint(epsilonCollisionPoint);
                epsilon.setHP(epsilon.getHP() - 6);
            }

            for (int j = 0; j < trigoraths.size(); j++) {
                Point2D trigorathCollisionPoint = squarantines.get(i).onTrigorathCollision(trigoraths.get(j).getX1(), trigoraths.get(j).getX2(), trigoraths.get(j).getX3(), trigoraths.get(j).getY1(), trigoraths.get(j).getY2(), trigoraths.get(j).getY3());
                if (trigorathCollisionPoint != null) {
                    impactOnPoint(trigorathCollisionPoint);
                }
            }
            for (int j = 0; j < squarantines.size(); j++) {
                if (i != j) {
                    Point2D squarantineCollisionPoint = squarantines.get(i).onSquarantineCollision(squarantines.get(j).getX1(), squarantines.get(j).getX2(), squarantines.get(j).getX3(), squarantines.get(j).getX4(), squarantines.get(j).getY1(), squarantines.get(j).getY2(), squarantines.get(j).getY3(), squarantines.get(j).getY4());
                    if (squarantineCollisionPoint != null) {
                        impactOnPoint(squarantineCollisionPoint);
                    }
                }
            }
            if (squarantines.get(i).getHP() <= 0) {
                collectables.add(new Collectable(squarantines.get(i).getCenterOfGravity().getX(), squarantines.get(i).getCenterOfGravity().getY(), new Color(0x22FF00)));
                squarantines.get(i).getTimer().cancel();
                squarantines.remove(i);
                i--;
            }
        }

        //epsilon stuff
        epsilon.move();
        if (epsilon.getHP() <= 0) {
            gameOver();
            paused = true;
        }
        for (int i = 0; i < collectables.size(); i++) {
            if (Calculator.distance(epsilon.getX(), epsilon.getY(), collectables.get(i).getX(), collectables.get(i).getY()) <= collectables.get(i).getRadius() + epsilon.getRadius() + 20) {
                epsilon.setXP(epsilon.getXP() + collectables.get(i).getXp());
                AudioPlayer.play(AudioPlayer.COIN);
                collectables.remove(i);
                i--;
            }
        }
        if (epsilon.getX() - epsilon.getRadius() < 0) {
            epsilon.setX(epsilon.getRadius());
            epsilon.setVx(0);
        } else if (epsilon.getX() + epsilon.getRadius() > GamePanel.getInstance().getScreenWidth()) {
            epsilon.setX(GamePanel.getInstance().getScreenWidth() - epsilon.getRadius());
            epsilon.setVx(0);
        }

        if (epsilon.getY() - epsilon.getRadius() < 0) {
            epsilon.setY(epsilon.getRadius());
            epsilon.setVy(0);
        } else if (epsilon.getY() + epsilon.getRadius() > GamePanel.getInstance().getScreenHeight()) {
            epsilon.setY(GamePanel.getInstance().getScreenHeight() - epsilon.getRadius());
            epsilon.setVy(0);
        }
    }

    public void gameWon() {
        GameMusicPlayer.getInstance().getClip().stop();
        GameMusicPlayer.getInstance().setPlaying(false);
        AudioPlayer.play(AudioPlayer.WIN_MUSIC);
        FileController.writeXP(epsilon.getXP());
        java.util.Timer timer = new java.util.Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                epsilon.setRadius(epsilon.getRadius() + 5);
                if (epsilon.getRadius() == 800) {
                    timer.cancel();
                }
            }
        }, 200, 40);
        java.util.Timer timer2 = new java.util.Timer();
        timer2.schedule(new TimerTask() {
            @Override
            public void run() {
                GamePanel.getInstance().shrinkToZero();
                if (GamePanel.getInstance().getScreenHeight() <= -10 || GamePanel.getInstance().getScreenWidth() <= -10) {
                    timer2.cancel();
                    String[] responses = {"Main Menu"};
                    if (JOptionPane.showOptionDialog(null, "Your XP = " + epsilon.getXP(), "Game Over", JOptionPane.INFORMATION_MESSAGE, JOptionPane.INFORMATION_MESSAGE, null, responses, 0) != -2) {
                        GameFrame.getInstance().dispose();
                        epsilon.setXP(0);
                        epsilon.setHP(100);
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
        FileController.writeXP(epsilon.getXP());
        epsilon.setHP(0);
        AudioPlayer.play(AudioPlayer.LOSE_MUSIC);
        AudioPlayer.play(AudioPlayer.SCREAM);
        String[] responses = {"Main Menu"};
        if (JOptionPane.showOptionDialog(null, "Your XP = " + epsilon.getXP(), "Game Over", JOptionPane.INFORMATION_MESSAGE, JOptionPane.INFORMATION_MESSAGE, null, responses, 0) != -2) {
            GameFrame.getInstance().dispose();
            epsilon.setXP(0);
            epsilon.setHP(100);
            new MainMenu();
        }
    }

    public void impactOnPoint(Point2D collisionPoint) {
        double rate = 12;
        for (int i = 0; i < trigoraths.size(); i++) {
            if (Calculator.distance(trigoraths.get(i).getCenterOfGravity().getX(), trigoraths.get(i).getCenterOfGravity().getY(), collisionPoint.getX(), collisionPoint.getY()) <= 70) {
                double angle = Math.atan2(collisionPoint.getY() - trigoraths.get(i).getCenterOfGravity().getY(), collisionPoint.getX() - trigoraths.get(i).getCenterOfGravity().getX());
                trigoraths.get(i).setVx(-(rate - 3) * Math.cos(angle));
                trigoraths.get(i).setVy(-(rate - 3) * Math.sin(angle));
            }
        }
        for (int i = 0; i < squarantines.size(); i++) {
            if (Calculator.distance(squarantines.get(i).getCenterOfGravity().getX(), squarantines.get(i).getCenterOfGravity().getY(), collisionPoint.getX(), collisionPoint.getY()) <= 70) {
                double angle = Math.atan2(collisionPoint.getY() - squarantines.get(i).getCenterOfGravity().getY(), collisionPoint.getX() - squarantines.get(i).getCenterOfGravity().getX());
                squarantines.get(i).setVx(-(rate - 3) * Math.cos(angle));
                squarantines.get(i).setVy(-(rate - 3) * Math.sin(angle));
            }
        }
        if (Calculator.distance(epsilon.getX(), epsilon.getY(), collisionPoint.getX(), collisionPoint.getY()) <= 70) {
            if (collisionPoint.getX() >= epsilon.getX() && collisionPoint.getY() >= epsilon.getY()) {
                epsilon.setVx(-rate);
                epsilon.setVy(-rate);
                epsilon.setDecU(true);
                epsilon.setDecL(true);
                epsilon.setDecD(true);
                epsilon.setDecR(true);
            }
            if (collisionPoint.getX() <= epsilon.getX() && collisionPoint.getY() <= epsilon.getY()) {
                epsilon.setVx(rate);
                epsilon.setVy(rate);
                epsilon.setDecU(true);
                epsilon.setDecL(true);
                epsilon.setDecD(true);
                epsilon.setDecR(true);
            }
            if (collisionPoint.getX() <= epsilon.getX() && collisionPoint.getY() >= epsilon.getY()) {
                epsilon.setVx(rate);
                epsilon.setVy(-rate);
                epsilon.setDecU(true);
                epsilon.setDecL(true);
                epsilon.setDecD(true);
                epsilon.setDecR(true);
            }
            if (collisionPoint.getX() >= epsilon.getX() && collisionPoint.getY() <= epsilon.getY()) {
                epsilon.setVx(-rate);
                epsilon.setVy(rate);
                epsilon.setDecU(true);
                epsilon.setDecL(true);
                epsilon.setDecD(true);
                epsilon.setDecR(true);
            }
        }

    }

    public void impactOnPointWithoutEpsilon(Point2D collisionPoint) {
        double rate = 20;
        for (int i = 0; i < trigoraths.size(); i++) {
            if (Calculator.distance(trigoraths.get(i).getCenterOfGravity().getX(), trigoraths.get(i).getCenterOfGravity().getY(), collisionPoint.getX(), collisionPoint.getY()) <= 150) {
                double angle = Math.atan2(collisionPoint.getY() - trigoraths.get(i).getCenterOfGravity().getY(), collisionPoint.getX() - trigoraths.get(i).getCenterOfGravity().getX());
                trigoraths.get(i).setVx(-(rate - 3) * Math.cos(angle));
                trigoraths.get(i).setVy(-(rate - 3) * Math.sin(angle));
            }
        }
        for (int i = 0; i < squarantines.size(); i++) {
            if (Calculator.distance(squarantines.get(i).getCenterOfGravity().getX(), squarantines.get(i).getCenterOfGravity().getY(), collisionPoint.getX(), collisionPoint.getY()) <= 150) {
                double angle = Math.atan2(collisionPoint.getY() - squarantines.get(i).getCenterOfGravity().getY(), collisionPoint.getX() - squarantines.get(i).getCenterOfGravity().getX());
                squarantines.get(i).setVx(-(rate - 3) * Math.cos(angle));
                squarantines.get(i).setVy(-(rate - 3) * Math.sin(angle));
            }
        }
    }

    public void mouseClicked(int x, int y) {
        if (empower) {
            makeNewBullet(x, y);

            java.util.Timer timer = new java.util.Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    makeNewBullet(x, y);
                    timer.cancel();
                }
            }, 100, 1000);
            java.util.Timer timer2 = new java.util.Timer();
            timer2.schedule(new TimerTask() {
                @Override
                public void run() {
                    makeNewBullet(x, y);
                    timer2.cancel();
                }
            }, 200, 1000);

        } else {
            makeNewBullet(x, y);
        }

    }

    public void generateWave(int wave) {
        Random random = new Random();
        makeNewSquarantine();
        makeNewTrigorath();
        for (int i = 0; i < wave * difficulty; i++) {
            if (random.nextBoolean()) {
                makeNewSquarantine();
            }
        }
        for (int i = 0; i < wave * difficulty; i++) {
            if (random.nextBoolean()) {
                makeNewTrigorath();
            }
        }
    }

    public void makeNewBullet(int x, int y) {
        Bullet bullet = new Bullet(epsilon.getX(), epsilon.getY());
        double angle = Math.atan2(y - epsilon.getY(), x - epsilon.getX());
        bullet.setVx(bullet.getConstantVelocity() * Math.cos(angle));
        bullet.setVy(bullet.getConstantVelocity() * Math.sin(angle));
        bullets.add(bullet);
    }

    public void makeNewTrigorath() {
        Random random = new Random();
        int initialPositionX = random.nextInt(GamePanel.getInstance().getScreenWidth());
        int initialPositionY = random.nextInt(GamePanel.getInstance().getScreenHeight());
        if (random.nextBoolean()) {
            initialPositionX += GamePanel.getInstance().getScreenWidth();
        } else {
            initialPositionX -= GamePanel.getInstance().getScreenWidth();
        }
        if (random.nextBoolean()) {
            initialPositionY += GamePanel.getInstance().getScreenHeight();
        } else {
            initialPositionY -= GamePanel.getInstance().getScreenHeight();
        }
        trigoraths.add(new Trigorath(initialPositionX, initialPositionY, initialPositionX + 30, initialPositionY, initialPositionX + 15, initialPositionY - 25));
    }

    public void makeNewSquarantine() {
        Random random = new Random();
        int initialPositionX = random.nextInt(GamePanel.getInstance().getScreenWidth());
        int initialPositionY = random.nextInt(GamePanel.getInstance().getScreenHeight());
        if (random.nextBoolean()) {
            initialPositionX += GamePanel.getInstance().getScreenWidth();
        } else {
            initialPositionX -= GamePanel.getInstance().getScreenWidth();
        }
        if (random.nextBoolean()) {
            initialPositionY += GamePanel.getInstance().getScreenHeight();
        } else {
            initialPositionY -= GamePanel.getInstance().getScreenHeight();
        }
        squarantines.add(new Squarantine(initialPositionX, initialPositionY, initialPositionX + 25, initialPositionY, initialPositionX + 25, initialPositionY + 25, initialPositionX, initialPositionY + 25));

    }

    public void activateAbility() {
        if (!epsilon.getAbility().isActive()) {
            epsilon.getAbility().setActive(true);
            java.util.Timer timer = new java.util.Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    epsilon.getAbility().setActive(false);
                    timer.cancel();
                }
            }, 5 * 1000, 1111);
            if (epsilon.getAbility().isProteus()) {
                epsilon.addVertex();
                epsilon.setXP(epsilon.getXP() - 100);
            }
            if (epsilon.getAbility().isAceso()) {
                epsilon.setXP(epsilon.getXP() - 100);
            }
            if (epsilon.getAbility().isAres()) {
                setDamageRate(7);
                epsilon.setXP(epsilon.getXP() - 100);
            }
        }
    }

    //GETTER SETTERS
    public int getCurrentWave() {
        return wave;
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

    public Timer getModelTimer() {
        return modelTimer;
    }

    public Timer getViewTimer() {
        return viewTimer;
    }

    public boolean isPastTen() {
        return pastTen;
    }

    public void setPastTen(boolean pastTen) {
        this.pastTen = pastTen;
    }

    public void setDamageRate(int damageRate) {
        this.damageRate = damageRate;
    }

    public Epsilon getEpsilon() {
        return epsilon;
    }
}
