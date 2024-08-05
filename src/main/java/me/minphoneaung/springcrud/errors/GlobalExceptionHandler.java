package me.minphoneaung.springcrud.errors;

import me.minphoneaung.springcrud.students.Student;
import me.minphoneaung.springcrud.students.StudentDto;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

@ControllerAdvice
@Controller
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ModelAndView handleDataIntegrityViolationException(EmailAlreadyExistsException ex) {
        var dto = new StudentDto("", "", null, null, null);
        ModelAndView mav = new ModelAndView(ex.getViewName() + ".html");
        mav.addObject("emailDuplicateError", ex.getMessage());
        mav.addObject("student", dto);
        return mav;
    };

    @ExceptionHandler(ResourceNotFoundException.class)
    public String handleResourceNotFoundException(ResourceNotFoundException ex) {
        return "error/404";
    }
}
