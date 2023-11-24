package com.emlyn.ma.rpc.rmi;

import lombok.extern.slf4j.Slf4j;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

@Slf4j
public class Server {

    public static void main(String[] args) {
        log.info("Starting RMI server...");
        try {
            // 创建服务注册表
            LocateRegistry.createRegistry(1099);
            // 将服务实例绑定到一个地址
            Naming.rebind("worldClockService", new WorldClockService());
            log.info("RMI server started.");
        } catch (Exception e) {
            log.error("Error starting RMI server.", e);
        }
    }

}
