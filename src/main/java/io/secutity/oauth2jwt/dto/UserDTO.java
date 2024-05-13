package io.secutity.oauth2jwt.dto;


import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Setter
public class UserDTO {


    private String role;
    private String name;
    private String username;
}
