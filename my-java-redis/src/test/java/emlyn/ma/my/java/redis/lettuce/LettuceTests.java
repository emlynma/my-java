package emlyn.ma.my.java.redis.lettuce;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.api.sync.RedisCommands;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletionStage;

public class LettuceTests {

    private static RedisClient redisClient;

    @BeforeAll
    public static void init() {
        redisClient = RedisClient.create("redis://localhost:6379");
    }

    @Test
    void testSync() {
        StatefulRedisConnection<String, String> connect = redisClient.connect();
        RedisCommands<String, String> syncCommands = connect.sync();
        System.out.println(syncCommands.keys("*"));
        RedisAsyncCommands<String, String> asyncCommands = connect.async();
        CompletionStage<Void> voidCompletionStage = asyncCommands.keys("*")
                .thenAccept(System.out::println);
        if (voidCompletionStage.toCompletableFuture().isDone()) {
            System.out.println("done");
        }
    }

}
