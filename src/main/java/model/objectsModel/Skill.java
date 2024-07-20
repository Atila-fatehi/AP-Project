package model.objectsModel;

import java.io.Serializable;

public class Skill implements Serializable {



    //TODO ooooooooooooooooooooo
    boolean ares;
    boolean aceso;
    boolean proteus;
    boolean isActive;

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isAres() {
        return ares;
    }

    public void setAres(boolean ares) {
        this.ares = ares;
    }

    public boolean isAceso() {
        return aceso;
    }

    public void setAceso(boolean aceso) {
        this.aceso = aceso;
    }

    public boolean isProteus() {
        return proteus;
    }

    public void setProteus(boolean proteus) {
        this.proteus = proteus;
    }
}
