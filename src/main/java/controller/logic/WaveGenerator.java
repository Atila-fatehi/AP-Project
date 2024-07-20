package controller.logic;

import controller.audio.players.AudioPlayer;

import java.util.Random;
import java.util.TimerTask;

public abstract class WaveGenerator {
    public static boolean inWait;

    public static void handleWaves() {
        if (GameState.wave <= 3) {
            generateSimpleWave();
        } else if (GameState.wave <= 9) {
            generateComplexWave();
        } else if (GameState.wave == 10) {
            generateFinalBoss();
        }
    }

    public static void generateSimpleWave() {
        if (GameState.trigoraths.isEmpty() && GameState.squarantines.isEmpty() && !inWait) {
            GameState.wave++;
            AudioPlayer.play(AudioPlayer.WAVE);
            java.util.Timer timer = new java.util.Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    AudioPlayer.play(AudioPlayer.AWOOGA);
                    Random random = new Random();
                    EnemyGenerator.makeNewSquarantine();
                    EnemyGenerator.makeNewTrigorath();
                    for (int i = 0; i < GameState.wave * GameState.difficulty; i++) {
                        if (random.nextBoolean()) {
                            EnemyGenerator.makeNewSquarantine();
                        }
                    }
                    for (int i = 0; i < GameState.wave * GameState.difficulty; i++) {
                        if (random.nextBoolean()) {
                            EnemyGenerator.makeNewTrigorath();
                        }
                    }
                    inWait = false;
                    timer.cancel();
                }
            }, 3000, 1111);
            inWait = true;
        }
    }

    public static void generateComplexWave() {
        if (GameState.wave == 4 || GameState.wave == 5 || GameState.wave == 6) {
            if (GameState.getComplexEnemies().isEmpty() && !inWait) {
                GameState.wave++;
                AudioPlayer.play(AudioPlayer.WAVE);
                java.util.Timer timer = new java.util.Timer();
                timer.schedule(new TimerTask() {
                    int count = 0;

                    @Override
                    public void run() {
                        if (count == 0) AudioPlayer.play(AudioPlayer.AWOOGA);
                        Random random = new Random();
                        EnemyGenerator.makeNewTrigorath();
                        EnemyGenerator.makeNewSquarantine();
                        if (GameState.omenocts.isEmpty()) EnemyGenerator.makeNewOmenoct();
                        for (int i = 0; i < count; i++) {
                            if (random.nextBoolean()) EnemyGenerator.makeNewTrigorath();
                            if (random.nextBoolean()) EnemyGenerator.makeNewSquarantine();
                            if (random.nextBoolean()) {
                                EnemyGenerator.makeNewWyrm();
                            } else {
                                EnemyGenerator.makeNewNecropick();
                            }
                            if (random.nextInt(3) == 0) EnemyGenerator.makeNewArchmire();
                        }
                        if (count == 2) {
                            inWait = false;
                            timer.cancel();
                        }
                        count++;
                    }
                }, 3000, 20000);
                inWait = true;
            }
        }
        if (GameState.wave == 7) {
            if (GameState.getComplexEnemies().isEmpty() && !inWait) {
                GameState.wave++;
                AudioPlayer.play(AudioPlayer.WAVE);
                java.util.Timer timer = new java.util.Timer();
                timer.schedule(new TimerTask() {
                    int count = 0;

                    @Override
                    public void run() {
                        if (count == 0) AudioPlayer.play(AudioPlayer.AWOOGA);
                        Random random = new Random();
                        EnemyGenerator.makeNewTrigorath();
                        EnemyGenerator.makeNewSquarantine();
                        if (GameState.barricados.isEmpty()) EnemyGenerator.makeNewBarricados();
                        for (int i = 0; i < count; i++) {
                            if (random.nextBoolean()) EnemyGenerator.makeNewTrigorath();
                            if (random.nextBoolean()) EnemyGenerator.makeNewSquarantine();
                            if (random.nextBoolean()) EnemyGenerator.makeNewWyrm();
                            if (random.nextInt(4) == 0) EnemyGenerator.makeNewArchmire();
                        }
                        if (count == 2) {
                            inWait = false;
                            timer.cancel();
                        }
                        count++;
                    }
                }, 3000, 20000);
                inWait = true;
            }
        }
        if (GameState.wave == 8) {
            if (GameState.getComplexEnemies().isEmpty() && !inWait) {
                if (!GameState.barricados.isEmpty()) GameState.barricados.get(0).selfDestruct();
                GameState.wave++;
                AudioPlayer.play(AudioPlayer.WAVE);
                java.util.Timer timer = new java.util.Timer();
                timer.schedule(new TimerTask() {
                    int count = 0;
                    @Override
                    public void run() {
                        if (count == 0) AudioPlayer.play(AudioPlayer.AWOOGA);
                        Random random = new Random();
                        EnemyGenerator.makeNewTrigorath();
                        EnemyGenerator.makeNewSquarantine();
                        EnemyGenerator.makeNewWyrm();
                        if (GameState.orbs.isEmpty()) EnemyGenerator.makeNewOrb();
                        for (int i = 0; i < count; i++) {
                            if (random.nextInt(5) == 0) EnemyGenerator.makeNewTrigorath();
                            if (random.nextInt(5) == 0) EnemyGenerator.makeNewSquarantine();
                            if (random.nextInt(5) == 0) EnemyGenerator.makeNewWyrm();
                            if (random.nextInt(5) == 0) EnemyGenerator.makeNewNecropick();
                            if (random.nextInt(5) == 0) EnemyGenerator.makeNewOmenoct();
                            if (random.nextInt(5) == 0) EnemyGenerator.makeNewArchmire();
                        }
                        if (count == 2) {
                            inWait = false;
                            timer.cancel();
                        }
                        count++;
                    }
                }, 3000, 20000);
                inWait = true;
            }
        }
    }

    public static void generateFinalBoss(){

    }
}
