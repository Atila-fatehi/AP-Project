package controller.logic;

import controller.FrameController;
import controller.audio.players.AudioPlayer;
import controller.util.Constants;
import controller.util.CostumeTimer;
import model.objectsModel.Portal;
import model.objectsModel.boss.AttackType;
import model.objectsModel.epsilon.Epsilon;
import view.gameGUI.GamePanel;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public abstract class WaveGenerator {
    public static boolean inWait;
    public static boolean[] generated = new boolean[10];
    public static int[] waveStart = new int[10];
    public static boolean waveEnded = false;

    public static void handleWaves() {
        if (GameState.wave == 1 && !generated[0]) {
            waveStart[0] = GameState.elapsedTime;
            generateSimpleWave();
            generated[0] = true;
        }
        if (GameState.wave == 2 && !generated[1]) {
            if (GameState.getComplexEnemies().isEmpty() && Portal.portals.isEmpty()) {
                waveStart[1] = GameState.elapsedTime;
                generateSimpleWave();
                generated[1] = true;
            }
        }
        if (GameState.wave == 3 && !generated[2]) {
            if (GameState.getComplexEnemies().isEmpty() && Portal.portals.isEmpty()) {
                waveStart[2] = GameState.elapsedTime;
                generateSimpleWave();
                generated[2] = true;
            }
        }
        if (GameState.wave == 4 && !generated[3]) {
            if (GameState.getComplexEnemies().isEmpty() && Portal.portals.isEmpty()) {
                waveStart[3] = GameState.elapsedTime;
                generateSimpleWave();
                generated[3] = true;
            }
        }
        if (GameState.wave == 5 && !generated[4]) {
            if (GameState.getComplexEnemies().isEmpty() && Portal.portals.isEmpty()) {
                waveStart[4] = GameState.elapsedTime;
                generateComplexWave();
                generated[4] = true;
            }
        }
        if (GameState.wave == 6 && !generated[5]) {
            if (GameState.getComplexEnemies().isEmpty() && Portal.portals.isEmpty()) {
                waveStart[5] = GameState.elapsedTime;
                generateComplexWave();
                generated[5] = true;
            }
        }
        if (GameState.wave == 7 && !generated[6]) {
            if (GameState.getComplexEnemies().isEmpty() && Portal.portals.isEmpty()) {
                waveStart[6] = GameState.elapsedTime;
                generateComplexWave();
                generated[6] = true;
            }
        }
        if (GameState.wave == 8 && !generated[7]) {
            if (GameState.getComplexEnemies().isEmpty() && Portal.portals.isEmpty()) {
                waveStart[7] = GameState.elapsedTime;
                generateComplexWave();
                generated[7] = true;
            }
        }
        if (GameState.wave == 9 && !generated[8]) {
            if (GameState.getComplexEnemies().isEmpty() && Portal.portals.isEmpty()) {
                waveStart[8] = GameState.elapsedTime;
                generateComplexWave();
                generated[8] = true;
            }
        }
        if (GameState.wave == 10 && !generated[9]) {
            if (GameState.getComplexEnemies().isEmpty() && Portal.portals.isEmpty()) {
                waveStart[9] = GameState.elapsedTime;
                generateFinalBoss();
                generated[9] = true;
            }
        }
        if (GameState.getComplexEnemies().isEmpty() && Portal.portals.isEmpty() && waveEnded) {
            new Portal(EnemyGenerator.randomXonScreen(), EnemyGenerator.randomYonScreen());
//            if (!GameState.barricados.isEmpty()) GameState.barricados.get(0).selfDestruct();
            GameState.wave++;
        }
    }

    public static void generateSimpleWave() {
        if (!inWait) {
            waveEnded = false;
            AudioPlayer.play(AudioPlayer.WAVE);
            EnemyGenerator.makeNewSquarantine();
            EnemyGenerator.makeNewTrigorath();
            java.util.Timer timer = new java.util.Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    AudioPlayer.play(AudioPlayer.AWOOGA);
                    Random random = new Random();
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
                    waveEnded = true;
                    inWait = false;
                    timer.cancel();
                }
            }, 3000, 1111);
            inWait = true;
        }

    }

    public static void generateComplexWave() {
        if (GameState.wave == 5 || GameState.wave == 6 || GameState.wave == 7) {
            if (GameState.getComplexEnemies().isEmpty() && !inWait) {
                waveEnded = false;
                AudioPlayer.play(AudioPlayer.WAVE);
                java.util.Timer timer = new java.util.Timer();
                EnemyGenerator.makeNewTrigorath();
                EnemyGenerator.makeNewSquarantine();
                timer.schedule(new TimerTask() {
                    int count = 0;

                    @Override
                    public void run() {
                        if (count == 0) AudioPlayer.play(AudioPlayer.AWOOGA);
                        Random random = new Random();
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
                            waveEnded = true;
                            inWait = false;
                            timer.cancel();
                        }
                        count++;
                    }
                }, 3000, 10000);
                inWait = true;
            }
        }
        if (GameState.wave == 8) {
            if (GameState.getComplexEnemies().isEmpty() && !inWait) {
                waveEnded = false;
                AudioPlayer.play(AudioPlayer.WAVE);
                EnemyGenerator.makeNewTrigorath();
                EnemyGenerator.makeNewSquarantine();
                java.util.Timer timer = new java.util.Timer();
                timer.schedule(new TimerTask() {
                    int count = 0;

                    @Override
                    public void run() {
                        if (count == 0) AudioPlayer.play(AudioPlayer.AWOOGA);
                        Random random = new Random();
                        if (GameState.barricados.isEmpty()) EnemyGenerator.makeNewBarricados();
                        for (int i = 0; i < count; i++) {
                            if (random.nextBoolean()) EnemyGenerator.makeNewTrigorath();
                            if (random.nextBoolean()) EnemyGenerator.makeNewSquarantine();
                            if (random.nextBoolean()) EnemyGenerator.makeNewWyrm();
                            if (random.nextInt(4) == 0) EnemyGenerator.makeNewArchmire();
                        }
                        if (count == 2) {
                            waveEnded = true;
                            inWait = false;
                            timer.cancel();
                        }
                        count++;
                    }
                }, 3000, 10000);
                inWait = true;
            }
        }
        if (GameState.wave == 9) {
            if (GameState.getComplexEnemies().isEmpty() && !inWait) {
//                if (!GameState.barricados.isEmpty()) GameState.barricados.get(0).selfDestruct();
                waveEnded = false;
                AudioPlayer.play(AudioPlayer.WAVE);
                EnemyGenerator.makeNewTrigorath();
                EnemyGenerator.makeNewSquarantine();
                java.util.Timer timer = new java.util.Timer();
                timer.schedule(new TimerTask() {
                    int count = 0;
                    @Override
                    public void run() {
                        if (count == 0) AudioPlayer.play(AudioPlayer.AWOOGA);
                        Random random = new Random();
                        EnemyGenerator.makeNewWyrm();
                        if (GameState.orbs.isEmpty()) EnemyGenerator.makeNewOrb();
                        for (int i = 0; i < count; i++) {
                            if (random.nextInt(4) == 0) EnemyGenerator.makeNewTrigorath();
                            if (random.nextInt(4) == 0) EnemyGenerator.makeNewSquarantine();
                            if (random.nextInt(4) == 0) EnemyGenerator.makeNewWyrm();
                            if (random.nextInt(4) == 0) EnemyGenerator.makeNewNecropick();
                            if (random.nextInt(4) == 0) EnemyGenerator.makeNewOmenoct();
                            if (random.nextInt(4) == 0) EnemyGenerator.makeNewArchmire();
                        }
                        if (count == 2) {
                            waveEnded = true;
                            inWait = false;
                            timer.cancel();
                        }
                        count++;
                    }
                }, 3000, 15000);
                inWait = true;
            }
        }
    }

    public static void generateFinalBoss() {
        FrameController.setPanelToFinalPosition();
        EnemyGenerator.makeSmiley();
        EnemyGenerator.makeHand();
        EnemyGenerator.makeSecondHand();
        waveEnded = false;
        java.util.Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!GameState.fists.isEmpty()) GameState.fists.get(0).setAttackType(AttackType.POWER_PUNCH);

                if (!GameState.hands.isEmpty() && !GameState.secondHands.isEmpty() && !GameState.smilies.isEmpty()) {
                    GameState.hands.get(0).setAttackType(AttackType.VOMIT);
                    GameState.secondHands.get(0).setAttackType(AttackType.VOMIT);
                    GameState.smilies.get(0).setAttackType(AttackType.VOMIT);

                    GameState.smilies.get(0).setDamageable(true);
                    GameState.hands.get(0).setDamageable(false);
                    GameState.secondHands.get(0).setDamageable(false);

                    if (CostumeTimer.getInstance().getMap().containsKey(GameState.hands.get(0).getId()))
                        CostumeTimer.getInstance().getMap().remove(GameState.hands.get(0).getId()).cancel();
                    if (CostumeTimer.getInstance().getMap().containsKey(GameState.secondHands.get(0).getId()))
                        CostumeTimer.getInstance().getMap().remove(GameState.secondHands.get(0).getId()).cancel();
                }

