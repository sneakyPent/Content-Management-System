package com.zn.cms.authentication.service;


import com.zn.cms.authentication.dto.JwtResponse;
import com.zn.cms.authentication.model.RefreshToken;
import com.zn.cms.security.jwt.JwtTokenUtil;
import com.zn.cms.user.service.CmsUserDetailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class JwtService {

	private final AuthenticationManager authenticationManager;
	private final CmsUserDetailServiceImpl cmsUserDetailServiceImpl;
	private final RefreshTokenService refreshTokenService;
	private final JwtTokenUtil jwtTokenUtil;


	public Optional<JwtResponse> createJwt(String username, String password) throws Exception {
		authenticate(username, password);

		UserDetails userDetails = cmsUserDetailServiceImpl
				.loadUserByUsername(username);

		String token = jwtTokenUtil.generateToken(userDetails);

		Optional<RefreshToken> refreshTokenOptional = refreshTokenService.generateRefreshTokenFromUsername(username);
		if(refreshTokenOptional.isPresent()){
			RefreshToken refreshToken = refreshTokenOptional.get();
			return Optional.of(new JwtResponse(token, refreshToken.getToken(),userDetails.getUsername()));
		}
		return Optional.empty();
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
