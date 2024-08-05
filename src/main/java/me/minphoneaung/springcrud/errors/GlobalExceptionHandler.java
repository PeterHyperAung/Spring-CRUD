package me.minphoneaung.springcrud.errors;

import me.minphoneaung.springcrud.web.rest.dto.StudentDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

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
