package com.warehouse.service;

import com.warehouse.dto.UserResponseDto;
import com.warehouse.exception.UserNotFoundException;
import com.warehouse.mapper.UserResponseMapper;
import com.warehouse.model.User;
import com.warehouse.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserResponseMapper userResponseMapper;

    public UserService(UserRepository userRepository, UserResponseMapper userResponseMapper) {
        this.userRepository = userRepository;
        this.userResponseMapper = userResponseMapper;
    }

    public List<UserResponseDto> findAll(){
        return userRepository.findAll()
                .stream()
                .map(userResponseMapper::toDto)
                .collect(Collectors.toList());
    }

    public UserResponseDto saveUser(User user) {
        return userResponseMapper.toDto(userRepository.save(user));
    }

    public void delete(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        userRepository.delete(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public UserResponseDto findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        return userResponseMapper.toDto(user);
    }

}
