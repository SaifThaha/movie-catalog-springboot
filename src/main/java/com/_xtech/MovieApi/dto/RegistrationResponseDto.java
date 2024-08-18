package com._xtech.MovieApi.dto;

import com._xtech.MovieApi.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationResponseDto {
    public Long userId;
    private String userName;
    private String email;
    private Role role;
}
