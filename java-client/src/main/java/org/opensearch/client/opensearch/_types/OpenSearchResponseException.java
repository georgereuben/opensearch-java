/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * The OpenSearch Contributors require contributions made to
 * this file be licensed under the Apache-2.0 license or a
 * compatible open source license.
 */

package org.opensearch.client.opensearch._types;

import java.util.Collections;
import java.util.Map;

public class OpenSearchResponseException extends OpenSearchException {
    private final Map<String, String> responseHeaders;

    public OpenSearchResponseException(ErrorResponse response, Map<String, String> responseHeaders) {
        super(response);
        this.responseHeaders = Collections.unmodifiableMap(responseHeaders != null ? responseHeaders : Collections.emptyMap());
    }

    /**
     * HTTP response headers from the failed request
     */
    public Map<String, String> getResponseHeaders() {
        return responseHeaders;
    }

    /**
     * Get a specific header value (case-insensitive)
     */
    public String getHeader(String name) {
        return responseHeaders.entrySet()
            .stream()
            .filter(entry -> entry.getKey().equalsIgnoreCase(name))
            .map(Map.Entry::getValue)
            .findFirst()
            .orElse(null);
    }
}
