package com.muhammet.service;

import com.muhammet.dto.request.GetMyProfileRequestDto;
import com.muhammet.dto.response.GetMyProfileResponseDto;
import com.muhammet.repository.IUserProfileRepository;
import com.muhammet.repository.entity.UserProfile;
import com.muhammet.utility.JwtTokenManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserProfileUnitTest {

    @InjectMocks
    UserProfileService userProfileService;
    @Mock
    IUserProfileRepository userProfileRepository;
    @Mock
    JwtTokenManager jwtTokenManager;

    @Test
    void getMyProfileTest(){
        when(jwtTokenManager.getIdFromToken(any()))
                        .thenReturn(Optional.of(9l));
        when(userProfileRepository.findOptionalByAuthid(any()))
                .thenReturn(Optional.of(
                        UserProfile.builder()
                                .phone("0 555 666")
                                .avatar("")
                                .name("Muhammet")
                                .surname("HOCA")
                                .username("muhammet")
                                .build()
                ));
        GetMyProfileResponseDto responseDto =
                userProfileService
                        .getMyProfile(GetMyProfileRequestDto.builder()
                        .token("5634gdf456456456")
                .build());
        Assertions.assertTrue(responseDto != null);
    }

}
