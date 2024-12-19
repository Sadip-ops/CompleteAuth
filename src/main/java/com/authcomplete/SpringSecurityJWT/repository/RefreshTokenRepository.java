package com.authcomplete.SpringSecurityJWT.repository;


import com.authcomplete.SpringSecurityJWT.entity.RefreshToken;
import com.authcomplete.SpringSecurityJWT.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    Optional <RefreshToken> findByUserInfo(UserInfo byUsername);
}
