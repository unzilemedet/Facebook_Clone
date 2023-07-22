package com.muhammet.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class GetMyProfileResponseDto {
    String avatar;
    String name;
    String username;
    String about;
}
