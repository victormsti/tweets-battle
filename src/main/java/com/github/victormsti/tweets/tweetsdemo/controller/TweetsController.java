package com.github.victormsti.tweets.tweetsdemo.controller;

import com.github.victormsti.tweets.tweetsdemo.model.Tweet;
import com.github.victormsti.tweets.tweetsdemo.service.TweetsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
//@CrossOrigin(origins = {"http://localhost:4200"})
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TweetsController {

    private TweetsService service;

    public TweetsController(TweetsService service){
        this.service = service;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/tweets")
    public List<Tweet> getTweets(){
        return service.getTweets();
    }

}
