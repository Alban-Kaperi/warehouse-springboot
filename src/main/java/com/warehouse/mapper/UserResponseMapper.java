package com.warehouse.mapper;

import com.warehouse.dto.UserResponseDto;
import com.warehouse.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserResponseMapper {
    UserResponseDto toDto(User user);
}

