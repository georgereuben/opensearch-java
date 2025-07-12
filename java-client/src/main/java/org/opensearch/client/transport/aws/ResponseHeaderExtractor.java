package org.opensearch.client.transport.aws;

import java.util.LinkedHashMap;
import software.amazon.awssdk.http.SdkHttpResponse;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public final class ResponseHeaderExtractor {
    private ResponseHeaderExtractor() {}

    public static Map<String, String> extractHeaders(SdkHttpResponse httpResponse) {
        return httpResponse.headers()
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> Objects.toString(entry.getValue()),
                        (existing, replacement) -> existing,
                        LinkedHashMap::new
                ));
    }
}
