package com.muhammet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Rest istekerlinin tümünde yetki kontrolünün aktif ewdilmesi için aşağıdaki
 * anotasyonun eklenmesi gereklidir.
 * @EnableGlobalMethodSecurity(prePostEnabled = true)
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class UserServiceSecurityConfig {

    @Bean
    JwtTokenFilter getJwtTokenFilter() {
        return new JwtTokenFilter();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        /**
         * Bu kısımda gelen işsteklerin fitrelenmesi ve belli adreslere gelen isteklerin,
         * hangilerinin public olacağını hangilerinin de kısılanacağını belirliyoruz.
         * 1. gelen istek -> HttpSecurity
         * 2. kısıtlanacak adreslerin listesi -> antMatchers
         * 2.1. "/api/v1/admin/login" -> admin giriş sayfasını tanımlanık
         * 2.2. "/api/v1/user/**" -> kullanıcı isteklerinin tamamını tanımladık.
         * 3. izin verme işlemi -> permitAll()
         * 4. tüm istekleri belirtmek -> anyRequest()
         * 5. authenticated -> gelen isteklerin doğrulanması
         *
         */
        http.authorizeRequests()
                .antMatchers("/api/v1/userprofile/getpage","/login.html","/api/v1/userroles/**"
                ,"/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html")

                .permitAll()
                .anyRequest().authenticated();
        /**
         * Eğer oturum açılmamış ve yetki yok ise login page yönlendirilmesini yapmak gereklidir.
         */
        // http.formLogin();
        /**
         * Form sayfası özelleştirilebilir, kendi form sayfamızı kullanmak istiyorsak
         */
        // http.formLogin().loginPage("/login.html");

        /**
         * Burada gelen isteğin filtre mekanizmasına uğramadan önce JWT ile kontrol edilmesi ve
         * yetkili bir kullanıcı ise kendisine özel bir kimlik kartı tanımlanmasını sağlıyoruz.
         * 1. İlk yapmamız gereken şey, filtreleme işleminden önce yapılması planlanan kodlamanın
         * ne olduğunu bildiriyor ve yönlendiriyoruz.
         * 1.1. ilk kısım, filtreleme işlemini yaparak jwt kontorlünü sağlayacak olan sınıfı tanımlıyoruz.
         * 1.2 filtreleme işleminde kontrol edilecek sınıf bileşenini veriyoruz.
         */
        http.addFilterBefore(getJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
