package com.emlyn.ma.rpc.rmi;

import lombok.extern.slf4j.Slf4j;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Slf4j
public class WorldClockService extends UnicastRemoteObject implements WorldClock {

    protected WorldClockService() throws RemoteException {}

    @Override
    public LocalDateTime getLocalDateTime(String zoneId) throws RemoteException {
        log.info("Request for zoneId: {}", zoneId);
        return LocalDateTime.now(ZoneId.of(zoneId));
    }

}
