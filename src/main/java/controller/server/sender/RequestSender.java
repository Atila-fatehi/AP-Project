package controller.server.sender;

import controller.server.RequestManager;
import controller.server.request.Request;

import java.io.*;
import java.net.Socket;

public class RequestSender {
    private static RequestSender instance;
    private final Socket socket;
    private final BufferedReader in;
    private final PrintWriter out;
    private final ObjectInputStream objectInputStream;
    private final ObjectOutputStream objectOutputStream;

    public static RequestSender getInstance() {
        if (instance == null) {
            try {
                instance = new RequestSender(RequestManager.socket);
                return instance;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return instance;
    }
    public RequestSender(Socket socket) throws IOException {
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectInputStream = new ObjectInputStream(socket.getInputStream());
    }

    public String sendRequest(Request request) {
        try {
            objectOutputStream.writeObject(request);
        } catch (Exception e) {
            System.out.println("e");
            e.printStackTrace();
        }
        try {
            return in.readLine();
        }catch (Exception ee){
            System.out.println("ee");
            return null;
        }
    }



    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
