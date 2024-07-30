package controller.server.request;

import controller.server.response.Response;

public interface Request {
    Response handle(RequestHandler requestHandler);
}
