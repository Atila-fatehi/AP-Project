package controller.logic;

import controller.FileController;
import model.objectsModel.enemy.*;
import model.objectsModel.epsilon.Bullet;
import model.objectsModel.epsilon.Epsilon;
import model.objectsModel.miniBoss.Barricados;
import model.objectsModel.miniBoss.BlackOrb;
import model.objectsModel.miniBoss.Laser;

import javax.swing.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class GameData implements Serializable {
    private static GameData instance;

    public static GameData getInstance() {
        if (instance == null) instance = new GameData();

        return instance;
    }

    public Epsilon epsilon;
    public ArrayList<Bullet> bullets = new ArrayList<>();
    public ArrayList<Trigorath> trigoraths = new ArrayList<>();
    public ArrayList<Squarantine> squarantines = new ArrayList<>();
    public ArrayList<Collectable> collectables = new ArrayList<>();
    public ArrayList<Omenoct> omenocts = new ArrayList<>();
    public ArrayList<Archmire> archmires = new ArrayList<>();
    public ArrayList<Necropick> necropicks = new ArrayList<>();
    public ArrayList<Wyrm> wyrms = new ArrayList<>();
    public ArrayList<Barricados> barricados = new ArrayList<>();
    public ArrayList<BlackOrb> orbs = new ArrayList<>();
    public ArrayList<Laser> lasers = new ArrayList<>();
    public ArrayList<JPanel> panels = new ArrayList<>();
    public int elapsedTime;
    public int difficulty ;
    public int wave;

    public boolean banish;
    public boolean empower;
    public boolean heal;

    public void setData() {
        epsilon = Epsilon.getInstance();
        bullets = GameState.bullets;
        trigoraths = GameState.trigoraths;
        squarantines = GameState.squarantines;
        collectables = GameState.collectables;
        omenocts = GameState.omenocts;
        archmires = GameState.archmires;
        necropicks = GameState.necropicks;
        wyrms = GameState.wyrms;
        barricados = GameState.barricados;
        orbs = GameState.orbs;
        lasers = GameState.lasers;
        panels = GameState.panels;
        elapsedTime = GameState.elapsedTime;
        difficulty = GameState.difficulty;
        wave = GameState.wave;
        banish = GameState.banish;
        empower = GameState.empower;
        heal = GameState.heal;
    }
}
