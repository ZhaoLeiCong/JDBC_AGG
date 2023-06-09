package com.atguigu.demo01;

import java.util.Objects;

/**
 * Billkin
 * 2023/6/4
 */
public class Admin {
    private int id;
    private String user_name;
    private String pwd;

    public Admin() {
    }

    public Admin(int id, String user_name, String pwd) {
        this.id = id;
        this.user_name = user_name;
        this.pwd = pwd;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Admin)) return false;
        Admin admin = (Admin) o;
        return id == admin.id && Objects.equals(user_name, admin.user_name) && Objects.equals(pwd, admin.pwd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user_name, pwd);
    }

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + id +
                ", user_name='" + user_name + '\'' +
                ", pwd='" + pwd + '\'' +
                '}';
    }
}
