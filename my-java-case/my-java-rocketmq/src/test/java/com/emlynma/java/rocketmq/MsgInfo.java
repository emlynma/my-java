package com.emlynma.java.rocketmq;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MsgInfo {

    private String uuid;
    private String name;
    private String data;

}
