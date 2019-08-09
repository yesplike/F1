package com.yesp.server.sevice;

import com.yesp.server.dao.redisDaoImpl;
import com.yesp.server.mapper.CategoryMapper;
import com.yesp.server.model.Category;
import com.yesp.server.model.CategoryExample;
import com.yesp.server.model.Item;
import com.yesp.server.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class petServiceImpl {

    @Autowired
    private redisDaoImpl redisDao;

    public List<Category> queryCategory() {


        List<Category> list=redisDao.getHashTableValues("category");

        return list;
    }

    public List<Product> queryProduct(String catid) {

        List<Product> list=redisDao.keys("product:"+catid+":*");
        return list;
    }

    public List<Item> queryItems(String productid) {

        List<Item> list=redisDao.keys("item:"+productid+":*");
        return list;

    }

    public Item queryItem(String productid, String itemid) {

        List list=redisDao.getSet("item:"+productid+":"+itemid);
        return (Item)list.get(0);
    }
}
