package com.sym.shiroweb.filter;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class MyFilter extends AuthorizationFilter {
    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        //获取主体
        Subject subject = getSubject(servletRequest,servletResponse);
        String[] roles = (String[])o;
        if(roles==null || roles.length==0){
            return true;
        }
        for(String role:roles){
            if(subject.isPermitted(role)){
                return true;
            }
        }
        return false;
    }
}
