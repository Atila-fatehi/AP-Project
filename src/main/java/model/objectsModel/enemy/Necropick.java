package model.objectsModel.enemy;

import controller.logic.GameState;
import controller.util.Constants;
import model.Paintable.Paintable;
import model.movable.Movable;
import model.objectsModel.epsilon.Bullet;
import model.objectsModel.epsilon.Epsilon;
import view.gameGUI.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.util.Random;
import java.util.TimerTask;

public class Necropick implements Paintable, Movable {
    private int HP = 10;
    private double x;
    private double y;
    private final int radiusFromEpsilon = 180;
    private boolean played;
    private Image image;
    private java.util.Timer timer;
    private boolean disappear;

    public Necropick(double x, double y) {
        this.x = x;
        this.y = y;
        try {
            Image yourImage = (Image) ImageIO.read(Constants.NECRO_PICK);
            image = yourImage.getScaledInstance(50, 50, Image.SCALE_DEFAULT);

        } catch (Exception e) {
            System.out.println("exception in necropick paint");
        }
        timer = new java.util.Timer();
        timer.schedule(new TimerTask() {
            int count = 0;

            @Override
            public void run() {
                if (disappear) {
                    disappear = false;
                    fire();
                } else {
                    if (count % 2 == 0) {
                        disappear = true;
                    }
                    count++;
                }
            }
        }, 2000, 1000);
    }

    @Override
    public void selfPaint(Graphics g) {
        int locX = GamePanel.getInstance().getLocationX();
        int locY = GamePanel.getInstance().getLocationY();
        if (!disappear) g.drawImage(image, (int) x - locX, (int) y - locY, GamePanel.getInstance());


        for (int i = 0; i < GameState.panels.size(); i++) {
            locX = GameState.panels.get(i).getX();
            locY = GameState.panels.get(i).getY();
            Graphics g2 = GameState.panels.get(i).getGraphics();
            if (!disappear) g2.drawImage(image, (int) x - locX, (int) y - locY, GameState.panels.get(i));
        }
    }

    @Override
    public void move() {

//        x += vx;
//        y += vy;
//
//        if (maxVelocityX > 0) {
//            if (vx < maxVelocityX) {
//                vx += accX;
//            }
//        } else {
//            if (vx > maxVelocityX) {
//                vx += accX;
//            }
//        }
//
//        if (maxVelocityY > 0) {
//            if (vy < maxVelocityY) {
//                vy += accY;
//            }
//        } else {
//            if (vy > maxVelocityY) {
//                vy += accY;
//            }
//        }
    }

    private boolean calculated;
    private double destinationX;
    private double destinationY;

    @Override
    public void calculateMovingDirection(double x, double y) {
        if (!disappear) {

//            if (!calculated) {
//                if (new Random().nextBoolean()) {
//                    if (new Random().nextBoolean()) {
//                        y += 100;
//                    } else {
//                        y -= 100;
//                    }
//                } else {
//                    if (new Random().nextBoolean()) {
//                        x += 100;
//                    } else {
//                        x -= 100;
//                    }
//                }
//                destinationX = x;
//                destinationY = y;
//            }
//            double angle = Math.atan2(destinationY - this.y, destinationX - this.x);
//            maxVelocityX = constantVelocity * Math.cos(angle);
//            maxVelocityY = constantVelocity * Math.sin(angle);
//            accX = Math.cos(angle);
//            accY = Math.sin(angle);
//            calculated = true;
        } else {
            if (new Random().nextBoolean()) {
                if (new Random().nextBoolean()) {
                    y += radiusFromEpsilon;
                } else {
                    y -= radiusFromEpsilon;
                }
                x -= 25;
            } else {
                if (new Random().nextBoolean()) {
                    x += radiusFromEpsilon;
                } else {
                    x -= radiusFromEpsilon;
                }
                y -= 25;
            }
            this.x = x;
            this.y = y;
            calculated = false;
        }
    }

    void fire() {
        Bullet bullet = new Bullet(x + 25, y + 25, false, Color.GRAY);
        bullet.calculateMovingDirection(x + 25, y + 25 + 5);
        GameState.bullets.add(bullet);
        bullet = new Bullet(x + 25, y + 25, false, Color.GRAY);
        bullet.calculateMovingDirection(x + 25 + 5, y + 25);
        GameState.bullets.add(bullet);
        bullet = new Bullet(x + 25, y + 25, false, Color.GRAY);
        bullet.calculateMovingDirection(x + 25 - 5, y + 25);
        GameState.bullets.add(bullet);
        bullet = new Bullet(x + 25, y + 25, false, Color.GRAY);
        bullet.calculateMovingDirection(x + 25, y + 25 - 5);
        GameState.bullets.add(bullet);

        bullet = new Bullet(x + 25, y + 25, false, Color.GRAY);
        bullet.calculateMovingDirection(x + 25 - 5, y + 25 - 5);
        GameState.bullets.add(bullet);
        bullet = new Bullet(x + 25, y + 25, false, Color.GRAY);
        bullet.calculateMovingDirection(x + 25 + 5, y + 25 - 5);
        GameState.bullets.add(bullet);
        bullet = new Bullet(x + 25, y + 25, false, Color.GRAY);
        bullet.calculateMovingDirection(x + 25 + 5, y + 25 + 5);
        GameState.bullets.add(bullet);
        bullet = new Bullet(x + 25, y + 25, false, Color.GRAY);
        bullet.calculateMovingDirection(x + 25 - 5, y + 25 + 5);
        GameState.bullets.add(bullet);
    }

}
