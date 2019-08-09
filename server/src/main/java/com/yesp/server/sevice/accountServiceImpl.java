package com.yesp.server.sevice;

import com.yesp.server.config.MyConst;
import com.yesp.server.dao.redisDaoImpl;
import com.yesp.server.mapper.AccountMapper;
import com.yesp.server.mapper.ProfileMapper;
import com.yesp.server.model.Account;
import com.yesp.server.model.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class accountServiceImpl {

    @Autowired
    private redisDaoImpl redisDao;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private ProfileMapper profileMapper;

    public Account login(Account account) {

        Account a=(Account) redisDao.getHashTableValue("account",account.getUsername());

        System.out.println(a);
        if(a==null){
            return null;
        }else if(a.getPassword().equals(account.getPassword())){
            return a;
        }else {
            return null;
        }
    }


    public void addGuestCookie() {

        List list=new ArrayList(){
            {
                add(MyConst.GUESTCOOKIE);
                add(MyConst.SESSIONID);
                add("-1");
            }
        };

        redisDao.executeRedisByLua(list,"addCookie.lua");
    }

    public void addUserCookie(Account account) {

        List list=new ArrayList(){
            {
                add(MyConst.USERCOOKIE);
                add(MyConst.SESSIONID);
                add(account.getUserid().toString());
            }
        };

        redisDao.executeRedisByLua(list,"addCookie.lua");
    }

    public void delUserCookie() {

        List list=new ArrayList(){
            {
                add(MyConst.USERCOOKIE);
                add(MyConst.SESSIONID);
            }
        };
        redisDao.executeRedisByLua(list,"delCookie.lua");
    }

    public void delGuestCookie() {

        List list=new ArrayList(){
            {
                add(MyConst.GUESTCOOKIE);
                add(MyConst.SESSIONID);
            }
        };
        redisDao.executeRedisByLua(list,"delCookie.lua");
    }

    public boolean verUserCookie(){

        List list=new ArrayList(){
            {
                add(MyConst.USERCOOKIE);
                add(MyConst.SESSIONID);
            }
        };
        List result=redisDao.executeRedisByLua(list,"verCookie.lua");
        return result.get(0).toString().equals("1");

    }
    //  注册，先在mySQL中 添加   后在redis  添加
    public boolean register(Account account) {

        // 判断用户是否已经存在了，存在了就不可以注册
        if(redisDao.existsHashTableValue("account",account.getUsername())){
            return true;
        }

        //  sql 中添加
        accountMapper.insert(account);

        //   redis 中添加
        redisDao.setHashTable("account",account.getUsername(),account);

        account.getProfile().setUserid(account.getUserid());

        //   添加上 profile
        profileMapper.insert(account.getProfile());
        redisDao.setHashTable("profile",account.getProfile().getUserid().toString(),account.getProfile());

        return false;

    }
}
