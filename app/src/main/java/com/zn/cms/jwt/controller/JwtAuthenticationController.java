package com.zn.cms.jwt.controller;


import com.zn.cms.security.jwt.JwtTokenUtil;
import com.zn.cms.jwt.models.JwtRequest;
import com.zn.cms.jwt.models.JwtResponse;
import com.zn.cms.user.service.CmsUserDetailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import static com.zn.cms.utils.Router.AUTHENTICATE;

@RequiredArgsConstructor
@RestController
@RequestMapping(AUTHENTICATE)
public class JwtAuthenticationController {

	private final AuthenticationManager authenticationManager;

	private final CmsUserDetailServiceImpl cmsUserDetailServiceImpl;

	private final JwtTokenUtil jwtTokenUtil;


	@PostMapping("/signin")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		final UserDetails userDetails = cmsUserDetailServiceImpl
				.loadUserByUsername(authenticationRequest.getUsername());

		final String token = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new JwtResponse(token,
				userDetails.getUsername()));
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}