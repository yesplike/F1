package com.yesp.server.controller;


import com.yesp.server.model.Account;
import com.yesp.server.sevice.accountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping( name = "accountController" , value = {"/account"})
public class AccountController {

    @Autowired
    private accountServiceImpl accountService;



    //   添加游客 cookie  每个页面都会走一遍，若是没有用户就添加游客
    @RequestMapping( value = "/addguest")
    public ResponseEntity<Void> addGuest(){

        // 若是没有用户就添加上游客
        if(!accountService.verUserCookie()){
            accountService.addGuestCookie();
        }

        return new ResponseEntity<Void>(HttpStatus.OK);
    }


    //  登录  ， 删除游客cookie   添加上用户cookie
    @RequestMapping( value = "/login")
    public ResponseEntity<Account> login(@RequestBody Account account){

        Account account1=accountService.login(account);

        if(account1!=null){

            accountService.addUserCookie(account1);
            accountService.delGuestCookie();

            return new ResponseEntity<Account>(account1,HttpStatus.OK);
        }else{
            return new ResponseEntity<Account>(account1,HttpStatus.NOT_FOUND);
        }


    }

    //  注销
    @RequestMapping( value = "/out")
    public ResponseEntity<Void> login(){

        if(accountService.verUserCookie()){
            accountService.delUserCookie();
        }
        return new ResponseEntity<Void>(HttpStatus.OK);

    }

    //  注册用户
    @RequestMapping( value = "/register")
    public ResponseEntity<Void> register(@RequestBody Account account){

        if(accountService.register(account)){
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Void>(HttpStatus.OK);
    }


}
