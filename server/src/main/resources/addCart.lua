--
-- Created by IntelliJ IDEA.
-- User: ysp
-- Date: 2019/7/19
-- Time: 15:51
-- To change this template use File | Settings | File Templates.
--
local userid=redis.call("hget",KEYS[1],KEYS[2]);

local f=redis.call("exists","maxid:"..userid)

if f==0 then
    redis.call("set","maxid:"..userid,"11");
end

local o=redis.call("get","maxid:"..userid)

local ifnull=string.sub(o,1,1);

local orderid=string.sub(o,2);

-- '0'  已经结算了 要增加新订单

if ifnull=='0' then
    orderid=orderid+1;
    redis.call("set","maxid:"..userid,"1"..orderid)
end

--  判断是否已经有个一样的商品了
local flag=redis.call("exists","carts:"..userid..":"..orderid..":"..KEYS[3]);

local number
-- 如果有一样的了
if flag==1 then
    number=redis.call("get","carts:"..userid..":"..orderid..":"..KEYS[3]);
    number=tonumber(number)+tonumber(KEYS[4])
    redis.call("set","carts:"..userid..":"..orderid..":"..KEYS[3],number)
else

    redis.call("set","carts:"..userid..":"..orderid..":"..KEYS[3],KEYS[4]);
    number=tonumber(KEYS[4])
end

--  当时新用户的第一个订单时，在前面 新建了一个maxid 中 ifnull 为 1， 但是传过去的要是 0
if f==0 then
    ifnull='0'
end

return {userid,tostring(orderid),ifnull,tostring(flag),tostring(number)}
