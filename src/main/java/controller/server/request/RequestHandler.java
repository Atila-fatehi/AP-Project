package controller.server.request;

import controller.server.response.Response;

import java.util.ArrayList;

public interface RequestHandler {
    Response handleLoginRequest(LoginRequest loginRequest);
    Response handleSignUpRequest(SignUpRequest signUpRequest);
    Response handleShowLeaderBoardRequest(ShowLeaderBoardRequest showLeaderBoardRequest);
}
