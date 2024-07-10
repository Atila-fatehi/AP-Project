package model.logic;

import controller.FileController;
import controller.util.Constants;
import model.objectsModel.*;

import java.util.ArrayList;
import java.util.Objects;

public class GameState {
    public static final Epsilon epsilon = Epsilon.getInstance();
    public static final ArrayList<Bullet> bullets = new ArrayList<>();
    public static final ArrayList<Trigorath> trigoraths = new ArrayList<>();
    public static final ArrayList<Squarantine> squarantines = new ArrayList<>();
    public static final ArrayList<Collectable> collectables = new ArrayList<>();
    public static int elapsedTime;
    public static int difficulty = Objects.requireNonNull(FileController.readSettings())[1];
    public static int wave;

}
