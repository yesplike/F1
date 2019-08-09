--
-- Created by IntelliJ IDEA.
-- User: ysp
-- Date: 2019/7/17
-- Time: 16:13
-- To change this template use File | Settings | File Templates.
--

redis.call("select",KEYS[1])
redis.call("flushdb")