package lv.ami.fuelmaster.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class CustomExceptionHandler extends AbstractBaseController {

    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, Model model) {
        //model.addAttribute("customAttribute", "Custom Value");
        return "error";
    }
}