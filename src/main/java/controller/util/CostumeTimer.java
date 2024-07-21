package controller.util;

import java.util.HashMap;
import java.util.Timer;

public class CostumeTimer {

    static CostumeTimer instance;

    static public CostumeTimer getInstance(){
        if (instance == null) instance = new CostumeTimer();
        return instance;
    }

    HashMap<String, java.util.Timer> map = new HashMap<>();

    public void newTimer(String id, java.util.Timer timer) {
        map.put(id, timer);
    }

    public HashMap<String, Timer> getMap() {
        return map;
    }
}
