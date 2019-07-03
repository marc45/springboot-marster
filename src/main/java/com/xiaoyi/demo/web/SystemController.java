package com.xiaoyi.demo.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description
 * @auther 小懿
 * @create 2019-07-02 10:44
 */

@Controller
@RequestMapping("/api")
public class SystemController {

    /**
     * @CrossOrigin:  局部跨域注解
     * @return
     */
    @RequestMapping(value = "/hello")
    @ResponseBody
    public String hello(){
        return "Hello Word";
    }
}

