package com.yesp.server.controller;


import com.yesp.server.model.Cart;
import com.yesp.server.sevice.accountServiceImpl;
import com.yesp.server.sevice.cartServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(name = "cartController",value = {"/cart"})
public class CartController {

    @Autowired
    private cartServiceImpl cartService;

    @Autowired
    private accountServiceImpl accountService;


    @RequestMapping(value = "/addCart/{itemid}/{quantity}")
    public ResponseEntity<Void> addCart(@PathVariable String itemid,@PathVariable String quantity){

        if(!accountService.verUserCookie()){
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }

        cartService.addCart(itemid,quantity);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }




    @RequestMapping(value = "/queryCart")
    public ResponseEntity<List<Cart>> queryCart(){

        //  x先判断是否是用户， 若不是就跳到登录页面
        if(accountService.verUserCookie()){

            List<Cart> list=cartService.queryCart();
            if(list==null){
                return new ResponseEntity<List<Cart>>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<List<Cart>>(list,HttpStatus.OK);

        }else{

            return new ResponseEntity<List<Cart>>(HttpStatus.BAD_REQUEST);
        }

    }


    @RequestMapping(value = "/updateQuantity/{itemid}/{quantity}")
    public ResponseEntity<Void> updateQuantity(@PathVariable String itemid ,@PathVariable String quantity){


        cartService.updateQuantity(itemid,quantity);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }


    @RequestMapping(value = "/delQuantity/{itemid}")
    public ResponseEntity<Void> delQuantity(@PathVariable String itemid ){


        cartService.delCart(itemid);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }


    @RequestMapping(value = "/checkout/{total}")
    public ResponseEntity<Void> checkout(@PathVariable String total){

        if(total.equals("0")){
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }

        cartService.checkout(total);
        return new ResponseEntity<Void>(HttpStatus.OK);


    }


}
