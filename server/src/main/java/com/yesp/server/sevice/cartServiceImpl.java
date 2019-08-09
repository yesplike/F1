package com.yesp.server.sevice;

import com.yesp.server.config.MyConst;
import com.yesp.server.dao.redisDaoImpl;
import com.yesp.server.mapper.CartMapper;
import com.yesp.server.mapper.OrdersMapper;
import com.yesp.server.model.Cart;
import com.yesp.server.model.CartKey;
import com.yesp.server.model.Item;
import com.yesp.server.model.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class cartServiceImpl {

    @Autowired
    private redisDaoImpl redisDao;

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private OrdersMapper ordersMapper;

    public List<Cart> queryCart() {

        List list=new ArrayList(){
            {
                add(MyConst.USERCOOKIE);
                add(MyConst.SESSIONID);
            }
        };

        //{u,o,it,q}
        List<List> result=redisDao.executeRedisByLua(list,"queryCart.lua");

        if(result.size()==0){
            return null;
        }else{

            List<Integer> userid=result.get(0);
            List<Integer> orderid=result.get(1);
            List<List<Item>> item=result.get(2);
            List<Integer> quantity=result.get(3);

            List<Cart> list1=new ArrayList<>();

            for(int i=0;i<userid.size();i++){

                Cart cart=new Cart();

                cart.setUserid(userid.get(i));
                cart.setOrderid(orderid.get(i));
                cart.setItem(item.get(i).get(0));
                cart.setQuantity(quantity.get(i));

                list1.add(cart);
            }
            return list1;
        }

    }

    public void updateQuantity(String itemid, String quantity) {

        List list=new ArrayList(){
            {
                add(MyConst.USERCOOKIE);
                add(MyConst.SESSIONID);
                add(itemid);
                add(quantity);
            }
        };

        List<Integer> result=redisDao.executeRedisByLua(list,"updateQuantity.lua");

        int userid=result.get(0);
        int orderid=result.get(1);

        Cart cart=new Cart();
        cart.setUserid(userid);
        cart.setOrderid(orderid);
        cart.setItemid(Integer.parseInt(itemid));
        cart.setQuantity(Integer.parseInt(quantity));

        cartMapper.updateByPrimaryKey(cart);

    }

    public void delCart(String itemid) {

        List list=new ArrayList(){
            {
                add(MyConst.USERCOOKIE);
                add(MyConst.SESSIONID);
                add(itemid);
            }
        };

        List<Integer> result=redisDao.executeRedisByLua(list,"delCart.lua");

        int userid=result.get(0);
        int orderid=result.get(1);

        CartKey cart=new CartKey();
        cart.setUserid(userid);
        cart.setOrderid(orderid);
        cart.setItemid(Integer.parseInt(itemid));

        cartMapper.deleteByPrimaryKey(cart);

    }
     // { userid,orderid,flag,number}

    public void addCart(String itemid, String quantity) {

        List list=new ArrayList(){
            {
                add(MyConst.USERCOOKIE);
                add(MyConst.SESSIONID);
                add(itemid);
                add(quantity);
            }
        };
        List<Integer> result=redisDao.executeRedisByLua(list,"addCart.lua");

        int userid=result.get(0);
        int orderid=result.get(1);
        int ifnull=result.get(2);
        int flag=result.get(3);
        int number=result.get(4);

        if(ifnull==0){    //   新订单， 且是新订单最早的一个商品   ，新开一个订单

            Orders orders=new Orders();
            orders.setUserid(userid);
            orders.setOrderid(orderid);
            ordersMapper.insert(orders);

            Cart cart=new Cart();
            cart.setQuantity(number);
            cart.setUserid(userid);
            cart.setOrderid(orderid);
            cart.setItemid(Integer.parseInt(itemid));
            cartMapper.insert(cart);


        }else {

            if(flag==1){     //  老订单，  但是有一样的商品了， 只需加上数量

                Cart cart=new Cart();
                cart.setQuantity(number);
                cart.setUserid(userid);
                cart.setOrderid(orderid);
                cart.setItemid(Integer.parseInt(itemid));
                cartMapper.updateByPrimaryKey(cart);

            }else{           //   老订单的新商品

                Cart cart=new Cart();
                cart.setQuantity(number);
                cart.setUserid(userid);
                cart.setOrderid(orderid);
                cart.setItemid(Integer.parseInt(itemid));
                cartMapper.insert(cart);

            }

        }

    }


    public void checkout(String total) {

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String date=sdf.format(new Date());

        List list=new ArrayList(){
            {
                add(MyConst.USERCOOKIE);
                add(MyConst.SESSIONID);
                add(date);
                add(total);
            }
        };

        List<Integer> list1=redisDao.executeRedisByLua(list,"checkout.lua");

        int userid=list1.get(0);
        int orderid=list1.get(1);

        Orders orders=new Orders();
        orders.setUserid(userid);
        orders.setOrderid(orderid);
        orders.setOrderdate(new Date());
        orders.setTotalprice(Float.parseFloat(total));

        ordersMapper.updateByPrimaryKey(orders);

    }
}
