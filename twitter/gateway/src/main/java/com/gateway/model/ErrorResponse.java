package com.gateway.model;

import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.Objects;

public class ErrorResponse implements Serializable {
    private String message;
    private HttpStatus status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ErrorResponse that = (ErrorResponse) o;
        return Objects.equals(message, that.message) &&
                Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {

        return Objects.hash(message, status);
    }

    public ErrorResponse(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    @Override
    public String toString() {
        return this.message + " " + this.status.value();
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}
