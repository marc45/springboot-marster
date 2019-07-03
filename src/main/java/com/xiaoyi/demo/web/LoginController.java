package com.xiaoyi.demo.web;

import com.google.code.kaptcha.Constants;
import com.xiaoyi.demo.entity.User;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import javax.servlet.http.HttpServletRequest;

/**
 * @Description
 * @auther 小懿
 * @create 2019-07-02 14:33
 */

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @PostMapping("/login")
    public String login(HttpServletRequest request, User user, boolean rememberMe, Model model, String verifyCode){
        //判断验证码
        String rightCode = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
        if (StringUtils.isNotBlank(verifyCode) && StringUtils.isNotBlank(rightCode) && verifyCode.equals(rightCode)) {
            //验证码通过
            // 创建用户登录的token
            UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
            // 创建subject主体
            Subject subject = SecurityUtils.getSubject();
            try {
                //主体提交登录请求到SecurityManager
                token.setRememberMe(rememberMe);
                subject.login(token);
            }catch (IncorrectCredentialsException ice){
                model.addAttribute("msg","密码不正确");
            }catch(UnknownAccountException uae){
                model.addAttribute("msg","账号不存在");
            }catch(AuthenticationException ae){
                model.addAttribute("msg","状态不正常");
            }
            if(subject.isAuthenticated()){
                model.addAttribute("username", getUser().getUsername());
                return "index";
            }else{
                token.clear();
                return "login";
            }
        } else {
            model.addAttribute("msg","验证码不正确");
            return "login";
        }
    }

    @GetMapping({"/","/index"})
    public String index(Model model){
        model.addAttribute("username", getUser().getUsername());
        return "index";
    }

    /* shiro获取当前用户
     * @return
     */
    private User getUser(){
        User currentUser = (User) SecurityUtils.getSubject().getPrincipal();
        return  currentUser;
    }

}
