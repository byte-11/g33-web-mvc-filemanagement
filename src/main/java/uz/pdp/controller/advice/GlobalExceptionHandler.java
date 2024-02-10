package uz.pdp.controller.advice;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import uz.pdp.controller.exception.UserNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({UserNotFoundException.class})
    public String handle(Model model, Exception ex) {
//        modelAndView.setViewName("auth");
//        modelAndView.addObject("error", ex.getMessage());
        model.addAttribute("error", ex.getMessage());
        return "auth";
    }

    @ExceptionHandler({EmptyResultDataAccessException.class})
    public String handleEmptyResult(Model model, Exception ex) {
//        modelAndView.setViewName("auth");
//        modelAndView.addObject("error", ex.getMessage());
        model.addAttribute("error", ex.getMessage());
        return "auth";
    }

}
