package emlyn.ma.my.java.redis.redisson;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RSet;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.redisson.config.Config;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
public class LockTests {

    private static RedissonClient redissonClient;

    @BeforeAll
    public static void init() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://localhost:6379");
        config.setCodec(new StringCodec());
        redissonClient = Redisson.create(config);
    }

    @Test
    public void testLock() throws InterruptedException {
        Thread thread1 = new Thread(() -> {
            Set<String> list = getListWithCache(1200);
            System.out.println(list);
        });
        Thread thread2 = new Thread(() -> {
            Set<String> list = getListWithCache(1200);
            System.out.println(list);
        });
        Thread thread3 = new Thread(() -> {
            Set<String> list = getListWithCache(500);
            System.out.println(list);
        });
        thread1.start();
        thread2.start();
        thread3.start();

        thread1.join();
        thread2.join();
        thread3.join();

        redissonClient.getList("my-redis-key").delete();
    }

    private Set<String> getListWithCache(int time) {
        String key = "my-redis-key";
        // 先查缓存
        RSet<String> rSet = redissonClient.getSet(key);
        if (!rSet.isEmpty()) {
            System.out.println("first hint cache");
            return rSet.readAll();
        }
        RLock lock = redissonClient.getLock(key + "_lock");
        try {
            LocalDateTime start = LocalDateTime.now();
            boolean b = lock.tryLock(1200, 1000, TimeUnit.MILLISECONDS);
            LocalDateTime end = LocalDateTime.now();
            System.out.println("lock cost: " + Duration.between(start, end).toMillis() + "ms");
            if (!b) {
                System.out.println("lock failed");
                return new HashSet<>();
            }
            // 再次查缓存
            if (!rSet.isEmpty()) {
                System.out.println("second hint cache");
                return rSet.readAll();
            }
            System.out.println("cache breakdown");
            // 缓存为空，查询数据源
            List<String> list = getList(time);
            if (list != null && !list.isEmpty()) {
                // 将数据源查询结果写入缓存
                rSet.addAll(list);
                System.out.println("write cache");
                return rSet.readAll();
            }
            System.out.println("cache preheat");
            return rSet.readAll();
        } catch (Exception e) {
            log.error("lock error", e);
            return new HashSet<>();
        } finally {
            try {
                lock.unlock();
            } catch (Exception e) {
                log.error("unlock error", e);
            }
        }
    }

    private List<String> getList(int time) throws InterruptedException {
        Thread.sleep(time);
        return List.of("mahaoran", "zhangsan", "lisi");
    }

}
