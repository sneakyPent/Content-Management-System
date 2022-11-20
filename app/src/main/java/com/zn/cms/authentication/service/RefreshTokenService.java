package com.zn.cms.authentication.service;

import com.zn.cms.authentication.dto.RefreshTokenResponse;
import com.zn.cms.authentication.model.RefreshToken;
import com.zn.cms.authentication.repository.RefreshTokenRepository;
import com.zn.cms.security.jwt.JwtTokenUtil;
import com.zn.cms.user.model.User;
import com.zn.cms.user.repository.UserRepository;
import com.zn.cms.user.service.CmsUserDetailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenUtil jwtTokenUtil;
    private final CmsUserDetailServiceImpl cmsUserDetailServiceImpl;

    private final UserRepository userRepository;

    @Value("${application.security.jwt.refreshExpirationDateInMs}")
    public long refreshTokenDurationMs;

    public Optional<RefreshToken>  findByToken(String token){
        return refreshTokenRepository.findByToken(token);
    }

    public Optional<RefreshToken> generateRefreshTokenFromUsername(String userName) {
        Optional<User> userOpt = userRepository.findByUsername(userName);
        return userOpt.map(user -> refreshTokenRepository.save(
                RefreshToken.builder()
                        .user(user)
                        .token(UUID.randomUUID().toString())
                        .expiryDate(Instant.now().plusMillis(refreshTokenDurationMs))
                        .build()));
    }

    public Optional<RefreshTokenResponse> getRefreshToken(String requestRefreshToken){

        Optional<User> userOpt = findByToken(requestRefreshToken)
                .map(this::verifyExpiration)
                .map(RefreshToken::getUser);
        if (userOpt.isPresent()){
            User user = userOpt.get();
            UserDetails userDetails = cmsUserDetailServiceImpl.loadUserByUsername(user.getUsername());
            String token = jwtTokenUtil.generateToken(userDetails);
            return Optional.of(new RefreshTokenResponse(token, requestRefreshToken));
        }
        return Optional.empty();
    }


    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            return null;
        }
        return token;
    }
//
//    public int deleteByUserId(Long userId) {
//        return refreshTokenRepository.deleteByUser(userRepository.findById(userId).get());
//    }

}
