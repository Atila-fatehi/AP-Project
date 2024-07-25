package controller.logic;

import controller.FileController;
import controller.KeyController;
import controller.util.Constants;
import model.objectsModel.Portal;
import model.objectsModel.epsilon.Epsilon;
import view.frames.MainMenu;
import view.gameGUI.GameFrame;
import view.gameGUI.GamePanel;
import controller.audio.players.AudioPlayer;
import controller.audio.players.GameMusicPlayer;

import javax.swing.*;
import java.util.*;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

public class GameManager {

    private static GameManager instance;

    public static GameManager getInstance() {
        if (instance == null) instance = new GameManager();
        return instance;
    }

    private boolean paused;
    private java.util.Timer modelTimer;
    private java.util.Timer viewTimer;
    private boolean gameOver;
    private boolean gameWon;

    public static void initiateNewGame() {
        instance = new GameManager();
        instance.startElapsedTimer();
    }

    public GameManager() {
        viewTimer = new java.util.Timer();
        viewTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!paused) {
                    UpdateView.update();
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
                    UpdateModel.update();
                }
                if (gameOver) {
                    modelTimer.cancel();
                }
            }
        }, 0, (int) (double) TimeUnit.SECONDS.toMillis(1) / 100);
    }

    private java.util.Timer elapsedTimer;

    public void startElapsedTimer() {
        elapsedTimer = new java.util.Timer();
        elapsedTimer.schedule(new TimerTask() {
            @Override
            public void run() {
//                if (Epsilon.getInstance().getSkill().isAceso() && Epsilon.getInstance().getSkill().isActive()) {
//                    Epsilon.getInstance().setHP(Epsilon.getInstance().getHP() + 1);
//                }
                if (!paused) GameState.elapsedTime++;
                if (GameState.elapsedTime == 10) {
                    UpdateView.pastTen = true;
                }
                if (GameState.elapsedTime % 5 == 0) {
                    FileController.serializeGameState();
                }
            }
        }, 0, 1000);
    }

    public void stopTimers() {
        GameManager.getInstance().getModelTimer().cancel();
        GameManager.getInstance().getViewTimer().cancel();
        GameManager.getInstance().getElapsedTimer().cancel();
    }

    public void continueTimers() {
        elapsedTimer = new java.util.Timer();
        elapsedTimer.schedule(new TimerTask() {
            @Override
            public void run() {
//                if (Epsilon.getInstance().getSkill().isAceso() && Epsilon.getInstance().getSkill().isActive()) {
//                    Epsilon.getInstance().setHP(Epsilon.getInstance().getHP() + 1);
//                }
                if (!paused) GameState.elapsedTime++;
                if (GameState.elapsedTime == 10) {
                    UpdateView.pastTen = true;
                }
                if (GameState.elapsedTime % 5 == 0) {
                    FileController.serializeGameState();
                }
            }
        }, 0, 1000);
        viewTimer = new java.util.Timer();
        viewTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!paused) {
                    UpdateView.update();
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
                    UpdateModel.update();
                }
                if (gameOver) {
                    modelTimer.cancel();
                }
            }
        }, 0, (int) (double) TimeUnit.SECONDS.toMillis(1) / 100);
    }

    public void gameWon() {
        GameMusicPlayer.getInstance().getClip().stop();
        GameMusicPlayer.getInstance().setPlaying(false);
        paused = true;
        stopTimers();
        AudioPlayer.play(AudioPlayer.WIN_MUSIC);
        FileController.writeXP(Epsilon.getInstance().getXP());
        java.util.Timer timer = new java.util.Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Epsilon.getInstance().setRadius(Epsilon.getInstance().getRadius() + 5);
                UpdateView.update();
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
                    if (JOptionPane.showOptionDialog(null, "Your XP : " + Epsilon.getInstance().getXP() + "\n" +
                            "Bullets fired : " + GameState.allBulletCount + "\n" +
                            "Successful Bullets :  " + GameState.successfulBulletCount + "\n" +
                            "Enemies Killed " + GameState.killedEnemies, "Game Over", JOptionPane.INFORMATION_MESSAGE, JOptionPane.INFORMATION_MESSAGE, null, responses, 0) != -2) {
                        GameFrame.getInstance().dispose();
                        if (!GameState.smilies.isEmpty()) GameState.smilies.get(0).selfDestruct();
                        Epsilon.getInstance().setXP(0);
                        Epsilon.getInstance().setHP(100);
                        new MainMenu();
                    }
                }
            }
        }, 5000, 50);
    }

    public void gameOver() {
        if (Epsilon.getInstance().savedToCheckPoint && !Epsilon.getInstance().alreadyDead) {
            String[] responses = {"OK"};
            if (JOptionPane.showOptionDialog(null, "You died but you have a checkpoint", "Game Over", JOptionPane.INFORMATION_MESSAGE, JOptionPane.INFORMATION_MESSAGE, null, responses, 0) != -2) {
                Epsilon.getInstance().setHP(10);
                Epsilon.getInstance().setX(Constants.INITIAL_EPSILON_POSX);
                Epsilon.getInstance().setY(Constants.INITIAL_EPSILON_POSY);
                GameState.initiateNewGameOnCheckpoint();
                GameState.wave = FileController.loadCheckPoint();
                WaveGenerator.generated[GameState.wave - 1] = false;
                WaveGenerator.handleWaves();
                GameManager.getInstance().setPaused(false);
                Epsilon.getInstance().alreadyDead = true;
            }
        } else {
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
    }

    public void mouseClicked(int x, int y) {
        if (GameState.empower) {
            EnemyGenerator.makeNewBullet(x, y);

            java.util.Timer timer = new java.util.Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    EnemyGenerator.makeNewBullet(x, y);
                    timer.cancel();
                }
            }, 100, 1111);
            java.util.Timer timer2 = new java.util.Timer();
            timer2.schedule(new TimerTask() {
                @Override
                public void run() {
                    EnemyGenerator.makeNewBullet(x, y);
                    timer2.cancel();
                }
            }, 200, 1111);
        } else {
            EnemyGenerator.makeNewBullet(x, y);
        }

    }

    public void activateAbility() {
//        if (!Epsilon.getInstance().getSkill().isActive()) {
//            Epsilon.getInstance().getSkill().setActive(true);
//            java.util.Timer timer = new java.util.Timer();
//            timer.schedule(new TimerTask() {
//                @Override
//                public void run() {
//                    Epsilon.getInstance().getSkill().setActive(false);
//                    timer.cancel();
//                }
//            }, 5 * 1000, 1111);
//            if (Epsilon.getInstance().getSkill().isProteus()) {
//                Epsilon.getInstance().addVertex();
//                Epsilon.getInstance().setXP(Epsilon.getInstance().getXP() - 100);
//            }
//            if (Epsilon.getInstance().getSkill().isAceso()) {
//                Epsilon.getInstance().setXP(Epsilon.getInstance().getXP() - 100);
//            }
//            if (Epsilon.getInstance().getSkill().isAres()) {
//                Epsilon.getInstance().setDamageRate(7);
//                Epsilon.getInstance().setXP(Epsilon.getInstance().getXP() - 100);
//            }
//        }
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }


    public java.util.Timer getModelTimer() {
        return modelTimer;
    }

    public java.util.Timer getViewTimer() {
        return viewTimer;
    }

    public Timer getElapsedTimer() {
        return elapsedTimer;
    }

    public void setGameWon(boolean gameWon) {
        this.gameWon = gameWon;
    }

    public boolean isGameWon() {
        return gameWon;
    }

    public void confuseControls() {
        KeyController.changeCodes();
        new java.util.Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                KeyController.initiateKeyCodes();
                this.cancel();
            }
        }, 8000, 1111);
    }
}
