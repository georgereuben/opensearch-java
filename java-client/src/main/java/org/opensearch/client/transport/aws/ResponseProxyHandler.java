/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * The OpenSearch Contributors require contributions made to
 * this file be licensed under the Apache-2.0 license or a
 * compatible open source license.
 */

package org.opensearch.client.transport.aws;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Map;

public class ResponseProxyHandler implements InvocationHandler {
    private final Object originalResponse;
    private final Map<String, String> responseHeaders;

    public ResponseProxyHandler(Object originalResponse, Map<String, String> responseHeaders) {
        this.originalResponse = originalResponse;
        this.responseHeaders = Collections.unmodifiableMap(responseHeaders);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if ("getResponseHeaders".equals(method.getName()) && method.getParameterCount() == 0) {
            return responseHeaders;
        }

        // sending all other calls to the original response object
        return method.invoke(originalResponse, args);
    }
}
