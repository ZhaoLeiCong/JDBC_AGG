package com.atguigu.demo04DAO;

import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Billkin
 * 2023/6/6
 * BaseDAO:封装通用的增删改查的操作
 * 封装了针对于数据表的通用操作
 */
public abstract class BaseDAO<T> {
    private Class<T> clazz = null;
    {
        //获取BaseDAO子类带泛型的父类的泛型
        Type genericSuperclass = this.getClass().getGenericSuperclass();
        ParameterizedType paramType = (ParameterizedType) genericSuperclass;
        Type[] types = paramType.getActualTypeArguments();
        clazz = (Class<T>) types[0];
    }
    //更新数据
     int update(Connection conn,String sql,Object...args){
        PreparedStatement ps = null;
        try{
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i+1,args[i]);
            }
            return ps.executeUpdate();
        }catch(Exception e){
            throw new RuntimeException(e);
        }finally{
            try {
                ps.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    //获取数据表中的一条记录
    public T getInstance(Connection conn,String sql,Object...args){
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            ps=conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i+1,args[i]);

            }
            rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int count = rsmd.getColumnCount();
            if(rs.next()){
                T t = clazz.newInstance();
                for (int i = 1; i <= count; i++) {
                    Object columnValue = rs.getObject(i);
                    String columnLabel = rsmd.getColumnLabel(i);
                    if (columnValue!=null){
                        Field field = clazz.getDeclaredField(columnLabel);
                        field.setAccessible(true);
                        field.set(t,columnValue);
                    }
                }
                return t;
            }
            return null;
        }catch (Exception e){
            throw new RuntimeException(e);
        }finally{
            try {
                rs.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }finally {
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    //获取一组数据
    public List<T> getForList(Connection conn,String sql,Object...args){
        PreparedStatement ps = null;
        ResultSet rs = null;
        ResultSetMetaData rsmd = null;
        try {
            ps=conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i+1,args[i]);
            }
            rs = ps.executeQuery();
            rsmd = rs.getMetaData();
            int count = rsmd.getColumnCount();
            ArrayList<T> list = new ArrayList<>();
            while(rs.next()){
                T t = clazz.newInstance();
                for (int i = 1; i <= count; i++) {
                    Object columnValue = rs.getObject(i);
                    String columnLabel = rsmd.getColumnLabel(i);
                    if (columnValue!=null){
                        Field field = clazz.getDeclaredField(columnLabel);
                        field.setAccessible(true);
                        field.set(t,columnValue);
                    }
                }
                list.add(t);
            }
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            try {
                rs.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }finally {
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    //查询特殊值的方法
    public <T> T getValue(Connection conn,String sql ,Object...args){
        PreparedStatement ps = null;
        ResultSet rs = null;
        ResultSetMetaData rsmd = null;
        try {
            ps=conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i+1,args[i]);
            }
            rs= ps.executeQuery();
            rsmd = rs.getMetaData();
            int count = rsmd.getColumnCount();
            if (rs.next()){
                return (T) rs.getObject(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally{
            try {
                rs.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }finally {
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            }
        }
        return null;
    }

}
