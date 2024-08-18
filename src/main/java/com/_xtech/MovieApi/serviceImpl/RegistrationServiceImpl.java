package com._xtech.MovieApi.serviceImpl;

import com._xtech.MovieApi.Repository.UserRepository;
import com._xtech.MovieApi.dto.RegistrationRequestDto;
import com._xtech.MovieApi.dto.RegistrationResponseDto;
import com._xtech.MovieApi.entity.User;
import com._xtech.MovieApi.enums.Role;
import com._xtech.MovieApi.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public RegistrationResponseDto registerUser(RegistrationRequestDto registrationRequestDto) {
        //map the userRegistrationDto to the User entity class
        User user = modelMapper.map(registrationRequestDto,User.class);

        //encode the password
        String encodedPassword = passwordEncoder.encode(registrationRequestDto.getPassword());

        //set the encoded password
        user.setPassword(encodedPassword);

        //set the role
        user.setRole(Role.USER);

        //save the user to the database
        User registeredUser = userRepository.save(user);

        //map the user entity class to the response dto
        return modelMapper.map(registeredUser,RegistrationResponseDto.class);
    }
}
