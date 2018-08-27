package com.sym;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

public class AuthenticationTest {

    SimpleAccountRealm simpleAccountRealm = new SimpleAccountRealm();


    @Before
    public void addUser(){
        //simpleAccountRealm 新增用户
        simpleAccountRealm.addAccount("suyiming","111111","admin");
    }


    @Test
    public void testAuthentication(){


        //构建DefaultSecurityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        //设置realm
        defaultSecurityManager.setRealm(simpleAccountRealm);
        //注入DefaultSecurityManager
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        //主题获取 及提交请求
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("suyiming","111111");

        //用户登录
        subject.login(usernamePasswordToken);
        System.out.println("is isAuthenticated:"+subject.isAuthenticated());

        subject.checkRole("admin");

//        //用户登出
//        subject.logout();
//        System.out.println("is isAuthenticated:"+subject.isAuthenticated());

    }
}
