package UserInterface.GameGUI;

import Controller.GameManager;
import Model.Bullet;
import Model.Epsilon;
import Model.Trigorath;

import javax.swing.*;
import javax.swing.border.Border;
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
    private Epsilon epsilon;
    private ArrayList<Bullet> bullets = new ArrayList<>();
    private ArrayList<Trigorath> trigoraths = new ArrayList<>();
    private final GameManager gameManager;
    private int elapsedTime;

    public GamePanel() {
        setFocusable(true);
        setLayout(null);
        //add Listeners
        addListeners();
        //Game Manager
        this.gameManager = new GameManager(this);
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                elapsedTime++;
            }
        });
        timer.start();
    }
    public void setEpsilon(Epsilon epsilon){
        this.epsilon = epsilon;
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //draw Enemies
        g.setColor(new Color(0xFFD900));
        for (int i = 0; i < trigoraths.size(); i++) {
            int[] xPoints = {(int) trigoraths.get(i).getX1(), (int) trigoraths.get(i).getX2(), (int) trigoraths.get(i).getX3()};
            int[] yPoints = {(int) trigoraths.get(i).getY1(), (int) trigoraths.get(i).getY2(), (int) trigoraths.get(i).getY3()};
            g.fillPolygon(xPoints, yPoints, 3);
        }

        //draw epsilon
        g.setColor(new Color(0x8A07DC));
        g.fillOval((int) epsilon.getX(), (int) epsilon.getY(), (int) epsilon.getRadius(), (int) epsilon.getRadius());
        //draw bullets
        for (int i = 0; i < bullets.size(); i++) {
            g.fillOval((int) bullets.get(i).getX(), (int) bullets.get(i).getY(), (int) bullets.get(i).getRadius(), (int) bullets.get(i).getRadius());
        }
        //draw Strings
        g.setColor(new Color(0x8A26FF));
        g.setFont(new Font("HelveticaNeue-CondensedBlack", Font.BOLD, 15));
        g.drawString("HP : " + String.valueOf(epsilon.getHP()), 10, 20);
        g.drawString("XP : " + String.valueOf(epsilon.getXP()), 100, 20);
        g.drawString("WAVE : 1", 170, 20);
        g.drawString("ELAPSED TIME : " + String.valueOf(elapsedTime), 250, 20);
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
                //gameManager.mouseClicked(e.getX(), e.getY());
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
                    case KeyEvent.VK_SPACE:
                        gameManager.setPaused(!gameManager.isPaused());
                        openShop();
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

    public void openShop() {
        JFrame shopFrame = new JFrame();
        shopFrame.getContentPane().setBackground(new Color(0x000000));
        shopFrame.setTitle("SHOP");
        Border border = BorderFactory.createLineBorder(Color.WHITE, 2);
        shopFrame.getRootPane().setBorder(border);
        shopFrame.setUndecorated(true);
        shopFrame.setSize(700, 700);
        shopFrame.setLocationRelativeTo(null);
        shopFrame.setLayout(null);
        shopFrame.setVisible(true);
        shopFrame.setResizable(false);

        JLabel label = new JLabel("XP : " + String.valueOf(epsilon.getXP()));
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setBounds(200, 100, 300, 100);
        label.setFont(new Font("HelveticaNeue-CondensedBlack", Font.BOLD, 25));
        label.setForeground(new Color(0xFB8B24));


        JLabel label3 = new JLabel("O' Hephaestus، Banish");
        label3.setHorizontalAlignment(JLabel.CENTER);
        label3.setBounds(100, 200, 300, 100);
        label3.setFont(new Font("HelveticaNeue-CondensedBlack", Font.BOLD, 25));
        label3.setForeground(new Color(0xFB8B24));
        JButton button2 = new JButton("100 XP");
        button2.setBounds(400, 225, 200, 50);
        button2.setFocusable(false);
        button2.setHorizontalAlignment(JButton.CENTER);
        button2.setHorizontalTextPosition(JButton.CENTER);
        button2.setBackground(new Color(0x9A1A03));
        button2.setForeground(new Color(0xFB8B24));
        button2.setFont(new Font("HelveticaNeue-CondensedBlack", Font.BOLD, 25));

        JLabel label1 = new JLabel("O’ Athena، Empower");
        label1.setHorizontalAlignment(JLabel.CENTER);
        label1.setBounds(100, 270, 300, 100);
        label1.setFont(new Font("HelveticaNeue-CondensedBlack", Font.BOLD, 25));
        label1.setForeground(new Color(0xFB8B24));
        JButton button3 = new JButton("75 XP");
        button3.setBounds(400, 300, 200, 50);
        button3.setFocusable(false);
        button3.setHorizontalAlignment(JButton.CENTER);
        button3.setHorizontalTextPosition(JButton.CENTER);
        button3.setBackground(new Color(0x9A1A03));
        button3.setForeground(new Color(0xFB8B24));
        button3.setFont(new Font("HelveticaNeue-CondensedBlack", Font.BOLD, 25));

        JLabel label2 = new JLabel("O' Apollo Heal");
        label2.setHorizontalAlignment(JLabel.CENTER);
        label2.setBounds(100, 340, 300, 100);
        label2.setFont(new Font("HelveticaNeue-CondensedBlack", Font.BOLD, 25));
        label2.setForeground(new Color(0xFB8B24));
        JButton button4 = new JButton("50 XP");
        button4.setBounds(400, 375, 200, 50);
        button4.setFocusable(false);
        button4.setHorizontalAlignment(JButton.CENTER);
        button4.setHorizontalTextPosition(JButton.CENTER);
        button4.setBackground(new Color(0x9A1A03));
        button4.setForeground(new Color(0xFB8B24));
        button4.setFont(new Font("HelveticaNeue-CondensedBlack", Font.BOLD, 25));

        JButton button1 = new JButton("Done");
        button1.setBounds(200, 550, 300, 50);
        button1.setFocusable(false);
        button1.setHorizontalAlignment(JButton.CENTER);
        button1.setHorizontalTextPosition(JButton.CENTER);
        button1.setBackground(new Color(0x9A1A03));
        button1.setForeground(new Color(0xFB8B24));
        button1.setFont(new Font("HelveticaNeue-CondensedBlack", Font.BOLD, 25));

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameManager.setPaused(!gameManager.isPaused());
                shopFrame.dispose();
            }
        });
        shopFrame.add(label);
        shopFrame.add(label1);
        shopFrame.add(label2);
        shopFrame.add(label3);
        shopFrame.add(button1);
        shopFrame.add(button2);
        shopFrame.add(button3);
        shopFrame.add(button4);
        shopFrame.setVisible(true);
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

    public ArrayList<Trigorath> getTrigoraths() {
        return trigoraths;
    }

    public void setTrigoraths(ArrayList<Trigorath> trigoraths) {
        this.trigoraths = trigoraths;
    }
}
