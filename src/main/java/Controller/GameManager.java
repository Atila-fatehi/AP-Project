package Controller;

import Model.Bullet;
import Model.Epsilon;
import Model.Trigorath;
import UserInterface.GameGUI.GameFrame;
import UserInterface.GameGUI.GamePanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class GameManager {
    private final GamePanel gamePanel;
    private final Epsilon epsilon;
    private boolean accU, accD, accR, accL;
    private boolean decU, decD, decR, decL;
    private static final int MAX_VELOCITY = 11;
    private static final int ACCELERATION = 1;
    private static final int expandRate = 15;
    private final ArrayList<Bullet> bullets = new ArrayList<>();
    private final ArrayList<Trigorath> trigoraths = new ArrayList<>();

    public GameManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.epsilon = gamePanel.getEpsilon();
        trigoraths.add(new Trigorath(100,100 + 20,100 + 10,100,100,100 - 18,6,14,15));
        new Timer((int) (double) TimeUnit.SECONDS.toMillis(1) / 60/*GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0].getDisplayMode().getRefreshRate()*/, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateView();
            }
        }) {{
            setCoalesce(true);
        }}.start();
        new Timer((int) (double) TimeUnit.SECONDS.toMillis(1) / 100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateModel();
            }
        }) {{
            setCoalesce(true);
        }}.start();

    }

    public void updateView() {
        gamePanel.shrink();
        gamePanel.setBullets(bullets);
        gamePanel.setTrigoraths(trigoraths);
        gamePanel.repaint();
    }

    public void updateModel() {
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).move();
            if (bullets.get(i).onWallCollision(gamePanel.getScreenWidth(), gamePanel.getScreenHeight()) == 1) {
                gamePanel.setLocation(gamePanel.getLocationX() - expandRate, gamePanel.getLocationY());
                gamePanel.setSize(gamePanel.getScreenWidth() + expandRate, gamePanel.getScreenHeight());
                gamePanel.setScreenWidth(gamePanel.getScreenWidth() + expandRate);
                gamePanel.setLocationX(gamePanel.getLocationX() - expandRate);
                epsilon.setX(epsilon.getX() + expandRate);
                bullets.remove(i);
                i--;
            }else
            if (bullets.get(i).onWallCollision(gamePanel.getScreenWidth(), gamePanel.getScreenHeight()) == 2) {
                gamePanel.setLocation(gamePanel.getLocationX(), gamePanel.getLocationY() - expandRate);
                gamePanel.setSize(gamePanel.getScreenWidth(), gamePanel.getScreenHeight() + expandRate);
                gamePanel.setScreenHeight(gamePanel.getScreenHeight() + expandRate);
                gamePanel.setLocationY(gamePanel.getLocationY() - expandRate);
                epsilon.setY(epsilon.getY() + expandRate);
                bullets.remove(i);
                i--;
            }else
            if (bullets.get(i).onWallCollision(gamePanel.getScreenWidth(), gamePanel.getScreenHeight()) == 3) {
                gamePanel.setSize(gamePanel.getScreenWidth() + expandRate, gamePanel.getScreenHeight());
                gamePanel.setScreenWidth(gamePanel.getScreenWidth() + expandRate);
                bullets.remove(i);
                i--;
            }else
            if (bullets.get(i).onWallCollision(gamePanel.getScreenWidth(), gamePanel.getScreenHeight()) == 4) {
                gamePanel.setSize(gamePanel.getScreenWidth(), gamePanel.getScreenHeight() + expandRate);
                gamePanel.setScreenHeight(gamePanel.getScreenHeight() + expandRate);
                bullets.remove(i);
                i--;
            }
        }
        epsilon.move();
        for (int i = 0; i < trigoraths.size(); i++) {
            trigoraths.get(i).calculateMovingDirection(epsilon.getX(),epsilon.getY());
            trigoraths.get(i).move();
        }
        if (accU) {
            if (epsilon.getVy() >= -MAX_VELOCITY) {
                epsilon.setVy(epsilon.getVy() - ACCELERATION);
            }
        }
        if (accD) {
            if (epsilon.getVy() <= MAX_VELOCITY) {
                epsilon.setVy(epsilon.getVy() + ACCELERATION);
            }
        }
        if (accL) {
            if (epsilon.getVx() >= -MAX_VELOCITY) {
                epsilon.setVx(epsilon.getVx() - ACCELERATION);
            }
        }
        if (accR) {
            if (epsilon.getVx() <= MAX_VELOCITY) {
                epsilon.setVx(epsilon.getVx() + ACCELERATION);
            }
        }
        if (decU) {
            if (epsilon.getVy() < 0) {
                epsilon.setVy(epsilon.getVy() + ACCELERATION);
            }
        }
        if (decD) {
            if (epsilon.getVy() > 0) {
                epsilon.setVy(epsilon.getVy() - ACCELERATION);
            }
        }
        if (decL) {
            if (epsilon.getVx() < 0) {
                epsilon.setVx(epsilon.getVx() + ACCELERATION);
            }
        }
        if (decR) {
            if (epsilon.getVx() > 0) {
                epsilon.setVx(epsilon.getVx() - ACCELERATION);
            }
        }
        if (epsilon.getX() < 0) {
            epsilon.setX(0);
            epsilon.setVx(0);
        } else if (epsilon.getX() + epsilon.getRadius() > gamePanel.getScreenWidth()) {
            epsilon.setX(gamePanel.getScreenWidth() - epsilon.getRadius());
            epsilon.setVx(0);
        }

        if (epsilon.getY() < 0) {
            epsilon.setY(0);
            epsilon.setVy(0);
        } else if (epsilon.getY() + epsilon.getRadius() > gamePanel.getScreenHeight()) {
            epsilon.setY(gamePanel.getScreenHeight() - epsilon.getRadius());
            epsilon.setVy(0);
        }

    }

    public void mouseClicked(int x, int y) {
        Bullet bullet = new Bullet(epsilon.getX() + epsilon.getRadius() / 2, epsilon.getY() + epsilon.getRadius() / 2);
        double angle = Math.atan2(y - epsilon.getY() - epsilon.getRadius() / 2, x - epsilon.getX() - epsilon.getRadius() / 2);
        bullet.setVx(((int) Math.round(bullet.getConstantVelocity() * Math.cos(angle))));
        bullet.setVy(((int) Math.round(bullet.getConstantVelocity() * Math.sin(angle))));
        bullets.add(bullet);
    }


    //GETTER SETTERS
    public boolean isAccU() {
        return accU;
    }

    public void setAccU(boolean accU) {
        this.accU = accU;
    }

    public boolean isAccD() {
        return accD;
    }

    public void setAccD(boolean accD) {
        this.accD = accD;
    }

    public boolean isAccR() {
        return accR;
    }

    public void setAccR(boolean accR) {
        this.accR = accR;
    }

    public boolean isAccL() {
        return accL;
    }

    public void setAccL(boolean accL) {
        this.accL = accL;
    }

    public boolean isDecU() {
        return decU;
    }

    public void setDecU(boolean decU) {
        this.decU = decU;
    }

    public boolean isDecD() {
        return decD;
    }

    public void setDecD(boolean decD) {
        this.decD = decD;
    }

    public boolean isDecR() {
        return decR;
    }

    public void setDecR(boolean decR) {
        this.decR = decR;
    }

    public boolean isDecL() {
        return decL;
    }

    public void setDecL(boolean decL) {
        this.decL = decL;
    }
}
