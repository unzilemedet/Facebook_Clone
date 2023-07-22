package com.muhammet.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Document(collection = "postresimpozisyon")
public class PostResimPozisyon {
    @Id
    String id;
    String postresimid;
    String userid;
    int x;
    int y;
}
