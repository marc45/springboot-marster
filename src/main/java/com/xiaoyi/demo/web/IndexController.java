package com.xiaoyi.demo.web;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @auther 小懿
 * @create 2019-07-02 15:38
 */
@RestController
public class IndexController {

    @GetMapping("/dog")
    public String dog(){
        Subject subject = SecurityUtils.getSubject();
        if(subject.hasRole("dog")){
            return "dog√";
        }
        else {
            return  "dog×";
        }
    }

    @GetMapping("/cat")
    public String cat(){
        Subject subject = SecurityUtils.getSubject();
        if(subject.hasRole("cat")){
            return "cat√";
        }
        else {
            return  "cat×";
        }
    }


    @GetMapping("/sing")
    @RequiresRoles("cat")
    public String sing(){
        return "sing";
    }
    @GetMapping("/jump")
    @RequiresPermissions("jump")
    public String jump(){
        return "jump";
    }
    @GetMapping("/rap")
    public String rap(){
        Subject subject = SecurityUtils.getSubject();
        if(subject.isPermitted("rap")){
            return "rap";
        }else{
            return "没权限你Rap个锤子啊!";
        }

    }
    @GetMapping("/basketball")
    public String basketball(){
        Subject subject = SecurityUtils.getSubject();
        if(subject.isPermitted("basketball")){
            return "basketball";
        }else{
            return "你会打个粑粑球!";
        }
    }
}
