package com.fqy.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;

public class ShiroUtil {
    /**
     * 初始化shiro的运行环境
     */
    static {
        //1.初始化shiro的安全管理器
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        //2.设置用户的安全信息到安全管理器
        Realm realm = new IniRealm("classpath:shiro.ini");
        securityManager.setRealm(realm);
        //3.使用SecurityUtils将securityManager设置到运行环境中
        SecurityUtils.setSecurityManager(securityManager);
    }
    public static Subject login(String username,String password){
        //1.创建一个Subject实例
        Subject subject = SecurityUtils.getSubject();
        //2.创建用于认证的认证器token令牌，记录用户认证的身份和凭证即账号和密码
        AuthenticationToken token =
                new UsernamePasswordToken(username,password);
        //3.主体要进行登录，登录的时候进行认证检查
        subject.login(token);
        //用户认证状态
        System.out.println("用户认证状态："+subject.isAuthenticated());

        return subject;
    }
}
