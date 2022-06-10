package com.remcoil.timetracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin(origins= "*", methods = {RequestMethod.GET, RequestMethod.DELETE, RequestMethod.PUT})
@SpringBootApplication
public class TimeTrackerApplication {
    @GetMapping("/")
    @ResponseBody
    String home() {
        return "Hello World!";
    }

    @DeleteMapping("/")
    @ResponseBody
    String homeDelete() {
        return "Resource deleted!";
    }

    public static void main(String[] args) {
        SpringApplication.run(TimeTrackerApplication.class, args);
    }
}
