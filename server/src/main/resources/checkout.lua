--
-- Created by IntelliJ IDEA.
-- User: ysp
-- Date: 2019/7/19
-- Time: 18:12
-- To change this template use File | Settings | File Templates.
--

local userid=redis.call("hget",KEYS[1],KEYS[2])

local m=redis.call("get","maxid:"..userid);

local orderid=string.sub(m,2);

redis.call("set","carts:"..userid..":"..orderid,KEYS[3]..":"..KEYS[4]);

redis.call("set","maxid:"..userid,"0"..orderid);

return {userid,orderid}


