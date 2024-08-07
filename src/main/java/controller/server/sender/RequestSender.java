package controller.server.sender;

import controller.server.RequestManager;
import controller.server.request.Request;
import controller.server.response.Response;

import java.io.*;
import java.net.Socket;

public class RequestSender {
    private static RequestSender instance;
    private final Socket socket;
    private final ObjectInputStream objectInputStream;
    private final ObjectOutputStream objectOutputStream;

    public static RequestSender getInstance() {
        if (instance == null) {
            try {
                instance = new RequestSender(RequestManager.socket);
                return instance;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    public RequestSender(Socket socket) throws IOException {
        this.socket = socket;
        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectInputStream = new ObjectInputStream(socket.getInputStream());
    }

    public Response sendRequest(Request request) {
        try {
            objectOutputStream.writeObject(request);
        } catch (Exception e) {
            System.out.println("e");
            e.printStackTrace();
        }
        try {
            return (Response) objectInputStream.readObject();
        } catch (Exception ee) {
            System.out.println("ee");
            return null;
        }
    }


}
