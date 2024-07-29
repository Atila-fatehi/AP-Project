package controller.server;

import controller.util.Constants;

import java.io.IOException;
import java.net.Socket;

public abstract class RequestManager {

    public static Socket socket;

    public static boolean connect() {
        try {
            socket = new Socket(Constants.HOST , Constants.PORT);
            return true;
        }catch (Exception e){
            System.out.println("exception in server connection.");
            return false;
        }
    }
}
