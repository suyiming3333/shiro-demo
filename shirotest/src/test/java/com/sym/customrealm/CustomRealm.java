package com.sym.customrealm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CustomRealm extends AuthorizingRealm {

    Map<String,String> userMap = new HashMap<>();
    {
        //密码为md5带盐加密的密文
        userMap.put("suyiming","f11c5a29e248aa48215aa1029c9933cf");
        super.setName("customRealm");
    }

    //重写授权方法
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //从主题传过来的token获取用户名
        String username = (String) principalCollection.getPrimaryPrincipal();

        Set<String> roles = getRolesByUsername(username);
        Set<String> permissions = getPermissionsByUsername(username);

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setRoles(roles);
        simpleAuthorizationInfo.setStringPermissions(permissions);
        return simpleAuthorizationInfo;
    }


    //模拟从数据库中获取权限数据
    private Set<String> getRolesByUsername(String username) {
        Set<String> roleResults = new HashSet<>();
        roleResults.add("admin");
        roleResults.add("user");
        return roleResults;
    }

    //模拟从数据库中获取角色数据
    private Set<String> getPermissionsByUsername(String username) {
        Set<String> permissionsResults = new HashSet<>();
        permissionsResults.add("user:delete");
        permissionsResults.add("user:add");
        return permissionsResults;
    }


    //重写验证方法
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        //从主题传过来的token获取用户名
        String username = (String) authenticationToken.getPrincipal();

        String password = getPasswordByUsername(username);

        if(password==null){
            return null;
        }
        //返回验证信息
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo("suyiming","111111","customRealm");
        //假如设置是盐，验证信息返回时需要设置盐
        simpleAuthenticationInfo.setCredentialsSalt(ByteSource.Util.bytes("suyiming"));
        return simpleAuthenticationInfo;
    }

    //这里应该冲数据库或者缓存获取用户密码
    private String getPasswordByUsername(String username) {
        return userMap.get(username).toString();
    }

    public static void main(String[] args) {
        Md5Hash md5Hash = new Md5Hash("111111","suyiming");
        System.out.println(md5Hash.toString());
    }
}
