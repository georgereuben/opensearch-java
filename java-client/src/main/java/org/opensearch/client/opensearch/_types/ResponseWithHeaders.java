package org.opensearch.client.opensearch._types;

import java.util.Map;

public interface ResponseWithHeaders {
    /**
     * HTTP response headers from the OpenSearch response.
     * Headers are case-insensitive and values are the raw header values.
     * @return immutable map of response headers
     */
    Map<String, String> headers();
}
