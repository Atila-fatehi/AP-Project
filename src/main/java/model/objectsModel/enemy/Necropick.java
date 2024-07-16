package model.objectsModel.enemy;

import controller.util.Constants;
import model.Paintable.Paintable;
import model.movable.Movable;
import view.gameGUI.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.util.Random;
import java.util.TimerTask;

public class Necropick implements Paintable, Movable {
    private int HP = 10;
    private double x;
    private double y;
    private double constantVelocity = 1d;
    private double maxVelocityX;
    private double maxVelocityY;
    private double vx;
    private double vy;
    private double accX;
    private double accY;
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
                    //fire
                } else {
                    if (count % 2 == 0) {
                        disappear = true;
                    }
                    count++;
                }
            }
        }, 8000, 4000);
    }

    @Override
    public void selfPaint(Graphics g) {
        int locX = GamePanel.getInstance().getLocationX();
        int locY = GamePanel.getInstance().getLocationY();
        if (!disappear) g.drawImage(image, (int) x - locX, (int) y - locY, GamePanel.getInstance());

    }

    @Override
    public void move() {
        x += vx;
        y += vy;

        if (maxVelocityX > 0) {
            if (vx < maxVelocityX) {
                vx += accX;
            }
        } else {
            if (vx > maxVelocityX) {
                vx += accX;
            }
        }

        if (maxVelocityY > 0) {
            if (vy < maxVelocityY) {
                vy += accY;
            }
        } else {
            if (vy > maxVelocityY) {
                vy += accY;
            }
        }

    }

    private boolean calcualted;
    private double destinationX;
    private double destinationY;

    @Override
    public void calculateMovingDirection(double x, double y) {
        if (!disappear) {
            if (!calcualted) {
                if (new Random().nextBoolean()) {
                    if (new Random().nextBoolean()) {
                        y += 100;
                    } else {
                        y -= 100;
                    }
                } else {
                    if (new Random().nextBoolean()) {
                        x += 100;
                    } else {
                        x -= 100;
                    }
                }
                destinationX = x;
                destinationY = y;
            }
            double angle = Math.atan2(destinationY - this.y, destinationX - this.x);
            maxVelocityX = constantVelocity * Math.cos(angle);
            maxVelocityY = constantVelocity * Math.sin(angle);
            accX = Math.cos(angle);
            accY = Math.sin(angle);
            calcualted = true;
        } else {
            if (new Random().nextBoolean()) {
                if (new Random().nextBoolean()) {
                    y += 100;
                } else {
                    y -= 100;
                }
            } else {
                if (new Random().nextBoolean()) {
                    x += 100;
                } else {
                    x -= 100;
                }
            }
            this.x = x;
            this.y = y;
            calcualted = false;
        }
    }

}
