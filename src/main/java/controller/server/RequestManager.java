package controller.server;

import controller.util.Constants;
import view.frames.LoginFrame;
import view.frames.MainMenu;

import java.io.IOException;
import java.net.Socket;

public abstract class RequestManager {

    public static Socket socket;

    public static boolean connect() {
        try {
            socket = new Socket(Constants.HOST , Constants.PORT);
            LoginFrame.getInstance().setVisible(true);
            return true;
        }catch (Exception e){
            MainMenu.getInstance().serverStatus = "Disconnected.";
            MainMenu.getInstance().refresh();
            return false;
        }
    }









}
