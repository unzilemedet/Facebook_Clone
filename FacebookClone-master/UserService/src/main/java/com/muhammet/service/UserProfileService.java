package com.muhammet.service;

import com.muhammet.dto.request.GetMyProfileRequestDto;
import com.muhammet.dto.request.UserProfileSaveRequestDto;
import com.muhammet.dto.request.UserProfileUpdateRequestDto;
import com.muhammet.dto.response.GetMyProfileResponseDto;
import com.muhammet.exception.ErrorType;
import com.muhammet.exception.UserException;
import com.muhammet.mapper.IUserProfileMapper;
import com.muhammet.rabbitmq.model.CreateUserModel;
import com.muhammet.repository.IUserProfileRepository;
import com.muhammet.repository.entity.UserProfile;
import com.muhammet.utility.JwtTokenManager;
import com.muhammet.utility.ServiceManager;
import com.muhammet.utility.TokenCreator;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserProfileService extends ServiceManager<UserProfile,String> {
    private final IUserProfileRepository repository;
    private final JwtTokenManager jwtTokenManager;
    public UserProfileService(IUserProfileRepository repository,
                              JwtTokenManager jwtTokenManager) {
        super(repository);
        this.repository=repository;
        this.jwtTokenManager=jwtTokenManager;
    }

    public void save(UserProfileSaveRequestDto dto){
       save(IUserProfileMapper.INSTANCE.toUserProfile(dto));
    }

    public void save(CreateUserModel model){
        save(IUserProfileMapper.INSTANCE.toUserProfile(model));
    }

    public void update(UserProfileUpdateRequestDto dto){
        Optional<Long> authid = jwtTokenManager.getIdFromToken(dto.getToken());
        if(authid.isEmpty())
            throw new UserException(ErrorType.ERROR_INVALID_TOKEN);
        Optional<UserProfile> userProfile = repository.findOptionalByAuthid(authid.get());
        if(userProfile.isPresent()){
            UserProfile profile = userProfile.get();
            profile.setAddress(dto.getAddress());
            profile.setAvatar(dto.getAvatar());
            profile.setGender(dto.getGender());
            profile.setName(dto.getName());
            profile.setPhone(dto.getPhone());
            profile.setSurname(dto.getSurname());
            update(profile);
        }
    }

    @Cacheable(value = "getnametoupper")
    public String getNameToUpper(String name){
        try{
            Thread.sleep(3000);
        }catch (Exception ex){

        }
        return name.toUpperCase();
    }

    @CacheEvict(value = "getnametoupper",allEntries = true)
    public void clearCacheToUpper(){
        System.out.println("Tüm cache i temizledim");
    }

    public GetMyProfileResponseDto getMyProfile(GetMyProfileRequestDto dto) {
        Optional<Long> authid = jwtTokenManager.getIdFromToken(dto.getToken());
        if(authid.isEmpty())
            throw new UserException(ErrorType.ERROR_INVALID_TOKEN);
        Optional<UserProfile> userProfile = repository.findOptionalByAuthid(authid.get());
        if(userProfile.isEmpty())
            throw new UserException(ErrorType.ERROR_NOT_FOUND_USERNAME);
        return GetMyProfileResponseDto.builder()
                .about(userProfile.get().getPhone())
                .avatar(userProfile.get().getAvatar())
                .name(userProfile.get().getName()+" "+userProfile.get().getSurname())
                .username(userProfile.get().getUsername())
                .build();
    }

    public UserProfile getOtherProfile(GetMyProfileRequestDto dto) {
        Optional<UserProfile> userProfile = findById(dto.getUserid());
        if(userProfile.isEmpty())
            throw new UserException(ErrorType.ERROR_NOT_FOUND_USERNAME);
        return userProfile.get();
    }

    public Optional<UserProfile> findByAuthid(Long authid){
        return repository.findOptionalByAuthid(authid);
    }
}
