package controller.logic;

import controller.FileController;
import controller.util.CostumeMap;
import controller.util.CostumeTimer;
import model.Paintable.Paintable;
import model.collision.Collidable;
import model.objectsModel.Portal;
import model.objectsModel.boss.Hand;
import model.objectsModel.boss.SecondHand;
import model.objectsModel.boss.Smiley;
import model.objectsModel.enemy.*;
import model.objectsModel.epsilon.Bullet;
import model.objectsModel.epsilon.Epsilon;
import model.objectsModel.miniBoss.Barricados;
import model.objectsModel.miniBoss.BlackOrb;
import model.objectsModel.miniBoss.Laser;
import view.gameGUI.GamePanel;

import javax.swing.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class GameState implements Serializable {

    public static Epsilon epsilon = Epsilon.getInstance();
    public static ArrayList<Bullet> bullets = new ArrayList<>();
    public static ArrayList<Trigorath> trigoraths = new ArrayList<>();
    public static ArrayList<Squarantine> squarantines = new ArrayList<>();
    public static ArrayList<Collectable> collectables = new ArrayList<>();
    public static ArrayList<Omenoct> omenocts = new ArrayList<>();
    public static ArrayList<Archmire> archmires = new ArrayList<>();
    public static ArrayList<Necropick> necropicks = new ArrayList<>();
    public static ArrayList<Wyrm> wyrms = new ArrayList<>();
    public static ArrayList<Barricados> barricados = new ArrayList<>();
    public static ArrayList<BlackOrb> orbs = new ArrayList<>();
    public static ArrayList<Laser> lasers = new ArrayList<>();
    public static ArrayList<JPanel> panels = new ArrayList<>();
    public static ArrayList<Smiley> smilies = new ArrayList<>();
    public static ArrayList<Hand> hands = new ArrayList<>();
    public static ArrayList<SecondHand> secondHands = new ArrayList<>();
    public static int elapsedTime;
    public static int difficulty = Objects.requireNonNull(FileController.readSettings())[1];
    public static int wave;

    public static boolean banish;
    public static boolean empower;
    public static boolean heal;
    public static boolean dismay;
    public static boolean slumber;
    public static boolean slaughter;

    public static String getAbility() {
        if (banish) {
            return "O' Hephaestus, Banish";
        } else if (empower) {
            return "O’ Athena, Empower";
        } else if (heal) {
            return "O' Apollo, Heal";
        }else if (dismay){
            return "O’ Deimos, Dismay";
        } else if (slumber) {
            return "O’Hypnos, Slumber";
        } else if (slaughter) {
            return "O’ Phonoi, Slaughter";
        }

        return "None";
    }

    public static void setData(GameData data) {
        epsilon = data.epsilon;
        bullets = data.bullets;
        trigoraths = data.trigoraths;
        squarantines = data.squarantines;
        collectables = data.collectables;
        omenocts = data.omenocts;
//        archmires = data.archmires;
        necropicks = data.necropicks;
        wyrms = data.wyrms;
        barricados = data.barricados;
        orbs = data.orbs;
        lasers = data.lasers;
        panels = data.panels;
        smilies = data.smilies;
//        hands = data.hands;
//        secondHands = data.secondHands;
        elapsedTime = data.elapsedTime;
        difficulty = data.difficulty;
        wave = data.wave;
        banish = data.banish;
        empower = data.empower;
        heal = data.heal;
        GamePanel.setInstance();
        Epsilon.setInstance(epsilon);

        CostumeTimer.getInstance().getMap().clear();
        CostumeMap.getInstance().getMap().clear();
    }

    public static ArrayList<Paintable> getPaintables() {
        ArrayList<Paintable> paintables = new ArrayList<>();
        paintables.addAll(trigoraths);
        paintables.addAll(squarantines);
        paintables.addAll(collectables);
        paintables.addAll(omenocts);
        paintables.addAll(necropicks);
        paintables.addAll(wyrms);
        paintables.addAll(barricados);
        paintables.addAll(bullets);
        paintables.add(epsilon);
        paintables.addAll(Portal.portals);
        paintables.addAll(hands);
        paintables.addAll(secondHands);
        paintables.addAll(smilies);
        paintables.addAll(archmires);
        paintables.addAll(lasers);
        paintables.addAll(orbs);
        return paintables;
    }

    public static ArrayList<Collidable> getComplexEnemies() {
        ArrayList<Collidable> collidable = new ArrayList<>();
        collidable.addAll(trigoraths);
        collidable.addAll(squarantines);
        collidable.addAll(omenocts);
        collidable.addAll(necropicks);
        collidable.addAll(wyrms);
        collidable.addAll(archmires);
        return collidable;
    }

    public static void initiateNewGame() {
        Epsilon.makeInstance();
        epsilon = Epsilon.getInstance();

        bullets.clear();
        trigoraths.clear();
        squarantines.clear();
        omenocts.clear();
        necropicks.clear();
        wyrms.clear();
        archmires.clear();
        collectables.clear();
        barricados.clear();
        orbs.clear();
        lasers.clear();
        panels.clear();
        smilies.clear();
        hands.clear();
        secondHands.clear();
        elapsedTime = 0;
        difficulty = Objects.requireNonNull(FileController.readSettings())[1];
        wave = 0;
    }
}
