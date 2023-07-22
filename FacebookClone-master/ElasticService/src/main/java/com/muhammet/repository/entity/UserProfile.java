package com.muhammet.repository.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document(indexName="userprofile")
public class UserProfile {
    @Id
    String id;
    String userid;
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
