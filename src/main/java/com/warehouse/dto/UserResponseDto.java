package com.warehouse.dto;

import com.warehouse.model.Role;

import java.util.List;

public class UserResponseDto {
    private Long id;
    private String username;
    private List<Role> roles;

    public UserResponseDto(Long id, String username, List<Role> roles) {
        this.id = id;
        this.username = username;
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
    public List<Role> getRoles() {
        return roles;
    }
    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

}
