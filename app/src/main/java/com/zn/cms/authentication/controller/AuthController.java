package com.zn.cms.authentication.controller;

import com.zn.cms.authentication.dto.JwtRequest;
import com.zn.cms.authentication.dto.JwtResponse;
import com.zn.cms.authentication.dto.RefreshTokenRequest;
import com.zn.cms.authentication.dto.RefreshTokenResponse;
import com.zn.cms.authentication.service.JwtService;
import com.zn.cms.authentication.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.zn.cms.utils.Router.AUTHENTICATE;

@RequiredArgsConstructor
@RestController
@RequestMapping(AUTHENTICATE)
public class AuthController {

	private final JwtService jwtService;
	private final RefreshTokenService refreshTokenService;

	@PostMapping("/signin")
	public ResponseEntity<JwtResponse> getAuthenticationToken(@Valid @RequestBody JwtRequest jwtRequest) throws Exception {

		return jwtService.createJwt(jwtRequest.getUsername(), jwtRequest.getPassword()).map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.badRequest().build());
	}

	@PostMapping("/refreshtoken")
	public ResponseEntity<?> updateGivenToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
		return refreshTokenService.getRefreshToken(refreshTokenRequest.getRefreshToken()).map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.badRequest().build());
	}

}
