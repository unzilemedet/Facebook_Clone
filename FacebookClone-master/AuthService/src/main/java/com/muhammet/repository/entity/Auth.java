package com.muhammet.repository.entity;

import lombok.*;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "tblauth")
public class Auth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String username;
    String password;
    String email;
    /**
     * Kullanıcının kayıt edilme tarihini tutan değer. Long olarak tutulur.
     */
    Long createat;
    /**
     * 0- Kullanıcı kayıt edilmiş ama onaylanmamış
     * 1- Kullanıcı kayıt edilmiş ve onaylanmış
     * 2- Kullanıcı kayıt edilmiş ve onaylanmış fakat hesabı kilitlenmiş
     * 3- Kullanıcı kayıt edilmiş ve onaylanmış fakat hesabı silinmiş
     */
    int status;

//    /**
//     * Kullanıcının durumu yukarıdaki değişken ile tutulabileceği gibi
//     * bir enum ile de tutulabilir. aşağıda tanımlanan Enum içinde,
//     * Aktif, Pasif ve Silinmiş durumlarını tutabiliriz.
//     */
//    @Enumerated(EnumType.STRING)
//    State state;


}
