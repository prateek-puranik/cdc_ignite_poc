package com.example.IgniteTrial.controller;

import com.example.IgniteTrial.listner.JMSListner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class ListnerController {

    @Autowired
    JMSListner listner;

    /**
     * Call this controller localhost:8080(GET) to start the AsycJMSListner
     */
    @GetMapping
    public String sayHello() throws Exception {
        listner.ListnerStarter();
        return "Listner Started";
    }
}
