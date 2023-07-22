package com.muhammet.controller;

import com.muhammet.repository.entity.UserRoles;
import com.muhammet.service.UserRolesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/userroles")
public class UserRolesController {

    private final UserRolesService userRolesService;

    @GetMapping("/save")
    public ResponseEntity<Void> save(String userprofileid,String role){
        UserRoles userRoles = UserRoles.builder()
                .role(role)
                .userprofileid(userprofileid)
                .build();
        userRolesService.save(userRoles);
        return ResponseEntity.ok().build();
    }
}
