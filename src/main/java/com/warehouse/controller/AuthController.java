package com.warehouse.controller;

import com.warehouse.dto.AuthResponseDto;
import com.warehouse.dto.LoginRequestDto;
import com.warehouse.enums.RoleEnum;
import com.warehouse.model.Role;
import com.warehouse.model.User;
import com.warehouse.repository.RoleRepository;
import com.warehouse.repository.UserRepository;
import com.warehouse.security.JWTService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private JWTService jwtService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JWTService jwtService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @PostMapping("login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginRequestDto loginDto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(),
                        loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtService.generateToken(authentication);
        return new ResponseEntity<>(new AuthResponseDto(token), HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        /*
         * To make it work fully we need to store out tokens in the database so that we can invalidate them properly
         * But this was not part of the scope of this project at least on what I understood.
         */
        String token = extractTokenFromRequest(request);

        if (token != null) {
            jwtService.invalidateToken(token);
            // Clear the security context
            SecurityContextHolder.clearContext();
            return ResponseEntity.ok("Logged out successfully");
        }

        return ResponseEntity.badRequest().body("No token found");
    }

    private String extractTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }


    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody LoginRequestDto loginDto) {
        if (userRepository.findByUsername(loginDto.getUsername()).isPresent()) {
            return new ResponseEntity<>("Username is already taken", HttpStatus.BAD_REQUEST);
        }

        var newUser = new User();
        newUser.setUsername(loginDto.getUsername());
        newUser.setPassword(passwordEncoder.encode(loginDto.getPassword()));


        Role clientRole = roleRepository.findByName(RoleEnum.CLIENT.name())
                .orElseThrow(() -> new RuntimeException("CLIENT role not found"));
        Set<Role> roles = new HashSet<>();
        roles.add(clientRole);
        newUser.setRoles(roles);

        userRepository.save(newUser);

        return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);

    }


}
