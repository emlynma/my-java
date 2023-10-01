package emlyn.ma.my.java.rpc.thrift;

import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportException;

@Slf4j
public class Server {

    public static void main(String[] args) {
        log.info("Starting server...");
        WorldClockService.Processor<WorldClockServiceImpl> processor =
                new WorldClockService.Processor<>(new WorldClockServiceImpl());
        try {
            TServerTransport serverTransport = new TServerSocket(9090);
            TServer server = new TSimpleServer(new TServer.Args(serverTransport).processor(processor));
            server.serve();
        } catch (TTransportException e) {
            log.error("Error starting server", e);
            throw new RuntimeException(e);
        }
    }

}
