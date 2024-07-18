package model.objectsModel.miniBoss;

import controller.logic.GameState;

public abstract class OrbManager {
    public static void laser() {
        double[] x = new double[5];
        double[] y = new double[5];
        for (int i = 0; i < 5; i++) {
            x[i] = GameState.orbs.get(i).getX();
            y[i] = GameState.orbs.get(i).getY();
            GameState.orbs.get(i).setDamageable(true);
        }
        int size = 100;
        for (int i = 1; i < 5; i++) {
            GameState.lasers.add(new Laser(new double[]{x[0] + 20, x[0] + size - 40, x[i] + size - 40, x[i] + 10},
                    new double[]{y[0] + 10 + size / 2, y[0] + 10 + size / 2, y[i] + 10 + size / 2, y[i] + 10 + size / 2} , i * 10));
            //01 02 03 04
        }
        for (int i = 2; i < 3; i++) {
            GameState.lasers.add(new Laser(new double[]{x[1] + 20, x[1] + size - 40, x[i] + size - 40, x[i] + 10},
                    new double[]{y[1] + 10 + size / 2, y[1] + 10 + size / 2, y[i] + 10 + size / 2, y[i] + 10 + size / 2} , 12));
            //12
        }
        GameState.lasers.add(new Laser(new double[]{x[1] + 20, x[1] + size - 20, x[3] + size - 20, x[3] + 10},
                new double[]{y[1] + 10 + size / 2, y[1] + 10 + size / 2, y[3] + 10 + size / 2, y[3] + 10 + size / 2} , 13));
        //13
        GameState.lasers.add(new Laser(new double[]{x[1] + size - 40, x[1] + size - 40, x[4] + size - 40, x[4] + size - 40},
                new double[]{y[1] + size / 2 - 30, y[1] + size / 2 + 20, y[4] + size / 2 + 20, y[4] + size / 2 - 30} , 14));
//14


        GameState.lasers.add(new Laser(new double[]{x[4] + 20, x[4] + size - 20, x[2] + size - 20, x[2] + 10},
                new double[]{y[4] + 10 + size / 2, y[4] + 10 + size / 2, y[2] + 10 + size / 2, y[2] + 10 + size / 2} , 24));
        //23
        GameState.lasers.add(new Laser(new double[]{x[2] + size - 40, x[2] + size - 40, x[3] + size - 40, x[3] + size - 40},
                new double[]{y[2] + size / 2 - 30, y[2] + size / 2 + 20, y[3] + size / 2 + 20, y[3] + size / 2 - 30} , 23));
        //24

        GameState.lasers.add(new Laser(new double[]{x[4] + 20, x[4] + size - 40, x[3] + size - 40, x[3] + 10},
                new double[]{y[4] + 10 + size / 2, y[4] + 10 + size / 2, y[3] + 10 + size / 2, y[3] + 10 + size / 2} , 34));
        //34
    }

    public static void destroyLaser(int index){
        if(index == 0){
            for (int i = 0; i < GameState.lasers.size(); i++) {
                if(GameState.lasers.get(i).getCode() % 10 == 0){
                    GameState.lasers.remove(i);
                    i--;
                }
            }
        }

        if(index == 1) {
            for (int i = 0; i < GameState.lasers.size(); i++) {
                if ((GameState.lasers.get(i).getCode() >= 10 && GameState.lasers.get(i).getCode() < 20)
                        || GameState.lasers.get(i).getCode() % 10 == 1) {
                    GameState.lasers.remove(i);
                    i--;
                }

            }
        }

        if(index == 2) {
            for (int i = 0; i < GameState.lasers.size(); i++) {
                if ((GameState.lasers.get(i).getCode() >= 20 && GameState.lasers.get(i).getCode() < 30)
                        || GameState.lasers.get(i).getCode() % 10 == 2) {
                    GameState.lasers.remove(i);
                    i--;
                }

            }
        }

        if(index == 3) {
            for (int i = 0; i < GameState.lasers.size(); i++) {
                if ((GameState.lasers.get(i).getCode() >= 30 && GameState.lasers.get(i).getCode() < 40)
                        || GameState.lasers.get(i).getCode() % 10 == 3) {
                    GameState.lasers.remove(i);
                    i--;
                }
            }
        }

        if(index == 4) {
            for (int i = 0; i < GameState.lasers.size(); i++) {
                if ((GameState.lasers.get(i).getCode() >= 40 && GameState.lasers.get(i).getCode() < 50)
                        || GameState.lasers.get(i).getCode() % 10 == 4) {
                    GameState.lasers.remove(i);
                    i--;
                }

            }
        }
    }
}
