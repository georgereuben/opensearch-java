/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * The OpenSearch Contributors require contributions made to
 * this file be licensed under the Apache-2.0 license or a
 * compatible open source license.
 */

package org.opensearch.client.transport.aws;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import software.amazon.awssdk.http.SdkHttpResponse;

public final class ResponseHeaderExtractor {
    private ResponseHeaderExtractor() {}

    public static Map<String, String> extractHeaders(SdkHttpResponse httpResponse) {
        return httpResponse.headers().entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> {
            List<String> values = entry.getValue();
            return values == null || values.isEmpty() ? "" : values.get(0);
        }, (existing, replacement) -> existing, LinkedHashMap::new));
    }

    public static Map<String, List<String>> extractMultiValueHeaders(SdkHttpResponse httpResponse) {
        return httpResponse.headers().entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> {
            Object value = entry.getValue();
            if (value instanceof List) {
                return ((List<?>) value).stream().map(Objects::toString).collect(Collectors.toList());
            } else {
                return Collections.singletonList(Objects.toString(value));
            }
        }, (existing, replacement) -> existing, LinkedHashMap::new));
    }
}
