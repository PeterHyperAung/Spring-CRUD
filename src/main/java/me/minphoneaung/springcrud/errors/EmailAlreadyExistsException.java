package me.minphoneaung.springcrud.errors;


public class EmailAlreadyExistsException extends RuntimeException {
    private String message;
    private String viewName;

    public String getViewName() {
        return viewName;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public EmailAlreadyExistsException(String msg, String viewName) {
        super(msg);
        this.message = msg;
        this.viewName = viewName;
    }
}
