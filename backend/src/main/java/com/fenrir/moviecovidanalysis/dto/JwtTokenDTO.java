package com.fenrir.moviecovidanalysis.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JwtTokenDTO {
    private String accessToken;
    private String tokenType;

    public JwtTokenDTO(String accessToken) {
        this.accessToken = accessToken;
        this.tokenType = "Bearer";
    }
}
