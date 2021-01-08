package com.zking.shiro01.utils;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;

public class Demo {
    public static void main(String[] args) {
        //1，创建SecurityManagerFactory工厂类
        IniSecurityManagerFactory securityManagerFactory=
                new IniSecurityManagerFactory("classpath:shiro-permission.ini");

        //2,创建SecurityManager
        SecurityManager securityManager = securityManagerFactory.getInstance();

        //3,将SecurityManager交由SecurityUtils
        SecurityUtils.setSecurityManager(securityManager);

        //4,获取Subject
        Subject subject = SecurityUtils.getSubject();

        /**
         * org.apache.shiro.authc.UnknownAccountException：账号错误（身份（Account）是账号）
         * org.apache.shiro.authc.IncorrectCredentialsException:密码错误（凭据（credentials）是密码）
         *
         */
        //5,创建登录令牌TOken
        UsernamePasswordToken token=new UsernamePasswordToken(
                "zs",
                "123"
        );
        //6,身份认证
        try {
            subject.login(token);
            System.out.println("认证成功");
        } catch (Exception e) {
            e.printStackTrace();
        }


        /**
         * 授权
         * org.apache.shiro.authz.UnauthorizedException:没有权限
         *
         */
        //角色认证
        try {
            if (subject.hasRole("role1")) {
                System.out.println("角色认证成功");
            } else {
                System.out.println("角色认证失败");
            }
            //subject.checkRole("role2");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //8授权验证
        try {
            if (subject.isPermitted("system:user:add")) {
                System.out.println("授权验证成功");
            } else {
                System.out.println("授权验证失败");
            }
            //subject.checkPermissions("system:user:add");
        } catch (Exception e) {
            e.printStackTrace();
        }


        //7,安全退出
        subject.logout();
        System.out.println("安全退出了");

    }
}
