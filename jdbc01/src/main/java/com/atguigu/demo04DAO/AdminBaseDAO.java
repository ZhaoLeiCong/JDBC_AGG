package com.atguigu.demo04DAO;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

/**
 * Billkin
 * 2023/6/6
 */
public class AdminBaseDAO extends BaseDAO<Admin> implements MyBaseDAO{
    @Override
    public void insert(Connection conn, Admin admin) {
        String sql = "insert into admin(user_name,pwd,birth,deposit) value(?,?,?,?)";
        update(conn,sql,admin.getUser_name(),admin.getPwd(),admin.getBirth(),admin.getDeposit());
    }

    @Override
    public void deleteByID(Connection conn, int id) {
        String sql = "delete from admin where id = ?";
        update(conn,sql,id);
    }

    @Override
    public void updateByID(Connection conn, Admin admin) {
        String sql = "update admin set user_name =?,pwd =?,birth=? where id =?";
        update(conn,sql,admin.getUser_name(),admin.getPwd(),admin.getBirth(),admin.getId());
    }

    @Override
    public Admin getAdminByID(Connection conn, int id) {
        String sql = "select id,user_name,birth,pwd,deposit from admin where id =?";

        return (Admin) getInstance(conn,sql,id);
    }

    @Override
    public List<Admin> getAll(Connection conn) {
        String sql ="select id,user_name,birth,pwd,deposit from admin";
        List list = getForList(conn,sql);
        return list;
    }

    @Override
    public Long getCount(Connection conn) {
        String sql = "select count(*) from admin";
        return (Long) getValue(conn,sql);
    }

    @Override
    public Date getMaxBirth(Connection conn) {
        String sql = "select max(birth) from admin ";
        return (Date) getValue(conn,sql);
    }
}
