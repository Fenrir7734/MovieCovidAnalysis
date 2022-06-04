package com.fenrir.masterdetail.dto.mapper;

import com.fenrir.masterdetail.dto.JwtTokenDTO;
import com.fenrir.masterdetail.dto.RegisterRequest;
import com.fenrir.masterdetail.dto.UserDTO;
import com.fenrir.masterdetail.model.Role;
import com.fenrir.masterdetail.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User fromRegisterRequest(RegisterRequest register) {
        return new User(
                null,
                register.getUsername(),
                register.getEmail(),
                register.getPassword(),
                Role.ROLE_USER
        );
    }

    public UserDTO toUserDTO(User user) {
        return new UserDTO(
                user.getUsername(),
                user.getEmail()
        );
    }

    public JwtTokenDTO toJwtTokenDTO(String token) {
        return new JwtTokenDTO(token);
    }
}
