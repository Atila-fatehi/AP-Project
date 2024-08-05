package controller.server.response;

import java.io.Serializable;
import java.util.ArrayList;

public class Response implements Serializable {
    private String message;
    private ArrayList<String> list;

    public Response(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<String> getList() {
        return list;
    }

    public void setList(ArrayList<String> list) {
        this.list = list;
    }
}
