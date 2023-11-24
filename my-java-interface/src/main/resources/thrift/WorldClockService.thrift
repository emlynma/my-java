namespace java com.emlyn.ma.rpc.thrift

struct Request {
    1: string zoneId
}

struct Response {
    1: string localDateTime
}

service WorldClockService {
    Response getLocalDateTime(1: Request request)
}