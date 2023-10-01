package emlyn.ma.my.java.redis.redisson;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.redisson.Redisson;
import org.redisson.api.RList;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.redisson.config.Config;

import java.time.Duration;
import java.util.List;

public class RedissonTests {

    private static RedissonClient redissonClient;

    @BeforeAll
    public static void init() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://localhost:6379");
        config.setCodec(new StringCodec());
        redissonClient = Redisson.create(config);
    }

    @Test
    public void testListCRUD() {
        String key = "not_exists_key";
        RList<Object> list = redissonClient.getList(key);
        list.expire(Duration.ofSeconds(60 * 10));
        List<Object> objectList = list.readAll();
        // 判断是否存在, 存在则删除
        if (list.isExists()) {
            list.delete();
        }
        // 添加元素
        list.add("test01");
        list.add("test02");
        list.add("test03");
        // 获取元素
        Assertions.assertEquals("test01", list.get(0));
        // 移除元素
        list.remove("test01");
        Assertions.assertEquals(2, list.size());
        System.out.println(objectList);
    }

}
