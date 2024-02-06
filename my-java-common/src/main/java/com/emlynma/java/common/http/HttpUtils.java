package com.emlynma.java.common.http;

import com.emlynma.java.common.json.JsonUtils;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
public abstract class HttpUtils {

    private static final int DEFAULT_CONNECT_TIMEOUT = 100;
    private static final int DEFAULT_TIMEOUT = 200;
    private static final int DEFAULT_RETRIES = 1;
    private static final String DEFAULT_CONTENT_TYPE = "application/json";

    private static final HttpClient httpClient;

    static {
        httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofMillis(DEFAULT_CONNECT_TIMEOUT))
                .build();
    }

    public static String get(String url) {
        return get(url, null, null, null, null);
    }

    public static String get(String url, Map<String, Object> params) {
        return get(url, params, null, null, null);
    }

    public static String get(String url, Map<String, Object> params, Map<String, String> headers) {
        return get(url, params, headers, null, null);
    }

    public static String get(String url, Map<String, Object> params, Map<String, String> headers, Integer timeout, Integer retries) {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(params == null ? url : url + "?" + buildParams(params)))
                .timeout(Duration.ofMillis(timeout == null ? DEFAULT_TIMEOUT : timeout));
        if (headers != null && !headers.isEmpty()) {
            builder.headers(buildHeaders(headers));
        }
        return send(builder.build(), retries == null ? DEFAULT_RETRIES : retries);
    }

    public static String post(String url) {
        return post(url, null, null, null, null);
    }

    public static String post(String url, Map<String, Object> body) {
        return post(url, body, null, null, null);
    }

    public static String post(String url, Map<String, Object> body, Map<String, String> headers) {
        return post(url, body, headers, null, null);
    }

    public static String post(String url, Map<String, Object> body, Map<String, String> headers, Integer timeout, Integer retries) {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(buildBody(body, headers)))
                .uri(URI.create(url))
                .timeout(Duration.ofMillis(timeout == null ? DEFAULT_TIMEOUT : timeout));
        if (headers != null && !headers.isEmpty()) {
            builder.headers(buildHeaders(headers));
        }
        return send(builder.build(), retries == null ? DEFAULT_RETRIES : retries);
    }

    private static String send(HttpRequest httpRequest, int retries) {
        int times = 0;
        do {
            try {
                HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
                if (httpResponse.statusCode() == 200) {
                    return httpResponse.body();
                } else {
                    log.error("request fail, url: {}, status code: {}", httpRequest.uri(), httpResponse.statusCode());
                    return null;
                }
            } catch (Exception e) {
                log.warn("request timeout, url: {}, retry times {}", httpRequest.uri(), times++, e);
            }
        } while (retries-- > 0);
        log.error("request fail, url: {}, total request times {}", httpRequest.uri(), times);
        return null;
    }

    private static String buildParams(Map<String, Object> params) {
        if (params == null || params.isEmpty()) {
            return "";
        }
        return params.entrySet().stream()
                .peek(entry -> {
                    if (entry.getValue() == null) {
                        entry.setValue("");
                    }
                })
                .map(entry -> {
                    // 对键和值进行URL编码
                    return URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8) +
                            "=" +
                            URLEncoder.encode(entry.getValue().toString(), StandardCharsets.UTF_8);
                })
                .collect(Collectors.joining("&"));
    }

    private static String buildJsonBody(Map<String, Object> body) {
        if (body == null || body.isEmpty()) {
            return "";
        }
        return JsonUtils.toJson(body);
    }

    private static String buildBody(Map<String, Object> body, Map<String, String> headers) {
        String contentType = Optional.ofNullable(headers).map(h -> h.get("Content-Type")).orElse(DEFAULT_CONTENT_TYPE);
        if (contentType.contains("application/json")) {
            return buildJsonBody(body);
        } else {
            return buildParams(body);
        }
    }

    private static String[] buildHeaders(Map<String, String> headers) {
        if (headers == null) {
            return new String[]{};
        }
        String[] headerArray = new String[headers.size() * 2];
        int i = 0;
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            headerArray[i++] = entry.getKey();
            headerArray[i++] = entry.getValue();
        }
        return headerArray;
    }

}
