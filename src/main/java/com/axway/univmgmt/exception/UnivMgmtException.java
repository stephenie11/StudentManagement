package com.axway.univmgmt.exception;

import org.springframework.http.HttpStatus;

public class UnivMgmtException extends Exception {

    private int statusCode;
    private String message;
    private StatusException statusException;

    public UnivMgmtException( StatusException statusException ) {
        super();
        this.statusCode = statusException.getStatusCode();
        this.message = statusException.getMessage();
        this.statusException = statusException;
    }

    public int getStatusCode() {
        return statusCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public StatusException getStatusException() {
        return statusException;
    }

    public HttpStatus getHttpStatus() {
        switch (statusException) {
            case RESOURCE_OK:
                return HttpStatus.OK;
            case RESOURCE_CREATED:
                return HttpStatus.CREATED;
            case RESOURCE_NO_CONTENT:
                return HttpStatus.NO_CONTENT;
            case RESOURCE_BAD_REQUEST:
                return HttpStatus.BAD_REQUEST;
            case RESOURCE_UNAUTHORIZED:
                return HttpStatus.UNAUTHORIZED;
            case RESOURCE_CONFLICT:
                return HttpStatus.CONFLICT;
            case RESOURCE_INTERNAL_SERVER_ERROR:
                return HttpStatus.INTERNAL_SERVER_ERROR;
            default:
                return HttpStatus.NOT_IMPLEMENTED;
        }
    }
}
