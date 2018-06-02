package com.axway.univmgmt.exception;

public enum StatusException {

    RESOURCE_OK(200, "Resource ok"),
    RESOURCE_CREATED(201, "Resource created"),
    RESOURCE_NO_CONTENT(204, "Resource no content"),
    RESOURCE_BAD_REQUEST(400, "Resource bad request"),
    RESOURCE_UNAUTHORIZED(401, "Resource unauthorized"),
    RESOURCE_CONFLICT(409, "Resource conflict"),
    RESOURCE_INTERNAL_SERVER_ERROR(500, "Resource internal server error");

    private int statusCode;
    private String message;

    StatusException( int statusCode, String message ) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }
}
