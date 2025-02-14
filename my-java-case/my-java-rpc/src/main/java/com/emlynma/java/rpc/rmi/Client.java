package com.emlynma.java.rpc.rmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.LocalDateTime;

public class Client {

    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            WorldClock worldClock = (WorldClock) registry.lookup("worldClockService");
            LocalDateTime localDateTime = worldClock.getLocalDateTime("Asia/Shanghai");
            System.out.println(localDateTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
