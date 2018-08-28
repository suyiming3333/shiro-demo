package com.sym.shiroweb.utils;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisException;

import java.util.Set;


@Component
public class JedisUtil {

    @Autowired
    private JedisPool jedisPool;

    private Jedis getResource(){
        return jedisPool.getResource();
    }

    //jedis数据库存放值
    public byte[] set(byte[] key, byte[] value) {
        Jedis jedis = getResource();
        try{
            jedis.set(key,value);
            return value;
        }catch(JedisException e){
            e.getMessage();
            return null;
        }finally {
            jedis.close();
        }
    }

    //设置超时时间
    public void expire(byte[] key, int i) {
        Jedis jedis = getResource();
        try{
            jedis.expire(key,i);
        }catch(JedisException e){
            e.getMessage();
        }finally {
            jedis.close();
        }
    }

    //获取value
    public byte[] get(byte[] key) {
        Jedis jedis = getResource();
        try{
            return jedis.get(key);
        }catch(JedisException e){
            e.getMessage();
            return null;
        }finally {
            jedis.close();
        }
    }

    //删除
    public void del(byte[] key) {
        Jedis jedis = getResource();
        try{
            jedis.del(key);
        }catch(JedisException e){
            e.getMessage();
        }finally {
            jedis.close();
        }
    }

    //获取所有keys
    public Set<byte[]> keys(String shiro_session_prefix) {
        Jedis jedis = getResource();
        try{
            return jedis.keys((shiro_session_prefix+"*").getBytes());
        }catch(JedisException e){
            e.getMessage();
            return null;
        }finally {
            jedis.close();
        }
    }
}
