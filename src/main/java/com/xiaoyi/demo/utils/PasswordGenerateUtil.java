package com.xiaoyi.demo.utils;

import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * @Description
 * @auther 小懿
 * @create 2019-07-03 9:14
 */
public class PasswordGenerateUtil {

    /**
     * 获取，i，啊
     * @param username
     * @param password
     * @param salt
     * @param hashTimes
     * @return
     */
    public static String getPassword(String username,String password,String salt,int hashTimes){
        Md5Hash md5Hash = new Md5Hash(password,salt,hashTimes);
        return md5Hash.toString();
    }
}
