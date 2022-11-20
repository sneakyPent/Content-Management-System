package com.zn.cms.authentication.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class RefreshTokenResponse {

	private final String accessToken;
	private final String refreshToken;
	private final String tokenType = "Bearer";

}
