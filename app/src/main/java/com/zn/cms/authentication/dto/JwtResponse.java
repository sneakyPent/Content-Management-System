package com.zn.cms.authentication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class JwtResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;
	private final String type = "Bearer";
	private final String token;
	private final String refreshToken;
	private final String username;

}