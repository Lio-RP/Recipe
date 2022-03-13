package com.springframework.webdevelopment.recipeproject.controllers;

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
    public ModelAndView handNumberFormatException(Exception exception){

        log.debug("Handling 400 Bad Request.........");
        log.debug(exception.getMessage());

        ModelAndView mav = new ModelAndView();

        mav.setViewName("400Error");
        mav.addObject("exception", exception);

        return mav;
    }
}
