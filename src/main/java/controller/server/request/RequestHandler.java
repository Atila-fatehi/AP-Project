package controller.server.request;

import controller.server.response.Response;

public interface RequestHandler {
    Response handleLoginRequest(LoginRequest loginRequest);
}
