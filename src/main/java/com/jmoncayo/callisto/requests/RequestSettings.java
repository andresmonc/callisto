package com.jmoncayo.callisto.requests;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
@Getter
public class RequestSettings {
	@Builder.Default
	private final boolean enableSslCert = false;

	@Builder.Default
	private final boolean automaticallyFollowRedirects = true;

	@Builder.Default
	private final boolean followOriginalHttpMethod = true;

	@Builder.Default
	private final boolean followAuthorizationHeader = true;

	@Builder.Default
	private final boolean removeRefererHeaderOnRedirect = true;

	@Builder.Default
	private final boolean enableStrictHttpParser = false;

	@Builder.Default
	private final boolean encodeUrlAutomatically = true;

	@Builder.Default
	private final boolean disableCookieJar = false;

	@Builder.Default
	private final boolean userCipherSuiteDuringHandshake = true;
}
