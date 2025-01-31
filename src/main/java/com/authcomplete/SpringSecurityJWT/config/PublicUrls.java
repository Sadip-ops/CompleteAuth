package com.authcomplete.SpringSecurityJWT.config;

public class PublicUrls {
    public static final String[] PUBLIC_URLS = {
            /*  "/v3/",
              "/authorize/swagger-ui/",
              "/v3/api-docs/",*/
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/swagger-resources",
            "/api/v1/user/login",
            "/api/v1/user/refresh-token"
//            ,"/api/v1/user/register",
//            "/api/v1/user/role/add"
    };
}
