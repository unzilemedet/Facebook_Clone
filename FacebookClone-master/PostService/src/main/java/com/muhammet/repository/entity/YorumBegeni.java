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
@Document(collection = "yorumbegeni")
public class YorumBegeni {
    @Id
    String id;
    String yorumid;
    String userid;
    Long begenizamani;
}
