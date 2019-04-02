package com.cognibank.securityMicroservice.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class User implements Serializable {
    private static final long serialVersionUID = 4L;

    @Id
    private String userId;
    @Column(name="userName")
    private String userName;
    @Column(name="password")
    private String password;
    @Column(name="email")
    private String email;
    @Column(name="phone")
    private String phone;
    @Column(name="otpCode")
    private String otpCode;
    @Column(name="authID")
    private String authID;

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", otpCode='" + otpCode + '\'' +
                ", authID='" + authID + '\'' +
                '}';
    }

    public User withPassword (final String password) {
        setPassword (password);
        return this;
    }
    public User withUserName (final String userName) {
        setUserName (userName);
        return this;
    }


    public String getAuthID() {
        return authID;
    }

    public void setAuthID(String authID) {
        this.authID = authID;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOtpCode() {
        return otpCode;
    }

    public void setOtpCode(String otpCode) {
        this.otpCode = otpCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }



}
