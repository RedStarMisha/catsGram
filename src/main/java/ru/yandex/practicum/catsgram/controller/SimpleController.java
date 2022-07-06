package ru.yandex.practicum.catsgram.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.catsgram.service.HackCatService;

import java.util.Optional;
import java.util.logging.Level;

@RestController
    public class SimpleController {
        private final static Logger log = LoggerFactory.getLogger(SimpleController.class);
        private final HackCatService hackCatService;

    @Autowired
    public SimpleController(HackCatService hackCatService) {
        this.hackCatService = hackCatService;
    }

        @GetMapping("/home")
        public String homePage() {
            log.info("Запрос получен.");
            return "Котограм";
        }

    @GetMapping("/do-hack")
    public Optional<String> doHack(){
        return hackCatService.doHackNow();
        // хакните этих котиков
    }

    }

