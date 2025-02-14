package com.emlynma.java.rpc.thrift;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

public class Client {

    public static void main(String[] args) {
        try (TTransport transport = new TSocket("localhost", 9090)) {
            transport.open();

            WorldClockService.Client client = new WorldClockService.Client(new TBinaryProtocol(transport));

            Request request = new Request().setZoneId("Asia/Shanghai");
            Response response = client.getLocalDateTime(request);
            System.out.println(response.getLocalDateTime());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
