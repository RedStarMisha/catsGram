package ru.yandex.practicum.catsgram.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Level;

@RestController
    public class SimpleController {
        private final static Logger log = LoggerFactory.getLogger(SimpleController.class);
        @GetMapping("/home")
        public String homePage() {
            log.info("Запрос получен.");
            return "Котограм";
        }
    }

