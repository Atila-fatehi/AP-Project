package controller.server.request;

import controller.server.response.Response;

import java.io.Serializable;

public class SignUpRequest implements Request, Serializable {
    private String username;
    private String password;
    private int xp;

    public SignUpRequest(String username, String password , int xp) {
        this.username = username;
        this.password = password;
        this.xp = xp;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    @Override
    public Response handle(RequestHandler requestHandler) {
        return requestHandler.handleSignUpRequest(this);
    }
}

