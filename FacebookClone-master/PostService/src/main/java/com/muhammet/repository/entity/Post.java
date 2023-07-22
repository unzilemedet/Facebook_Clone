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
@Document(collection = "post")
public class Post {
    @Id
    String id;
    String userid;
    Long paylasimzamani;
    /**
     * Integer - int arasında ne fark olur?
     * int -> bu değer db de olmasa bile default olarak 0 gelecektir.
     */
    int begenisayisi;
    String aciklama;
    int yorumsayisi;
}
