package com.muhammet.mapper;

import com.muhammet.dto.request.UserProfileSaveRequestDto;
import com.muhammet.dto.request.UserProfileUpdateRequestDto;
import com.muhammet.rabbitmq.model.CreateUserModel;
import com.muhammet.repository.entity.UserProfile;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,componentModel = "spring")
public interface IUserProfileMapper {
    IUserProfileMapper INSTANCE = Mappers.getMapper(IUserProfileMapper.class);

    UserProfile toUserProfile(final UserProfileSaveRequestDto dto);

    UserProfile toUserProfile( final UserProfileUpdateRequestDto dto);

    UserProfile toUserProfile(final CreateUserModel model);
}
