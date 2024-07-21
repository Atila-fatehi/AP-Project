package model.objectsModel;

import java.io.Serializable;
import java.util.HashMap;

public class Skill implements Serializable {

    HashMap<String , Boolean> attack = new HashMap<>();
    HashMap<String , Boolean> defence = new HashMap<>();
    HashMap<String , Boolean> shapeShift = new HashMap<>();

    public Skill() {
        attack.put("Ares" , false);
        attack.put("Astrape" , false);
        attack.put("Cerberus" , false);

        defence.put("Aceso" , false);
        defence.put("Melampus" , false);
        defence.put("Chiron" , false);

        shapeShift.put("Proteus" , false);
        shapeShift.put("Empusa" , false);
        shapeShift.put("Dolus" , false);
    }


    public HashMap<String, Boolean> getAttack() {
        return attack;
    }

    public HashMap<String, Boolean> getDefence() {
        return defence;
    }

    public HashMap<String, Boolean> getShapeShift() {
        return shapeShift;
    }
}
