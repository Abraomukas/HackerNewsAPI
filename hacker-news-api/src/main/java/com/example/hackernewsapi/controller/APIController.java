package com.example.hackernewsapi.controller;

import com.example.hackernewsapi.service.APIService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/hacker-api")
public class APIController {

    private APIService service;

    @RequestMapping
    public void launchApp() {
        service.launchApp();
    }
}
