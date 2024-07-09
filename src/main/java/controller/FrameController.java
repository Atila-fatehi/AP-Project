package controller;

import java.awt.*;
import java.awt.event.KeyEvent;

public abstract class FrameController {
    public static void minimizeAllWindows(){
        try {
            Robot robot = new Robot();

            robot.keyPress(KeyEvent.VK_WINDOWS);
            robot.delay(100);

            robot.keyPress(KeyEvent.VK_M);
            robot.delay(100);

            robot.keyRelease(KeyEvent.VK_M);

            robot.keyRelease(KeyEvent.VK_WINDOWS);
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }
}
