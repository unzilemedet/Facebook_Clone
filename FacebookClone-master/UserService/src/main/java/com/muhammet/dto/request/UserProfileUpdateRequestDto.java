package com.muhammet.dto.request;

import com.muhammet.repository.entity.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserProfileUpdateRequestDto {
    @NotEmpty
    @Size(min = 5)
    String token;
    String name;
    String surname;
    String phone;
    String address;
    String avatar;
    Gender gender;
}
