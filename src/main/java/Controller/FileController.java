package Controller;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.PrintWriter;
import java.nio.file.Paths;

public class FileController {
    public static void createFiles() {
        File file = new File(Paths.get("").toAbsolutePath() +"\\src\\main\\java\\dataBase\\settings.txt");
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
        file = new File(Paths.get("").toAbsolutePath() + "\\src\\main\\java\\dataBase\\abilityCode.txt");
        if (!file.exists()) {
            try {
                PrintWriter printWriter = new PrintWriter(file);
                printWriter.println(0);
                printWriter.flush();
                printWriter.close();
            } catch (Exception e) {

            }
        }
        file = new File(Paths.get("").toAbsolutePath() + "\\src\\main\\java\\dataBase\\XP.txt");
        if (!file.exists()) {
            try {
                PrintWriter printWriter = new PrintWriter(file);
                printWriter.println(0);
                printWriter.flush();
                printWriter.close();
            } catch (Exception e) {

            }
        }
        file = new File(Paths.get("").toAbsolutePath() + "\\src\\main\\java\\dataBase\\keys.txt");
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

    public static void writeSettings(String val1 , String val2){
        File file = new File(Paths.get("").toAbsolutePath() + "\\src\\main\\java\\dataBase\\settings.txt");
        try {
            PrintWriter printWriter = new PrintWriter(file);
            printWriter.println(val1);
            printWriter.println(val2);
            printWriter.flush();
            printWriter.close();
        }catch (Exception e){

        }
    }
}
