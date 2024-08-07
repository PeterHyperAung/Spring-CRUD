package me.minphoneaung.springcrud.web.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import me.minphoneaung.springcrud.dto.PaginationResponseDto;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

public abstract class ViewController implements PaginableViewController {

    public <T> void addPaginationAttribute(Model model, PaginationResponseDto<T> resource, String resourceName) {
        model.addAttribute("pageNo", resource.pageNo());
        model.addAttribute("previousPage", resource.pageNo() - 1);
        model.addAttribute("nextPage", resource.pageNo() + 1);
        model.addAttribute("pageSize", resource.pageSize());
        model.addAttribute("totalPages", resource.totalPages());
        model.addAttribute("itemsCountInCurrentPage", resource.itemsCountInCurrentPage());
        model.addAttribute(resourceName, resource.data());
    }

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
