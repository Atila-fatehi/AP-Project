package controller.logic;

import controller.FileController;
import controller.util.Constants;
import model.Paintable.Paintable;
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
        paintables.addAll(bullets);
        paintables.addAll(trigoraths);
        paintables.addAll(squarantines);
        paintables.addAll(collectables);
        return paintables;
    }

}
