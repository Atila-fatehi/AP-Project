package model;

import controller.util.Constants;
import model.objectsModel.*;

import java.util.ArrayList;

public class GameState {
    private final Epsilon epsilon = Epsilon.getInstance();
    private final ArrayList<Bullet> bullets = new ArrayList<>();
    private final ArrayList<Trigorath> trigoraths = new ArrayList<>();
    private final ArrayList<Squarantine> squarantines = new ArrayList<>();
    private final ArrayList<Collectable> collectables = new ArrayList<>();
    private int elapsedTime;
    private int wave;

    public GameState() {


    }
}
