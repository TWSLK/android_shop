package com.bear.ma079;



import java.io.Serializable;

public class User implements Serializable {
    private int id;
    private String username;
    private String password;
    private String nickname;
    private String phone;
    private String type;
    private String gold;
    private String realname;
    private String address;
    private String starttime;



    private String validatetime;

    public String getValidatetime() {
        return validatetime;
    }

    public void setValidatetime(String validatetime) {
        this.validatetime = validatetime;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getNewgold() {
        return newgold;
    }

    public void setNewgold(String newgold) {
        this.newgold = newgold;
    }

    private String newgold;




    public String getZhifubao() {
        return zhifubao;
    }

    public void setZhifubao(String zhifubao) {
        this.zhifubao = zhifubao;
    }

    private String zhifubao;

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public User(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname=nickname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGold() {
        return gold;
    }

    public void setGold(String gold) {
        this.gold = gold;
    }

    @Override
    public String toString() {
        return this.id+this.username+this.password+this.nickname+this.phone+this.gold+this.type;
    }
}
