package com.kodigo.shopping.online.store.security.utils;

import javax.servlet.http.HttpServletRequest;

public class HttpRequestUtil {

    private static final String AUTHENTICATION_HEADER = "Authorization";

    private HttpRequestUtil() {
    }

    public static String getAuthenticationToken(HttpServletRequest request) {
        String authorization = request.getHeader(AUTHENTICATION_HEADER);
        if (authorization.contains("Bearer")) {
            return authorization.substring(7);
        }
        throw new InvalidBearerTokenFormatException("Token format not valid.");
    }

    public static boolean requestNotContainsAuthentication(HttpServletRequest request) {
        return request.getHeader(AUTHENTICATION_HEADER) == null;
    }
}
