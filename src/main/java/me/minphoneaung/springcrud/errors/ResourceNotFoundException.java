package me.minphoneaung.springcrud.errors;

public class ResourceNotFoundException extends RuntimeException {

    private String message;
    private String viewName;

    public String getViewName() {
        return viewName;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public ResourceNotFoundException(String message) {
        super(message);
        this.message = message;
    }

    public ResourceNotFoundException(String message, String viewName) {
        super(message);
        this.message = message;
        this.viewName = viewName;
    }


}
