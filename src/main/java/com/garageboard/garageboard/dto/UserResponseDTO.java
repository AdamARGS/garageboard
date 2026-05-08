package com.garageboard.garageboard.dto;

import lombok.Getter;
import lombok.Setter;

import com.garageboard.garageboard.entities.User;

import lombok.AllArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
public class UserResponseDTO {
    private long id;
    private String username;
    private String email;

    public UserResponseDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
    }
}
