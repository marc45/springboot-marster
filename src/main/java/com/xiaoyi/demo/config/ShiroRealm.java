package com.xiaoyi.demo.config;

import com.xiaoyi.demo.entity.User;
import com.xiaoyi.demo.utils.PasswordGenerateUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @auther 小懿
 * @create 2019-07-02 14:08
 * 自定义Realm：域，Shiro从从Realm获取安全数据（如用户、角色、权限），
 * 就是说SecurityManager要验证用户身份，那么它需要从Realm获取相应的用户进行比较以确定用户身份是否合法；
 * 也需要从Realm得到用户相应的角色/权限进行验证用户是否能进行操作；
 */
public class ShiroRealm extends AuthorizingRealm {
    /**
     * 用户授权
     * @param principal
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
        System.out.println("调用了授权方法");
        // 1.获取当前登录的用户
        User user = (User) principal.getPrimaryPrincipal();
        // 2.通过SimpleAuthenticationInfo做授权
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        // 3.获取数据库用户角色信息并将用户角色信息添加到SimpleAuthenticationInfo
        simpleAuthorizationInfo.addRole(user.getRole());
        // 4.获取数据库用户权限信息并将用户权限信息添加到SimpleAuthenticationInfo
        simpleAuthorizationInfo.addStringPermissions(user.getPermissions());
        return simpleAuthorizationInfo;
    }

    /**
     * 用户认证
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // 获得token中的用户名
        String username = (String)token.getPrincipal();
        // 通过用户名获取数据库的用户信息
        User user = getUserByUserName(username);
        if(user == null){
            return null;
        }
        //3.通过SimpleAuthenticationInfo做身份处理
        SimpleAuthenticationInfo simpleAuthenticationInfo =
                new SimpleAuthenticationInfo(user,user.getPassword(),  ByteSource.Util.bytes(user.getCredentialsSalt()),getName());
        //4.用户账号状态验证等其他业务操作
        if(!user.getAvailable()){
            throw new AuthenticationException("该账号已经被禁用");
        }
        //5.返回身份处理对象
        return simpleAuthenticationInfo;
    }

    /**
     * 模拟通过username从数据库中查找到user实体
     * @param username
     * @return
     */
    private User getUserByUserName(String username){
        List<User> users = getUsers();
        for(User user : users){
            if(user.getUsername().equals(username)){
                return user;
            }
        }
        return null;
    }

    /**
     * 模拟数据库数据
     * @return
     */
    private List<User> getUsers(){
        List<User> users = new ArrayList<>(2);
        List<String> cat = new ArrayList<>(2);
        cat.add("sing");
        cat.add("rap");
        List<String> dog = new ArrayList<>(2);
        dog.add("jump");
        dog.add("basketball");
        users.add(new User("admin", PasswordGenerateUtil.getPassword("admin", "123456", "admin" + "shiro" + "1", 2), true, "1", "cat", cat));
        users.add(new User("test",PasswordGenerateUtil.getPassword("test", "123456", "admin" + "shiro" + "1", 2),true,"1","dog", dog));
        return users;
    }
}
