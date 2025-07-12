package org.opensearch.client.transport.aws;

import java.util.*;

import software.amazon.awssdk.http.SdkHttpResponse;

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

    public static Map<String, List<String>> extractMultiValueHeaders(SdkHttpResponse httpResponse) {
        return httpResponse.headers()
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> {
                            Object value = entry.getValue();
                            if (value instanceof List) {
                                return ((List<?>) value).stream()
                                        .map(Objects::toString)
                                        .collect(Collectors.toList());
                            } else {
                                return Collections.singletonList(Objects.toString(value));
                            }
                        },
                        (existing, replacement) -> existing,
                        LinkedHashMap::new
                ));
    }
}
