package com.fqy;

import com.fqy.util.ShiroUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.text.IniRealm;

import org.apache.shiro.subject.Subject;
import org.junit.Test;

public class ShiroTest {

    @Test
    public void test(){
        //1.初始化shiro的安全管理器
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        //2.设置用户的安全信息到安全管理器
        Realm realm = new IniRealm("classpath:shiro.ini");
        securityManager.setRealm(realm);
        //3.使用SecurityUtils将securityManager设置到运行环境中
        SecurityUtils.setSecurityManager(securityManager);
        //4.创建一个Subject实例
        Subject subject = SecurityUtils.getSubject();
        //5.创建用于认证的认证器token令牌，记录用户认证的身份和凭证即账号和密码
        AuthenticationToken token =
                new UsernamePasswordToken("zhangsan","123456");
        System.out.println("用户认证状态："+subject.isAuthenticated());
        //6.主体要进行登录，登录的时候进行认证检查
        subject.login(token);
        //用户认证状态
        System.out.println("用户认证状态："+subject.isAuthenticated());

        //7.检查角色的授权状态
        System.out.println("是否拥有admin角色"+subject.hasRole("admin"));
        System.out.println("是否拥有public角色"+subject.hasRole("public"));

        //8.检查权限的授权状态
        System.out.println("product:view："+subject.isPermitted("product:view"));
        System.out.println("product:view："+subject.isPermitted("product:view","product:create")[1]);

        //主体信息
        System.out.println("用户名：" + subject.getPrincipal());

        //退出
        subject.logout();
        System.out.println("用户认证状态："+subject.isAuthenticated());
    }

    @Test
    public void test2(){
        //登录认证
        Subject subject = ShiroUtil.login("zhangsan", "123456");
        //授权资源检查
        System.out.println("是否拥有admin角色："+ subject.hasRole("admin"));
        //退出系统
        subject.logout();
    }
}
