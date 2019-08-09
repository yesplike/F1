--
-- Created by IntelliJ IDEA.
-- User: ysp
-- Date: 2019/7/17
-- Time: 19:01
-- To change this template use File | Settings | File Templates.
--

return redis.call("hexists",KEYS[1],KEYS[2])