//                if (!GameState.hands.isEmpty() && !GameState.secondHands.isEmpty() && !GameState.smilies.isEmpty()) {
//                    GameState.smilies.get(0).setAttackType(AttackType.SLAP);
//
//                    if (new Random().nextBoolean()) {
//                        GameState.hands.get(0).setAttackType(AttackType.NAN);
//                        GameState.secondHands.get(0).setAttackType(AttackType.SLAP);
//                    } else {
//                        GameState.hands.get(0).setAttackType(AttackType.SLAP);
//                        GameState.secondHands.get(0).setAttackType(AttackType.NAN);
//                    }
//
//                    GameState.smilies.get(0).setDamageable(true);
//                    GameState.hands.get(0).setDamageable(false);
//                    GameState.secondHands.get(0).setDamageable(false);
//
//                    if (CostumeTimer.getInstance().getMap().containsKey(GameState.hands.get(0).getId()))
//                        CostumeTimer.getInstance().getMap().remove(GameState.hands.get(0).getId()).cancel();
//                    if (CostumeTimer.getInstance().getMap().containsKey(GameState.secondHands.get(0).getId()))
//                        CostumeTimer.getInstance().getMap().remove(GameState.secondHands.get(0).getId()).cancel();
//                }


//                if (new Random().nextBoolean()) {
//                    if (!GameState.hands.isEmpty() && !GameState.secondHands.isEmpty() && !GameState.smilies.isEmpty()) {
//                        GameState.hands.get(0).setAttackType(AttackType.SQUEEZE);
//                        GameState.secondHands.get(0).setAttackType(AttackType.SQUEEZE);
//                        GameState.smilies.get(0).setAttackType(AttackType.SQUEEZE);
//
//                        GameState.smilies.get(0).setDamageable(true);
//                        GameState.hands.get(0).setDamageable(false);
//                        GameState.secondHands.get(0).setDamageable(false);
//
//                        if (CostumeTimer.getInstance().getMap().containsKey(GameState.hands.get(0).getId()))
//                            CostumeTimer.getInstance().getMap().remove(GameState.hands.get(0).getId()).cancel();
//                        if (CostumeTimer.getInstance().getMap().containsKey(GameState.secondHands.get(0).getId()))
//                            CostumeTimer.getInstance().getMap().remove(GameState.secondHands.get(0).getId()).cancel();
//                    }
//                } else {
//                    if (!GameState.hands.isEmpty() && !GameState.secondHands.isEmpty() && !GameState.smilies.isEmpty()) {
//                        GameState.hands.get(0).setAttackType(AttackType.PROJECTILE);
//                        GameState.secondHands.get(0).setAttackType(AttackType.PROJECTILE);
//                        GameState.smilies.get(0).setAttackType(AttackType.PROJECTILE);
//
//                        GameState.smilies.get(0).setDamageable(false);
//                        GameState.hands.get(0).setDamageable(true);
//                        GameState.secondHands.get(0).setDamageable(true);
//
//                        if (CostumeTimer.getInstance().getMap().containsKey(GameState.smilies.get(0).getId()))
//                            CostumeTimer.getInstance().getMap().remove(GameState.smilies.get(0).getId()).cancel();
//                    }
//                }
            }
        }, 3000, 15000);

    }
}

