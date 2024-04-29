package Controller;

import Model.*;
import UserInterface.Frames.MainMenu;
import UserInterface.GameGUI.GameFrame;
import UserInterface.GameGUI.GamePanel;
import util.cal;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class GameManager {
    private final GamePanel gamePanel;
    private final GameFrame gameFrame;
    private final Epsilon epsilon;
    private boolean paused;
    private static final int expandRate = 15;
    private final ArrayList<Bullet> bullets = new ArrayList<>();
    private final ArrayList<Trigorath> trigoraths = new ArrayList<>();
    private final ArrayList<Squarantine> squarantines = new ArrayList<>();
    private final ArrayList<Collectable> collectables = new ArrayList<>();
    private final cal cal;
    private int wave;
    private int difficulty;
    private int sensitivity;
    private int damageRate;
    private final java.util.Timer modelTimer;
    private final java.util.Timer viewTimer;
    private boolean gameOver;
    private boolean gameWon;
    private boolean empower;

    public GameManager(GameFrame gameFrame, GamePanel gamePanel) {
        this.gameFrame = gameFrame;
        this.gamePanel = gamePanel;
        epsilon = new Epsilon(350, 350, 13);
        gamePanel.setEpsilon(epsilon);
        damageRate = 5;
        wave = 0;
        cal = new cal();
        File file = new File(Paths.get("").toAbsolutePath() + "\\src\\main\\java\\dataBase\\settings.txt");
        try {
            Scanner scanner = new Scanner(file);
            sensitivity = Integer.parseInt(scanner.nextLine());
            difficulty = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {

        }
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
                gamePanel.shrink();
            }
            gamePanel.setBullets(bullets);
            gamePanel.setTrigoraths(trigoraths);
            gamePanel.setSquarantines(squarantines);
            gamePanel.setCollectables(collectables);
        }
        gamePanel.repaint();
    }

    public void updateModel() {
        //generate enemies
        if (trigoraths.isEmpty() && squarantines.isEmpty()) {
            wave++;
            if (wave == 4) {
                gameWon = true;
                gameWon();
            } else {
                generateWave(wave);
            }
        }
        //Bullet stuff
        //Tri collision
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).move();
            for (Trigorath trigorath : trigoraths) {
                Point2D trigorathCollisionPoint = bullets.get(i).onTrigorathCollision(trigorath.getX1(), trigorath.getX2(), trigorath.getX3(), trigorath.getY1(), trigorath.getY2(), trigorath.getY3());
                if (trigorathCollisionPoint != null) {
                    trigorath.setHP(trigorath.getHP() - damageRate);
                    impactOnPoint(trigorathCollisionPoint);
                    bullets.remove(i);
                    i--;
                    break;
                }
            }
        }
        //Squ collision
        for (int i = 0; i < bullets.size(); i++) {
            for (Squarantine squarantine : squarantines) {
                Point2D squarantineCollisionPoint = bullets.get(i).onSquarantineCollision(squarantine.getX1(), squarantine.getX2(), squarantine.getX3(), squarantine.getX4(), squarantine.getY1(), squarantine.getY2(), squarantine.getY3(), squarantine.getY4());
                if (squarantineCollisionPoint != null) {
                    squarantine.setHP(squarantine.getHP() - damageRate);
                    impactOnPoint(squarantineCollisionPoint);
                    bullets.remove(i);
                    i--;
                    break;
                }
            }
        }
        //wall collision
        for (int i = 0; i < bullets.size(); i++) {
            int wallCollisionNum = bullets.get(i).onWallCollision(gamePanel.getScreenWidth(), gamePanel.getScreenHeight());
            if (wallCollisionNum == 1) {
                gamePanel.setLocation(gamePanel.getLocationX() - expandRate, gamePanel.getLocationY());
                gamePanel.setSize(gamePanel.getScreenWidth() + expandRate, gamePanel.getScreenHeight());
                gamePanel.setScreenWidth(gamePanel.getScreenWidth() + expandRate);
                gamePanel.setLocationX(gamePanel.getLocationX() - expandRate);
                epsilon.setX(epsilon.getX() + expandRate);
                for (Trigorath trigorath : trigoraths) {
                    trigorath.shiftX(expandRate);
                }
                for (Squarantine squarantine : squarantines) {
                    squarantine.shiftX(expandRate);
                }
                for (Collectable collectable : collectables) {
                    collectable.shiftX(expandRate);
                }
                bullets.remove(i);
                i--;
            } else if (wallCollisionNum == 2) {
                gamePanel.setLocation(gamePanel.getLocationX(), gamePanel.getLocationY() - expandRate);
                gamePanel.setSize(gamePanel.getScreenWidth(), gamePanel.getScreenHeight() + expandRate);
                gamePanel.setScreenHeight(gamePanel.getScreenHeight() + expandRate);
                gamePanel.setLocationY(gamePanel.getLocationY() - expandRate);
                epsilon.setY(epsilon.getY() + expandRate);
                for (Trigorath trigorath : trigoraths) {
                    trigorath.shiftY(expandRate);
                }
                for (Squarantine squarantine : squarantines) {
                    squarantine.shiftY(expandRate);
                }
                for (Collectable collectable : collectables) {
                    collectable.shiftY(expandRate);
                }
                bullets.remove(i);
                i--;
            } else if (wallCollisionNum == 3) {
                gamePanel.setSize(gamePanel.getScreenWidth() + expandRate, gamePanel.getScreenHeight());
                gamePanel.setScreenWidth(gamePanel.getScreenWidth() + expandRate);
                bullets.remove(i);
                i--;
            } else if (wallCollisionNum == 4) {
                gamePanel.setSize(gamePanel.getScreenWidth(), gamePanel.getScreenHeight() + expandRate);
                gamePanel.setScreenHeight(gamePanel.getScreenHeight() + expandRate);
                bullets.remove(i);
                i--;
            }
        }

        //Tri stuff
        for (int i = 0; i < trigoraths.size(); i++) {
            trigoraths.get(i).calculateMovingDirection(epsilon.getX(), epsilon.getY());
            trigoraths.get(i).move();
            if(epsilon.hasVertex()){
                if(trigoraths.get(i).onPointCollision(epsilon.getXPoints().getFirst() , epsilon.getYPoints().getFirst()) != null){
                    trigoraths.get(i).setHP(trigoraths.get(i).getHP() - 10);
                    impactOnPoint(new Point2D.Double(epsilon.getXPoints().getFirst() , epsilon.getYPoints().getFirst()));
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
            for (Squarantine squarantine : squarantines) {
                Point2D squarantineCollisionPoint = trigoraths.get(i).onSquarantineCollision(squarantine.getX1(), squarantine.getX2(), squarantine.getX3(), squarantine.getX4(), squarantine.getY1(), squarantine.getY2(), squarantine.getY3(), squarantine.getY4());
                if (squarantineCollisionPoint != null) {
                    impactOnPoint(squarantineCollisionPoint);
                }
            }
            if (trigoraths.get(i).getHP() <= 0) {
                collectables.add(new Collectable(trigoraths.get(i).getCenterOfGravity().getX(), trigoraths.get(i).getCenterOfGravity().getY(), new Color(0xFFD900)));
                trigoraths.remove(i);
                i--;
            }
        }

        //squarantine stuff
        for (int i = 0; i < squarantines.size(); i++) {
            squarantines.get(i).calculateMovingDirection(epsilon.getX(), epsilon.getY());
            squarantines.get(i).move();
            if(epsilon.hasVertex()){
                if(squarantines.get(i).onPointCollision(epsilon.getXPoints().getFirst() , epsilon.getYPoints().getFirst()) != null){
                    squarantines.get(i).setHP(squarantines.get(i).getHP() - 10);
                    impactOnPoint(new Point2D.Double(epsilon.getXPoints().getFirst() , epsilon.getYPoints().getFirst()));
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
                collectables.add(new Collectable(squarantines.get(i).getCenterOfGravity().getX() + 10, squarantines.get(i).getCenterOfGravity().getY() + 10, new Color(0x22FF00)));
                squarantines.get(i).getTimer().cancel();
                squarantines.remove(i);
                i--;
            }
        }

        //epsilon stuff
        epsilon.move();
        if (epsilon.getHP() <= -1111110) {
            gameOver();
            paused = true;
        }
        for (int i = 0; i < collectables.size(); i++) {
            if (cal.distance(epsilon.getX(), epsilon.getY(), collectables.get(i).getX(), collectables.get(i).getY()) <= collectables.get(i).getRadius() + epsilon.getRadius() + 20) {
                epsilon.setXP(epsilon.getXP() + collectables.get(i).getXp());
                collectables.remove(i);
                i--;
            }
        }
        if (epsilon.getX() - epsilon.getRadius() < 0) {
            epsilon.setX(epsilon.getRadius());
            epsilon.setVx(0);
        } else if (epsilon.getX() + epsilon.getRadius() > gamePanel.getScreenWidth()) {
            epsilon.setX(gamePanel.getScreenWidth() - epsilon.getRadius());
            epsilon.setVx(0);
        }

        if (epsilon.getY() - epsilon.getRadius() < 0) {
            epsilon.setY(epsilon.getRadius());
            epsilon.setVy(0);
        } else if (epsilon.getY() + epsilon.getRadius() > gamePanel.getScreenHeight()) {
            epsilon.setY(gamePanel.getScreenHeight() - epsilon.getRadius());
            epsilon.setVy(0);
        }
    }

    public void gameWon() {
        File file = new File(Paths.get("").toAbsolutePath() + "/src/main/java/dataBase/XP.txt");
        try {
            PrintWriter printWriter = new PrintWriter(file);
            printWriter.println(epsilon.getXP());
            printWriter.flush();
            printWriter.close();
        } catch (Exception e) {

        }
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
                gamePanel.shrinkToZero();
                if (gamePanel.getScreenHeight() <= -10 || gamePanel.getScreenWidth() <= -10) {
                    timer2.cancel();
                    String[] responses = {"Main Menu"};
                    if (JOptionPane.showOptionDialog(null, "Your XP = " + epsilon.getXP(), "Game Over", JOptionPane.INFORMATION_MESSAGE, JOptionPane.INFORMATION_MESSAGE, null, responses, 0) != -2) {
                        gameFrame.dispose();
                        epsilon.setXP(0);
                        epsilon.setHP(100);
                        new MainMenu();
                    }
                }
            }
        }, 5000, 50);
    }

    public void gameOver() {
        gameOver = true;
        epsilon.setHP(0);
        String[] responses = {"Main Menu"};
        if (JOptionPane.showOptionDialog(null, "Your XP = " + epsilon.getXP(), "Game Over", JOptionPane.INFORMATION_MESSAGE, JOptionPane.INFORMATION_MESSAGE, null, responses, 0) != -2) {
            gameFrame.dispose();
            epsilon.setXP(0);
            epsilon.setHP(100);
            new MainMenu();
        }
    }

    public void impactOnPoint(Point2D collisionPoint) {
        double rate = 12;
        for (Trigorath trigorath : trigoraths) {
            if (cal.distance(trigorath.getCenterOfGravity().getX(), trigorath.getCenterOfGravity().getY(), collisionPoint.getX(), collisionPoint.getY()) <= 70) {
                double angle = Math.atan2(collisionPoint.getY() - trigorath.getCenterOfGravity().getY(), collisionPoint.getX() - trigorath.getCenterOfGravity().getX());
                trigorath.setVx(-(rate - 3) * Math.cos(angle));
                trigorath.setVy(-(rate - 3) * Math.sin(angle));
            }
        }
        for (Squarantine squarantine : squarantines) {
            if (cal.distance(squarantine.getCenterOfGravity().getX(), squarantine.getCenterOfGravity().getY(), collisionPoint.getX(), collisionPoint.getY()) <= 70) {
                double angle = Math.atan2(collisionPoint.getY() - squarantine.getCenterOfGravity().getY(), collisionPoint.getX() - squarantine.getCenterOfGravity().getX());
                squarantine.setVx(-(rate - 3) * Math.cos(angle));
                squarantine.setVy(-(rate - 3) * Math.sin(angle));
            }
        }
        if (cal.distance(epsilon.getX(), epsilon.getY(), collisionPoint.getX(), collisionPoint.getY()) <= 70) {
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
        for (Trigorath trigorath : trigoraths) {
            if (cal.distance(trigorath.getCenterOfGravity().getX(), trigorath.getCenterOfGravity().getY(), collisionPoint.getX(), collisionPoint.getY()) <= 170) {
                double angle = Math.atan2(collisionPoint.getY() - trigorath.getCenterOfGravity().getY(), collisionPoint.getX() - trigorath.getCenterOfGravity().getX());
                trigorath.setVx(-(rate - 3) * Math.cos(angle));
                trigorath.setVy(-(rate - 3) * Math.sin(angle));
            }
        }
        for (Squarantine squarantine : squarantines) {
            if (cal.distance(squarantine.getCenterOfGravity().getX(), squarantine.getCenterOfGravity().getY(), collisionPoint.getX(), collisionPoint.getY()) <= 170) {
                double angle = Math.atan2(collisionPoint.getY() - squarantine.getCenterOfGravity().getY(), collisionPoint.getX() - squarantine.getCenterOfGravity().getX());
                squarantine.setVx(-(rate - 3) * Math.cos(angle));
                squarantine.setVy(-(rate - 3) * Math.sin(angle));
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
        int initialPositionX = random.nextInt(gamePanel.getScreenWidth());
        int initialPositionY = random.nextInt(gamePanel.getScreenHeight());
        if (random.nextBoolean()) {
            initialPositionX += gamePanel.getScreenWidth();
        } else {
            initialPositionX -= gamePanel.getScreenWidth();
        }
        if (random.nextBoolean()) {
            initialPositionY += gamePanel.getScreenHeight();
        } else {
            initialPositionY -= gamePanel.getScreenHeight();
        }
        trigoraths.add(new Trigorath(initialPositionX, initialPositionY, initialPositionX + 30, initialPositionY, initialPositionX + 15, initialPositionY - 25));
    }

    public void makeNewSquarantine() {
        Random random = new Random();
        int initialPositionX = random.nextInt(gamePanel.getScreenWidth());
        int initialPositionY = random.nextInt(gamePanel.getScreenHeight());
        if (random.nextBoolean()) {
            initialPositionX += gamePanel.getScreenWidth();
        } else {
            initialPositionX -= gamePanel.getScreenWidth();
        }
        if (random.nextBoolean()) {
            initialPositionY += gamePanel.getScreenHeight();
        } else {
            initialPositionY -= gamePanel.getScreenHeight();
        }
        squarantines.add(new Squarantine(initialPositionX, initialPositionY, initialPositionX + 25, initialPositionY, initialPositionX + 25, initialPositionY + 25, initialPositionX, initialPositionY + 25));

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
}
