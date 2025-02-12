package com.warehouse.dto;

import com.warehouse.model.Role;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public class UserRequestDto {

    private Long id;

    @NotNull(message = "Username must not be null")
    @Size(min = 3, message = "Username must be at least 3")
    private String username;

    @NotNull(message = "Password must not be null")
    @Size(min = 6, message = "Password must be at least 8")
    private String password;

    private List<String> roles;

    public UserRequestDto(Long id, String username, String password, List<String> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getRoles() {
        return roles;
    }
    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
