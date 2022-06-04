package com.fenrir.moviecovidanalysis.dto.mapper;

import com.fenrir.moviecovidanalysis.dto.JwtTokenDTO;
import com.fenrir.moviecovidanalysis.dto.RegisterRequest;
import com.fenrir.moviecovidanalysis.dto.UserDTO;
import com.fenrir.moviecovidanalysis.model.Role;
import com.fenrir.moviecovidanalysis.model.User;
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
