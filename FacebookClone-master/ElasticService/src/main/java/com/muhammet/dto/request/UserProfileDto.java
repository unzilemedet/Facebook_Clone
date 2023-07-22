package com.muhammet.dto.request;

import com.muhammet.repository.entity.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserProfileDto {
    String id;
    Long authid;
    String username;
    String email;
    String name;
    String surname;
    String phone;
    String address;
    String avatar;
    Gender gender;
}
