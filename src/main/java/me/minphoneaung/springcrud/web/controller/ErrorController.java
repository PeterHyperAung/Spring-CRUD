package me.minphoneaung.springcrud.web.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

public abstract class ErrorController {

    @GetMapping("/error")
    public String errorHandler(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        System.out.println("STATUS");
        System.out.println(status);
        if(status != null) {
            int statusCode = Integer.parseInt(status.toString());

            if(statusCode == 404) {
                return "error/404";
            }

        }

        return "students-list";
    }
}
