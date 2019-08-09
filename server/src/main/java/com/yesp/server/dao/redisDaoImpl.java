package com.yesp.server.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Repository
public class redisDaoImpl {

    @Autowired
    public RedisTemplate<Serializable,Serializable> redisTemplate;

    @Autowired
    public RedisTemplate<Serializable,Serializable> redisTemplate1;


    public void setString(String key , String value){
        ValueOperations vo=redisTemplate1.opsForValue();
        vo.set(key,value);
    }


    //  获得 多个键值 的set 值
    public List keys(String key){
        // 这里的 set 里存的是所有类似：‘product:4’ 的键 但是要的是他们的值
        Set set=redisTemplate.keys(key);

        //  getUnion  方法如下       将Set 转化成 List
        return Arrays.asList(getUnion(set).toArray());
    }

    //  sunion 能获得所有键的值  这里是  set.union  返回的是所有值得 Set
    private Set getUnion(Set keys){

        SetOperations set=redisTemplate.opsForSet();
        return set.union("",keys);
    }


    public List getSet(String key){
        SetOperations set=redisTemplate.opsForSet();
        Set set1=set.members(key);

        return Arrays.asList(set1.toArray());

    }


    public void setSet(String key,Object value){
        SetOperations set=redisTemplate.opsForSet();
        set.add(key,value);
    }


    public void setHashTable(String tablename, String key, Object value) {

        HashOperations hash=redisTemplate.opsForHash();
        hash.put(tablename,key,value);

    }

    public Object getHashTableValue(String tablename, String key) {

        HashOperations hash=redisTemplate.opsForHash();
        return hash.get(tablename,key);

    }


    public List getHashTableValues(String tablename) {

        HashOperations hash=redisTemplate.opsForHash();
        return hash.values(tablename);
    }

    public boolean existsHashTableValue(String tablename, String key) {

        HashOperations hash=redisTemplate.opsForHash();
        return hash.hasKey(tablename,key);

    }


    // 设置执行Lua 的公共函数
    public List executeRedisByLua(List arg, String funName){
        // 1、生成执行脚本生成器
        DefaultRedisScript<List> redisScript=new DefaultRedisScript<>();
        //2、找到源程序
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource(funName)));
        // 3、 设置参数列表
        redisScript.setResultType(List.class);

        return redisTemplate.execute(redisScript,arg);

    }


}
