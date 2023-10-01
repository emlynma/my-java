package emlyn.ma.my.java.rpc.thrift;

import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Slf4j
public class WorldClockServiceImpl implements WorldClockService.Iface {

    @Override
    public Response getLocalDateTime(Request request) throws TException {
        log.info("Request for zoneId: {}", request.getZoneId());
        return new Response().setLocalDateTime(LocalDateTime.now(ZoneId.of(request.getZoneId())).toString());
    }

}
