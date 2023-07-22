package com.muhammet.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum ErrorType {

    ERROR_PASSWORD(2004,"Girmiş olduğunuz şifreler uyuşmamaktadır", HttpStatus.BAD_REQUEST),
    ERROR_USERNAME(2005,"Bu kullanıcı adı daha önce kayıt edilmiştir. Lütfen farklı bir kullanıcı adı giriniz.", HttpStatus.BAD_REQUEST),
    ERROR_NOT_FOUND_USERNAME(2006,"Böyle bir kullanıcı bulunmamaktadır.", HttpStatus.INTERNAL_SERVER_ERROR),
    BAD_REQUEST(4000,"Geçersiz istek ya da parametre", HttpStatus.BAD_REQUEST),
    ERROR_INVALID_TOKEN(3000,"Geçersiz token bilgisi", HttpStatus.UNAUTHORIZED),
    ERROR_ACCESS_DENIED(3001,"Yetkisiz Erişim. Lütfen geçerli bir kullanıcı ile tekrar deneyin.", HttpStatus.UNAUTHORIZED),
    ERROR(9000, "Beklenmeyen bir hata oluştur. Lütfen tekrar deneyiniz.", HttpStatus.INTERNAL_SERVER_ERROR)  ;

    int code;
    String message;
    HttpStatus httpStatus;

}
