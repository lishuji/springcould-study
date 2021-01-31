package com.licky.elasticsearch.controller;

import com.licky.elasticsearch.utils.ESUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping
public class CreatIndexController {

    @Resource
    ESUtils esUtils;

    public void creatIndex(@RequestBody String content){

        esUtils.TestCreateIndex();

    }

}
