package com.example.mobile_store.mapper;

import com.example.mobile_store.dto.RegisterDTO;
import com.example.mobile_store.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface UserMapper {
    @Mapping(target = "role", source = "role.id")
        // call role.getID()
    RegisterDTO toDTO(User user);

    @Mapping(target = "role.id", source = "role")
        // call role.setID()
    User toEntity(RegisterDTO registerDTO);
}