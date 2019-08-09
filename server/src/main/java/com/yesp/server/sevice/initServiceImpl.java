package com.yesp.server.sevice;

import com.yesp.server.dao.redisDaoImpl;
import com.yesp.server.mapper.*;
import com.yesp.server.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class initServiceImpl {


    @Autowired
    public AccountMapper accountMapper;

    @Autowired
    public ProfileMapper profileMapper;

    @Autowired
    public CategoryMapper categoryMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    public redisDaoImpl redisDao;


    public void init(){
        initDb();
        initAccount();
        initProfile();
        initCategory();
        initProduct();
        initItem();
        initOrders();
        initCart();
    }

    public void initAccount(){

        AccountExample accountExample=new AccountExample();
        accountExample.createCriteria().andUseridIsNotNull();
        List<Account> list=accountMapper.selectByExample(accountExample);

       // System.out.println(list.get(0).getProfile().getCatid());

        list.forEach(c->redisDao.setHashTable("account",c.getUsername(),c));

    }

    public void initProfile(){

        ProfileExample profileExample=new ProfileExample();
        profileExample.createCriteria().andUseridIsNotNull();
        List<Profile> list=profileMapper.selectByExample(profileExample);

        //list.forEach(c-> System.out.println(c.getCategory().getName()));
        list.forEach(c->redisDao.setHashTable("profile",c.getUserid().toString(),c));

    }

    public void initCategory(){

        CategoryExample categoryExample=new CategoryExample();
        categoryExample.createCriteria().andCatidIsNotNull();
        List<Category> list=categoryMapper.selectByExample(categoryExample);

        list.forEach(c->redisDao.setHashTable("category",c.getCatid().toString(),c));

    }

    public void initProduct(){

        ProductExample productExample=new ProductExample();
        productExample.createCriteria().andProductidIsNotNull();
        List<Product> list=productMapper.selectByExample(productExample);

        list.forEach(c->redisDao.setSet("product:"+c.getCatid()+":"+c.getProductid(),c));

    }

    public void initItem(){

        ItemExample itemExample=new ItemExample();
        itemExample.createCriteria().andItemidIsNotNull();
        List<Item> list=itemMapper.selectByExample(itemExample);

        list.forEach(c->redisDao.setSet("item:"+c.getProductid()+":"+c.getItemid(),c));

    }

    public void initOrders(){

        OrdersExample ordersExample=new OrdersExample();
        ordersExample.createCriteria().andOrderidIsNotNull();

        List<Orders> list=ordersMapper.selectByExample(ordersExample);

        list.forEach(c->{

            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            if(c.getOrderdate()!=null){
                String date=sdf.format(c.getOrderdate());
                redisDao.setString("orders:"+c.getUserid()+":"+c.getOrderid(),date+":"+c.getTotalprice());
            }


        });


        AccountExample example1=new AccountExample();
        example1.createCriteria().andUseridIsNotNull();

        List<Account> list1=accountMapper.selectByExample(example1);
        //  遍历用户 ， 通过过滤器 过滤出订单中该用户的所有订单吗并 通过 max（） 找到订单号最大的订单
        list1.forEach(a->{

            Optional<Orders> p=list.stream().filter(c->c.getUserid()==a.getUserid()).max((o1, o2)->o1.getOrderid()-o2.getOrderid());
            if(p.isPresent()){  //   判断是否存在 防止出错
                Orders o=p.get();

                //                                                为 1 时 说明该订单还没结算，可以继续在该订单下购买。
                redisDao.setString("maxid:"+o.getUserid(),(o.getOrderdate()==null?1:0)+new Integer(o.getOrderid()).toString());
            }
        });

    }


    public void initCart(){

        CartExample cartExample=new CartExample();
        cartExample.createCriteria().andUseridIsNotNull();
        List<Cart> list=cartMapper.selectByExample(cartExample);
        list.forEach(c->redisDao.setString("carts:"+c.getUserid()+":"+c.getOrderid()+":"+c.getItemid(),c.getQuantity().toString()));

    }



    public void initDb(){

        List list=new ArrayList();
        list.add("8");
        redisDao.executeRedisByLua(list,"flushdb.lua");
    }

}
