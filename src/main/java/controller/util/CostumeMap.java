package controller.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;

public class CostumeMap {
    static CostumeMap instance;

    static public CostumeMap getInstance(){
        if (instance == null) instance = new CostumeMap();
        return instance;
    }

    HashMap<String, HashMap<String , java.util.Timer>> map = new HashMap<>();

    public void newTimer(String id, HashMap<String , java.util.Timer> map) {
        this.map.put(id, map);
    }

    public HashMap<String, HashMap<String , java.util.Timer>> getMap() {
        return map;
    }
}
