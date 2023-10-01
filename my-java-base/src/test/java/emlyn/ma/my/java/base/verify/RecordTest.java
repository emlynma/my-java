package emlyn.ma.my.java.base.verify;

import emlyn.ma.my.java.common.util.JsonUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import org.junit.jupiter.api.Test;

public class RecordTest {

    @Test
    void testRecord() {
        var user1 = new User(1001, "crecema", 24);
        System.out.println(JsonUtils.toJson(user1));
        System.out.println(user1);
        System.out.println(user1.name());

        var user2 = User.builder()
                .id(1002)
                .name("crecema")
                .build();
        System.out.println(JsonUtils.toJson(user2));
    }

    @Builder
    private record User (
            Integer id,
            @JsonProperty("na")
            String name,
            Integer age) {}

}
