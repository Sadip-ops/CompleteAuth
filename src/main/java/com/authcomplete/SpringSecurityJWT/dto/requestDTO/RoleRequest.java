package com.authcomplete.SpringSecurityJWT.dto.requestDTO;


import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleRequest {
    private String name;
}
