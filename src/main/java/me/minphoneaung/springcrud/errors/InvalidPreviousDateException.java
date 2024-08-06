package me.minphoneaung.springcrud.errors;

public class InvalidPreviousDateException extends RuntimeException {

    public String message;

    @Override
    public String getMessage() {
        return message;
    }

    public InvalidPreviousDateException(String message) {
        this.message = message;
    }

}
