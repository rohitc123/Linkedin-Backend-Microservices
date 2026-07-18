package com.example.linkedInApp.user_service.service;

import com.example.linkedInApp.user_service.dto.LoginRequestDto;
import com.example.linkedInApp.user_service.dto.SignUpRequestDto;
import com.example.linkedInApp.user_service.dto.UserDto;
import com.example.linkedInApp.user_service.entity.User;
import com.example.linkedInApp.user_service.exception.BadRequestException;
import com.example.linkedInApp.user_service.exception.ResourceNotFoundException;
import com.example.linkedInApp.user_service.repository.UserRepository;
import com.example.linkedInApp.user_service.utils.PasswordUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final JwtService jwtService;

    public UserDto signUp(SignUpRequestDto signUpRequestDto) {
        User user=modelMapper.map(signUpRequestDto,User.class);

        user.setPassword(PasswordUtils.hashPassword(signUpRequestDto.getPassword()));

        User savedUser=userRepository.save(user);
        return modelMapper.map(savedUser, UserDto.class);
    }

    public String login(LoginRequestDto loginRequestDto) {
        User user= (User) userRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(()->new ResourceNotFoundException("user not found of this email: "+loginRequestDto.getEmail()));

        boolean isPasswordMatch=PasswordUtils.checkPassword(loginRequestDto.getPassword(),user.getPassword());

        if(!isPasswordMatch){
            throw new BadRequestException("Incorrect password");
        }

        return jwtService.generateAccessToken(user);
    }
}
