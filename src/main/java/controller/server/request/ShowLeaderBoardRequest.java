package controller.server.request;

import controller.server.response.Response;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public class ShowLeaderBoardRequest implements Request , Serializable {
    @Override
    public Response handle(RequestHandler requestHandler) {
        return requestHandler.handleShowLeaderBoardRequest(this);
    }
}
