package com.yesp.server.controller;

import com.yesp.server.model.Category;
import com.yesp.server.model.Item;
import com.yesp.server.model.Product;
import com.yesp.server.sevice.petServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(name = "petController" ,value = {"/pet"})
public class PetController {

    @Autowired
    private petServiceImpl petService;

    @RequestMapping(value = "/queryCategory")
    public ResponseEntity<List<Category>> queryCategory(){

        List<Category> list=petService.queryCategory();

        return new ResponseEntity<List<Category>>(list,HttpStatus.OK);
    }


    @RequestMapping(value = "/queryProd/{catid}")
    public ResponseEntity<List<Product>> queryProduct(@PathVariable String catid){

       // System.out.println(catid);
        List<Product> list=petService.queryProduct(catid);

        return new ResponseEntity<List<Product>>(list,HttpStatus.OK);
    }


    @RequestMapping(value = "/queryItems/{productid}")
    public ResponseEntity<List<Item>> queryItems(@PathVariable String productid){

        // System.out.println(catid);
        List<Item> list=petService.queryItems(productid);

        return new ResponseEntity<List<Item>>(list,HttpStatus.OK);
    }

    @RequestMapping(value = "/queryItem/{productid}/{itemid}")
    public ResponseEntity<Item> queryItem(@PathVariable String productid,@PathVariable String itemid){

        // System.out.println(catid);
        Item item=petService.queryItem(productid,itemid);

        return new ResponseEntity<Item>(item,HttpStatus.OK);
    }

}
