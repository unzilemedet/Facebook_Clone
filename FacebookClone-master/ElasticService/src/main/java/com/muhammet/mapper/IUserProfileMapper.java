package com.muhammet.mapper;

import com.muhammet.dto.request.UserProfileDto;
import com.muhammet.repository.entity.UserProfile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,componentModel = "spring")
public interface IUserProfileMapper {
    IUserProfileMapper INSTANCE = Mappers.getMapper(IUserProfileMapper.class);

    @Mapping(target= "userid",source= "id")
    UserProfile toUserProfile(final UserProfileDto dto);
}
