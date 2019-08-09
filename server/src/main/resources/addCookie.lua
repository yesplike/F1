--
-- Created by IntelliJ IDEA.
-- User: ysp
-- Date: 2019/7/17
-- Time: 18:56
-- To change this template use File | Settings | File Templates.
--
local a=redis.call("hexists",KEYS[1],KEYS[2])

if a==0 then
    redis.call("hset",KEYS[1],KEYS[2],KEYS[3])
end
