package com.example.hackernewsapi.controller;

import com.example.hackernewsapi.service.APIService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/hacker-api")
public class APIController {

    private final APIService service;

    @RequestMapping
    public List<String> launchApp() {
        return service.launchApp();
    }
}
