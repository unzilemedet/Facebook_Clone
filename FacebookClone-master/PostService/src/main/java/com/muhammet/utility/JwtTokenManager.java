package com.muhammet.utility;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class JwtTokenManager {
    /**
     * JWT token üretiminde kullanılacak olan kriptolama algoritmalarının
     * kullanacağın anahtar bilgisini burada veriyoruz.
     * !!!!!! DİKKAT !!!!!!!
     * Bu anahtar bilgisinin açık bir şekilde kod içinde var oolması bir güvenlik açığıdır. Anahtarın
     * kod içinden alınarak ENV şeklinde başka bir repo üzerinden erişilebilir olması daha güvenli olacaktır.
     */

    private final String sifreAnahtari = "wr@M=dU4rur8splp0LvuPR_bEThutusT&q9ni3restaNlH-kOgab&wIFRobraspi"; // 80*80*80 -> 80^64

    private Long exDate = 1000L * 60 * 2; // 2 dakika
    /**
     * JWT üretimi yapılırken belli bilgileriln payload içine eklenmesi gereklidir.
     * yani token verdiğimiz kullanıcıya ait kimlik bilgilerini doğrulayaviliecek
     * bilgiler olabilir. Ama dikkat etmeliyiz ki burada bulunan bilgiler açık
     * bir şekilde iletilmektedir. bu nedenle Claim nesnelerinin içine eklenecek
     * bilgilerin özel gizli bilgiler olamamasına dikkat etmeliyiz.
     * Erişim kısıtları ve sayfa geçişleri için belli parametrelerin token
     * içerisinde olması ve eşleştirilmesi gereklidir.
     * @param id
     * @return
     */
    public Optional<String> createToken(Long id){
        String token;
        try{
            token = JWT.create().withAudience()
                    .withClaim("id", id) // Payload dataları
                    .withClaim("howtopage","AuthMicroService") // Payload dataları
                    .withClaim("isOne", true) // Payload dataları
                    .withIssuer("Java7") // token üreten uygulama
                    .withIssuedAt(new Date()) // token oluşturulma tarihi
                    .withExpiresAt(new Date(System.currentTimeMillis() + exDate)) // token in sona erme zamanı
                    .sign(Algorithm.HMAC512(sifreAnahtari)); // token imzalama algoritması
            return Optional.of(token);
        }catch (Exception e){
            return Optional.empty();
        }
    }

    /**
     * İlk olarak bir JWT token bilgisinin bize ait olup olmadığının, kullanım süresinin
     * dolup dolmadığının kontrolünün yapılması gereklidir.
     * Token içinde önceden bizim tarafımızdan eklenen bilgilerin token Claim nesnleri
     * içinden alınmadsı gereklidir.
     * @param token
     * @return
     */
    public Optional<Long> getIdFromToken(String token){
        try{
            Algorithm algorithm = Algorithm.HMAC512(sifreAnahtari);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("Java7")
                    .build();
            DecodedJWT decodedJWT = verifier.verify(token);
            if(decodedJWT==null) return Optional.empty();
            return Optional.of(decodedJWT.getClaim("id").asLong());
        }catch (Exception e){
            return Optional.empty();
        }
    }
}
