package com.authcomplete.SpringSecurityJWT.dto.responseDTO;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponseDto {

    private String accessToken;
    private String refreshToken;
}
