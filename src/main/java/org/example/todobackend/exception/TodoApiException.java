package org.example.todobackend.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class TodoApiException extends ResponseStatusException {
    public TodoApiException(HttpStatusCode statusCode,String message) {
        super(statusCode, message);
    }
}
