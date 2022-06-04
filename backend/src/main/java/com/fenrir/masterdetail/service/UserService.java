package com.fenrir.masterdetail.service;

import com.fenrir.masterdetail.dto.JwtTokenDTO;
import com.fenrir.masterdetail.dto.LoginRequest;
import com.fenrir.masterdetail.dto.RegisterRequest;
import com.fenrir.masterdetail.dto.UserDTO;
import com.fenrir.masterdetail.dto.mapper.UserMapper;
import com.fenrir.masterdetail.exception.DuplicateCredentialsException;
import com.fenrir.masterdetail.model.User;
import com.fenrir.masterdetail.repository.UserRepository;
import com.fenrir.masterdetail.security.UserDetailsImpl;
import com.fenrir.masterdetail.security.jwt.JwtUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserService {
    private UserRepository userRepository;
    private UserMapper userMapper;
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;
    private JwtUtils jwtUtils;

    public UserDTO registerUser(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateCredentialsException("Account with this email address already exists.");
        }
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new DuplicateCredentialsException("Account with this username already exists.");
        }

        User user = userMapper.fromRegisterRequest(request);
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user = userRepository.save(user);
        return userMapper.toUserDTO(user);
    }

    public JwtTokenDTO authenticateUser(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        String token = jwtUtils.generateJwtToken(principal);
        return userMapper.toJwtTokenDTO(token);
    }

    public boolean validateToken(JwtTokenDTO tokenDTO) {
        return jwtUtils.validateToken(tokenDTO.getAccessToken());
    }
}
