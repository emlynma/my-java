package emlyn.ma.my.java.common.util;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Map;

public abstract class HttpUtils {

    private static final HttpClient httpClient = HttpClient.newBuilder().build();

    public static String get(String url, Map<String, String> params) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(new URI(buildQueryUrl(url, params)))
                .timeout(Duration.ofMillis(1000))
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public static String post(String url, Map<String, String> params) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(buildQuery(params)))
                .uri(new URI(url))
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    private static String buildQueryUrl(String url, Map<String, String> params) {
        String query = buildQuery(params);
        return url + (query.isEmpty() ? "" : "?" + query);
    }

    private static String buildQuery(Map<String, String> params) {
        if (params == null || params.isEmpty()) {
            return "";
        }
        StringBuilder queryBuilder = new StringBuilder();
        var iterator = params.entrySet().iterator();
        if (iterator.hasNext()) {
            var next = iterator.next();
            queryBuilder.append(next.getKey()).append("=").append(next.getValue());
        }
        while (iterator.hasNext()) {
            var next = iterator.next();
            queryBuilder.append("&").append(next.getKey()).append("=").append(next.getValue());
        }
        return queryBuilder.toString();
    }

}
