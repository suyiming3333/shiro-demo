package com.sym;


import com.alibaba.druid.pool.DruidDataSource;
import com.sym.customrealm.CustomRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;

import org.apache.shiro.subject.Subject;

import org.junit.Test;

public class CustomRealmTest {


    @Test
    public void testCustomRealm(){

        CustomRealm customRealm = new CustomRealm();

        //构建DefaultSecurityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        //设置realm
        defaultSecurityManager.setRealm(customRealm);


        /**
         * matcher加密
         */
        //创建加密的matcher
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("md5");//设置加密的方法名
        matcher.setHashIterations(1);//设置加密循环次数

        //注入DefaultSecurityManager
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        //主题获取 及提交请求
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("suyiming","111111");

        //用户登录
        subject.login(usernamePasswordToken);
        System.out.println("is isAuthenticated:"+subject.isAuthenticated());

        subject.checkRole("admin");

        subject.checkPermission("user:delete");

//        //用户登出
//        subject.logout();
//        System.out.println("is isAuthenticated:"+subject.isAuthenticated());

    }
}
