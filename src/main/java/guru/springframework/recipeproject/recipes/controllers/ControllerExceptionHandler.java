package guru.springframework.recipeproject.recipes.controllers;

import guru.springframework.recipeproject.recipes.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NumberFormatException.class)
    public ModelAndView handleBadNumber(Exception exception){
        log.error("Handling number format exception.");

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("errors/400");
        modelAndView.addObject("exception", exception);

        return modelAndView;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFound(Exception exception){
        log.error("Handling not found exception.");

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("errors/404");
        modelAndView.addObject("exception", exception);

        return modelAndView;
    }

}
