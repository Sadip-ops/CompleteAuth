package com.authcomplete.SpringSecurityJWT.dto.requestDTO;


import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {

    private String username;
    private String password;
    private List<String> roles;


}