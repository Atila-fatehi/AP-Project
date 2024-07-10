package view.gameGUI;

import controller.util.Constants;
import controller.GameManager;
import controller.KeyController;
import controller.MouseController;
import model.objectsModel.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class GamePanel extends JPanel {
    private static GamePanel instance;

    public static GamePanel getInstance() {
        if (instance == null) {
            instance = new GamePanel();
        }
        return instance;
    }

    private int locationX = 600;
    private int locationY = 200;
    private int screenWidth = 700;
    private int screenHeight = 700;
    private static final int shrinkageRate = 2;
    private int shrinkageCounter = 0;
    private Epsilon epsilon;
    private ArrayList<Bullet> bullets = new ArrayList<>();
    private ArrayList<Trigorath> trigoraths = new ArrayList<>();
    private ArrayList<Squarantine> squarantines = new ArrayList<>();
    private ArrayList<Collectable> collectables = new ArrayList<>();
    private int elapsedTime;

    public GamePanel() {
        setFocusable(true);
        setLayout(null);
        KeyController.initiateKeyCodes();
        addMouseListener(new MouseController());
        addKeyListener(new KeyController());

        abilityStuff();
    }


    public void startTimer(){
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (epsilon.getAbility().isAceso() && epsilon.getAbility().isActive()) {
                    epsilon.setHP(epsilon.getHP() + 1);
                }
                elapsedTime++;
                if (elapsedTime == 10) {
                    GameManager.getInstance().setPastTen(true);
                }
            }

        });
        timer.start();
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //draw Enemies
        g.setFont(Constants.BOLD_15);
        for (Trigorath trigorath : trigoraths) {
            g.setColor(Constants.TRI_YELLOW);
            g.fillPolygon(trigorath.getXPoints(), trigorath.getYPoints(), 3);
            g.setColor(Color.BLACK);
            g.drawString(String.valueOf(trigorath.getHP()), (int) trigorath.getPosXHP(), (int) trigorath.getPosYHP());
        }
        for (Squarantine squarantine : squarantines) {
            g.setColor(Constants.SQUA_GREEN);
            g.fillPolygon(squarantine.getXPoints(), squarantine.getYPoints(), 4);
            g.setColor(Color.BLACK);
            g.drawString(String.valueOf(squarantine.getHP()), (int) squarantine.getPosXHP(), (int) squarantine.getPosYHP());
        }
        //draw collectable
        for (Collectable collectable : collectables) {
            g.setColor(collectable.getColor());
            g.fillOval((int) collectable.getX(), (int) collectable.getY(), (int) collectable.getRadius() * 2, (int) collectable.getRadius() * 2);
        }
        //draw epsilon
        g.setColor(Constants.EPSILON_COLOR);
        g.fillOval((int) (epsilon.getX() - epsilon.getRadius()), (int) (epsilon.getY() - epsilon.getRadius()), (int) epsilon.getRadius() * 2, (int) epsilon.getRadius() * 2);
        if (epsilon.hasVertex()) {
            g.drawLine((int) (epsilon.getX() - epsilon.getRadius()), (int) epsilon.getY(), (int) epsilon.getVertexX(), (int) epsilon.getVertexY());
            g.drawLine((int) (epsilon.getX() + epsilon.getRadius()), (int) epsilon.getY(), (int) epsilon.getVertexX(), (int) epsilon.getVertexY());
            if (epsilon.getVertexesNum() >= 2) {
                g.drawLine((int) (epsilon.getX() - epsilon.getRadius()), (int) epsilon.getY(), (int) epsilon.getVertexX(), (int) (epsilon.getVertexY() + 2 * epsilon.getRadius() + 14));
                g.drawLine((int) (epsilon.getX() + epsilon.getRadius()), (int) epsilon.getY(), (int) epsilon.getVertexX(), (int) (epsilon.getVertexY() + 2 * epsilon.getRadius() + 14));
            }
            if (epsilon.getVertexesNum() >= 3) {
                g.drawLine((int) epsilon.getX(), (int) (epsilon.getY() - epsilon.getRadius()), (int) (epsilon.getX() + epsilon.getRadius() + 7), (int) epsilon.getY());
                g.drawLine((int) epsilon.getX(), (int) (epsilon.getY() + epsilon.getRadius()), (int) (epsilon.getX() + epsilon.getRadius() + 7), (int) epsilon.getY());
            }
            if (epsilon.getVertexesNum() >= 4) {
                g.drawLine((int) epsilon.getX(), (int) (epsilon.getY() - epsilon.getRadius()), (int) (epsilon.getX() - epsilon.getRadius() - 7), (int) epsilon.getY());
                g.drawLine((int) epsilon.getX(), (int) (epsilon.getY() + epsilon.getRadius()), (int) (epsilon.getX() - epsilon.getRadius() - 7), (int) epsilon.getY());
            }
        }
        g.setColor(Constants.DARK_BLUE);
        g.fillOval((int) (epsilon.getX() - epsilon.getRadius()) + 4, (int) (epsilon.getY() - epsilon.getRadius()) + 4, (int) epsilon.getRadius() * 2 - 8, (int) epsilon.getRadius() * 2 - 8);
        //draw bullets
        g.setColor(Constants.EPSILON_COLOR);
        for (int i = 0; i < bullets.size(); i++) {
            g.fillOval((int) (bullets.get(i).getX() - bullets.get(i).getRadius()), (int) (bullets.get(i).getY() - bullets.get(i).getRadius()), (int) bullets.get(i).getRadius() * 2, (int) bullets.get(i).getRadius() * 2);
        }
        //draw Strings
        g.setColor(Constants.STRING_COLOR);
        g.drawString("HP : " + epsilon.getHP() +
                  "       XP : " + epsilon.getXP() +
                  "       WAVE : " + GameManager.getInstance().getCurrentWave() +
                  "       ELAPSED TIME : " + elapsedTime, 10, 20);
        g.dispose();
    }

    public void abilityStuff() {
        File file = new File(Paths.get("").toAbsolutePath() + "\\src\\main\\java\\dataBase\\abilityCode.txt");
        try {
            Scanner scanner = new Scanner(file);
            int num = Integer.parseInt(scanner.nextLine());
            if (num == 11) {
                epsilon.getAbility().setAres(true);
//                epsilon.setXP(epsilon.getXP() - 100);
//                gameManager.setDamageRate(7);
            }
            if (num == 21) {
//                epsilon.setXP(epsilon.getXP() - 100);
                epsilon.getAbility().setAceso(true);
            }
            if (num == 31) {
                epsilon.getAbility().setProteus(true);
            }
        } catch (Exception e) {

        }
//        java.util.Timer timer = new java.util.Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                epsilon.getAbility().setAres(false);
//                epsilon.getAbility().setAceso(false);
//                epsilon.getAbility().setProteus(false);
//                timer.cancel();
//            }
//        }, 5 * 60 * 1000, 1111);
    }

    public void shrink() {
        if (shrinkageCounter == shrinkageRate) {
            setLocation(locationX, locationY);
            setSize(screenWidth, screenHeight);
            if (screenWidth >= 500) {
                screenWidth -= 2;
                locationX += 1;
                epsilon.setX(epsilon.getX() - 1);
            }
            if (screenHeight >= 500) {
                screenHeight -= 2;
                locationY += 1;
                epsilon.setY(epsilon.getY() - 1);
            }
            shrinkageCounter = 0;
        }
        shrinkageCounter++;
    }

    public void shrinkToZero() {
        setLocation(locationX, locationY);
        setSize(screenWidth, screenHeight);
        screenWidth -= 6;
        locationX += 3;
        screenHeight -= 6;
        locationY += 3;
    }

    //GETTERS AND SETTERS
    public Epsilon getEpsilon() {
        return epsilon;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public void setBullets(ArrayList<Bullet> bullets) {
        this.bullets = bullets;
    }

    public int getLocationX() {
        return locationX;
    }

    public void setLocationX(int locationX) {
        this.locationX = locationX;
    }

    public int getLocationY() {
        return locationY;
    }

    public void setLocationY(int locationY) {
        this.locationY = locationY;
    }

    public void setTrigoraths(ArrayList<Trigorath> trigoraths) {
        this.trigoraths = trigoraths;
    }

    public void setSquarantines(ArrayList<Squarantine> squarantines) {
        this.squarantines = squarantines;
    }

    public void setCollectables(ArrayList<Collectable> collectables) {
        this.collectables = collectables;
    }

    public void setEpsilon(Epsilon epsilon) {
        this.epsilon = epsilon;
    }
}
