package com.atguigu.demo04DAO;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

/**
 * Billkin
 * 2023/6/6
 */
public interface MyBaseDAO {
    //插入数据
    abstract void insert(Connection conn,Admin admin);
    //删除数据
    abstract void deleteByID(Connection conn,int id);
    //更新数据
    abstract void updateByID(Connection conn,Admin admin);
    //查询一条数据
    abstract Admin getAdminByID(Connection conn,int id);
    //查询表中所有数据
    abstract List<Admin> getAll(Connection conn);
    //查询表中数据的个数
    abstract Long getCount(Connection conn);
    //查看生日最大值
    abstract Date getMaxBirth(Connection conn);
}
