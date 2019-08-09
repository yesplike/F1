--
-- Created by IntelliJ IDEA.
-- User: ysp
-- Date: 2019/7/19
-- Time: 10:39
-- To change this template use File | Settings | File Templates.
--

local a= redis.call("hexists",KEYS[1],KEYS[2])
local userid

if a==0 then
    return {}
end
userid=redis.call("hget",KEYS[1],KEYS[2])

local f=redis.call("exists","maxid:"..userid)

if f==0 then
    return {}
end

local b=redis.call("get","maxid:"..userid)

-- 得到 当前订单是否可继续购买   与 当前订单号
local ifnull=string.sub(b,1,1);
local orderid=string.sub(b,2);

if ifnull=='0' then
    return {}
end

--  c 是 一个数组  元素是一个个字符串 （carts:....）
local c=redis.call("keys","carts:"..userid..":"..orderid..":*");


-- 遍历这些 carts 记录
--  carts:1:2:3
--
local u={}
local o={}
local it={}
local q={}
--
for i, v  in ipairs(c) do
    -- 首先将字符串反转
    local a1=string.reverse(v)
    --  先找到：的位置
    local a2=string.find(a1,":")

    --再提取出第一个数字  反转就是 itemid
    local itemid=string.reverse(string.sub(a1,1,a2-1))

    -- 有itemid  找到该商品 item
    local a3=redis.call("keys","item:".."*:"..itemid)

    local item=redis.call("smembers", a3[1])

    -- 再找到数量
    local quantity=redis.call("get","carts:"..userid..":"..orderid..":"..itemid)

    u[i]=userid
    o[i]=orderid
    it[i]=item
    q[i]=quantity

end
local result={u,o,it,q}

return result

