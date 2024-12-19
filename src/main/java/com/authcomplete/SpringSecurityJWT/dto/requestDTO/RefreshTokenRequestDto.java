package com.authcomplete.SpringSecurityJWT.dto.requestDTO;

import lombok.*;


@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenRequestDto {
    private String refreshToken;
}
