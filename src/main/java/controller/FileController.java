package controller;

import controller.util.Constants;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public abstract class FileController {
    public static void createFiles() {
        File file = new File(Constants.SETTING_PATH);
        if (!file.exists()) {
            try {
                PrintWriter printWriter = new PrintWriter(file);
                printWriter.println(50);
                printWriter.println(1);
                printWriter.flush();
                printWriter.close();
            } catch (Exception e) {

            }
        }
        file = new File(Constants.ABILITY_PATH);
        if (!file.exists()) {
            try {
                PrintWriter printWriter = new PrintWriter(file);
                printWriter.println(0);
                printWriter.flush();
                printWriter.close();
            } catch (Exception e) {

            }
        }
        file = new File(Constants.XP_PATH);
        if (!file.exists()) {
            try {
                PrintWriter printWriter = new PrintWriter(file);
                printWriter.println(0);
                printWriter.flush();
                printWriter.close();
            } catch (Exception e) {

            }
        }
        file = new File(Constants.KEYS_PATH);
        if (!file.exists()) {
            try {
                PrintWriter printWriter = new PrintWriter(file);
                printWriter.println(KeyEvent.VK_W);
                printWriter.println(KeyEvent.VK_A);
                printWriter.println(KeyEvent.VK_S);
                printWriter.println(KeyEvent.VK_D);
                printWriter.println(KeyEvent.VK_SPACE);
                printWriter.println(KeyEvent.VK_R);
                printWriter.flush();
                printWriter.close();
            } catch (Exception e) {

            }
        }
    }

    public static void writeSettings(String val1, String val2) {
        File file = new File(Constants.SETTING_PATH);
        try {
            PrintWriter printWriter = new PrintWriter(file);
            printWriter.println(val1);
            printWriter.println(val2);
            printWriter.flush();
            printWriter.close();
        } catch (Exception e) {

        }
    }

    public static void writeKeys(int w, int s, int d, int a, int sh, int ab) {
        File file = new File(Constants.KEYS_PATH);
        try {
            PrintWriter printWriter = new PrintWriter(file);
            if (w != -1) {
                printWriter.println(w);
            } else {
                printWriter.println(KeyEvent.VK_W);
            }
            if (a != -1) {
                printWriter.println(a);
            } else {
                printWriter.println(KeyEvent.VK_A);
            }
            if (s != -1) {
                printWriter.println(s);
            } else {
                printWriter.println(KeyEvent.VK_S);
            }
            if (d != -1) {
                printWriter.println(d);
            } else {
                printWriter.println(KeyEvent.VK_D);
            }
            if (sh != -1) {
                printWriter.println(sh);
            } else {
                printWriter.println(KeyEvent.VK_SPACE);
            }
            if (ab != -1) {
                printWriter.println(ab);
            } else {
                printWriter.println(KeyEvent.VK_R);
            }
            printWriter.flush();
            printWriter.close();
        } catch (Exception e) {

        }
    }

    public static void writeXP(int xp) {
        File file = new File(Constants.XP_PATH);
        try {
            PrintWriter printWriter = new PrintWriter(file);
            printWriter.println(xp);
            printWriter.flush();
            printWriter.close();
        } catch (Exception e) {

        }
    }

    public static String readXP() {
        File file = new File(Constants.XP_PATH);
        try {
            Scanner scanner = new Scanner(file);
            return scanner.nextLine();
        } catch (Exception e) {
            return "";
        }
    }

    public static int[] readKeyCodes() {
        int[] codes = new int[6];
        File file = new File(Constants.KEYS_PATH);
        try {
            Scanner scanner = new Scanner(file);
            codes[0] = Integer.parseInt(scanner.nextLine());
            codes[1] = Integer.parseInt(scanner.nextLine());
            codes[2] = Integer.parseInt(scanner.nextLine());
            codes[3] = Integer.parseInt(scanner.nextLine());
            codes[4] = Integer.parseInt(scanner.nextLine());
            codes[5] = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            return null;
        }
        return codes;
    }

    public static int[] readSettings() {
        int[] codes = new int[2];
        File file = new File(Constants.SETTING_PATH);
        try {
            Scanner scanner = new Scanner(file);
            codes[0] = Integer.parseInt(scanner.nextLine());
            codes[1] = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            return null;
        }
        return codes;
    }

    public static int readAbilities() {
        File file = new File(Constants.ABILITY_PATH);
        try {
            Scanner scanner = new Scanner(file);
            return Integer.parseInt(scanner.nextLine());

        } catch (Exception e) {
            return 0;
        }
//        java.util.Timer timer = new java.util.Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                epsilon.getAbility().setAres(false);
//                epsilon.getAbility().setAceso(false);
//                epsilon.getAbility().setProteus(false);
//                timer.cancel();
//            }
//        }, 5 * 60 * 1000, 1111);
    }
}
