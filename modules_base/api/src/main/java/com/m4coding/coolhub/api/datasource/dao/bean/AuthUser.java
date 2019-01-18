package com.m4coding.coolhub.api.datasource.dao.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author mochangsheng
 * @description 数据库存储  认证登录的用户信息
 */
@Entity
public class AuthUser {

    private String token;
    private String scopes;
    private String userName;
    private String userAvatar;

    @Generated(hash = 810107285)
    public AuthUser(String token, String scopes, String userName,
            String userAvatar) {
        this.token = token;
        this.scopes = scopes;
        this.userName = userName;
        this.userAvatar = userAvatar;
    }

    @Generated(hash = 1740224645)
    public AuthUser() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getScopes() {
        return scopes;
    }

    public void setScopes(String scopes) {
        this.scopes = scopes;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    @Override
    public String toString() {
        return "AuthUser{" +
                "token='" + token + '\'' +
                ", scopes='" + scopes + '\'' +
                ", userName='" + userName + '\'' +
                ", userAvatar='" + userAvatar + '\'' +
                '}';
    }
}
