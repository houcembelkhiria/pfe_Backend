package com.youtube.jwt.controller;

import com.youtube.jwt.entity.UrlCount;
import com.youtube.jwt.entity.User;
import com.youtube.jwt.service.UrlCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UrlCountController {
    private final UrlCountService urlCountService;

    @Autowired
    public UrlCountController(UrlCountService urlCountService) {
        this.urlCountService = urlCountService;
    }

    @PostMapping("/url_count")
    public HttpStatus incrementUrlCount(@RequestBody String url) {
        urlCountService.incrementUrlCount(url);
        return HttpStatus.OK;
    }

    @GetMapping("/allUrls")
    public List<UrlCount> getAllUrls() {
        return urlCountService.getAllUrls();
    }
}
