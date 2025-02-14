package com.emlynma.java.mysql.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum Sex {

    FEMALE(0, "FEMALE"),
    MALE(1, "MALE");

    @JsonValue
    private final int code;
    private final String desc;

    @JsonCreator
    public static Sex valueOf(int code) {
        return Arrays.stream(Sex.values())
                .filter(sex -> sex.code == code)
                .findFirst()
                .orElse(null);
    }

}