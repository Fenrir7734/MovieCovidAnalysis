package com.fenrir.moviecovidanalysis.controller;

import com.fenrir.moviecovidanalysis.dto.JwtTokenDTO;
import com.fenrir.moviecovidanalysis.dto.LoginRequest;
import com.fenrir.moviecovidanalysis.dto.RegisterRequest;
import com.fenrir.moviecovidanalysis.dto.UserDTO;
import com.fenrir.moviecovidanalysis.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@AllArgsConstructor
@RestController
@RequestMapping(
        path = "/api/auth",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class AuthController {
    private UserService userService;

    @PostMapping(path = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> register(@RequestBody RegisterRequest request, UriComponentsBuilder builder) {
        UserDTO userDTO = userService.registerUser(request);
        return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
    }

    @PostMapping(path = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        JwtTokenDTO token = userService.authenticateUser(request);
        return ResponseEntity.ok(token);
    }

    @PostMapping(value = "/valid", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> validateToken(@RequestBody JwtTokenDTO jwtTokenDTO) {
        return userService.validateToken(jwtTokenDTO)
                ? ResponseEntity.ok().build()
                : ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
