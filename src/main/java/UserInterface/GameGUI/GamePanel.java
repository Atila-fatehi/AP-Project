package UserInterface.GameGUI;

import Controller.GameManager;
import Model.Bullet;
import Model.Epsilon;
import Model.Trigorath;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GamePanel extends JPanel {
    private int locationX = 600;
    private int locationY = 200;
    private static final int initialPanelWidth = 700;
    private static final int initialPanelHeight = 700;
    private int screenWidth = 700;
    private int screenHeight = 700;
    private static final int shrinkageRate = 2;
    private int shrinkageCounter = 0;
    private final Epsilon epsilon;
    private ArrayList<Bullet> bullets = new ArrayList<>();
    private ArrayList<Trigorath> trigoraths = new ArrayList<>();
    private final GameManager gameManager;
    private int elapsedTime ;

    public GamePanel(Epsilon epsilon) {
        this.epsilon = epsilon;
        setFocusable(true);
        setLayout(null);
        //add Listeners
        addListeners();
        //Game Manager
        this.gameManager = new GameManager(this);
        Timer time = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                elapsedTime++;
            }
        });
        time.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //draw Enemies
        g.setColor(new Color(0xFFD900));
        for (int i = 0; i < trigoraths.size(); i++) {
            int[] xPoints = {trigoraths.get(i).getX1(), trigoraths.get(i).getX2(), trigoraths.get(i).getX3()};
            int[] yPoints = {trigoraths.get(i).getY1(), trigoraths.get(i).getY2(), trigoraths.get(i).getY3()};
            g.fillPolygon(xPoints,yPoints,3);
            g.setColor(Color.RED);
            g.drawOval(trigoraths.get(i).getInnerCircleX() , trigoraths.get(i).getInnerCircleY() , trigoraths.get(i).getInnerCircleRadius(),trigoraths.get(i).getInnerCircleRadius());
        }

        //draw epsilon
        g.setColor(new Color(0x8A07DC));
        g.fillOval(epsilon.getX(), epsilon.getY(), epsilon.getRadius(), epsilon.getRadius());
        //draw bullets
        for (int i = 0; i < bullets.size(); i++) {
            g.fillOval(bullets.get(i).getX(), bullets.get(i).getY(), bullets.get(i).getRadius(), bullets.get(i).getRadius());
        }
        //draw Strings
        g.setColor(new Color(0x8A26FF));
        g.setFont(new Font("HelveticaNeue-CondensedBlack" , Font.BOLD ,15));
        g.drawString("HP : " + String.valueOf(epsilon.getHP()) , 10, 20);
        g.drawString("XP : " + String.valueOf(epsilon.getXP()) , 100 , 20);
        g.drawString("WAVE : 1"  , 170 , 20);
        g.drawString("ELAPSED TIME : " + String.valueOf(elapsedTime), 250 , 20);
        g.dispose();
    }

    public void addListeners() {
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                gameManager.mouseClicked(e.getX(), e.getY());
            }

            @Override
            public void mousePressed(MouseEvent e) {
                gameManager.mouseClicked(e.getX(), e.getY());
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                switch (keyCode) {
                    case KeyEvent.VK_W:
                        gameManager.setAccU(true);
                        gameManager.setDecU(false);
                        break;
                    case KeyEvent.VK_S:
                        gameManager.setAccD(true);
                        gameManager.setDecD(false);
                        break;
                    case KeyEvent.VK_A:
                        gameManager.setAccL(true);
                        gameManager.setDecL(false);
                        break;
                    case KeyEvent.VK_D:
                        gameManager.setAccR(true);
                        gameManager.setDecR(false);
                        break;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int keyCode = e.getKeyCode();
                switch (keyCode) {
                    case KeyEvent.VK_W:
                        gameManager.setAccU(false);
                        gameManager.setDecU(true);
                        break;
                    case KeyEvent.VK_S:
                        gameManager.setAccD(false);
                        gameManager.setDecD(true);
                        break;
                    case KeyEvent.VK_A:
                        gameManager.setAccL(false);
                        gameManager.setDecL(true);
                        break;
                    case KeyEvent.VK_D:
                        gameManager.setAccR(false);
                        gameManager.setDecR(true);
                        break;
                }
            }
        });
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
    public void setBullets(ArrayList<Bullet> bullets){
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

    public ArrayList<Trigorath> getTrigoraths() {
        return trigoraths;
    }

    public void setTrigoraths(ArrayList<Trigorath> trigoraths) {
        this.trigoraths = trigoraths;
    }
}
