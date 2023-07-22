package com.muhammet.service;

import com.muhammet.dto.request.GetMyProfileRequestDto;
import com.muhammet.dto.request.UserProfileSaveRequestDto;
import com.muhammet.dto.response.GetMyProfileResponseDto;
import com.muhammet.exception.ErrorType;
import com.muhammet.repository.entity.UserProfile;
import com.muhammet.utility.JwtTokenManager;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

/**
 * Standart testlere nazaran spring test yaparken bazı anotasyonları kullanmak
 * zorundasınız.
 * @SpringBootTest
 * @ActiveProfile("test")
 */
@SpringBootTest
public class UserProfileServiceTest {
    /**
     * Test taparken dikkat edilecekler,
     * 1- her bir sınıf için ayrı bir test sınıfı oluşturmalısınız.
     * 2- tüm merhodları test etmelisiniz.
     * 2.1. ÖNEMLİ, bir method un tüm ihtimalleri için ayrı ayrı test case leri yazmalısınız.
     * 3- test edilecek sınıfların ve methodların dış bileşenlerden ayrılarak soyutlanmış testlerinin yapılması
     * önemlidir. böylece sadece yazdığınız kodun testini yaparsınız.
     *
     */
    @Autowired
    UserProfileService userProfileService;
    @Autowired
    JwtTokenManager jwtTokenManager;

    @Test
    void testDeneme(){
        userProfileService.findAll().forEach(System.out::println);
    }

    @Test
    void saveTest(){
        userProfileService.save(UserProfileSaveRequestDto.builder()
                        .authid(4L)
                        .email("bahar@gmail.com")
                        .username("bahar.tekin")
                .build());
        Optional<UserProfile> user = userProfileService.findAll().stream().filter(x-> x.getUsername().equals("bahar.tekin")).findFirst();
        Assertions.assertTrue(user.isPresent());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/userProfile.csv")
    void saveCSVTest(Long authid, String email, String username){
        UserProfile userProfile = UserProfile.builder()
                .email(email)
                .username(username)
                .authid(authid)
                .build();
        UserProfile userProfileSave = userProfileService.save(userProfile);
        Assertions.assertTrue(userProfileSave.getId() !=null);
    }

    @Test
    void findByIdTest(){
        Optional<UserProfile> user = userProfileService.findByAuthid(150L);
        if(user.isPresent()){
            System.out.println(user.toString());
        }
        Assertions.assertTrue(user.isPresent());
    }

    @Test
    void getMyProfileInvalidTokenTest(){
       Exception exception = Assertions.assertThrows(
               Exception.class, ()->  userProfileService.getMyProfile(GetMyProfileRequestDto.builder()
                       .token("3243243242424234")
                       .build())
       );
       Assertions.assertEquals(ErrorType.ERROR_INVALID_TOKEN.getMessage(),exception.getMessage());

    }

    @Test
    void getMyProfileInvalidUserTest(){
        Optional<String> token = jwtTokenManager.createToken(2000L);
        Exception exception = Assertions.assertThrows(
                Exception.class, ()->  userProfileService.getMyProfile(GetMyProfileRequestDto.builder()
                        .token(token.get())
                        .build())
        );
        Assertions.assertEquals(ErrorType.ERROR_NOT_FOUND_USERNAME.getMessage(),exception.getMessage());
    }

    @Test
    void getMyProfileTest(){
        Optional<String> token = jwtTokenManager.createToken(9L);
        GetMyProfileResponseDto responseDto = userProfileService.getMyProfile(GetMyProfileRequestDto.builder()
                .token(token.get())
                .build());
        Assertions.assertTrue(responseDto.getUsername() !=null);
    }

}
