package com._xtech.MovieApi.entity;

import com._xtech.MovieApi.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @NotBlank(message = "first name is mandatory")
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @NotBlank(message = "last name is mandatory")
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @NotBlank(message = "username is mandatory")
    @Column(name = "username", nullable = false, unique = true)
    private String userName;
    @Email(message = "Enter a valid email")
    @NotBlank(message = "email is mandatory")
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @NotBlank(message = "password is mandatory")
    @Column(name = "password", nullable = false)
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$",
            message = "Password must contain at least one digit, one lowercase, one uppercase letter, and one special character")
    private String password;
    @Enumerated(value = EnumType.STRING)
    private Role role;
    @OneToMany(mappedBy = "user")
    private RefreshToken refreshToken;

}
