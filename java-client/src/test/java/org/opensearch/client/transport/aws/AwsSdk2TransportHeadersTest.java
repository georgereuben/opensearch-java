/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * The OpenSearch Contributors require contributions made to
 * this file be licensed under the Apache-2.0 license or a
 * compatible open source license.
 */

package org.opensearch.client.transport.aws;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;
import org.junit.Test;
import org.opensearch.client.opensearch._types.ErrorCause;
import org.opensearch.client.opensearch._types.ErrorResponse;
import org.opensearch.client.opensearch._types.OpenSearchResponseException;
import software.amazon.awssdk.http.SdkHttpResponse;

public class AwsSdk2TransportHeadersTest {

    @Test
    public void testResponseHeadersExtraction() {
        SdkHttpResponse mockResponse = mock(SdkHttpResponse.class);
        Map<String, List<String>> headers = Map.of(
            "x-opensearch-request-id",
            List.of("abc123"),
            "content-type",
            List.of("application/json"),
            "x-custom-header",
            List.of("custom-value")
        );
        when(mockResponse.headers()).thenReturn(headers);

        Map<String, String> extracted = ResponseHeaderExtractor.extractHeaders(mockResponse);

        assertEquals("abc123", extracted.get("x-opensearch-request-id"));
        assertEquals("application/json", extracted.get("content-type"));
        assertEquals("custom-value", extracted.get("x-custom-header"));
    }

    @Test
    public void testOpenSearchResponseExceptionWithHeaders() {
        Map<String, String> headers = Map.of("x-request-id", "test123");
        ErrorResponse errorResponse = ErrorResponse.of(
            err -> err.status(404).error(ErrorCause.of(cause -> cause.type("not_found").reason("Document not found")))
        );

        OpenSearchResponseException exception = new OpenSearchResponseException(errorResponse, headers);

        assertEquals("test123", exception.getHeader("x-request-id"));
        assertEquals(headers, exception.getResponseHeaders());
    }
}
