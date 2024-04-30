package UserInterface.GameGUI;

import Controller.GameManager;
import Model.*;
import UserInterface.Frames.MainMenu;
import audio.AudioPlayer;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TimerTask;

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
    private ArrayList<Squarantine> squarantines = new ArrayList<>();
    private ArrayList<Collectable> collectables = new ArrayList<>();
    private final GameManager gameManager;
    private int elapsedTime;
    private final GameFrame gameFrame;
    private final AudioPlayer audioPlayer;
    private int w, a, s, d, shop, ability;

    public GamePanel(GameFrame frame) {
        file();
        this.gameFrame = frame;
        audioPlayer = new AudioPlayer();
        setFocusable(true);
        setLayout(null);
        //add Listeners
        addListeners();
        //Game Manager
        this.gameManager = new GameManager(frame, this);
        //Timers
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (epsilon.getAbility().isAceso()) {
                    epsilon.setHP(epsilon.getHP() + 1);
                }
                elapsedTime++;
                if (elapsedTime == 10) {
                    gameManager.setPastTen(true);
                }
            }

        });
        timer.start();
    }

    public void setEpsilon(Epsilon epsilon) {
        this.epsilon = epsilon;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //draw Enemies
        g.setFont(new Font("HelveticaNeue-CondensedBlack", Font.BOLD, 15));
        for (Trigorath trigorath : trigoraths) {
            g.setColor(new Color(0xFFD900));
            g.fillPolygon(trigorath.getXPoints(), trigorath.getYPoints(), 3);
            g.setColor(Color.BLACK);
            g.drawString(String.valueOf(trigorath.getHP()), (int) trigorath.getPosXHP(), (int) trigorath.getPosYHP());
        }
        for (Squarantine squarantine : squarantines) {
            g.setColor(new Color(0x22FF00));
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
        g.setColor(new Color(0x38C1F1));
        g.fillOval((int) (epsilon.getX() - epsilon.getRadius()), (int) (epsilon.getY() - epsilon.getRadius()), (int) epsilon.getRadius() * 2, (int) epsilon.getRadius() * 2);
        if (epsilon.hasVertex()) {
            g.drawLine((int) (epsilon.getX() - epsilon.getRadius()), (int) epsilon.getY(), (int) epsilon.getVertexX(), (int) epsilon.getVertexY());
            g.drawLine((int) (epsilon.getX() + epsilon.getRadius()), (int) epsilon.getY(), (int) epsilon.getVertexX(), (int) epsilon.getVertexY());
            if (epsilon.getVertexesNum() >= 2) {
                g.drawLine((int) (epsilon.getX() - epsilon.getRadius()), (int) epsilon.getY(), (int) epsilon.getVertexX(), (int) (epsilon.getVertexY() + 2 * epsilon.getRadius() + 14));
                g.drawLine((int) (epsilon.getX() + epsilon.getRadius()), (int) epsilon.getY(), (int) epsilon.getVertexX(), (int) (epsilon.getVertexY() + 2 * epsilon.getRadius() + 14));
            }

        }
        g.setColor(new Color(0x011022));
        g.fillOval((int) (epsilon.getX() - epsilon.getRadius()) + 4, (int) (epsilon.getY() - epsilon.getRadius()) + 4, (int) epsilon.getRadius() * 2 - 8, (int) epsilon.getRadius() * 2 - 8);
        //draw bullets
        g.setColor(new Color(0x38C1F1));
        for (int i = 0; i < bullets.size(); i++) {
            g.fillOval((int) (bullets.get(i).getX() - bullets.get(i).getRadius()), (int) (bullets.get(i).getY() - bullets.get(i).getRadius()), (int) bullets.get(i).getRadius() * 2, (int) bullets.get(i).getRadius() * 2);
        }
        //draw Strings
        g.setColor(new Color(0x8A26FF));
        g.drawString("HP : " + String.valueOf(epsilon.getHP()) +
                "       XP : " + String.valueOf(epsilon.getXP()) +
                "       WAVE : " + gameManager.getCurrentWave() +
                "       ELAPSED TIME : " + String.valueOf(elapsedTime), 10, 20);
        g.dispose();
    }

    public void gameWonAnimation() {

    }

    public void addListeners() {
        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {

            }

            @Override
            public void mouseMoved(MouseEvent e) {
//                double angle = Math.atan2(epsilon.getY() - e.getY(), epsilon.getX() - e.getX());
//                if(epsilon.hasVertex()) {
//                    epsilon.updateVertexPosition(Math.cos(angle), Math.sin(angle));
//                }
            }
        });
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                gameManager.mouseClicked(e.getX(), e.getY());
            }

            @Override
            public void mousePressed(MouseEvent e) {

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
                if (keyCode == w) {
                    epsilon.setAccU(true);
                    epsilon.setDecU(false);
                }
                if (keyCode == s) {
                    epsilon.setAccD(true);
                    epsilon.setDecD(false);
                }
                if (keyCode == a) {
                    epsilon.setAccL(true);
                    epsilon.setDecL(false);
                }
                if (keyCode == d) {
                    epsilon.setAccR(true);
                    epsilon.setDecR(false);
                }
                if (keyCode == shop) {
                    gameManager.setPaused(!gameManager.isPaused());
                    audioPlayer.play(new File(Paths.get("").toAbsolutePath() + "\\src\\main\\java\\audio\\pause.wav"));
                    openShop();
                }
                if (keyCode == ability) {
                    abilityStuff();
                    epsilon.activateAbility();
                }
                if (keyCode == KeyEvent.VK_ESCAPE) {
                    gameFrame.dispose();
                    gameManager.setPaused(true);
                    gameManager.getModelTimer().cancel();
                    gameManager.getViewTimer().cancel();
                    new MainMenu();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int keyCode = e.getKeyCode();
                switch (keyCode) {
                    case KeyEvent.VK_W:
                        epsilon.setAccU(false);
                        epsilon.setDecU(true);
                        break;
                    case KeyEvent.VK_S:
                        epsilon.setAccD(false);
                        epsilon.setDecD(true);
                        break;
                    case KeyEvent.VK_A:
                        epsilon.setAccL(false);
                        epsilon.setDecL(true);
                        break;
                    case KeyEvent.VK_D:
                        epsilon.setAccR(false);
                        epsilon.setDecR(true);
                        break;
                }
            }
        });
    }

    public void abilityStuff() {
        File file = new File(Paths.get("").toAbsolutePath() + "\\src\\main\\java\\dataBase\\abilityCode.txt");
        try {
            Scanner scanner = new Scanner(file);
            int num = Integer.parseInt(scanner.nextLine());
            if (num == 11) {
                epsilon.getAbility().setAres(true);
                epsilon.setXP(epsilon.getXP() - 100);
                gameManager.setDamageRate(7);
            }
            if (num == 21) {
                epsilon.setXP(epsilon.getXP() - 100);
                epsilon.getAbility().setAceso(true);
            }
            if (num == 31) {
                epsilon.setXP(epsilon.getXP() - 100);
                epsilon.getAbility().setProteus(true);
            }
        } catch (Exception e) {

        }
        java.util.Timer timer = new java.util.Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                epsilon.getAbility().setAres(false);
                epsilon.getAbility().setAceso(false);
                epsilon.getAbility().setProteus(false);
                timer.cancel();
            }
        }, 5 * 60 * 1000, 1111);
    }

    public void openShop() {
        JFrame shopFrame = new JFrame();
        shopFrame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    gameManager.setPaused(!gameManager.isPaused());
                    shopFrame.dispose();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        shopFrame.getContentPane().setBackground(new Color(0x011022));
        shopFrame.setTitle("SHOP");
        shopFrame.setFocusable(true);
        shopFrame.requestFocus();
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
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (epsilon.getXP() >= 100) {
                    epsilon.setXP(epsilon.getXP() - 100);
                    gameManager.setPaused(!gameManager.isPaused());
                    shopFrame.dispose();
                    gameManager.impactOnPointWithoutEpsilon(new Point2D.Double(epsilon.getX(), epsilon.getY()));
                }
            }
        });

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
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (epsilon.getXP() >= 75) {
                    epsilon.setXP(epsilon.getXP() - 75);
                    gameManager.setPaused(!gameManager.isPaused());
                    shopFrame.dispose();
                    gameManager.setEmpower(true);
                    java.util.Timer timer = new java.util.Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            gameManager.setEmpower(false);
                            timer.cancel();
                        }
                    }, 10000, 100);
                } else {

                }
            }
        });

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
        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (epsilon.getXP() >= 50) {
                    epsilon.setXP(epsilon.getXP() - 50);
                    epsilon.setHP(epsilon.getHP() + 10);
                    gameManager.setPaused(!gameManager.isPaused());
                    shopFrame.dispose();
                } else {

                }
            }
        });

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

    public void shrinkToZero() {
        setLocation(locationX, locationY);
        setSize(screenWidth, screenHeight);
        screenWidth -= 6;
        locationX += 3;
        screenHeight -= 6;
        locationY += 3;
    }

    public void file() {
        File file = new File(Paths.get("").toAbsolutePath() + "\\src\\main\\java\\dataBase\\Keys.txt");
        try {
            Scanner scanner = new Scanner(file);
            w = Integer.parseInt(scanner.nextLine());
            a = Integer.parseInt(scanner.nextLine());
            s = Integer.parseInt(scanner.nextLine());
            d = Integer.parseInt(scanner.nextLine());
            shop = Integer.parseInt(scanner.nextLine());
            ability = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {

        }
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
}
