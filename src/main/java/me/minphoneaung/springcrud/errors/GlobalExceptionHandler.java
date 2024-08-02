package me.minphoneaung.springcrud.errors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ModelAndView handleDataIntegrityViolationException(EmailAlreadyExistsException ex, Model model) {
        ModelAndView mav = new ModelAndView(ex.getViewName());
        mav.addObject("emailDuplicateError", ex.getMessage());
        return mav;
    };
}
