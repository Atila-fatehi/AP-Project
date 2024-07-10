package controller;

import controller.util.Calculator;
import model.collision.WallCollisionHandler;
import model.objectsModel.*;
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
    private static final int expandRate = 1;
    private final ArrayList<Bullet> bullets = new ArrayList<>();
    private final ArrayList<Trigorath> trigoraths = new ArrayList<>();
    private final ArrayList<Squarantine> squarantines = new ArrayList<>();
    private final ArrayList<Collectable> collectables = new ArrayList<>();
    private int wave;
    private int difficulty;
    private int sensitivity;
    private final java.util.Timer modelTimer;
    private final java.util.Timer viewTimer;
    private boolean gameOver;
    private boolean gameWon;
    private boolean empower;
    private int elapsedTime;

    public GameManager() {

        if (FileController.readAbilities() == 11) {
            Epsilon.getInstance().getAbility().setAres(true);
//                epsilon.setXP(epsilon.getXP() - 100);
//                gameManager.setDamageRate(7);
        }
        if (FileController.readAbilities() == 21) {
//                epsilon.setXP(epsilon.getXP() - 100);
            Epsilon.getInstance().getAbility().setAceso(true);
        }
        if (FileController.readAbilities() == 31) {
            Epsilon.getInstance().getAbility().setProteus(true);
        }

        difficulty = Objects.requireNonNull(FileController.readSettings())[1];

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


    public void startElapsedTimer() {
        javax.swing.Timer timer = new javax.swing.Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Epsilon.getInstance().getAbility().isAceso() && Epsilon.getInstance().getAbility().isActive()) {
                    Epsilon.getInstance().setHP(Epsilon.getInstance().getHP() + 1);
                }
                elapsedTime++;
                if (elapsedTime == 10) {
                    pastTen = true;
                }
                GamePanel.getInstance().setElapsedTime(elapsedTime);
            }

        });
        timer.start();
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
                    trigoraths.get(j).setHP(trigoraths.get(j).getHP() - Epsilon.getInstance().getDamageRate());
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
                    squarantines.get(j).setHP(squarantines.get(j).getHP() - Epsilon.getInstance().getDamageRate());
                    impactOnPoint(squarantineCollisionPoint);
                    bullets.remove(i);
                    i--;
                    break;
                }
            }
        }
        //wall collision
        for (int i = 0; i < bullets.size(); i++) {
            if(bullets.get(i).wallCollision() != 0){
                WallCollisionHandler.handleWallCollision(bullets.get(i).wallCollision());
                bullets.remove(i);
                i--;
            }
        }

        //Tri stuff
        for (int i = 0; i < trigoraths.size(); i++) {
            trigoraths.get(i).calculateMovingDirection(Epsilon.getInstance().getX(), Epsilon.getInstance().getY());
            trigoraths.get(i).move();
            if (trigoraths.get(i).getX1() >= 0 && trigoraths.get(i).getX1() <= GamePanel.getInstance().getPanelWidth() && trigoraths.get(i).getY1() >= 0 && trigoraths.get(i).getX1() <= GamePanel.getInstance().getPanelHeight()) {
                if (!trigoraths.get(i).isPlayed()) {
                    AudioPlayer.play(AudioPlayer.GROAN);
                    trigoraths.get(i).setPlayed(true);
                }
            }
            if (Epsilon.getInstance().hasVertex()) {
                if (trigoraths.get(i).onPointCollision(Epsilon.getInstance().getVertexX(), Epsilon.getInstance().getVertexY()) != null) {
                    trigoraths.get(i).setHP(trigoraths.get(i).getHP() - 10);
                    AudioPlayer.play(AudioPlayer.SPLAT);
                    impactOnPoint(new Point2D.Double(Epsilon.getInstance().getVertexX(), Epsilon.getInstance().getVertexY()));
                }
                if (Epsilon.getInstance().getVertexesNum() >= 2) {
                    if (trigoraths.get(i).onPointCollision(Epsilon.getInstance().getVertexX(), Epsilon.getInstance().getVertexY() + Epsilon.getInstance().getRadius() * 2 + 14) != null) {
                        trigoraths.get(i).setHP(trigoraths.get(i).getHP() - 10);
                        AudioPlayer.play(AudioPlayer.SPLAT);
                        impactOnPoint(new Point2D.Double(Epsilon.getInstance().getVertexX(), Epsilon.getInstance().getVertexY()));
                    }
                }
                if (Epsilon.getInstance().getVertexesNum() >= 3) {
                    if (trigoraths.get(i).onPointCollision(Epsilon.getInstance().getX() + Epsilon.getInstance().getRadius() + 7, Epsilon.getInstance().getY()) != null) {
                        AudioPlayer.play(AudioPlayer.SPLAT);
                        trigoraths.get(i).setHP(trigoraths.get(i).getHP() - 10);
                        impactOnPoint(new Point2D.Double(Epsilon.getInstance().getX() + Epsilon.getInstance().getRadius() + 7, Epsilon.getInstance().getY()));
                    }
                }
                if (Epsilon.getInstance().getVertexesNum() >= 4) {
                    if (trigoraths.get(i).onPointCollision(Epsilon.getInstance().getX() - Epsilon.getInstance().getRadius() - 7, Epsilon.getInstance().getY()) != null) {
                        AudioPlayer.play(AudioPlayer.SPLAT);
                        trigoraths.get(i).setHP(trigoraths.get(i).getHP() - 10);
                        impactOnPoint(new Point2D.Double(Epsilon.getInstance().getX() - Epsilon.getInstance().getRadius() - 7, Epsilon.getInstance().getY()));
                    }
                }
            }
            Point2D epsilonCollisionPoint = trigoraths.get(i).onEpsilonCollision(Epsilon.getInstance().getX(), Epsilon.getInstance().getY(), Epsilon.getInstance().getRadius());
            if (epsilonCollisionPoint != null) {
                impactOnPoint(epsilonCollisionPoint);
                Epsilon.getInstance().setHP(Epsilon.getInstance().getHP() - 10);
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
            squarantines.get(i).calculateMovingDirection(Epsilon.getInstance().getX(), Epsilon.getInstance().getY());
            squarantines.get(i).move();
            if (squarantines.get(i).getX1() >= 0 && squarantines.get(i).getX1() <= GamePanel.getInstance().getPanelWidth() && squarantines.get(i).getY1() >= 0 && squarantines.get(i).getX1() <= GamePanel.getInstance().getPanelHeight()) {
                if (!squarantines.get(i).isPlayed()) {
                    AudioPlayer.play(AudioPlayer.GROAN);
                    squarantines.get(i).setPlayed(true);
                }
            }
            if (Epsilon.getInstance().hasVertex()) {
                if (squarantines.get(i).onPointCollision(Epsilon.getInstance().getVertexX(), Epsilon.getInstance().getVertexY()) != null) {
                    squarantines.get(i).setHP(squarantines.get(i).getHP() - 10);
                    AudioPlayer.play(AudioPlayer.SPLAT);
                    impactOnPoint(new Point2D.Double(Epsilon.getInstance().getVertexX(), Epsilon.getInstance().getVertexY()));
                }
                if (Epsilon.getInstance().getVertexesNum() >= 2) {
                    if (squarantines.get(i).onPointCollision(Epsilon.getInstance().getVertexX(), Epsilon.getInstance().getVertexY() + Epsilon.getInstance().getRadius() * 2 + 14) != null) {
                        squarantines.get(i).setHP(squarantines.get(i).getHP() - 10);
                        AudioPlayer.play(AudioPlayer.SPLAT);
                        impactOnPoint(new Point2D.Double(Epsilon.getInstance().getVertexX(), Epsilon.getInstance().getVertexY()));
                    }
                }
                if (Epsilon.getInstance().getVertexesNum() >= 3) {
                    if (squarantines.get(i).onPointCollision(Epsilon.getInstance().getX() + Epsilon.getInstance().getRadius() + 7, Epsilon.getInstance().getY()) != null) {
                        squarantines.get(i).setHP(squarantines.get(i).getHP() - 10);
                        AudioPlayer.play(AudioPlayer.SPLAT);
                        impactOnPoint(new Point2D.Double(Epsilon.getInstance().getX() + Epsilon.getInstance().getRadius() + 7, Epsilon.getInstance().getY()));
                    }
                }
                if (Epsilon.getInstance().getVertexesNum() >= 4) {
                    if (squarantines.get(i).onPointCollision(Epsilon.getInstance().getX() - Epsilon.getInstance().getRadius() - 7, Epsilon.getInstance().getY()) != null) {
                        squarantines.get(i).setHP(squarantines.get(i).getHP() - 10);
                        AudioPlayer.play(AudioPlayer.SPLAT);
                        impactOnPoint(new Point2D.Double(Epsilon.getInstance().getX() - Epsilon.getInstance().getRadius() - 7, Epsilon.getInstance().getY()));
                    }
                }
            }
            Point2D epsilonCollisionPoint = squarantines.get(i).onEpsilonCollision(Epsilon.getInstance().getX(), Epsilon.getInstance().getY(), Epsilon.getInstance().getRadius());
            if (epsilonCollisionPoint != null) {
                impactOnPoint(epsilonCollisionPoint);
                Epsilon.getInstance().setHP(Epsilon.getInstance().getHP() - 6);
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
        Epsilon.getInstance().move();
        if (Epsilon.getInstance().getHP() <= 0) {
            gameOver();
            paused = true;
        }
        for (int i = 0; i < collectables.size(); i++) {
            if (Calculator.distance(Epsilon.getInstance().getX(), Epsilon.getInstance().getY(), collectables.get(i).getX(), collectables.get(i).getY()) <= collectables.get(i).getRadius() + Epsilon.getInstance().getRadius() + 20) {
                Epsilon.getInstance().setXP(Epsilon.getInstance().getXP() + collectables.get(i).getXp());
                AudioPlayer.play(AudioPlayer.COIN);
                collectables.remove(i);
                i--;
            }
        }
        if (Epsilon.getInstance().getX() - Epsilon.getInstance().getRadius() < 0) {
            Epsilon.getInstance().setX(Epsilon.getInstance().getRadius());
            Epsilon.getInstance().setVx(0);
        } else if (Epsilon.getInstance().getX() + Epsilon.getInstance().getRadius() > GamePanel.getInstance().getPanelWidth()) {
            Epsilon.getInstance().setX(GamePanel.getInstance().getPanelWidth() - Epsilon.getInstance().getRadius());
            Epsilon.getInstance().setVx(0);
        }

        if (Epsilon.getInstance().getY() - Epsilon.getInstance().getRadius() < 0) {
            Epsilon.getInstance().setY(Epsilon.getInstance().getRadius());
            Epsilon.getInstance().setVy(0);
        } else if (Epsilon.getInstance().getY() + Epsilon.getInstance().getRadius() > GamePanel.getInstance().getPanelHeight()) {
            Epsilon.getInstance().setY(GamePanel.getInstance().getPanelHeight() - Epsilon.getInstance().getRadius());
            Epsilon.getInstance().setVy(0);
        }
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
        if (Calculator.distance(Epsilon.getInstance().getX(), Epsilon.getInstance().getY(), collisionPoint.getX(), collisionPoint.getY()) <= 70) {
            if (collisionPoint.getX() >= Epsilon.getInstance().getX() && collisionPoint.getY() >= Epsilon.getInstance().getY()) {
                Epsilon.getInstance().setVx(-rate);
                Epsilon.getInstance().setVy(-rate);
                Epsilon.getInstance().setDecU(true);
                Epsilon.getInstance().setDecL(true);
                Epsilon.getInstance().setDecD(true);
                Epsilon.getInstance().setDecR(true);
            }
            if (collisionPoint.getX() <= Epsilon.getInstance().getX() && collisionPoint.getY() <= Epsilon.getInstance().getY()) {
                Epsilon.getInstance().setVx(rate);
                Epsilon.getInstance().setVy(rate);
                Epsilon.getInstance().setDecU(true);
                Epsilon.getInstance().setDecL(true);
                Epsilon.getInstance().setDecD(true);
                Epsilon.getInstance().setDecR(true);
            }
            if (collisionPoint.getX() <= Epsilon.getInstance().getX() && collisionPoint.getY() >= Epsilon.getInstance().getY()) {
                Epsilon.getInstance().setVx(rate);
                Epsilon.getInstance().setVy(-rate);
                Epsilon.getInstance().setDecU(true);
                Epsilon.getInstance().setDecL(true);
                Epsilon.getInstance().setDecD(true);
                Epsilon.getInstance().setDecR(true);
            }
            if (collisionPoint.getX() >= Epsilon.getInstance().getX() && collisionPoint.getY() <= Epsilon.getInstance().getY()) {
                Epsilon.getInstance().setVx(-rate);
                Epsilon.getInstance().setVy(rate);
                Epsilon.getInstance().setDecU(true);
                Epsilon.getInstance().setDecL(true);
                Epsilon.getInstance().setDecD(true);
                Epsilon.getInstance().setDecR(true);
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
        Bullet bullet = new Bullet(Epsilon.getInstance().getX(), Epsilon.getInstance().getY());
        double angle = Math.atan2(y - Epsilon.getInstance().getY(), x - Epsilon.getInstance().getX());
        bullet.setVx(bullet.getConstantVelocity() * Math.cos(angle));
        bullet.setVy(bullet.getConstantVelocity() * Math.sin(angle));
        bullets.add(bullet);
    }

    public void makeNewTrigorath() {
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
        trigoraths.add(new Trigorath(initialPositionX, initialPositionY, initialPositionX + 30, initialPositionY, initialPositionX + 15, initialPositionY - 25));
    }

    public void makeNewSquarantine() {
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
        squarantines.add(new Squarantine(initialPositionX, initialPositionY, initialPositionX + 25, initialPositionY, initialPositionX + 25, initialPositionY + 25, initialPositionX, initialPositionY + 25));

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

    public java.util.Timer getModelTimer() {
        return modelTimer;
    }

    public java.util.Timer getViewTimer() {
        return viewTimer;
    }

}
