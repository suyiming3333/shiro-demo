package com.sym.shiroweb.session;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.web.session.mgt.WebSessionKey;

import javax.servlet.ServletRequest;
import java.io.Serializable;

public class CustomSessionManager extends DefaultSessionManager{


    /**
     * @description 重写读取session方法优化读取redisn session 性能
     * @param sessionKey
     * @return
     * @throws UnknownSessionException
     */
    @Override
    protected Session retrieveSession(SessionKey sessionKey) throws UnknownSessionException {
        Serializable sessionId = this.getSessionId(sessionKey);

        ServletRequest request = null;
        if(sessionKey instanceof WebSessionKey){
            request = ((WebSessionKey)sessionKey).getServletRequest();
        }
        if(request!=null && sessionId !=null){
            Session session = (Session) request.getAttribute(sessionId.toString());
            if(session !=null){
                return session;
            }
        }

        //如果request里面不带sesion，则重福利方法获取session,并放入request
        Session session = super.retrieveSession(sessionKey);
        if(request!=null && sessionId !=null){
            request.setAttribute(sessionId.toString(),session);
        }
        return session;
    }
}
