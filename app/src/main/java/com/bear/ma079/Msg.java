package com.bear.ma079;

import java.io.Serializable;

public class Msg implements Serializable {
    private String id;
    private String gid;
    private String uid;
    private String uname;
    private String msg;


    public Msg( ) {
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return this.id+this.uname+this.gid+this.uid+this.msg;
    }
}