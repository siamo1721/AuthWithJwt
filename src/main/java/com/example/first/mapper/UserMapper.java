package com.example.first.mapper;

import com.example.first.dto.register.response.RegisterDtoResponse;
import com.example.first.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    RegisterDtoResponse toDto(User user,String accessToken, String refreshToken);
}
