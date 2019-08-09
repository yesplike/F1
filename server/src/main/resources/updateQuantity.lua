--
-- Created by IntelliJ IDEA.
-- User: ysp
-- Date: 2019/7/19
-- Time: 14:44
-- To change this template use File | Settings | File Templates.
--
local userid=redis.call("hget",KEYS[1],KEYS[2]);

local o=redis.call("get","maxid:"..userid)

local orderid=string.sub(o,2)

redis.call("set","carts:"..userid..":"..orderid..":"..KEYS[3],tostring(KEYS[4]));

local b={userid,orderid}

return b