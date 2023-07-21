package sfu.cmpt276.project.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GoogleMapsController {

    @GetMapping("/showMap")
    public String index() {
        return "maps/index";
    }
}