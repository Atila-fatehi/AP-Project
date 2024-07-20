package controller.logic;

import controller.FileController;
import model.Paintable.Paintable;
import model.collision.Collidable;
import model.objectsModel.enemy.*;
import model.objectsModel.epsilon.Bullet;
import model.objectsModel.epsilon.Epsilon;
import model.objectsModel.miniBoss.Barricados;
import model.objectsModel.miniBoss.BlackOrb;
import model.objectsModel.miniBoss.Laser;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Objects;

public class GameState {

    //TODO add pause and make them not static
    public static Epsilon epsilon = Epsilon.getInstance();
    public static final ArrayList<Bullet> bullets = new ArrayList<>();
    public static final ArrayList<Trigorath> trigoraths = new ArrayList<>();
    public static final ArrayList<Squarantine> squarantines = new ArrayList<>();
    public static final ArrayList<Collectable> collectables = new ArrayList<>();
    public static final ArrayList<Omenoct> omenocts = new ArrayList<>();
    public static final ArrayList<Archmire> archmires = new ArrayList<>();
    public static final ArrayList<Necropick> necropicks = new ArrayList<>();
    public static final ArrayList<Wyrm> wyrms = new ArrayList<>();
    public static final ArrayList<Barricados> barricados = new ArrayList<>();
    public static final ArrayList<BlackOrb> orbs = new ArrayList<>();
    public static final ArrayList<Laser> lasers = new ArrayList<>();
    public static final ArrayList<JPanel> panels = new ArrayList<>();
    public static int elapsedTime;
    public static int difficulty = Objects.requireNonNull(FileController.readSettings())[1];
    public static int wave;

    public static boolean banish;
    public static boolean empower;
    public static boolean heal;

    public static String getAbility() {
        if (banish) {
            return "O' Hephaestus, Banish";
        } else if (empower) {
            return "O’ Athena, Empower";
        } else if (heal) {
            return "O' Apollo, Heal";
        }
        return "";
    }

    public static ArrayList<Paintable> getPaintables(){
        ArrayList<Paintable> paintables = new ArrayList<>();
        paintables.addAll(trigoraths);
        paintables.addAll(squarantines);
        paintables.addAll(collectables);
        paintables.addAll(omenocts);
        paintables.addAll(necropicks);
        paintables.addAll(wyrms);
        paintables.addAll(barricados);
        paintables.addAll(lasers);
        paintables.addAll(orbs);
        paintables.addAll(bullets);
        paintables.add(epsilon);
        paintables.addAll(archmires);
        return paintables;
    }
    public static ArrayList<Collidable> getComplexEnemies(){
        ArrayList<Collidable> collidable = new ArrayList<>();
        collidable.addAll(trigoraths);
        collidable.addAll(squarantines);
        collidable.addAll(omenocts);
        collidable.addAll(necropicks);
        collidable.addAll(wyrms);
        collidable.addAll(archmires);
        return  collidable;
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
        elapsedTime = 0;
        difficulty = Objects.requireNonNull(FileController.readSettings())[1];
        wave = 0;
    }
}
