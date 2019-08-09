package com.yesp.server.controller;

import com.yesp.server.sevice.initServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping(name = "initController" , value = {"/init"})
public class initController {

    @Autowired
    public initServiceImpl initService;

    @RequestMapping("/init")
    public ResponseEntity<Void> init(){

       // System.out.println(1);

        initService.init();

        return new ResponseEntity<Void>(HttpStatus.OK);
    }



}
