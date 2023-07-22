package com.muhammet.manager;

import com.muhammet.dto.request.UserProfileSaveRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@FeignClient(
        name = "user-profile-service",
        url = "http://localhost:9093/userprofile"
)
public interface IUserProfileManager {
    @PostMapping("/save")
    ResponseEntity<Void> save(@RequestBody @Valid UserProfileSaveRequestDto dto);
}
