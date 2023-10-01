package emlyn.ma.my.java.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

public class JsonUtilsTests {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    private static class Tag {
        private String id;
        private String name;
        private String display;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    private static class TagGroup {
        private int id;
        private String name;
        private Tag[] tags;
        private List<Tag> tagList;
        private Map<String, Tag> tagMap;
    }

    private static Tag TAG_01;
    private static Tag TAG_02;
    private static Tag TAG_03;
    private static TagGroup TAG_GROUP;
    private static String JSON;

    @BeforeAll
    static void beforeAll() throws JsonProcessingException {
        TAG_01 = new Tag("101", "tag_01", "Test");
        TAG_02 = new Tag("102", "tag_02", "Java");
        TAG_03 = new Tag("103", "tag_03", "C++");
        TAG_GROUP = TagGroup.builder()
                .id(1001)
                .name("simple bean")
                .tags(new Tag[]{TAG_01, TAG_02, TAG_03})
                .tagList(List.of(TAG_01, TAG_02, TAG_03))
                .tagMap(Map.of("101", TAG_01, "102", TAG_02, "103", TAG_03))
                .build();
        JSON = new ObjectMapper().writeValueAsString(TAG_GROUP);
    }

    @Test
    void testToJson() {
        String json = JsonUtils.toJson(TAG_GROUP);
        Assertions.assertEquals(JSON, json);
    }

    @Test
    void testToObject() {
        TagGroup tagGroup = JsonUtils.toObject(JSON, TagGroup.class);
        Assertions.assertEquals(TAG_GROUP, tagGroup);
        tagGroup = JsonUtils.toObject(JSON, new TypeReference<>() {});
        Assertions.assertEquals(TAG_GROUP, tagGroup);
    }

    @Test
    void testToMap() {

        Map<String, Tag> tagMap = JsonUtils.toMap(JsonUtils.toJson(TAG_GROUP.getTagMap()), Tag.class);
        Assertions.assertEquals(TAG_GROUP.getTagMap(), tagMap);
    }

    @Test
    void testToList() {
        List<Tag> tagList = JsonUtils.toList(JsonUtils.toJson(TAG_GROUP.getTagList()), Tag.class);
        Assertions.assertEquals(TAG_GROUP.getTagList(), tagList);
    }

}
