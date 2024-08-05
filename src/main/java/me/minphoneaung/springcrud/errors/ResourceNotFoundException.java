package me.minphoneaung.springcrud.errors;

public class ResourceNotFoundException extends RuntimeException {

    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    public ResourceNotFoundException(String message) {
        super(message);
        this.message = message;
    }

}
