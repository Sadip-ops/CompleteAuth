package com.authcomplete.SpringSecurityJWT.dto.requestDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequestDto {

    @NotBlank(message = "Username cannot be Blank.")
    private String username;
    @NotBlank(message ="Password cannot be Blank." )

    private String password;
}
