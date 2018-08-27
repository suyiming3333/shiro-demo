package com.sym.shiroweb.controller;


import com.sym.shiroweb.vo.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller()
public class UserController {


    @RequestMapping(value = "/doLogin",method= RequestMethod.POST,produces = "application/json;charset=utf-8")
    @ResponseBody
    public String doLogin(User user){
        //获取主体
        Subject subject = SecurityUtils.getSubject();
        //获取token
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(),user.getPassword());
        try{
            subject.login(token);
        }catch (AuthenticationException e){
            return e.getMessage();
        }

        return "login success";
    }

    @RequestMapping(value = "/testAdminRole",produces = "application/json;charset=utf-8")
    @ResponseBody
    @RequiresRoles("admin")
    public String testAdminRole(){
        System.out.println("有admin角色权限 可以访问！");
        return "admin access";
    }

    @RequestMapping(value = "/testSuperAdminRole",produces = "application/json;charset=utf-8")
    @ResponseBody
    @RequiresRoles("SuperAdminRole")
    public String testSuperAdminRole(){
        System.out.println("有SuperAdminRole角色权限 可以访问！");
        return "SuperAdminRole access";
    }

    @RequestMapping(value = "/testPermissionUserDel",produces = "application/json;charset=utf-8")
    @ResponseBody
    public String testPermissionUserDel(){
        System.out.println("有PermissionUserDel权限 可以访问！");
        return "PermissionUserDel access";
    }
}
