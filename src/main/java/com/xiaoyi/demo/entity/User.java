package com.xiaoyi.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Description
 * @auther 小懿
 * @create 2019-07-02 14:14
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity{

    private String username;
    private String password;
    private Boolean available;
    private String salt;
    private String role;
    private List<String> permissions;

    /**
     *
     * 重写获取盐值方法，自定义realm使用
     * Gets credentials salt.
     *
     * @return the credentials salt
     */
    public String getCredentialsSalt() {
        return username + "shiro" + salt;
    }


}
