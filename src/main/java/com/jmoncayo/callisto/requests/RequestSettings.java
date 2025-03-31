package com.jmoncayo.callisto.requests;

import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
public class RequestSettings {
    private final boolean enableSslCert;
    private final boolean automaticallyFollowRedirects;
    private final boolean followOriginalHttpMethod;
    private final boolean followAuthorizationHeader;
    private final boolean removeRefererHeaderOnRedirect;
    private final boolean enableStrictHttpParser;
    private final boolean encodeUrlAutomatically;
    private final boolean disableCookieJar;
    private final boolean userCipherSuiteDuringHandshake;
}
